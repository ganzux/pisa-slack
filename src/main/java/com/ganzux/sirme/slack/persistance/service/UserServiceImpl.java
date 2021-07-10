package com.ganzux.sirme.slack.persistance.service;

import com.ganzux.sirme.slack.persistance.dto.UserDto;
import com.ganzux.sirme.slack.persistance.entity.User;
import com.ganzux.sirme.slack.persistance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired private UserRepository userRepository;

  @Override
  public UserDto get(String userId) {
    LOGGER.info("get operation invoked {}", userId);

    User user = userRepository.getUserByUserId(userId);
    UserDto userDto = user.toDto();

    return userDto;
  }

}
