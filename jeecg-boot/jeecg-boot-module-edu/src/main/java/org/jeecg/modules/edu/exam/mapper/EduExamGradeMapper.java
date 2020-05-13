package org.jeecg.modules.edu.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecg.modules.edu.exam.entity.vo.EduExamGradeVO;
import org.springframework.stereotype.Repository;


/**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Repository
public interface EduExamGradeMapper extends BaseMapper<EduExamGrade> {
    IPage<EduExamGradeVO> getExamGradeList(@Param("eduExamGrade") EduExamGrade eduExamGrade);
}
