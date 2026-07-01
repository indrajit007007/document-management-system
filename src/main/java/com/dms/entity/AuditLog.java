package com.dms.entity;

import com.dms.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private UUID documentId;

    @Column(nullable = false)
    private UUID actorId;

    @Enumerated(EnumType.STRING)
    private DocumentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private DocumentStatus toStatus;

    private String comment;

    private LocalDateTime occurredAt;

    @PrePersist
    public void prePersist() {
        occurredAt = LocalDateTime.now();
    }
}