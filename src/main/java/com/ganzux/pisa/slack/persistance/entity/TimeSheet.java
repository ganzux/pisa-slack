package com.ganzux.pisa.slack.persistance.entity;

import com.ganzux.pisa.slack.persistance.dto.TimeSheetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "timesheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "duration_min", nullable = false)
    private int duration;

    @Size(max = 250)
    @Column(name = "comments")
    private String comments;

    @Column(name = "date_from", nullable = false)
    private Date dateFrom;

    @Column(name = "date_to", nullable = false)
    private Date dateTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    // TODO integrate with a Mapper
    public TimeSheetDto toDto() {

        TimeSheetDto timeSheet = new TimeSheetDto(id, project.toDto(), user.toDto(), duration, comments,
                dateFrom.toLocalDate(), dateTo.toLocalDate());

        return timeSheet;
    }

}
