package org.jeecg.modules.pokemon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.pokemon.entity.PokemonHandbook;
import org.jeecg.modules.pokemon.service.IPokemonHandbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 宝可梦图鉴
 * @Author: jeecg-boot
 * @Date:   2019-11-19
 * @Version: V1.0
 */
@RestController
@RequestMapping("/pokemon/pokemonHandbook")
@Slf4j
public class PokemonHandbookController extends JeecgController<PokemonHandbook, IPokemonHandbookService> {
	@Autowired
	private IPokemonHandbookService pokemonHandbookService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pokemonHandbook
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PokemonHandbook pokemonHandbook,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PokemonHandbook> queryWrapper = QueryGenerator.initQueryWrapper(pokemonHandbook, req.getParameterMap());
		Page<PokemonHandbook> page = new Page<PokemonHandbook>(pageNo, pageSize);
		IPage<PokemonHandbook> pageList = pokemonHandbookService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pokemonHandbook
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PokemonHandbook pokemonHandbook) {
		pokemonHandbookService.save(pokemonHandbook);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pokemonHandbook
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PokemonHandbook pokemonHandbook) {
		pokemonHandbookService.updateById(pokemonHandbook);
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
		pokemonHandbookService.removeById(id);
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
		this.pokemonHandbookService.removeByIds(Arrays.asList(ids.split(",")));
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
		PokemonHandbook pokemonHandbook = pokemonHandbookService.getById(id);
		if(pokemonHandbook==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pokemonHandbook);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pokemonHandbook
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PokemonHandbook pokemonHandbook) {
        return super.exportXls(request, pokemonHandbook, PokemonHandbook.class, "宝可梦图鉴");
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
        return super.importExcel(request, response, PokemonHandbook.class);
    }

}
