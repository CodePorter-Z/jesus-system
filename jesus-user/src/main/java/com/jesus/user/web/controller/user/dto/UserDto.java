package com.jesus.user.web.controller.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String mobile;
    private String status;
    private String roleId;

}
