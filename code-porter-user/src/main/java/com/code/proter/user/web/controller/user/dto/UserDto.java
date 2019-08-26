package com.code.proter.user.web.controller.user.dto;

import com.code.proter.user.domain.user.User;
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
