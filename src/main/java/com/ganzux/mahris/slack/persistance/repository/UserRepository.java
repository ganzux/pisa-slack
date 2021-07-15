package com.ganzux.mahris.slack.persistance.repository;


import com.ganzux.mahris.slack.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User getUserByUserId(String userId);

}
