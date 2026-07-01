package com.dms.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ApproveReviewRequest {

    @NotNull
    private UUID approverId;

    @NotNull
    private UUID actorId;
}
