package com.ganzux.sirme.slack.persistance.service;

import com.ganzux.sirme.slack.persistance.dto.UserDto;

public interface UserService {

  UserDto get(String userId);

}
