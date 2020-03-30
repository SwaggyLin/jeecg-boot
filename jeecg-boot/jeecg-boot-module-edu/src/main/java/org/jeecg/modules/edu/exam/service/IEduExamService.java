package org.jeecg.modules.edu.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.edu.exam.entity.EduExam;

/**
 * @Description: 考试表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface IEduExamService extends IService<EduExam> {
    void addExamWithClass(EduExam eduExam, String selectedClasses);

    void editExamWithClass(EduExam eduExam, String selectedClasses);

}
