package org.jeecg.modules.edu.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edu.exam.entity.EduExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * @Description: 考试表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Repository
public interface EduExamMapper extends BaseMapper<EduExam> {

}
