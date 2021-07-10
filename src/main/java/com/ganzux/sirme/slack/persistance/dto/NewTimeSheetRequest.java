package com.ganzux.sirme.slack.persistance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTimeSheetRequest {

    private Long project;
    private Long user;
    private int duration;
    private String comments;

    @JsonFormat(pattern="yyyy/MM/dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate timeFrom;

    @JsonFormat(pattern="yyyy/MM/dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate timeTo;

}
