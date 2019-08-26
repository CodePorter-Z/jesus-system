package com.jesus.user.domain.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jesus.common.base.core.model.GenerateId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Role extends GenerateId<Long> implements Serializable {

    @Column(name = "role_type")
    private String roleType;

    @Column(name = "role_name")
    private String roleName;

    @Enumerated(EnumType.STRING)
    @Column
    private State state;

    /**
     * 使用资源IDs替代中间表
     */
    @Column(name = "resource_ids", length = 255)
    private String resourceIds;

    /**
     * 状态枚举
     */
    public enum State {

        ENABLED("启用"), DISABLED("禁用"), DELETED("异常");

        private String desc;

        State(String desc) {
            this.desc = desc;
        }
    }
}
