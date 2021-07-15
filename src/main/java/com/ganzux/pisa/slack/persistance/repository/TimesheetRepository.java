package com.ganzux.pisa.slack.persistance.repository;

import com.ganzux.pisa.slack.persistance.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<TimeSheet, Long> {

    List<TimeSheet> findTop10ByUserUserIdOrderByDateToDesc(String userId);

    List<TimeSheet> findAllByUserUserIdAndDateFromIsBetweenAndDateToIsBetween(String userId,
                                                                              Date periodInitA, Date periodEndB,
                                                                              Date periodInitC, Date periodEndD);

}
