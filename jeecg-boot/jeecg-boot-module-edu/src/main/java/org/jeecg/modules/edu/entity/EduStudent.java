package org.jeecg.modules.edu.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-21
 * @Version: V1.0
 */
@Data
@TableName("edu_student")
public class EduStudent implements Serializable {
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
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    private java.lang.String studentName;
	/**性别*/
	@Excel(name = "性别", width = 15)
    private java.lang.String sex;
	/**出生年月*/
	@Excel(name = "出生年月", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date birthDate;
	/**家庭住址*/
	@Excel(name = "家庭住址", width = 15)
    private java.lang.String address;
	/**家长姓名*/
	@Excel(name = "家长姓名", width = 15)
    private java.lang.String parentName;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    private java.lang.String mobile;
	/**座号*/
	@Excel(name = "座号", width = 15)
    private java.lang.String seatNum;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    private java.lang.Integer age;
	/**所属班级*/
	@Excel(name = "所属班级", width = 15)
    private java.lang.String classId;
}
