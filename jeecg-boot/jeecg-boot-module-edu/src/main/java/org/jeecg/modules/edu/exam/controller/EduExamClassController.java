package org.jeecg.modules.edu.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.exam.entity.EduExamClass;
import org.jeecg.modules.edu.exam.service.IEduExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 考试班级表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/exam/eduExamClass")
@Slf4j
public class EduExamClassController extends JeecgController<EduExamClass, IEduExamClassService> {
	@Autowired
	private IEduExamClassService eduExamClassService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduExamClass
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduExamClass eduExamClass,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduExamClass> queryWrapper = QueryGenerator.initQueryWrapper(eduExamClass, req.getParameterMap());
		Page<EduExamClass> page = new Page<EduExamClass>(pageNo, pageSize);
		IPage<EduExamClass> pageList = eduExamClassService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduExamClass
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduExamClass eduExamClass) {
		eduExamClassService.save(eduExamClass);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduExamClass
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduExamClass eduExamClass) {
		eduExamClassService.updateById(eduExamClass);
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
		eduExamClassService.removeById(id);
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
		this.eduExamClassService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduExamClass eduExamClass = eduExamClassService.getById(id);
		if(eduExamClass==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduExamClass);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduExamClass
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduExamClass eduExamClass) {
        return super.exportXls(request, eduExamClass, EduExamClass.class, "考试班级表");
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
        return super.importExcel(request, response, EduExamClass.class);
    }

}
