package com.jesus.user.domain.role;

import com.jesus.common.base.core.model.GenerateId;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name= "role_resource")
@Data
public class RoleResource extends GenerateId<Long> {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permissions_id")
    private Long permissionsId;
}
