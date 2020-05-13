package org.jeecg.modules.edu.exam.entity.vo;

import lombok.Data;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description
 * @Author linminfeng
 * @Date 2020-05-12 14:26
 */
@Data
public class EduExamGradeVO extends EduExamGrade {
    @Excel(name = "学生", width = 15)
    private java.lang.String studentName;
    @Excel(name = "座号", width = 15)
    private java.lang.String seatNum;
}
