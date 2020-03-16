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
 * @Description: 宝可梦道具
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
@Data
@TableName("pokemon_tool")
public class PokemonTool implements Serializable {
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
	/**道具类型*/
	@Excel(name = "道具类型", width = 15)
    private java.lang.String toolType;
	/**道具名称*/
	@Excel(name = "道具名称", width = 15)
    private java.lang.String toolName;
	/**日文名称*/
	@Excel(name = "日文名称", width = 15)
    private java.lang.String japaneseName;
	/**英文名称*/
	@Excel(name = "英文名称", width = 15)
    private java.lang.String englishName;
	/**道具描述*/
	@Excel(name = "道具描述", width = 15)
    private java.lang.String toolDesc;
	/**图片地址*/
	@Excel(name = "图片地址", width = 15)
    private java.lang.String imgUrl;
}
