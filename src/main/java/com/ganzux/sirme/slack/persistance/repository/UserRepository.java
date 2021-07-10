package com.ganzux.sirme.slack.persistance.repository;


import com.ganzux.sirme.slack.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User getUserByUserId(String userId);

}
