package com.ganzux.mahris.slack.persistance.service;

import com.ganzux.mahris.slack.persistance.dto.ProjectDto;
import com.ganzux.mahris.slack.persistance.entity.Project;
import com.ganzux.mahris.slack.persistance.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

  @Autowired private ProjectRepository projectRepository;

  @Override
  public List<ProjectDto> findAll() {
    LOGGER.info("get operation invoked");

    List<Project> allProjects = projectRepository.findAll();


    List<ProjectDto> allProjectsDto = allProjects.stream()
            .map(project -> new ProjectDto(project.getId(), project.getProjectId(), project.getProjectName(), project.getProjectDescription()))
            .collect(Collectors.toList());

    return allProjectsDto;
  }

}
