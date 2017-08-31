package de.testo.tiny.repository;

import de.testo.tiny.model.metrics.Audit;
import org.springframework.data.repository.Repository;

public interface CreationAuditRepository extends Repository<Audit, String> {
}
