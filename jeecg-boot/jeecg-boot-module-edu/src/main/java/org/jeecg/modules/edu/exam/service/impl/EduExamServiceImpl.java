package org.jeecg.modules.edu.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edu.exam.entity.EduExam;
import org.jeecg.modules.edu.exam.entity.EduExamClass;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecg.modules.edu.exam.mapper.EduExamClassMapper;
import org.jeecg.modules.edu.exam.mapper.EduExamGradeMapper;
import org.jeecg.modules.edu.exam.mapper.EduExamMapper;
import org.jeecg.modules.edu.exam.service.IEduExamService;
import org.jeecg.modules.edu.user.entity.EduStudent;
import org.jeecg.modules.edu.user.mapper.EduStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 考试表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Service
public class EduExamServiceImpl extends ServiceImpl<EduExamMapper, EduExam> implements IEduExamService {
    @Autowired
    private EduExamClassMapper eduExamClassMapper;
    @Autowired
    private EduStudentMapper eduStudentMapper;
    @Autowired
    private EduExamGradeMapper eduExamGradeMapper;

    @Override
    public void addExamWithClass(EduExam eduExam, String selectedClasses) {
        //保存考试信息
        this.save(eduExam);
        if(oConvertUtils.isNotEmpty(selectedClasses)) {
            String[] arr = selectedClasses.split(",");
            for (String classId : arr) {
                //保存考试班级信息
                EduExamClass examClass = new EduExamClass(eduExam.getId(), classId);
                eduExamClassMapper.insert(examClass);
                //提前生成成绩信息
                List<EduStudent> eduStudents=eduStudentMapper.selectList(new LambdaQueryWrapper<EduStudent>().eq(EduStudent::getClassId,classId));
                eduStudents.stream().forEach(eduStudent -> eduExamGradeMapper.insert(EduExamGrade.builder().examId(eduExam.getId()).classId(classId).studentId(eduStudent.getId()).build()));
            }

        }
    }

    @Override
    public void editExamWithClass(EduExam eduExam, String selectedClasses) {
        this.updateById(eduExam);
        eduExamClassMapper.delete(new QueryWrapper<EduExamClass>().lambda().eq(EduExamClass::getExamId, eduExam.getId()));
        eduExamGradeMapper.delete(new QueryWrapper<EduExamGrade>().lambda().eq(EduExamGrade::getExamId, eduExam.getId()));
        if(oConvertUtils.isNotEmpty(selectedClasses)) {
            String[] arr = selectedClasses.split(",");
            for (String classId : arr) {
                EduExamClass examClass = new EduExamClass(eduExam.getId(), classId);
                eduExamClassMapper.insert(examClass);
                //提前生成成绩信息
                List<EduStudent> eduStudents=eduStudentMapper.selectList(new LambdaQueryWrapper<EduStudent>().eq(EduStudent::getClassId,classId));
                eduStudents.stream().forEach(eduStudent -> eduExamGradeMapper.insert(EduExamGrade.builder().examId(eduExam.getId()).classId(classId).studentId(eduStudent.getId()).build()));
            }
        }
    }

}
