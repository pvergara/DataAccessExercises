package org.ecos.logic;

import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException {
        sliceAndDiceThisFileLines("./testFile", 1);
    }

    @SuppressWarnings("unused")
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