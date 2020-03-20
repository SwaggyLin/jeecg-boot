package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Repository
public interface SysCommonMapper {


	String getRelativeData(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("primaryKey") String primaryKey, @Param("relativeKey") String relativeKey);
}
