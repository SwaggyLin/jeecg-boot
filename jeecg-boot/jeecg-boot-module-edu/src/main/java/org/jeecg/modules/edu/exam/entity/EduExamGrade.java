package org.jeecg.modules.edu.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.aspect.annotation.RelativeData;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("edu_exam_grade")
public class EduExamGrade implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**评卷人*/
	@Excel(name = "评卷人", width = 15)
    private java.lang.String createBy;
	/**评卷日期*/
	@Excel(name = "评卷日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
	/**考试id*/
	@Excel(name = "考试id", width = 15)
	@RelativeData(mainTable = "edu_exam",fieldName = "exam_name")
    private java.lang.String examId;
	/**学生id*/
	@Excel(name = "学生id", width = 15)
	@RelativeData(mainTable = "edu_student",fieldName = "student_name,seat_num")
	private java.lang.String studentId;
	/**班级id*/
	@RelativeData(mainTable = "edu_class",fieldName = "class_name")
	@Excel(name = "班级id", width = 15)
    private java.lang.String classId;
	/**成绩*/
	@Excel(name = "成绩", width = 15)
    private java.lang.Double grade;
}
