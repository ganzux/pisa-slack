package com.ganzux.sirme.slack.controller;

import com.ganzux.sirme.slack.persistance.dto.NewTimeSheetRequest;
import com.ganzux.sirme.slack.persistance.dto.ProjectDto;
import com.ganzux.sirme.slack.persistance.dto.TimeSheetDto;
import com.ganzux.sirme.slack.persistance.dto.UserDto;
import com.ganzux.sirme.slack.persistance.service.ProjectService;
import com.ganzux.sirme.slack.persistance.service.TimesheetService;
import com.ganzux.sirme.slack.persistance.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mainframe")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TimesheetService timesheetService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<UserDto> getUserInfo(@PathVariable String userId) {
        LOGGER.info("getLastCrosswalkFile operation invoked {}", userId);

        UserDto userDto;

        try {
            userDto = userService.get(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Found crosswalk file operation {}", userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/projects", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<ProjectDto>> getAllProjects() {
        LOGGER.info("getLastCrosswalkFile operation invoked");

        List<ProjectDto> allProjects;

        try {
            allProjects = projectService.findAll();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Found crosswalk file operation ");

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/ts/{userId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<TimeSheetDto>> getLastTimeSheet(@PathVariable String userId) {
        LOGGER.info("getLastCrosswalkFile operation invoked");

        List<TimeSheetDto> lastTimeSheets;

        try {
            lastTimeSheets = timesheetService.getLasts(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Found crosswalk file operation ");

        return new ResponseEntity<>(lastTimeSheets, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/ts")
    public ResponseEntity<String> addNewRow(@RequestBody @Valid List<NewTimeSheetRequest> timeSheetRequests)
            throws Exception {

        timesheetService.save(timeSheetRequests);

        return new ResponseEntity<String>("OK", HttpStatus.CREATED);
    }

}
