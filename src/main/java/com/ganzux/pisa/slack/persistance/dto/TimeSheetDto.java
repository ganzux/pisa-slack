package com.ganzux.pisa.slack.persistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetDto {

    private Long id;
    private ProjectDto project;
    private UserDto user;
    private int duration;
    private String comments;

    private LocalDate timeFrom;
    private LocalDate timeTo;
}
