package org.jeecg.modules.edu.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.edu.user.entity.EduTeacher;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2020-03-18
 * @Version: V1.0
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    void addTeacherWithSubject(EduTeacher eduTeacher, String selectedSubjects);

    void editTeacherWithSubject(EduTeacher eduTeacher, String selectedSubjects);
}
