package org.jeecg.modules.edu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2020-03-18
 * @Version: V1.0
 */
@Data
@TableName("edu_teacher")
public class EduTeacher implements Serializable {
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
	/**教师名称*/
	@Excel(name = "教师名称", width = 15)
    private java.lang.String teacherName;
	/**头像*/
	@Excel(name = "头像", width = 15)
    private java.lang.String avatar;
	/**性别*/
	@Excel(name = "性别", width = 15)
	@Dict(dicCode = "sex")
    private java.lang.String sex;
	/**电话*/
	@Excel(name = "电话", width = 15)
    private java.lang.String mobile;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    private java.lang.String email;
}
