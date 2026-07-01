package com.dms.mapper;


import com.dms.dto.DocumentResponse;
import com.dms.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponse toResponse(Document document) {

        return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .type(document.getType())
                .status(document.getStatus())
                .tenantId(document.getTenantId())
                .authorId(document.getAuthorId())
                .reviewerId(document.getReviewerId())
                .approverId(document.getApproverId())
                .version(document.getVersion())
                .createdAt(document.getCreatedAt())
                .releasedAt(document.getReleasedAt())
                .obsoletedAt(document.getObsoletedAt())
                .build();
    }
}