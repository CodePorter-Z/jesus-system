package com.jesus.user.model.role.dto;

import lombok.Data;

@Data
public class RoleDto {

    private Long id;
    private Integer roleType;
    private String roleName;
    private String status;
}
