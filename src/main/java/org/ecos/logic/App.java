package org.ecos.logic;

import java.io.*;
import java.util.*;

import static java.lang.System.lineSeparator;
import static org.ecos.logic.FilePlaceEnumerator.*;

public class App {

    public static void main(String[] args) throws IOException {
        sliceAndDiceThisFileLines("./testFile", 1);
        mergeFileList(Arrays.asList("./part 1","./part 2"));
    }

    @SuppressWarnings("unused")

    public static void mergeFileList(List<String> givenFileList) throws IOException {

        int counter = 0;
        for (String element : givenFileList) {
            List<String> fileAsStringList = getAFileDividedOnItsLines(element);
            if(counter == 0 && givenFileList.size()==1)
                writeTheseFileLinesIn(fileAsStringList,FIRST_AND_LAST);
            else if(counter == 0)
                writeTheseFileLinesIn(fileAsStringList, FIRST);
            else if(counter == givenFileList.size()-1)
                writeTheseFileLinesIn(fileAsStringList, LAST);
            else
                writeTheseFileLinesIn(fileAsStringList, REST);
            counter++;
        }
    }

    private static void writeTheseFileLinesIn(List<String> fileAsStringList,FilePlaceEnumerator filePlace) throws IOException {
        int counter = 0; //ya se que podria haber usado booleans en vez de enums
        String ficheroFrank = "FicheroFrank";

        boolean mustAppend = (filePlace != FIRST) && (filePlace != FIRST_AND_LAST);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(ficheroFrank, mustAppend))) {
            for(String element: fileAsStringList) {
                if (fileAsStringList.size() -1 == counter){
                    String append = "";
                    if(filePlace == FIRST || filePlace ==REST)
                        append = lineSeparator();
                    printWriter.write(element + append);
                }
                else{
                    printWriter.write(element + System.lineSeparator());
                }
                counter++;
            }
        }
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
        List<String> lineArray = getAFileDividedOnItsLines(fileName);

        List<String> linesToRead = getListWithOnlyAmountOfLines(lineArray, amountOfLines);

        writeOnEveryFiles(linesToRead);
    }

    private static List<String> getAFileDividedOnItsLines(String fileName) throws FileNotFoundException {
        ArrayList<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }
        }
        return result;
    }


    private static List<String> getListWithOnlyAmountOfLines(List<String> lineArray, int amountOfLines) {
        int lineCounter = 0;
        int partCounter = 0;

        String[] fileParts = new String[lineArray.size()];
        for (String item : lineArray) {
            if (lineCounter < amountOfLines) {
                if (fileParts[partCounter] == null)
                    fileParts[partCounter] = item;
                else
                    fileParts[partCounter] += item;
                lineCounter++;
            }

            if (lineCounter == amountOfLines) {

                lineCounter = 0;
                partCounter++;
            } else
                fileParts[partCounter] += lineSeparator();
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