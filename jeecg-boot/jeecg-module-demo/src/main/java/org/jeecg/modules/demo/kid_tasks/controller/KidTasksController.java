package org.jeecg.modules.demo.kid_tasks.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.kid_tasks.entity.KidTasks;
import org.jeecg.modules.demo.kid_tasks.service.IKidTasksService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 儿童习惯任务模板表
 * @Author: jeecg-boot
 * @Date:   2025-04-12
 * @Version: V1.0
 */
@Tag(name="儿童习惯任务模板表")
@RestController
@RequestMapping("/kid_tasks/kidTasks")
@Slf4j
public class KidTasksController extends JeecgController<KidTasks, IKidTasksService> {
	@Autowired
	private IKidTasksService kidTasksService;
	
	/**
	 * 分页列表查询
	 *
	 * @param kidTasks
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "儿童习惯任务模板表-分页列表查询")
	@Operation(summary = "儿童习惯任务模板表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<KidTasks>> queryPageList(KidTasks kidTasks,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<KidTasks> queryWrapper = QueryGenerator.initQueryWrapper(kidTasks, req.getParameterMap());

		//获取当前登录用户
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String userName = loginUser.getUsername();
		// 增加查询条件，查询当前登录用户创建的数据
		if (oConvertUtils.isNotEmpty(userName)) {
			queryWrapper.eq("create_by", userName);
		}

		Page<KidTasks> page = new Page<>(pageNo, pageSize);
		IPage<KidTasks> pageList = kidTasksService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param kidTasks
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务模板表-添加")
	@Operation(summary = "儿童习惯任务模板表-添加")
	@RequiresPermissions("kid_tasks:kid_tasks:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody KidTasks kidTasks) {
		kidTasksService.save(kidTasks);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param kidTasks
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务模板表-编辑")
	@Operation(summary = "儿童习惯任务模板表-编辑")
	@RequiresPermissions("kid_tasks:kid_tasks:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody KidTasks kidTasks) {
		kidTasksService.updateById(kidTasks);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务模板表-通过id删除")
	@Operation(summary = "儿童习惯任务模板表-通过id删除")
	@RequiresPermissions("kid_tasks:kid_tasks:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		kidTasksService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务模板表-批量删除")
	@Operation(summary = "儿童习惯任务模板表-批量删除")
	@RequiresPermissions("kid_tasks:kid_tasks:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.kidTasksService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "儿童习惯任务模板表-通过id查询")
	@Operation(summary = "儿童习惯任务模板表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<KidTasks> queryById(@RequestParam(name="id",required=true) String id) {
		KidTasks kidTasks = kidTasksService.getById(id);
		if(kidTasks==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(kidTasks);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param kidTasks
    */
    @RequiresPermissions("kid_tasks:kid_tasks:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidTasks kidTasks) {
        return super.exportXls(request, kidTasks, KidTasks.class, "儿童习惯任务模板表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("kid_tasks:kid_tasks:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidTasks.class);
    }

}
