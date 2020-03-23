package org.jeecg.modules.edu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edu.entity.EduStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-21
 * @Version: V1.0
 */
@Repository
public interface EduStudentMapper extends BaseMapper<EduStudent> {

}
