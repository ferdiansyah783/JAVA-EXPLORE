package com.domain.models.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ProjectRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String source;

    private Date start_date;

    private Date end_date;
}
