package org.jeecg.modules.edu.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edu.entity.EduTeacher;
import org.jeecg.modules.edu.service.IEduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2020-03-18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/user/eduTeacher")
@Slf4j
public class EduTeacherController extends JeecgController<EduTeacher, IEduTeacherService> {
	@Autowired
	private IEduTeacherService eduTeacherService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduTeacher
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduTeacher eduTeacher,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduTeacher> queryWrapper = QueryGenerator.initQueryWrapper(eduTeacher, req.getParameterMap());
		Page<EduTeacher> page = new Page<EduTeacher>(pageNo, pageSize);
		IPage<EduTeacher> pageList = eduTeacherService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduTeacher
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduTeacher eduTeacher) {
		eduTeacherService.save(eduTeacher);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduTeacher
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduTeacher eduTeacher) {
		eduTeacherService.updateById(eduTeacher);
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
		eduTeacherService.removeById(id);
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
		this.eduTeacherService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduTeacher eduTeacher = eduTeacherService.getById(id);
		if(eduTeacher==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduTeacher);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduTeacher
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduTeacher eduTeacher) {
        return super.exportXls(request, eduTeacher, EduTeacher.class, "教师");
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
        return super.importExcel(request, response, EduTeacher.class);
    }

}
