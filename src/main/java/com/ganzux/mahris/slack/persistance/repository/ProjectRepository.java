package com.ganzux.mahris.slack.persistance.repository;

import com.ganzux.mahris.slack.persistance.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
