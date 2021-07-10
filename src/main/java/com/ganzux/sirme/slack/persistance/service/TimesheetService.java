package com.ganzux.sirme.slack.persistance.service;

import com.ganzux.sirme.slack.persistance.dto.NewTimeSheetRequest;
import com.ganzux.sirme.slack.persistance.dto.TimeSheetDto;

import java.util.List;

public interface TimesheetService {

  List<TimeSheetDto> getLasts(String userId);

  int save(List<NewTimeSheetRequest> timeSheetDtos);

}
