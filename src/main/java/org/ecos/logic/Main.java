package org.ecos.logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    //Para catalogo separado por genero la ultima imagen de tipo medium
    private static List<Node> attributesMale = new ArrayList<>();
    private static List<Node> attributesFemale = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*
        String filePath = "maria.json";

        JsonReader jsonReader = Json.createReader(new FileReader(filePath));
        JsonObject jsonObject = jsonReader.readObject();

        dataJson(jsonObject);

        //Ej3 Ficheros
        List<Book> bookList = readFrom(new File("Libros.txt"));

        bookList.forEach(System.out::println);

        transform(bookList);
        List<Book> anotherBookList = readFromBinary();
        anotherBookList.forEach(System.out::println);
*/

        //Father's exercise

        Document complexDocument = createTree("complex.xml");

        Node father = complexDocument.getFirstChild();
        getDataFrom(father);
        Element lastMaleImage;
        Element lastFemaleImage;
        lastMaleImage = (Element) attributesMale.get(attributesMale.size() - 1);
        lastFemaleImage = (Element) attributesFemale.get(attributesFemale.size() - 1);
        System.out.println("The last image in the male category is: " + lastMaleImage.getAttribute("image"));
        System.out.println();
        System.out.println("The last image in the female category is: " + lastFemaleImage.getAttribute("image"));


    }

    private static void getDataFrom(Node father) {
        NodeList elementList = father.getChildNodes();

        Node attribute;
        for (int i = 0; i < elementList.getLength(); i++) {
            Node currentNode = elementList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elementNode = (Element) currentNode;

                if (currentNode.getNodeName().equals("catalog_item")){
                    if (elementNode.getAttribute("gender").equals("Men's")) {
                        extracted(currentNode, attributesMale);

                    }
                    else if (elementNode.getAttribute("gender").equals("Women's")){
                        extracted(currentNode, attributesFemale);
                    }
                }
                getDataFrom(currentNode);
            }
        }
    }

    private static void extracted(Node currentNode, List<Node> attributesMale) {
        NodeList catalogList = currentNode.getChildNodes();
        for (int j = 0; j < catalogList.getLength(); j++) {
            if (catalogList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) catalogList.item(j);
                if (catalogList.item(j).getNodeName().equals("size") && currentElement.getAttribute("description").equals("Medium")) {
                    NodeList imageList = catalogList.item(j).getChildNodes();
                    for (int k = 0; k < imageList.getLength(); k++) {
                        if (imageList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                            attributesMale.add(imageList.item(k));
                        }
                    }
                }
            }
        }
    }

    private static Document createTree(String reference) {
        Document doc = null;
        try {
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            factoria.setIgnoringComments(true);
            DocumentBuilder builder = factoria.newDocumentBuilder();
            doc = builder.parse(reference);
        } catch (Exception e) {
            System.out.println("Error generando el árbol DOM: " + e.getMessage());
        }
        return doc;
    }
/*

    private static void transform(List<Book> bookList) throws IOException {

        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File("Libros.dat")))) {
            for (int i = 0; i < bookList.size(); i++) {
                writer.writeObject(bookList.get(i));
            }
        }

    }

    private static List<Book> readFrom(File file) throws FileNotFoundException {
        List<Book> books = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                List<String> fixedLines = Arrays.stream(currentLine.split("\t")).toList();
                books.add(new Book(Integer.parseInt(fixedLines.get(0)), Float.parseFloat(fixedLines.get(1)), fixedLines.get(2), fixedLines.get(3)));
            }
        }
        return books;
    }

    private static void dataJson(JsonObject data) {
        List<String> hobbies = getList(data.getJsonArray("hobbies"));
        String name = data.getString("nombre");
        boolean married = data.getBoolean("casado");
        int age = data.getInt("edad");

        System.out.println("Hobbies: " + hobbies);
        System.out.println("Nombre: " + name);
        System.out.println(married ? "Esta casada" : "Not");
        System.out.println(MessageFormat.format("Tiene {0} años\n", age));

        System.out.println("Experiencia laboral:");
        System.out.println();
        JsonArray experience = data.getJsonArray("experienciaLaboral");
        for (int i = 0; i < experience.size(); i++) {
            JsonObject industryPosition = experience.getJsonObject(i);
            int salary = industryPosition.getInt("salario");

            JsonObject industry = industryPosition.getJsonObject("empresa");
            String industryName = "";
            String industrySector = "";
            System.out.println("Salario: " + salary);

            industryName = industry.getString("nombre");
            industrySector = industry.getString("sector");

            System.out.println("Nombre de la empresa: " + industryName);
            System.out.println("Nombre del sector: " + industrySector);
        }
    }

    private static List<String> getList(JsonArray data) {
        List<String> addStringToListHere = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            addStringToListHere.add(data.getString(i));
        }
        return addStringToListHere;
    }

    private static List<Book> readFromBinary() throws IOException, ClassNotFoundException {
        List<Book> createdBook = new ArrayList<>();
        try (ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(Path.of(Main.class.getResource("/libros.dat").getPath())))) {
            boolean mustContinue = true;
            while (mustContinue) {
                try {
                    createdBook.add((Book) reader.readObject());
                } catch (EOFException e) {
                    mustContinue = false;
                }
            }
        }
        return createdBook;
    }


*/
}
