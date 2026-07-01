package com.dms.service;

import com.dms.dto.*;
import com.dms.entity.AuditLog;
import com.dms.entity.Document;
import com.dms.enums.DocumentStatus;
import com.dms.exception.AccessDeniedException;
import com.dms.exception.ResourceNotFoundException;
import com.dms.repository.AuditRepo;
import com.dms.repository.Document_Repo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final Document_Repo documentRepo;
    private final AuditRepo auditRepo;

    @Transactional
    public Document createDocument(UUID tenantId, CreateDocumentRequest request) {

        Document document = Document.builder()
                .title(request.getTitle())
                .type(request.getType())
                .tenantId(tenantId)
                .authorId(request.getAuthorId())
                .status(DocumentStatus.DRAFT)
                .version(1)
                .createdAt(LocalDateTime.now())
                .build();

        return documentRepo.save(document);
    }

    public Document getDocument(UUID tenantId, UUID id) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        if (!document.getTenantId().equals(tenantId)) {
            throw new AccessDeniedException("Tenant mismatch");
        }

        return document;
    }

    @Transactional
    public Document submit(UUID tenantId, UUID documentId, SubmitRequest request) {

        Document document = getDocument(tenantId, documentId);

        if (document.getStatus() != DocumentStatus.DRAFT) {
            throw new RuntimeException("Invalid workflow transition");
        }

        if (document.getAuthorId().equals(request.getReviewerId())) {
            throw new RuntimeException("Reviewer cannot be author");
        }

        document.setReviewerId(request.getReviewerId());

        changeStatus(
                document,
                request.getActorId(),
                DocumentStatus.IN_REVIEW,
                null
        );

        return documentRepo.save(document);
    }

    private void changeStatus(Document document,
                              UUID actorId,
                              DocumentStatus newStatus,
                              String comment) {

        AuditLog log = AuditLog.builder()
                .tenantId(document.getTenantId())
                .documentId(document.getId())
                .actorId(actorId)
                .fromStatus(document.getStatus())
                .toStatus(newStatus)
                .comment(comment)
                .occurredAt(LocalDateTime.now())
                .build();

        auditRepo.save(log);

        document.setStatus(newStatus);
    }

    @Transactional
    public Document approveReview(UUID tenantId,
                                  UUID documentId,
                                  ApproveReviewRequest request) {

        Document document = getDocument(tenantId, documentId);

        if (document.getStatus() != DocumentStatus.IN_REVIEW) {
            throw new RuntimeException("Invalid workflow transition");
        }

        document.setApproverId(request.getApproverId());

        changeStatus(
                document,
                request.getActorId(),
                DocumentStatus.PENDING_APPROVAL,
                null
        );

        return documentRepo.save(document);
    }

    @Transactional
    public Document reject(UUID tenantId,
                           UUID documentId,
                           RejectRequest request) {

        Document document = getDocument(tenantId, documentId);

        if (document.getStatus() != DocumentStatus.IN_REVIEW) {
            throw new RuntimeException("Only documents in review can be rejected");
        }

        if (request.getComment() == null || request.getComment().trim().isEmpty()) {
            throw new RuntimeException("Comment is required");
        }

        changeStatus(
                document,
                request.getActorId(),
                DocumentStatus.DRAFT,
                request.getComment()
        );

        return documentRepo.save(document);
    }

    @Transactional
    public Document approve(UUID tenantId,
                            UUID documentId,
                            ApproveRequest request) {

        Document document = getDocument(tenantId, documentId);

        if (document.getStatus() != DocumentStatus.PENDING_APPROVAL) {
            throw new RuntimeException("Invalid workflow transition");
        }

        document.setVersion(document.getVersion() + 1);
        document.setReleasedAt(LocalDateTime.now());

        changeStatus(
                document,
                request.getActorId(),
                DocumentStatus.RELEASED,
                null
        );

        return documentRepo.save(document);
    }

    @Transactional
    public Document obsolete(UUID tenantId,
                             UUID documentId,
                             ApproveRequest request) {

        Document document = getDocument(tenantId, documentId);

        if (document.getStatus() != DocumentStatus.RELEASED) {
            throw new RuntimeException("Only released documents can be obsoleted");
        }

        document.setObsoletedAt(LocalDateTime.now());

        changeStatus(
                document,
                request.getActorId(),
                DocumentStatus.OBSOLETE,
                null
        );

        return documentRepo.save(document);
    }
}