package com.dms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SubmitRequest {

    @NotNull
    private UUID reviewerId;

    @NotNull
    private UUID actorId;
}