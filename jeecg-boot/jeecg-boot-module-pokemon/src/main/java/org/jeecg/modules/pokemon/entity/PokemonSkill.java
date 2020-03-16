package org.jeecg.modules.pokemon.entity;

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
 * @Description: 宝可梦技能
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
@Data
@TableName("pokemon_skill")
public class PokemonSkill implements Serializable {
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
	/**地区*/
	@Excel(name = "地区", width = 15)
    private java.lang.String area;
	/**技能编号*/
	@Excel(name = "技能编号", width = 15)
    private java.lang.String skillNo;
	/**技能名称*/
	@Excel(name = "技能名称", width = 15)
    private java.lang.String skillName;
	/**日文名*/
	@Excel(name = "日文名", width = 15)
    private java.lang.String japaneseName;
	/**英文名字*/
	@Excel(name = "英文名字", width = 15)
    private java.lang.String englishName;
	/**技能属性*/
	@Excel(name = "技能属性", width = 15)
    private java.lang.String skillAttr;
	/**技能类型*/
	@Excel(name = "技能类型", width = 15)
    private java.lang.String skillType;
	/**威力*/
	@Excel(name = "威力", width = 15)
    private java.lang.String power;
	/**命中率*/
	@Excel(name = "命中率", width = 15)
    private java.lang.String hitRate;
	/**pp*/
	@Excel(name = "pp", width = 15)
    private java.lang.String skillPp;
}
