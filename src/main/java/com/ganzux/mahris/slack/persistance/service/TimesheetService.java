package com.ganzux.mahris.slack.persistance.service;

import com.ganzux.mahris.slack.persistance.dto.NewTimeSheetRequest;
import com.ganzux.mahris.slack.persistance.dto.TimeSheetDto;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetService {

  List<TimeSheetDto> getLasts(String userId);

  List<TimeSheetDto> getLasts(String userId, LocalDate init, LocalDate end);

  int save(List<NewTimeSheetRequest> timeSheetDtos);

  int save(String userId, String projectId, String minutes, String comments, String dateFrom, String dateTo);

}
