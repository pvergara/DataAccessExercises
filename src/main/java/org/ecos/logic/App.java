package org.ecos.logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        String fileName = "./testFile";
        //sliceAndDiceThisFileByChars(fileName, 5);
        sliceAndDiceThisFileByLines(fileName, 3);
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

    public static void sliceAndDiceThisFileByLines(String fileName, int amountOfLines) throws IOException {
        File usedFile = new File(fileName);
        int counter = 1;

        try (Scanner scanner = new Scanner(usedFile)) {
            while (scanner.hasNextLine()){
                String newFile = "parte" + counter;
                try(FileWriter fileWriterOne = new FileWriter(newFile)){
                    fileWriterOne.write(scanner.nextLine());
                }
                    counter++;
            }
        }

    }

}
