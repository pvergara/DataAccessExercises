package org.ecos.logic;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ecos.logic.Main.getFilmData;
import static org.ecos.logic.XMLFirst.*;

class AppTest {
    @Test
    void gettingAXMLDOMDocument(){
        Document result = getDocument(Objects.requireNonNull(AppTest.class.getResource("/films.xml")).getPath());
        assertThat(result).isNotNull();
    }

    @Test
    void traversingOnDocument(){
        Document result = getDocument(Objects.requireNonNull(AppTest.class.getResource("/films.xml")).getPath());
        assertThat(result).isNotNull();

        //SUT
        //Action
        parseDOM(result);

        //Assertion?!?!?!?
    }

    @Test
    void gettingFilmInformation(){
        Document result = getDocument(Objects.requireNonNull(AppTest.class.getResource("/films.xml")).getPath());
        assertThat(result).isNotNull();
        filmData(result,"Dune");
    }

    @Test
    void getTitleAndDirectors(){
        Document document = Main.makeADOMTreeFrom(Objects.requireNonNull(AppTest.class.getResource("/peliculas.xml")).getPath());

        List<Film> expectedFilmTitles = new ArrayList<>(Arrays.asList(
                new Film("Dune", new ArrayList<>(Arrays.asList(new Director("David Lynch")))),
                new Film("El Señor de los Anillos", new ArrayList<>(Arrays.asList(new Director("Peter Jackson")))),
                new Film("Matrix", new ArrayList<>(Arrays.asList(new Director("Andy Wachowski"),new Director("Larry Wachowski")))),
                new Film("Blade Runner", new ArrayList<>(Arrays.asList(new Director("Ridley Scott")))),
                new Film("Fargo", new ArrayList<>(Arrays.asList(new Director("Joel Coen"),new Director("Ethan Coen")))),
                new Film("Alien", new ArrayList<>(Arrays.asList(new Director("Ridley Scott"))))
        ));
        List<Film> actualFilmTitles = getFilmData(document);

        assertThat(actualFilmTitles).isEqualTo(expectedFilmTitles);
    }

}
