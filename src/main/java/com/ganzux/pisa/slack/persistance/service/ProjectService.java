package com.ganzux.pisa.slack.persistance.service;

import com.ganzux.pisa.slack.persistance.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

  List<ProjectDto> findAll();

}
