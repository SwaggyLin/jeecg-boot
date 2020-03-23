package org.jeecg.modules.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.entity.EduStudent;
import org.jeecg.modules.edu.service.IEduStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/user/eduStudent")
@Slf4j
public class EduStudentController extends JeecgController<EduStudent, IEduStudentService> {
	@Autowired
	private IEduStudentService eduStudentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduStudent
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduStudent eduStudent,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduStudent> queryWrapper = QueryGenerator.initQueryWrapper(eduStudent, req.getParameterMap());
		Page<EduStudent> page = new Page<EduStudent>(pageNo, pageSize);
		IPage<EduStudent> pageList = eduStudentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduStudent
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduStudent eduStudent) {
		eduStudentService.save(eduStudent);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduStudent
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduStudent eduStudent) {
		eduStudentService.updateById(eduStudent);
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
		eduStudentService.removeById(id);
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
		this.eduStudentService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduStudent eduStudent = eduStudentService.getById(id);
		if(eduStudent==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduStudent);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduStudent
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduStudent eduStudent) {
        return super.exportXls(request, eduStudent, EduStudent.class, "学生信息表");
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
        return super.importExcel(request, response, EduStudent.class);
    }

}
