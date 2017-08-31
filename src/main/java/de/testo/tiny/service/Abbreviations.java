package de.testo.tiny.service;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;

public class Abbreviations {

    private final AtomicInteger counter;

    private static final Character[] dictionary = IntStream.rangeClosed('a', 'z')
            .mapToObj( c -> (char) c)
            .toArray(Character[]::new);

    private static final int base26 = dictionary.length;

    public Abbreviations(int counter) {
        this.counter = new AtomicInteger(counter);
    }

    public String next() {
        int andIncrement = counter.getAndIncrement();

        String base26Representation = Integer.toString(andIncrement, base26);

        StringWriter collect = base26Representation.chars().
                map(c -> dictionary[getNumericValue(c)])
                .collect(StringWriter::new,
                        StringWriter::write,
                        (swl, swr) -> swl.write(swr.toString()));

        return collect.toString();
    }
}
