package com.dms.repository;


import com.dms.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface Document_Repo extends JpaRepository<Document, UUID> {

    Optional<Document> findByIdAndTenantId(UUID id, UUID tenantId);

}
