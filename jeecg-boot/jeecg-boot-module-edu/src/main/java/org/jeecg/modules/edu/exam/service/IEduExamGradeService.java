package org.jeecg.modules.edu.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecg.modules.edu.exam.entity.vo.EduExamGradeVO;

/**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface IEduExamGradeService extends IService<EduExamGrade> {

    IPage<EduExamGradeVO> selectPage(Page<EduExamGradeVO> page, EduExamGrade eduExamGrade);
}
