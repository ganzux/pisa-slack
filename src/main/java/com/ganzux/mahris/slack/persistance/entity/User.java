package com.ganzux.mahris.slack.persistance.entity;

import com.ganzux.mahris.slack.persistance.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, updatable = false, nullable = false)
  private Long id;

  @Size(min = 1, max = 15)
  @Column(name = "user_id", unique = true)
  private String userId;

  @Size(min = 1, max = 20)
  @Column(name = "first_name")
  private String firstName;

  @Size(min = 1, max = 50)
  @Column(name = "last_name")
  private String lastName;

  @Size(min = 1, max = 100)
  @Column(name = "career")
  private String career;

  @ManyToOne
  @JoinColumn(name = "manager")
  private User manager;

  // TODO integrate with a Mapper
  public UserDto toDto() {

    UserDto userDto = new UserDto(id, userId, firstName, lastName, career, null);
    // Infinite loop if we don't check id
    if (null != manager && !id.equals(manager.getId())) {
        userDto.setManager(manager.toDto());
    }

    return userDto;
  }

}
