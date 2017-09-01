package de.testo.tiny.repository;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;

public class AbbreviationsRepository {

    private final AtomicInteger counter;

    private static final Character[] dictionary = IntStream.rangeClosed('a', 'z')
            .mapToObj( c -> (char) c)
            .toArray(Character[]::new);

    private static final int base26 = dictionary.length;

    public AbbreviationsRepository(int startCounter) {
        this.counter = new AtomicInteger(startCounter);
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
