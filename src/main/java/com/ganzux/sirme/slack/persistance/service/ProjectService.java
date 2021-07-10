package com.ganzux.sirme.slack.persistance.service;

import com.ganzux.sirme.slack.persistance.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

  List<ProjectDto> findAll();

}
