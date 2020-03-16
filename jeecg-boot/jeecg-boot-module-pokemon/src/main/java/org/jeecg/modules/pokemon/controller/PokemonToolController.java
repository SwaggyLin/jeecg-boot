package org.jeecg.modules.pokemon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.pokemon.entity.PokemonTool;
import org.jeecg.modules.pokemon.service.IPokemonToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 宝可梦道具
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
@RestController
@RequestMapping("/pokemon/pokemonTool")
@Slf4j
public class PokemonToolController extends JeecgController<PokemonTool, IPokemonToolService> {
	@Autowired
	private IPokemonToolService pokemonToolService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pokemonTool
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PokemonTool pokemonTool,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PokemonTool> queryWrapper = QueryGenerator.initQueryWrapper(pokemonTool, req.getParameterMap());
		Page<PokemonTool> page = new Page<PokemonTool>(pageNo, pageSize);
		IPage<PokemonTool> pageList = pokemonToolService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pokemonTool
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PokemonTool pokemonTool) {
		pokemonToolService.save(pokemonTool);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pokemonTool
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PokemonTool pokemonTool) {
		pokemonToolService.updateById(pokemonTool);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pokemonToolService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pokemonToolService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PokemonTool pokemonTool = pokemonToolService.getById(id);
		if(pokemonTool==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pokemonTool);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pokemonTool
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PokemonTool pokemonTool) {
        return super.exportXls(request, pokemonTool, PokemonTool.class, "宝可梦道具");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PokemonTool.class);
    }

}
