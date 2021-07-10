package com.ganzux.sirme.slack.persistance.repository;

import com.ganzux.sirme.slack.persistance.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<TimeSheet, Long> {

    List<TimeSheet> findTop10ByUserUserIdOrderByDateToDesc(String userId);

}
