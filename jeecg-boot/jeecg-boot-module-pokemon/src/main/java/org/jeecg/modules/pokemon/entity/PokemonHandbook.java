package org.jeecg.modules.pokemon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 宝可梦图鉴
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@Data
@TableName("pokemon_handbook")
public class PokemonHandbook implements Serializable {
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
	/**图鉴id*/
	@Excel(name = "图鉴id", width = 15)
    private java.lang.String zukanId;
	/**图鉴细级id*/
	@Excel(name = "图鉴细级id", width = 15)
    private java.lang.String zukanSubId;
	/**宝可梦名称*/
	@Excel(name = "宝可梦名称", width = 15)
    private java.lang.String pokemonName;
	/**详细名称*/
	@Excel(name = "详细名称", width = 15)
    private java.lang.String pokemonSubName;
	/**宝可梦重量*/
	@Excel(name = "宝可梦重量", width = 15)
    private java.lang.Double weight;
	/**宝可梦高度*/
	@Excel(name = "宝可梦高度", width = 15)
    private java.lang.Double height;
	/**图片地址*/
	@Excel(name = "图片地址", width = 15)
    private java.lang.String fileName;
	/**宝可梦类型*/
	@Excel(name = "宝可梦类型", width = 15)
    private java.lang.String pokemonTypeId;
	/**宝可梦类型名称*/
	@Excel(name = "宝可梦类型名称", width = 15)
    private java.lang.String pokemonTypeName;
}
