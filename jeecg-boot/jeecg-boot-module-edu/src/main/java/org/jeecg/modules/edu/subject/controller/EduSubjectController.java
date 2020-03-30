package org.jeecg.modules.edu.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;

import org.jeecg.modules.edu.subject.entity.EduSubject;
import org.jeecg.modules.edu.subject.service.IEduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 科目
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edu/eduSubject")
@Slf4j
public class EduSubjectController extends JeecgController<EduSubject, IEduSubjectService> {
	@Autowired
	private IEduSubjectService eduSubjectService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eduSubject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EduSubject eduSubject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EduSubject> queryWrapper = QueryGenerator.initQueryWrapper(eduSubject, req.getParameterMap());
		Page<EduSubject> page = new Page<EduSubject>(pageNo, pageSize);
		IPage<EduSubject> pageList = eduSubjectService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eduSubject
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EduSubject eduSubject) {
		eduSubjectService.save(eduSubject);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eduSubject
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EduSubject eduSubject) {
		eduSubjectService.updateById(eduSubject);
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
		eduSubjectService.removeById(id);
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
		this.eduSubjectService.removeByIds(Arrays.asList(ids.split(",")));
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
		EduSubject eduSubject = eduSubjectService.getById(id);
		if(eduSubject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eduSubject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eduSubject
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EduSubject eduSubject) {
        return super.exportXls(request, eduSubject, EduSubject.class, "科目");
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
        return super.importExcel(request, response, EduSubject.class);
    }

    @RequestMapping(value = "/queryall", method = RequestMethod.GET )
	public Result<List<EduSubject>> queryAll(){
		Result<List<EduSubject>> result = new Result<>();
		List<EduSubject> list = eduSubjectService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到科目信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

}
