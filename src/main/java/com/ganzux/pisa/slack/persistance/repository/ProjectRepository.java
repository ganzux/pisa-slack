package com.ganzux.pisa.slack.persistance.repository;

import com.ganzux.pisa.slack.persistance.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
