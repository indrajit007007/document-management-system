package com.dms.dto;


import com.dms.enums.DocumentStatus;
import com.dms.enums.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class DocumentResponse {

    private UUID id;
    private String title;
    private DocumentType type;
    private DocumentStatus status;
    private UUID tenantId;
    private UUID authorId;
    private UUID reviewerId;
    private UUID approverId;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime releasedAt;
    private LocalDateTime obsoletedAt;
}
