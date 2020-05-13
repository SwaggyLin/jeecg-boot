package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Repository
public interface SysCommonMapper {


	Map<String,Object> getRelativeData(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("primaryKey") String primaryKey, @Param("relativeKey") String relativeKey);
}
