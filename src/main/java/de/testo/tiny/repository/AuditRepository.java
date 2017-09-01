package de.testo.tiny.repository;

import de.testo.tiny.model.metrics.Audit;
import de.testo.tiny.model.metrics.Type;
import org.springframework.data.repository.Repository;

public interface AuditRepository extends Repository<Audit, String> {

    void save(Audit audit);
    int countDistinctByTargetURLAndType(String targetURL, Type type);
}
