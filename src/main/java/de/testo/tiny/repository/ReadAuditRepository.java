package de.testo.tiny.repository;

import de.testo.tiny.model.metrics.Audit;
import org.springframework.data.repository.Repository;

public interface ReadAuditRepository extends Repository<Audit, String> {
}
