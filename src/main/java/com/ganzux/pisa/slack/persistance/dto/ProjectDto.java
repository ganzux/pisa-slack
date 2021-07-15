package com.ganzux.pisa.slack.persistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private Long id;
    private String projectId;
    private String projectName;
    private String projectDescription;
}
