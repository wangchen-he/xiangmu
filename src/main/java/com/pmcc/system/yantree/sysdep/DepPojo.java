package com.pmcc.system.yantree.sysdep;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName: SysDep <br>
 * @Description: TODO(组织机构)
 * @Date: 2020/2/5 14:30 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 *
 *  * READ_ONLY 只读模式，在此模式下，如果对数据进行更新操作，会有异常；
 *  * READ_WRITE 读写模式在更新缓存的时候会把缓存里面的数据换成一个锁，
 *    其它事务如果去取相应的缓存数据，发现被锁了，直接就去数据库查询；
 *  * NONSTRICT_READ_WRITE 不严格的读写模式则不会的缓存数据加锁；
 *  DynamicUpdate: 只更新对象有改变的字段,默认是false;
 *  DynamicInsert: 只是插入那些不为空的字段默认是false;
 *
 */
@Entity
@Table(name ="sys_dep")
public class DepPojo extends CommonEntity {

    private String depCName;        //中文名
    private String icon;            //图标
    private int status;            //用户状态：1正常、0冻结(冻结状态不可删除)、2删除



    @Column(name = "dep_cname")
    public String getDepCName() {
        return depCName;
    }

    public void setDepCName(String depCName) {
        this.depCName = depCName;
    }


    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }







}
