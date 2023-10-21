package org.ecos.logic;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void jdk11StringLines() {
        //Arrange
        String multilineString = "Baeldung helps \n \n developers \n explore Java.";


        //Act
        List<String> lines = multilineString.
                lines().
                filter(line -> !line.isBlank()).
                map(String::strip).
                collect(Collectors.toList());

        //Assertions
        assertThat(lines).
                containsExactly(
                        "Baeldung helps",
                        "developers",
                        "explore Java."
                );
    }

    @Test
    void jdk11NewFileClasses() throws IOException {
        Path path = Paths.get("/home/pvergaca/");
        Path filePath = Files.writeString(Files.createTempFile(path, "lerele", ".txt"), "Sample text");

        String fileContent = Files.readString(filePath);

        assertThat(fileContent).isEqualTo("Sample text");
    }

    @Test
    void jdk11CollectionToArray() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String[] sampleArray = sampleList.toArray(String[]::new);
        assertThat(sampleArray).containsExactly("Java", "Kotlin");
    }

    @Test
    void splitAnStringInLines() {
        String actualList = "" +
                "the Old or Ancient Ones, the Elder Gods, of cosmic good, and those of cosmic evil, bearing " +
                "many names, and themselves of different groups, as if associated with the elements and yet " +
                "transcending them: for there are the Water Beings, hidden in the depths; those of Air that " +
                "are the primal lurkers beyond time; those of Earth, horrible animate survivors of distant e" +
                "ons";

        String expectedList =
                "the Old or Ancient Ones, the Elder Gods, of cosmic good, and those of cosmic evil, bearing " + lineSeparator() +
                "many names, and themselves of different groups, as if associated with the elements and yet " + lineSeparator() +
                "transcending them: for there are the Water Beings, hidden in the depths; those of Air that " + lineSeparator() +
                "are the primal lurkers beyond time; those of Earth, horrible animate survivors of distant e" + lineSeparator() +
                "ons";

        int numberOfLines = 5;

        String linesDividedWithLines = addLineSeparatorAGivenNumberOfTimes(numberOfLines, actualList);

        assertThat(linesDividedWithLines).contains(lineSeparator());

        assertThat(linesDividedWithLines).hasLineCount(numberOfLines);

        List<String> dividedLineCollection = Arrays.stream(linesDividedWithLines.split(lineSeparator())).toList();
       assertThat(dividedLineCollection).isEqualTo(Arrays.stream(expectedList.split(lineSeparator())).toList() );
    }

    @Test
    void splitAStringInLinesDynamic() {
        //Arrange
        String actualList = "If anyone makes fun of your necklace, you just tell them PH'NGLUI MGLW'NAFH CTHULHU R'LYEH WGAH'NAGL FHTAGN";
        String expectedList = "If anyone mak" + lineSeparator() +
                "es fun of you" + lineSeparator() +
                "r necklace, y" + lineSeparator() +
                "ou just tell " + lineSeparator() +
                "them PH'NGLUI" + lineSeparator() +
                " MGLW'NAFH CT" + lineSeparator() +
                "HULHU R'LYEH " + lineSeparator() +
                "WGAH'NAGL FHT" + lineSeparator() +
                "AGN";
        List<String> dividedLineCollection = Arrays.stream(expectedList.split(lineSeparator())).toList();

        int numberOfLines = 9;

        //Act
        String actual = addLineSeparatorAGivenNumberOfTimes(numberOfLines, actualList);

        //Assertions
        assertThat(actual).contains(lineSeparator());
        assertThat(actual).hasLineCount(numberOfLines);


        assertThat(dividedLineCollection.size()).isEqualTo(numberOfLines);

        for (int i = 0; i < numberOfLines - 1 ; i++) {
            assertThat(dividedLineCollection.get(i).length()).isEqualTo(13);
        }
        assertThat(dividedLineCollection.get(8).length()).isEqualTo((actualList.length() / numberOfLines) - (actualList.length() % numberOfLines));
    }

    @Test
    void dividing5By3(){
        assertThat(5/3).isEqualTo(1);
        assertThat(5 % 3).isEqualTo(2);

        assertThat(6/3).isEqualTo(2);
        assertThat(6 % 3).isEqualTo(0);

        assertThat(11/3).isEqualTo(3);
        assertThat(11 % 3).isEqualTo(2);
    }

    private static String addLineSeparatorAGivenNumberOfTimes(int numberOfLines, String sampleList) {
        StringBuilder linesDividedWithLines = new StringBuilder();

        int numberOfDividing = numberOfLines - 1;
        int dividingLength = (sampleList.length() / numberOfDividing);
        int dividingIndex = dividingLength - 1;

        for (int i = 0; i < numberOfDividing; i++) {
            linesDividedWithLines.append(sampleList, ((dividingIndex + lineSeparator().length()) * i), ((dividingIndex + lineSeparator().length()) * i) + dividingLength).append(lineSeparator());
        }
        linesDividedWithLines.append(sampleList.substring(((dividingIndex + lineSeparator().length()) * numberOfDividing)));

        return linesDividedWithLines.toString();
    }
}
