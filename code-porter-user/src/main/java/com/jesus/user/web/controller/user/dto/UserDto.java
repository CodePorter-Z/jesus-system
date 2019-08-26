package com.jesus.user.web.controller.user.dto;

import com.jesus.user.domain.user.User;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String mobile;
    private User.State state;
    private String roleId;
    private String avatar;
    private String state_desc;

}
