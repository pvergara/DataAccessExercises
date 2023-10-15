package org.ecos.logic;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest
{
    @Test
    void jdk11StringLines(){
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
    void jdk11CollectionToArray(){
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String[] sampleArray = sampleList.toArray(String[]::new);
        assertThat(sampleArray).containsExactly("Java", "Kotlin");
    }
}
