package de.testo.tiny.service;

import de.testo.tiny.model.metrics.Audit;
import de.testo.tiny.model.metrics.TinyURLMetrics;
import de.testo.tiny.model.metrics.Type;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

@Service
public class MetricsService {

    private final AuditRepository audit;

    @Autowired
    public MetricsService(AuditRepository readRepository) {
        this.audit = readRepository;
    }

    public void incrementCreateCounter(TinyURL tinyURL) {
        Audit event = Audit.builder()
                .targetURL(tinyURL.getTargetURL())
                .type(Type.CREATE)
                .uuidTrace(randomUUID().toString())
                .build();
        audit.save(event);
    }


    public void incrementReadCounter(TinyURL tinyURL) {
        Audit event = Audit.builder()
                .targetURL(tinyURL.getTargetURL())
                .type(Type.READ)
                .uuidTrace(randomUUID().toString())
                .build();
        audit.save(event);
    }

    public TinyURLMetrics getMetricsFor(TinyURL tinyURL) {
        return TinyURLMetrics.builder()
                .creates(audit.countDistinctByTargetURLAndType(tinyURL.getTargetURL(), Type.CREATE))
                .reads(audit.countDistinctByTargetURLAndType(tinyURL.getTargetURL(), Type.READ))
                .build();
    }
}
