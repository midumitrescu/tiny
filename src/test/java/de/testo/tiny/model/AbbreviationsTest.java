package de.testo.tiny.model;


import de.testo.tiny.service.Abbreviations;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class AbbreviationsTest {

    @Test
    public void linearGeneration() {
        Abbreviations abbreviations = new Abbreviations(0);
        List<String> doubleCharAbbrev = IntStream.rangeClosed(0, 30).mapToObj(c -> abbreviations.next()).collect(toList());
        assertThat(doubleCharAbbrev)
                .containsExactly("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                        "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "ba", "bb", "bc", "bd", "be");
    }

}