package com.ganzux.pisa.slack.persistance.service;

import com.ganzux.pisa.slack.persistance.dto.UserDto;

public interface UserService {

  UserDto get(String userId);

}
