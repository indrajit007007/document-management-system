package com.dms.controller;

import com.dms.dto.*;
import com.dms.entity.AuditLog;
import com.dms.entity.Document;
import com.dms.mapper.DocumentMapper;
import com.dms.repository.AuditRepo;
import com.dms.service.WorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/{tenantId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final WorkflowService workflowService;
    private final AuditRepo auditRepo;
    private final DocumentMapper documentMapper;

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateDocumentRequest request) {

        Document document = workflowService.createDocument(tenantId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentMapper.toResponse(document));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(
            @PathVariable UUID tenantId,
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.getDocument(tenantId, id)));
    }

    @PatchMapping("/{id}/submit")
    public ResponseEntity<DocumentResponse> submit(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody SubmitRequest request) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.submit(tenantId, id, request)));
    }

    @PatchMapping("/{id}/approve-review")
    public ResponseEntity<DocumentResponse> approveReview(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody ApproveReviewRequest request) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.approveReview(tenantId, id, request)));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<DocumentResponse> reject(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody RejectRequest request) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.reject(tenantId, id, request)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<DocumentResponse> approve(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody ApproveRequest request) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.approve(tenantId, id, request)));
    }

    @PatchMapping("/{id}/obsolete")
    public ResponseEntity<DocumentResponse> obsolete(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody ApproveRequest request) {

        return ResponseEntity.ok(
                documentMapper.toResponse(
                        workflowService.obsolete(tenantId, id, request)));
    }

    @GetMapping("/{id}/audit-log")
    public ResponseEntity<List<AuditLog>> getAuditLog(
            @PathVariable UUID tenantId,
            @PathVariable UUID id) {

        // Validates tenant ownership
        workflowService.getDocument(tenantId, id);

        return ResponseEntity.ok(
                auditRepo.findByDocumentIdOrderByOccurredAtAsc(id));
    }
}