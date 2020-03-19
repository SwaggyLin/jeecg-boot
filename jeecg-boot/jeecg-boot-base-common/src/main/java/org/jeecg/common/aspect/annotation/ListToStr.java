package org.jeecg.common.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *  类描述:   List转字符串注解
 * 作    者： Swaggy Lin
 * 日    期： 2020年03月19日-上午10:54:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListToStr {
    /**
     * 需要转换为字符串的属性名称
     * @return
     */
    String fieldName();

}
