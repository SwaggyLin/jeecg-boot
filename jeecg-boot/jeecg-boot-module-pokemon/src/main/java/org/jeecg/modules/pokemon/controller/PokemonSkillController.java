package org.jeecg.modules.pokemon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.pokemon.entity.PokemonSkill;
import org.jeecg.modules.pokemon.service.IPokemonSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 宝可梦技能
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
@RestController
@RequestMapping("/pokemon/pokemonSkill")
@Slf4j
public class PokemonSkillController extends JeecgController<PokemonSkill, IPokemonSkillService> {
	@Autowired
	private IPokemonSkillService pokemonSkillService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pokemonSkill
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PokemonSkill pokemonSkill,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PokemonSkill> queryWrapper = QueryGenerator.initQueryWrapper(pokemonSkill, req.getParameterMap());
		Page<PokemonSkill> page = new Page<PokemonSkill>(pageNo, pageSize);
		IPage<PokemonSkill> pageList = pokemonSkillService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pokemonSkill
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PokemonSkill pokemonSkill) {
		pokemonSkillService.save(pokemonSkill);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pokemonSkill
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PokemonSkill pokemonSkill) {
		pokemonSkillService.updateById(pokemonSkill);
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
		pokemonSkillService.removeById(id);
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
		this.pokemonSkillService.removeByIds(Arrays.asList(ids.split(",")));
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
		PokemonSkill pokemonSkill = pokemonSkillService.getById(id);
		if(pokemonSkill==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pokemonSkill);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pokemonSkill
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PokemonSkill pokemonSkill) {
        return super.exportXls(request, pokemonSkill, PokemonSkill.class, "宝可梦技能");
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
        return super.importExcel(request, response, PokemonSkill.class);
    }

}
