package org.ecos.logic;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLFirst {
    static Logger log = Logger.getLogger("logger");

    private XMLFirst() {}

    public static Document getDocument(String pathToXML) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(pathToXML);
        } catch (Exception e) {
            log.info("Error generating DOM tree + " + e.getMessage());
        }

        return document;
    }

    public static void parseDOM(Document document) {
        Node root = document.getFirstChild();
        log.log(Level.INFO,"The visualized node is {0}\n", root.getNodeName());

        traverse(root.getChildNodes());
    }

    private static void traverse(NodeList films) {
        for (int i = 0; i < films.getLength(); i++) {
            Node film = films.item(i);
            if (film.getNodeType() == Node.ELEMENT_NODE) {
                log.log(Level.INFO,"\t-- ------------------------------------ --");
                log.log(Level.INFO,"\tThe visualized node is {0}\n", film.getNodeName());

                showFilmElements(film);
                tryToShowAttributes(film);
            }
        }
    }

    private static void showFilmElements(Node film) {
        NodeList dates = film.getChildNodes();
        for (int j = 0; j <dates.getLength(); j++) {
            Node auxNode = dates.item(j);
            if (auxNode.getNodeType() == Node.ELEMENT_NODE) {
                log.log(Level.INFO,"\t\t-- ------------------------------------ --");
                log.log(Level.INFO,()->"\t\t"+auxNode.getNodeName() + ": " + auxNode.getFirstChild().getNodeValue());
            }
        }
    }

    private static void tryToShowAttributes(Node film) {
        if (film.hasAttributes()) {
            NamedNodeMap attributes = film.getAttributes();
            for (int k = 0; k < attributes.getLength(); k++) {
                Node attribute = attributes.item(k);
                log.log(Level.INFO,"\tAttribute: Name: {0} and value: {1}",new Object[]{ attribute.getNodeName(), attribute.getNodeValue()});
            }
        }
    }

    public static void filmData(Document document, String filmName){
        NodeList titles = document.getElementsByTagName("Titulo");
        for (int i = 0; i < titles.getLength(); i++) {
            if(titles.item(i).getFirstChild().getNodeValue().equals(filmName)){
                Element fatherNode = (Element) titles.item(i).getParentNode();
                NodeList aux = fatherNode.getElementsByTagName("Titulo");
                if(aux.getLength()>0)
                    log.log(Level.INFO, aux.item(0).getFirstChild().getNodeValue());

                aux = fatherNode.getElementsByTagName("Director");
                if(aux.getLength()>0)
                    log.log(Level.INFO, aux.item(0).getFirstChild().getNodeValue());

                aux = fatherNode.getElementsByTagName("Anho");
                if(aux.getLength()>0)
                    log.log(Level.INFO, aux.item(0).getFirstChild().getNodeValue());

                break;
            }
        }
    }


}
