package org.pragadeesh.filesharing.dto;

import lombok.Data;
import org.pragadeesh.filesharing.model.Role;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;
}
