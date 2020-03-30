package org.jeecg.modules.edu.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.exam.entity.EduExamGrade;
import org.jeecg.modules.edu.exam.service.IEduExamGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 考试成绩表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/exam/eduExamGrade")
@Slf4j
public class EduExamGradeController extends JeecgController<EduExamGrade, IEduExamGradeService> {
	@Autowired
	private IEduExamGradeService eduExamGradeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduExamGrade
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduExamGrade eduExamGrade,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduExamGrade> queryWrapper = QueryGenerator.initQueryWrapper(eduExamGrade, req.getParameterMap());
		Page<EduExamGrade> page = new Page<EduExamGrade>(pageNo, pageSize);
		IPage<EduExamGrade> pageList = eduExamGradeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduExamGrade
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduExamGrade eduExamGrade) {
		eduExamGradeService.save(eduExamGrade);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduExamGrade
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduExamGrade eduExamGrade) {
		eduExamGradeService.updateById(eduExamGrade);
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
		eduExamGradeService.removeById(id);
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
		this.eduExamGradeService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduExamGrade eduExamGrade = eduExamGradeService.getById(id);
		if(eduExamGrade==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduExamGrade);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduExamGrade
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduExamGrade eduExamGrade) {
        return super.exportXls(request, eduExamGrade, EduExamGrade.class, "考试成绩表");
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
        return super.importExcel(request, response, EduExamGrade.class);
    }

}
