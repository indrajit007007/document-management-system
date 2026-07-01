package com.dms.entity;

import com.dms.enums.DocumentStatus;
import com.dms.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private UUID authorId;

    private UUID reviewerId;

    private UUID approverId;

    @Column(nullable = false)
    private Integer version;

    private LocalDateTime createdAt;

    private LocalDateTime releasedAt;

    private LocalDateTime obsoletedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        version = 1;
        status = DocumentStatus.DRAFT;
    }
}