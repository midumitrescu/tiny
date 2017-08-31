package de.testo.tiny.service;

import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.repository.CreationAuditRepository;
import de.testo.tiny.repository.ReadAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final CreationAuditRepository creationRepository;
    private final ReadAuditRepository readRepository;

    @Autowired
    public MetricsService(CreationAuditRepository creationRepository, ReadAuditRepository readRepository) {
        this.creationRepository = creationRepository;
        this.readRepository = readRepository;
    }

    public void incrementCreateCounter(TinyURL tinyURL) {

    }
}
