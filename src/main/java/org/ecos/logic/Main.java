package org.ecos.logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.json.*;
import javax.xml.parsers.*;

import java.io.*;
import java.util.*;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

public class Main {

    private static String films = Objects.requireNonNull(Main.class.getResource("/peliculas.xml")).getPath();
    private static boolean isDriverName;
    private static boolean isDriver;
    private static boolean isLong;
    private static boolean isLat;
    private static boolean isCircuit;
    private static boolean isCircuitName;
    private static boolean isPoints;
    private static String points;

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {

        /*//Ejercicio 2
        List<String> lines = getFromFile(Objects.requireNonNull(Main.class.getResource("/file.txt")).getPath());
        System.out.println("Number of lines is: " + lines.size());
        writeToFile(lines, "result.txt");

        //Ejercicio 3
        List<Book> books = new ArrayList<>(Arrays.asList(
            new Book("The Call of Cthulhu", "H.P. Lovecraft", 100),
            new Book("Lords and ladies", "Sir Terry Pratchett", 200),
            new Book("Diamon Age", "Neal Stephenson", 150),
            new Book("Neuromancer", "William Gibson", 300),
            new Book("Necronomicom", "Abdul Ablareth", 233)
            )
        );
        write(books);

        Book book = read(books.get(0));
        System.out.println(book);*/


        //Ejercicio 8
        //getSax("Carreras.xml");

        //Ejercicio 11
        showDriversNameTheCircuitsAswellAsTheAverageSpeedAndPointsObtainedIn(readJSON("Carreras.json"));

    }

    private static void showDriversNameTheCircuitsAswellAsTheAverageSpeedAndPointsObtainedIn(JsonValue jsonValue) {
        if (jsonValue.getValueType() == JsonValue.ValueType.ARRAY){
            JsonArray circuits = jsonValue.asJsonArray();
            for (JsonValue circuit: circuits){
                showDriversNameTheCircuitsAswellAsTheAverageSpeedAndPointsObtainedIn(circuit);
            }
        }
        if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT){
            JsonObject circuits = jsonValue.asJsonObject();
            for (String keyName: circuits.keySet()){
                if(keyName.equals("Driver"))
                    isDriver = true;
                if(isDriver){
                    isDriverName = keyName.equals("name");
                }
                if(keyName.equals("Constructor"))
                    isDriver = false;
                if (keyName.equals("Circuit")){
                    isCircuit = true;
                }
                isPoints = keyName.equals("points");

                if (isCircuit){
                    isCircuitName = keyName.equals("name");
                }
                if(keyName.equals("date"))
                    isCircuit = false;
                isLong = keyName.equals("long");
                isLat = keyName.equals("lat");

                showDriversNameTheCircuitsAswellAsTheAverageSpeedAndPointsObtainedIn(circuits.get(keyName));
            }
        }
        //TODO is
        if (jsonValue.getValueType() == JsonValue.ValueType.STRING){
            if(isDriverName){
                System.out.println("drivers name: "+jsonValue.toString().replace("\"",""));
                if(points!=null) {
                    System.out.println("Points: " + points);
                    points = null;
                }
            }
            if (isCircuitName){
                System.out.println("circuits name: " +jsonValue.toString().replace("\"",""));
            }
        }
        if (jsonValue.getValueType() == JsonValue.ValueType.NUMBER){
            if (isLong){
                System.out.println("long: "+jsonValue.toString().replace("\"",""));
            }
            if (isLat){
                System.out.println("lat: "+jsonValue.toString().replace("\"",""));
            }
            if (isPoints){
                points = jsonValue.toString().replace("\"","");
            }

        }

    }

    private static JsonValue readJSON(String file) throws FileNotFoundException {
        try(JsonReader reader = Json.createReader(new FileReader(file))){
            return reader.read();
        }
    }

    public static void getSax(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXHandler saxParser = new SAXHandler();
        parser.parse(path, saxParser);
    }
    private static Book read(Book book) throws IOException, ClassNotFoundException {
        Book theBook;
        try (FileInputStream fin = new FileInputStream(book.title.replace(" ", "_"));
             ObjectInputStream in = new ObjectInputStream(fin);) {
            theBook = (Book) in.readObject();
        }
        return theBook;
    }

    private static void write(List<Book> books) throws IOException {
        for (Book book : books) {
            write(book);
        }
    }

    private static void write(Book book) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(book.title.replace(" ", "_"));
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(book);
        }
    }


    private static List<String> getFromFile(String path) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }
        }
        return result;
    }

    private static void writeToFile(List<String> lines, String file) throws FileNotFoundException {
        String onlyOneLine = String.join(" ", lines);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(onlyOneLine);
        }
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
        for (int i = 0; i < titles.getLength(); i++) {
            Node item = titles.item(i);
            Element film = (Element) item.getParentNode();
            NodeList directors = film.getElementsByTagName("director");
            List<Director> directorCollection = new ArrayList<>();
            for (int j = 0; j < directors.getLength(); j++) {
                if (directors.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) directors.item(j);
                    Director director = new Director(element.getFirstChild().getNodeValue());

                    directorCollection.add(director);
                }
            }
            actualFilmTitles.add(new Film(item.getFirstChild().getNodeValue(), directorCollection));
        }
        return actualFilmTitles;
    }

    public static void sliceAndDiceThisFileByChars(String fileName, int amountOfChars) throws IOException {
        File usedFile = new File(fileName);

        try (FileReader fileReaderOne = new FileReader(usedFile)) {
            char[] buffer = new char[amountOfChars];

            int bytesRead;
            int counter = 1;

            while ((bytesRead = fileReaderOne.read(buffer)) != -1) {
                String newFile = "parte" + counter;
                try (FileWriter fileWriterOne = new FileWriter(newFile)) {
                    fileWriterOne.write(buffer, 0, bytesRead);
                }
                counter++;
            }
        }
    }

    public static void sliceAndDiceThisFileLines(String fileName, int amountOfLines) throws IOException {
        ArrayList<String> lineArray = getAFileDividedOnItsLines(fileName);

        List<String> linesToRead = getListWithOnlyAmountOfLines(lineArray, amountOfLines);

        writeOnEveryFiles(linesToRead);
    }

    private static ArrayList<String> getAFileDividedOnItsLines(String fileName) throws FileNotFoundException {
        ArrayList<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }
        }
        return result;
    }

    private static List<String> getListWithOnlyAmountOfLines(ArrayList<String> lineArray, int amountOfLines) {
        int counter = 0;
        int partCounter = 0;

        String[] fileParts = new String[lineArray.size()];
        for (String getString : lineArray) {
            if (counter < amountOfLines) {
                if (fileParts[partCounter] == null)
                    fileParts[partCounter] = getString;
                else
                    fileParts[partCounter] += getString;
                counter++;

            }
            if (counter == amountOfLines) {
                counter = 0;
                partCounter++;
            }
        }
        return Arrays.stream(fileParts).filter(Objects::nonNull).toList();
    }

    private static void writeOnEveryFiles(List<String> linesToRead) throws FileNotFoundException {
        String filePartName;
        int i = 1;
        for (String element : linesToRead) {
            filePartName = "part " + i;
            try (PrintWriter printWriter = new PrintWriter(filePartName)) {
                printWriter.write(element);
            }
            i++;
        }
    }
}
