package com.domain.models.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    private String type;

    @NotBlank
    @NonNull
    private byte[] data;
}
