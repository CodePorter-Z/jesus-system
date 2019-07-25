package com.jesus.user.domain.resource;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.jesus.common.base.core.model.GenerateId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
//配置为null的对象不参与序列化
//返回时尽量不要有null，可有为空串“” 或者 0 或者 []， 但尽量不要null。
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Resource extends GenerateId<Long> implements Serializable {

    @Column(name = "resource_name")
    private String resourceName;

    @Column
    private String url;

    @Column
    private String permission;

    @Column
    private Type type;

    public enum Type {
        Menu(1), Button(0);

        private Integer type;

        Type(Integer type) {
            this.type = type;
        }
    }
}
