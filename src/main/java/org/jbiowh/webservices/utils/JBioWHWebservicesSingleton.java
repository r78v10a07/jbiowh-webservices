package org.jbiowh.webservices.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jbiowhcore.basic.JBioWHUserData;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This Class is the used to parse the config file in
 * /etc/jbiowh/jbiowh-webservices.xml and provides the EntityManagerFactory from
 * the JBioWHPersistence class in jbiowh-persistence
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Nov 7, 2013
 */
public class JBioWHWebservicesSingleton {

    private static JBioWHWebservicesSingleton singleton;
    private String server;

    private JBioWHWebservicesSingleton() {
        server = null;
    }

    /**
     * Return a JBioWHWebservicesSingleton instance
     *
     * @return a JBioWHWebservicesSingleton instance
     */
    public static synchronized JBioWHWebservicesSingleton getInstance() {
        if (singleton == null) {
            singleton = new JBioWHWebservicesSingleton();
        }
        return singleton;
    }

    /**
     * Return the EntityManagerFactory from the JBioWHPersistence class. If the
     * schema is not open it will parser the XML file
     * /etc/jbiowh/jbiowh-webservices.xml and create the connection with the
     * database
     *
     * @return the EntityManagerFactory from the JBioWHPersistence
     */
    public EntityManagerFactory getWHEntityManager() {
        if (JBioWHPersistence.getInstance().getWHEntityManager() == null
                || !JBioWHPersistence.getInstance().getWHEntityManager().isOpen()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File("/etc/jbiowh/jbiowh-webservices.xml"));
                NodeList nodes = document.getElementsByTagName("webservice");
                JBioWHPersistence.getInstance().openSchema(new JBioWHUserData(getElement("driver", (Element) nodes.item(0)),
                        getElement("url", (Element) nodes.item(0)) + getElement("database", (Element) nodes.item(0)),
                        getElement("dbuser", (Element) nodes.item(0)),
                        getElement("dbpassword", (Element) nodes.item(0)), true), false, true);
                server = getElement("server", (Element) nodes.item(0));
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(JBioWHWebservicesSingleton.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(JBioWHWebservicesSingleton.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JBioWHWebservicesSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().INFO);
        return JBioWHPersistence.getInstance().getWHEntityManager();
    }

    public String getServer() {
        if (server == null) {
            getWHEntityManager();
        }
        return server;
    }

    /**
     * Get the character data in the XML element name from node 0
     *
     * @return the character data in the XML element name
     */
    private String getElement(String name, Element element) throws NullPointerException {
        NodeList namenode = element.getElementsByTagName(name);
        if (namenode == null) {
            throw new NullPointerException("Element '" + name + "' is not on XML config file");
        }
        if (namenode.getLength() == 0) {
            throw new NullPointerException("Element '" + name + "' is not on XML config file");
        }

        Element line = (Element) namenode.item(0);

        Node child = line.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return null;
    }

}
