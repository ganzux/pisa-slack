package com.ganzux.pisa.slack.persistance.service;

import com.ganzux.pisa.slack.persistance.dto.NewTimeSheetRequest;
import com.ganzux.pisa.slack.persistance.dto.TimeSheetDto;
import com.ganzux.pisa.slack.persistance.entity.Project;
import com.ganzux.pisa.slack.persistance.entity.TimeSheet;
import com.ganzux.pisa.slack.persistance.entity.User;
import com.ganzux.pisa.slack.persistance.repository.TimesheetRepository;
import com.ganzux.pisa.slack.persistance.repository.UserRepository;
import com.ganzux.pisa.slack.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimesheetServiceImpl implements TimesheetService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TimesheetServiceImpl.class);

  @Autowired private TimesheetRepository timesheetRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private TimeUtil timeUtil;

  @Override
  public List<TimeSheetDto> getLasts(String userId, LocalDate init, LocalDate end) {
    LOGGER.info("get operation invoked");

    List<TimeSheet> alTimeSheet = timesheetRepository.findAllByUserUserIdAndDateFromIsBetweenAndDateToIsBetween(
            userId,
            Date.valueOf(init),
            Date.valueOf(end),
            Date.valueOf(init),
            Date.valueOf(end));

    List<TimeSheetDto> alTimeSheetDtos = alTimeSheet.stream()
            .map(timeSheet -> timeSheet.toDto())
            .collect(Collectors.toList());

    return alTimeSheetDtos;
  }

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
  @Transactional(rollbackFor = Exception.class)
  public int save(String userId, String projectId, String minutes, String comments, String dateFrom, String dateTo) {

    User u = userRepository.getUserByUserId(userId);

    NewTimeSheetRequest newTimeSheetRequest = new NewTimeSheetRequest();

    newTimeSheetRequest.setUser(u.getId());
    newTimeSheetRequest.setProject(Long.valueOf(projectId));
    newTimeSheetRequest.setComments(comments);
    newTimeSheetRequest.setDuration(Integer.valueOf(minutes));
    newTimeSheetRequest.setTimeFrom(timeUtil.transformFromView(dateFrom));
    newTimeSheetRequest.setTimeTo(timeUtil.transformFromView(dateTo));

    return save(Arrays.asList(newTimeSheetRequest));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
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
