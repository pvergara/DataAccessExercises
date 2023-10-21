package org.ecos.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.lineSeparator;

public class App {

    public static void main(String[] args) throws IOException {
        String fileName = "./testFile";
        //sliceAndDiceThisFileByChars(fileName, 5);
        sliceAndDiceThisFileLines("./testFile", 4);
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
        File usedFile = new File(fileName);

        try (Scanner scanner = new Scanner(usedFile);) {
            ArrayList<String> lineArray = new ArrayList<>();

            while (scanner.hasNextLine()) {
                lineArray.add(scanner.nextLine());
            }

            int counter = 0;
            int partCounter = 0;
            String[] fileParts = new String[lineArray.size()];

            Arrays.fill(fileParts, "");


            for (String getString : lineArray) {
                if (counter < amountOfLines) {

                    fileParts[partCounter] += getString + "\n";
                    counter++;

                }
                if (counter == amountOfLines) {
                    counter = 0;
                    partCounter++;
                }
            }


            String filePartName = "";
           for (int i = 0; i < fileParts.length; i++) {
                filePartName = "part " + (i+1);
                if (!fileParts[i].isEmpty()){
                    try(PrintWriter printWriter = new PrintWriter(filePartName)){
                        printWriter.write(fileParts[i]);
                    }
                }

            }

        }
    }

}
