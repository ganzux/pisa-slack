package com.ganzux.sirme.slack.persistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String userId;
    private String firstName;
    private String last_name;
    private String career;
    private UserDto manager;
}
