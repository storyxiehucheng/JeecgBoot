package org.jeecg.modules.demo.childhabittaskmgr.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.childhabittaskmgr.entity.ChildHabitsTaskManager;
import org.jeecg.modules.demo.childhabittaskmgr.service.IChildHabitsTaskManagerService;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 儿童习惯任务卡管理
 * @Author: jeecg-boot
 * @Date:   2025-04-11
 * @Version: V1.0
 */
@Tag(name="儿童习惯任务卡管理")
@RestController
@RequestMapping("/childhabittaskmgr/childHabitsTaskManager")
@Slf4j
public class ChildHabitsTaskManagerController extends JeecgController<ChildHabitsTaskManager, IChildHabitsTaskManagerService> {
	@Autowired
	private IChildHabitsTaskManagerService childHabitsTaskManagerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param childHabitsTaskManager
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "儿童习惯任务卡管理-分页列表查询")
	@Operation(summary = "儿童习惯任务卡管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ChildHabitsTaskManager>> queryPageList(ChildHabitsTaskManager childHabitsTaskManager,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<ChildHabitsTaskManager> queryWrapper = QueryGenerator.initQueryWrapper(childHabitsTaskManager, req.getParameterMap());
		Page<ChildHabitsTaskManager> page = new Page<ChildHabitsTaskManager>(pageNo, pageSize);
		IPage<ChildHabitsTaskManager> pageList = childHabitsTaskManagerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param childHabitsTaskManager
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务卡管理-添加")
	@Operation(summary = "儿童习惯任务卡管理-添加")
	@RequiresPermissions("childhabittaskmgr:child_habits_task:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ChildHabitsTaskManager childHabitsTaskManager) {
		childHabitsTaskManagerService.save(childHabitsTaskManager);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param childHabitsTaskManager
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务卡管理-编辑")
	@Operation(summary = "儿童习惯任务卡管理-编辑")
	@RequiresPermissions("childhabittaskmgr:child_habits_task:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ChildHabitsTaskManager childHabitsTaskManager) {
		childHabitsTaskManagerService.updateById(childHabitsTaskManager);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务卡管理-通过id删除")
	@Operation(summary = "儿童习惯任务卡管理-通过id删除")
	@RequiresPermissions("childhabittaskmgr:child_habits_task:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		childHabitsTaskManagerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "儿童习惯任务卡管理-批量删除")
	@Operation(summary = "儿童习惯任务卡管理-批量删除")
	@RequiresPermissions("childhabittaskmgr:child_habits_task:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.childHabitsTaskManagerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "儿童习惯任务卡管理-通过id查询")
	@Operation(summary = "儿童习惯任务卡管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ChildHabitsTaskManager> queryById(@RequestParam(name="id",required=true) String id) {
		ChildHabitsTaskManager childHabitsTaskManager = childHabitsTaskManagerService.getById(id);
		if(childHabitsTaskManager==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(childHabitsTaskManager);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param childHabitsTaskManager
    */
    @RequiresPermissions("childhabittaskmgr:child_habits_task:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChildHabitsTaskManager childHabitsTaskManager) {
        return super.exportXls(request, childHabitsTaskManager, ChildHabitsTaskManager.class, "儿童习惯任务卡管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("childhabittaskmgr:child_habits_task:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ChildHabitsTaskManager.class);
    }

}
