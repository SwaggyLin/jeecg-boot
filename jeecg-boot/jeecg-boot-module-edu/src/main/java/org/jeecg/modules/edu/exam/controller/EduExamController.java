package org.jeecg.modules.edu.exam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edu.exam.entity.EduExam;
import org.jeecg.modules.edu.exam.entity.EduExamClass;
import org.jeecg.modules.edu.exam.service.IEduExamClassService;
import org.jeecg.modules.edu.exam.service.IEduExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 考试表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/exam/eduExam")
@Slf4j
public class EduExamController extends JeecgController<EduExam, IEduExamService> {
	@Autowired
	private IEduExamService eduExamService;
	@Autowired
	private IEduExamClassService eduExamClassService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduExam
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduExam eduExam,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduExam> queryWrapper = QueryGenerator.initQueryWrapper(eduExam, req.getParameterMap());
		Page<EduExam> page = new Page<EduExam>(pageNo, pageSize);
		IPage<EduExam> pageList = eduExamService.page(page, queryWrapper);
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
		Result<EduExam> result = new Result<EduExam>();
		String selectedClasses = jsonObject.getString("selectedClasses");
		try {
			EduExam eduExam = JSON.parseObject(jsonObject.toJSONString(), EduExam.class);
			eduExamService.addExamWithClass(eduExam,selectedClasses);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param jsonObject
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JSONObject jsonObject) {
		Result<EduExam> result = new Result<EduExam>();
		String selectedClasses = jsonObject.getString("selectedClasses");
		try {
			EduExam eduExam = JSON.parseObject(jsonObject.toJSONString(), EduExam.class);
			eduExamService.editExamWithClass(eduExam,selectedClasses);
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
		eduExamService.removeById(id);
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
		this.eduExamService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduExam eduExam = eduExamService.getById(id);
		if(eduExam==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduExam);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduExam
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduExam eduExam) {
        return super.exportXls(request, eduExam, EduExam.class, "考试表");
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
        return super.importExcel(request, response, EduExam.class);
    }
	@RequestMapping(value = "/queryExamClasses", method = RequestMethod.GET)
	public Result<List<String>> queryExamClasses(@RequestParam(name = "examId", required = true) String examId) {
		Result<List<String>> result = new Result<>();
		List<String> list = new ArrayList<String>();
		List<EduExamClass> examClasses = eduExamClassService.list(new QueryWrapper<EduExamClass>().lambda().eq(EduExamClass::getExamId, examId));
		if (examClasses == null || examClasses.size() <= 0) {
			result.error500("未找到该考试相关班级信息");
		} else {
			for (EduExamClass examClass : examClasses) {
				list.add(examClass.getClassId());
			}
			result.setSuccess(true);
			result.setResult(list);
		}
		return result;
	}
}
