package org.jeecg.modules.edu.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Repository
public interface EduExamGradeMapper extends BaseMapper<EduExamGrade> {

}
