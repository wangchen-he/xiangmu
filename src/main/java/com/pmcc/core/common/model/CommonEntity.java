package com.pmcc.core.common.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @ClassName: IdEntity <br>
 * @Description: TODO(注入生成的序列ID值)
 * @Date: 2019/11/3 13:49 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@MappedSuperclass
public abstract class CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;

    /**
     *  适用默认策略 则更改为  org.hibernate.id.UUIDGenerator
     *                  或者 org.hibernate.id.UUIDHexGenerator
     *  自定义ID策略   com.core.common.model.UIDGenerator
     */
    @Id
    @Column(name="oid")
    @GeneratedValue(generator="UIDGenerator") //定义一个主键生成策略
    @GenericGenerator(name="UIDGenerator", strategy="com.pmcc.core.common.model.UIDGenerator")
    public String getId()
    {
        return this.id;
    }

    public void setId(String _id) {
        this.id = _id;
    }
}
