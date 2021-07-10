package com.ganzux.sirme.slack.persistance.entity;

import com.ganzux.sirme.slack.persistance.dto.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, updatable = false, nullable = false)
  private Long id;

  @Size(min = 1, max = 50)
  @Column(name = "project_id", unique = true)
  private String projectId;

  @Size(min = 1, max = 100)
  @Column(name = "project_name")
  private String projectName;

  @Size(min = 0, max = 250)
  @Column(name = "project_description")
  private String projectDescription;


  // TODO integrate with a Mapper
  public ProjectDto toDto() {

    ProjectDto projectDto = new ProjectDto(id, projectId, projectName, projectDescription);

    return projectDto;
  }

}
