package com.lly.networklib.annotation;

import com.lly.networklib.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NetWork[v 1.0.0]
 * classes:network.lly.com.networklib.annotation.NetWork
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 11:06
 * @description
 */
@Target(ElementType.METHOD)//目标作用在方法之上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface NetWork {

    NetType netType() default NetType.All;
}
