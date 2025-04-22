package org.jeecg.modules.demo.kid_deduction.controller;

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
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.demo.kid_deduction.entity.KidDeductions;
import org.jeecg.modules.demo.kid_deduction.service.IKidDeductionsService;

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
 * @Description: 儿童习惯扣分模板表
 * @Author: jeecg-boot
 * @Date:   2025-04-12
 * @Version: V1.0
 */
@Tag(name="儿童习惯扣分模板表")
@RestController
@RequestMapping("/kid_deduction/kidDeductions")
@Slf4j
public class KidDeductionsController extends JeecgController<KidDeductions, IKidDeductionsService> {
	@Autowired
	private IKidDeductionsService kidDeductionsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param kidDeductions
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "儿童习惯扣分模板表-分页列表查询")
	@IgnoreAuth
	@Operation(summary = "儿童习惯扣分模板表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<KidDeductions>> queryPageList(KidDeductions kidDeductions,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<KidDeductions> queryWrapper = QueryGenerator.initQueryWrapper(kidDeductions, req.getParameterMap());
		Page<KidDeductions> page = new Page<KidDeductions>(pageNo, pageSize);
		IPage<KidDeductions> pageList = kidDeductionsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param kidDeductions
	 * @return
	 */
	@AutoLog(value = "儿童习惯扣分模板表-添加")
	@Operation(summary = "儿童习惯扣分模板表-添加")
	@RequiresPermissions("kid_deduction:kid_deductions:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody KidDeductions kidDeductions) {
		kidDeductionsService.save(kidDeductions);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param kidDeductions
	 * @return
	 */
	@AutoLog(value = "儿童习惯扣分模板表-编辑")
	@Operation(summary = "儿童习惯扣分模板表-编辑")
	@RequiresPermissions("kid_deduction:kid_deductions:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody KidDeductions kidDeductions) {
		kidDeductionsService.updateById(kidDeductions);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "儿童习惯扣分模板表-通过id删除")
	@Operation(summary = "儿童习惯扣分模板表-通过id删除")
	@RequiresPermissions("kid_deduction:kid_deductions:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		kidDeductionsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "儿童习惯扣分模板表-批量删除")
	@Operation(summary = "儿童习惯扣分模板表-批量删除")
	@RequiresPermissions("kid_deduction:kid_deductions:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.kidDeductionsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "儿童习惯扣分模板表-通过id查询")
	@Operation(summary = "儿童习惯扣分模板表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<KidDeductions> queryById(@RequestParam(name="id",required=true) String id) {
		KidDeductions kidDeductions = kidDeductionsService.getById(id);
		if(kidDeductions==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(kidDeductions);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param kidDeductions
    */
    @RequiresPermissions("kid_deduction:kid_deductions:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidDeductions kidDeductions) {
        return super.exportXls(request, kidDeductions, KidDeductions.class, "儿童习惯扣分模板表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("kid_deduction:kid_deductions:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidDeductions.class);
    }

}
