package org.jeecg.modules.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.entity.EduClass;
import org.jeecg.modules.edu.service.IEduClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 班级
 * @Author: jeecg-boot
 * @Date:   2020-03-20
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/eduClass")
@Slf4j
public class EduClassController extends JeecgController<EduClass, IEduClassService> {
	@Autowired
	private IEduClassService eduClassService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduClass
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduClass eduClass,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduClass> queryWrapper = QueryGenerator.initQueryWrapper(eduClass, req.getParameterMap());
		Page<EduClass> page = new Page<EduClass>(pageNo, pageSize);
		IPage<EduClass> pageList = eduClassService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduClass
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduClass eduClass) {
		eduClassService.save(eduClass);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduClass
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduClass eduClass) {
		eduClassService.updateById(eduClass);
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
		eduClassService.removeById(id);
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
		this.eduClassService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduClass eduClass = eduClassService.getById(id);
		if(eduClass==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduClass);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduClass
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduClass eduClass) {
        return super.exportXls(request, eduClass, EduClass.class, "班级");
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
        return super.importExcel(request, response, EduClass.class);
    }

}
