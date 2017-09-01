package de.testo.tiny.service;

import de.testo.tiny.model.stats.Event;
import de.testo.tiny.model.stats.TinyURLStats;
import de.testo.tiny.model.stats.Type;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

@Service
public class StatsService {

    private final EventsRepository audit;

    @Autowired
    public StatsService(EventsRepository readRepository) {
        this.audit = readRepository;
    }

    public void incrementCreateCounter(TinyURL tinyURL) {
        Event event = Event.builder()
                .targetURL(tinyURL.getTargetURL())
                .type(Type.CREATE)
                .trace(randomUUID())
                .build();
        audit.save(event);
    }


    public void incrementReadCounter(TinyURL tinyURL) {
        Event event = Event.builder()
                .targetURL(tinyURL.getTargetURL())
                .type(Type.READ)
                .trace(randomUUID())
                .build();
        audit.save(event);
    }

    public TinyURLStats getStatsFor(TinyURL tinyURL) {
        return TinyURLStats.builder()
                .creates(audit.countDistinctByTargetURLAndType(tinyURL.getTargetURL(), Type.CREATE))
                .redirects(audit.countDistinctByTargetURLAndType(tinyURL.getTargetURL(), Type.READ))
                .build();
    }
}
