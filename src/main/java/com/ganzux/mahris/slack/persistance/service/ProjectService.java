package com.ganzux.mahris.slack.persistance.service;

import com.ganzux.mahris.slack.persistance.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

  List<ProjectDto> findAll();

}
