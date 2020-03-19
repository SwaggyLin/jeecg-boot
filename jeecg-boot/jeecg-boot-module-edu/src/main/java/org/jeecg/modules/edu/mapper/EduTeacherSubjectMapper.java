package org.jeecg.modules.edu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edu.entity.EduTeacherSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 教师科目关联表
 * @Author: jeecg-boot
 * @Date:   2020-03-19
 * @Version: V1.0
 */
@Repository
public interface EduTeacherSubjectMapper extends BaseMapper<EduTeacherSubject> {

}
