package org.jeecg.common.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *  类描述:   管理表数据
 * 作    者： Swaggy Lin
 * 日    期： 2020年03月19日-上午10:54:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelativeData {
    /**
     * 主表名称
     * @return
     */
    String mainTable();
    /**
     * 关联字段名称
     * @return
     */
    String fieldName();
    /**
     * 主键名称
     * @return
     */
    String primaryKey() default "id";

}
