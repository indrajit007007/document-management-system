package com.dms.service;

import com.dms.dto.SubmitRequest;
import com.dms.entity.Document;
import com.dms.enums.DocumentStatus;
import com.dms.repository.AuditRepo;
import com.dms.repository.Document_Repo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceTest {

    @Mock
    private Document_Repo documentRepo;

    @Mock
    private AuditRepo auditRepo;

    @InjectMocks
    private WorkflowService workflowService;

    @Test
    void submitDocument_ShouldMoveToInReview() {

        UUID tenantId = UUID.randomUUID();
        UUID documentId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();

        Document document = Document.builder()
                .id(documentId)
                .tenantId(tenantId)
                .authorId(authorId)
                .status(DocumentStatus.DRAFT)
                .version(1)
                .build();

        // Mock the method actually used by the service
        when(documentRepo.findById(documentId))
                .thenReturn(Optional.of(document));

        when(documentRepo.save(any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SubmitRequest request = new SubmitRequest();
        request.setReviewerId(reviewerId);
        request.setActorId(UUID.randomUUID());

        Document result = workflowService.submit(tenantId, documentId, request);

        assertEquals(DocumentStatus.IN_REVIEW, result.getStatus());

        verify(documentRepo, times(1)).findById(documentId);
        verify(auditRepo, times(1)).save(any());
        verify(documentRepo, atLeastOnce()).save(any(Document.class));
    }
}