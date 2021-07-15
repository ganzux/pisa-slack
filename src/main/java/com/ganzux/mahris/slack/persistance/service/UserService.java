package com.ganzux.mahris.slack.persistance.service;

import com.ganzux.mahris.slack.persistance.dto.UserDto;

public interface UserService {

  UserDto get(String userId);

}
