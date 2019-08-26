package com.jesus.user.domain.logaction;

import com.jesus.common.base.core.model.GenerateId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "log_action")
public class LogAction extends GenerateId<Long> implements Serializable {

    @Column
    private String username;

    @Column
    private String uri;

    @Column
    private String ip;

    @Column
    private Integer type;

    @Column
    private String method;

}
