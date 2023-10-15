package org.ecos.logic;

import java.io.*;

import static java.lang.System.lineSeparator;

public class App {

    public static void main(String[] args) throws IOException {
        String fileName = "./testFile";
        //sliceAndDiceThisFileByChars(fileName, 5);
        addLineSeparatorAGivenNumberOfTimes(fileName, 3);
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


    public static String addLineSeparatorAGivenNumberOfTimes(String givenFile, int numberOfLines) throws IOException {
        StringBuilder linesDividedWithLines = new StringBuilder();
        int numberOfDividing = numberOfLines - 1;
        int dividingLength = (givenFile.length() / numberOfDividing);
        int dividingIndex = dividingLength - 1;
        try (FileReader fileReader = new FileReader(givenFile)) {
            for (int i = 0; i < numberOfDividing; i++) {
                linesDividedWithLines.append(givenFile, ((dividingIndex + lineSeparator().length()) * i), ((dividingIndex + lineSeparator().length()) * i) + dividingLength).append(lineSeparator());
            }
            linesDividedWithLines.append(givenFile.substring(((dividingIndex + lineSeparator().length()) * numberOfDividing)));

        }



        return linesDividedWithLines.toString();
    }
}
