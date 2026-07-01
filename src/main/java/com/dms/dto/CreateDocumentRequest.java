package com.dms.dto;

import com.dms.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateDocumentRequest {

    @NotBlank
    private String title;

    @NotNull
    private DocumentType type;

    @NotNull
    private UUID authorId;
}