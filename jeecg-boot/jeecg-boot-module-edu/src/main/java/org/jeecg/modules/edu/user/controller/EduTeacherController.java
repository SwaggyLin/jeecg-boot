package org.jeecg.modules.edu.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.user.entity.EduTeacher;
import org.jeecg.modules.edu.user.entity.EduTeacherSubject;
import org.jeecg.modules.edu.user.service.IEduTeacherService;
import org.jeecg.modules.edu.user.service.IEduTeacherSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
	@Autowired
	private IEduTeacherSubjectService eduTeacherSubjectService;
	
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
	 * @param jsonObject
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody JSONObject jsonObject) {
		Result<EduTeacher> result = new Result<EduTeacher>();
		String selectedSubjects = jsonObject.getString("selectedSubjects");

		try {
			EduTeacher eduTeacher = JSON.parseObject(jsonObject.toJSONString(), EduTeacher.class);
			eduTeacherService.addTeacherWithSubject(eduTeacher,selectedSubjects);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;

	}
	
	/**
	 *  编辑
	 *
	 * @param jsonObject
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JSONObject jsonObject) {
		Result<EduTeacher> result = new Result<EduTeacher>();
		String selectedSubjects = jsonObject.getString("selectedSubjects");
		try {
			EduTeacher eduTeacher = JSON.parseObject(jsonObject.toJSONString(), EduTeacher.class);
			eduTeacherService.editTeacherWithSubject(eduTeacher,selectedSubjects);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
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


	 @RequestMapping(value = "/queryTeacherSubject", method = RequestMethod.GET)
	 public Result<List<String>> queryTeacherSubject(@RequestParam(name = "teacherId", required = true) String teacherId) {
		 Result<List<String>> result = new Result<>();
		 List<String> list = new ArrayList<String>();
		 List<EduTeacherSubject> teacherSubjects = eduTeacherSubjectService.list(new QueryWrapper<EduTeacherSubject>().lambda().eq(EduTeacherSubject::getTeacherId, teacherId));
		 if (teacherSubjects == null || teacherSubjects.size() <= 0) {
			 result.error500("未找到教师相关科目信息");
		 } else {
			 for (EduTeacherSubject teacherSubject : teacherSubjects) {
				 list.add(teacherSubject.getSubjectId());
			 }
			 result.setSuccess(true);
			 result.setResult(list);
		 }
		 return result;
	 }
	 @RequestMapping(value = "/queryall", method = RequestMethod.GET )
	 public Result<List<EduTeacher>> queryAll(){
		 Result<List<EduTeacher>> result = new Result<>();
		 List<EduTeacher> list = eduTeacherService.list();
		 if(list==null||list.size()<=0) {
			 result.error500("未找到教师信息");
		 }else {
			 result.setResult(list);
			 result.setSuccess(true);
		 }
		 return result;
	 }

}
