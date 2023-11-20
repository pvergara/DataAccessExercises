package org.ecos.logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

public class Main {

    private static String films = Objects.requireNonNull(Main.class.getResource("/peliculas.xml")).getPath();

    public static void main(String[] args) {

        makeADOMTreeFrom(films);
        showMeTheNameOfTheDirectors(films);
    }

    private static void showMeTheNameOfThe(String XMLName) {
        Document xmlDocument = makeADOMTreeFrom(XMLName);
        if (xmlDocument == null)
            return;

        NodeList titles = xmlDocument.getElementsByTagName("titulo");

        for (var i = 0; i < titles.getLength(); i++) {
            System.out.println(titles.item(i).getTextContent());
        }
    }

    private static void showMeTheNameOfTheDirectors(String XMLName) {
        Document xmlDocument = makeADOMTreeFrom(XMLName);
        if (xmlDocument == null)
            return;
        NodeList film = xmlDocument.getElementsByTagName("pelicula");

        for (int i = 0; i < film.getLength(); i++) {
            if (film.item(i).hasChildNodes()) {
                NodeList filmDirectorsAndName = film.item(i).getChildNodes();

                for (int j = 0; j < filmDirectorsAndName.getLength(); j++) {
                    if (filmDirectorsAndName.item(j) == null) {
                        break;
                    }
                    if (filmDirectorsAndName.item(j).getNodeName().equals("titulo")) {
                        System.out.println("Titulo: " + filmDirectorsAndName.item(j).getFirstChild().getNodeValue());
                    }
                }
            }
        }
    }


    public static Document makeADOMTreeFrom(String XMLName) {
        Document xmlDocument = null;
        try {
            DocumentBuilderFactory factory = newInstance();
            factory.setExpandEntityReferences(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmlDocument = builder.parse(XMLName);
        } catch (Exception e) {
            System.out.println("There was an ERROR generating your precious, beautiful, scrumptious, tempting DOM Tree: " + e.getMessage());
        }
        return xmlDocument;
    }

    public static List<Film> getFilmData(Document document) {
        List<Film> actualFilmTitles = new ArrayList<>();
        NodeList titles = document.getElementsByTagName("titulo");
        for (int i = 0; i <titles.getLength(); i++) {
            Node item = titles.item(i);
            Element film = (Element) item.getParentNode();
            NodeList directors = film.getElementsByTagName("director");
            List<Director> directorCollection = new ArrayList<>();
            for (int j = 0; j <directors.getLength(); j++) {
                if (directors.item(j).getNodeType()==Node.ELEMENT_NODE) {
                    Element element = (Element) directors.item(j);
                    Director director = new Director(element.getFirstChild().getNodeValue());

                    directorCollection.add(director);
                }
            }
            actualFilmTitles.add(new Film(item.getFirstChild().getNodeValue(), directorCollection));
        }
        return actualFilmTitles;
    }

}
