package org.jeecg.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edu.entity.EduTeacher;
import org.jeecg.modules.edu.entity.EduTeacherSubject;
import org.jeecg.modules.edu.mapper.EduTeacherMapper;
import org.jeecg.modules.edu.mapper.EduTeacherSubjectMapper;
import org.jeecg.modules.edu.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2020-03-18
 * @Version: V1.0
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {
    @Autowired
    private EduTeacherSubjectMapper eduTeacherSubjectMapper;
    @Override
    public void addTeacherWithSubject(EduTeacher eduTeacher, String selectedSubjects) {
        this.save(eduTeacher);
        if(oConvertUtils.isNotEmpty(selectedSubjects)) {
            String[] arr = selectedSubjects.split(",");
            for (String subjectId : arr) {
                EduTeacherSubject teacherSubject = new EduTeacherSubject(eduTeacher.getId(), subjectId);
                eduTeacherSubjectMapper.insert(teacherSubject);
            }
        }
    }

    @Override
    public void editTeacherWithSubject(EduTeacher eduTeacher, String selectedSubjects) {
        this.updateById(eduTeacher);
        eduTeacherSubjectMapper.delete(new QueryWrapper<EduTeacherSubject>().lambda().eq(EduTeacherSubject::getTeacherId, eduTeacher.getId()));
        if(oConvertUtils.isNotEmpty(selectedSubjects)) {
            String[] arr = selectedSubjects.split(",");
            for (String subjectId : arr) {
                EduTeacherSubject teacherSubject = new EduTeacherSubject(eduTeacher.getId(), subjectId);
                eduTeacherSubjectMapper.insert(teacherSubject);
            }
        }
    }
}
