package com.dms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RejectRequest {

    @NotBlank
    private String comment;

    @NotNull
    private UUID actorId;
}
