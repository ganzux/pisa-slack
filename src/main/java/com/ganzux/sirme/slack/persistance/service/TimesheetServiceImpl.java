package com.ganzux.sirme.slack.persistance.service;

import com.ganzux.sirme.slack.persistance.dto.NewTimeSheetRequest;
import com.ganzux.sirme.slack.persistance.dto.ProjectDto;
import com.ganzux.sirme.slack.persistance.dto.TimeSheetDto;
import com.ganzux.sirme.slack.persistance.dto.UserDto;
import com.ganzux.sirme.slack.persistance.entity.Project;
import com.ganzux.sirme.slack.persistance.entity.TimeSheet;
import com.ganzux.sirme.slack.persistance.entity.User;
import com.ganzux.sirme.slack.persistance.repository.ProjectRepository;
import com.ganzux.sirme.slack.persistance.repository.TimesheetRepository;
import com.ganzux.sirme.slack.persistance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimesheetServiceImpl implements TimesheetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TimesheetServiceImpl.class);

  @Autowired private TimesheetRepository timesheetRepository;

  @Override
  public List<TimeSheetDto> getLasts(String userId) {
    LOGGER.info("get operation invoked");

    List<TimeSheet> alTimeSheet = timesheetRepository.findTop10ByUserUserIdOrderByDateToDesc(userId);

    List<TimeSheetDto> alTimeSheetDtos = alTimeSheet.stream()
            .map(timeSheet -> timeSheet.toDto())
            .collect(Collectors.toList());

    return alTimeSheetDtos;
  }

  @Override
  @Transactional
  public int save(List<NewTimeSheetRequest> timeSheetDtos) {

    for (NewTimeSheetRequest timeSheetDto : timeSheetDtos) {

      TimeSheet t = new TimeSheet();
      t.setDuration(timeSheetDto.getDuration());
      t.setComments(timeSheetDto.getComments());
      t.setDateFrom(Date.valueOf(timeSheetDto.getTimeFrom()));
      t.setDateTo(Date.valueOf(timeSheetDto.getTimeTo()));

      User u = new User();
      u.setId(timeSheetDto.getUser());
      t.setUser(u);

      Project p = new Project();
      p.setId(timeSheetDto.getProject());
      t.setProject(p);

      t = timesheetRepository.save(t);
      System.out.println(t.getId());
    }

    return 0;
  }

}
