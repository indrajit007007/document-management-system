package com.dms.repository;

import com.dms.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepo extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByDocumentIdOrderByOccurredAtAsc(UUID documentId);

}