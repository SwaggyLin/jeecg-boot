package org.jeecg.modules.edu.exam.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecg.modules.edu.exam.entity.vo.EduExamGradeVO;
import org.jeecg.modules.edu.exam.mapper.EduExamGradeMapper;
import org.jeecg.modules.edu.exam.service.IEduExamGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Service
public class EduExamGradeServiceImpl extends ServiceImpl<EduExamGradeMapper, EduExamGrade> implements IEduExamGradeService {
    @Autowired
    private EduExamGradeMapper eduExamGradeMapper;
    @Override
    public IPage<EduExamGradeVO> selectPage(Page<EduExamGradeVO> page,EduExamGrade eduExamGrade) {
        IPage<EduExamGradeVO> eduExamGradeVOList=eduExamGradeMapper.getExamGradeList(page,eduExamGrade);
        return eduExamGradeVOList;
    }
}
