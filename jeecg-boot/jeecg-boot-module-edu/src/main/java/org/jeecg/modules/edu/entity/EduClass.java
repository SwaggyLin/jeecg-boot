package org.jeecg.modules.edu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.aspect.annotation.RelativeData;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 班级
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@Data
@TableName("edu_class")
public class EduClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
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
	/**班级名称*/
	@Excel(name = "班级名称", width = 15)
    private java.lang.String className;
	/**学生人数*/
	@Excel(name = "学生人数", width = 15)
    private java.lang.Integer studentNum;
	/**班主任*/
	@Excel(name = "班主任", width = 15)
	@RelativeData(mainTable = "edu_teacher",fieldName = "teacher_name")
    private java.lang.String classTeacherId;
	/**年级*/
	@Excel(name = "年级", width = 15)
	@Dict(dicCode = "class_grade")
    private java.lang.String classGrade;
}
