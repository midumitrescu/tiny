package de.testo.tiny.repository;

import de.testo.tiny.model.stats.Event;
import de.testo.tiny.model.stats.Type;
import org.springframework.data.repository.Repository;

public interface EventsRepository extends Repository<Event, String> {

    void save(Event event);
    int countDistinctByTargetURLAndType(String targetURL, Type type);
}
