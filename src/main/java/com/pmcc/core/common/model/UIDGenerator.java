package com.pmcc.core.common.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.AbstractUUIDGenerator;

import java.io.Serializable;

/**
 * @ClassName: UIDGenerator <br>
 * @Description: 自动生产ID串号
 * @Date: 2019/11/3 13:42 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class UIDGenerator extends AbstractUUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return 20 + format(getAppId()+getIP()) +
                format(getHiTime()) + format(getLoTime()) +
                format(getCount()).substring(0, 4);
    }

    protected short getAppId()
    {
        return 253;
    }

    protected static String format(int intValue) {
        String formatted = Integer.toHexString( intValue );
        StringBuilder buf = new StringBuilder( "00000000" );
        buf.replace( 8 - formatted.length(), 8, formatted );
        return buf.toString();
    }

    protected  static String format(short shortValue) {
        String formatted = Integer.toHexString( shortValue );
        StringBuilder buf = new StringBuilder( "0000" );
        buf.replace( 4 - formatted.length(), 4, formatted );
        return buf.toString();
    }
}
