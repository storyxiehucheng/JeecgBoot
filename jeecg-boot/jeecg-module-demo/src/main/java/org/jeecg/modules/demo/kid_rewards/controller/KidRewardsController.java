package org.jeecg.modules.demo.kid_rewards.controller;

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
import org.jeecg.modules.demo.kid_rewards.entity.KidRewards;
import org.jeecg.modules.demo.kid_rewards.service.IKidRewardsService;

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
 * @Description: 儿童习惯奖励模板表
 * @Author: jeecg-boot
 * @Date:   2025-04-12
 * @Version: V1.0
 */
@Tag(name="儿童习惯奖励模板表")
@RestController
@RequestMapping("/kid_rewards/kidRewards")
@Slf4j
public class KidRewardsController extends JeecgController<KidRewards, IKidRewardsService> {
	@Autowired
	private IKidRewardsService kidRewardsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param kidRewards
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "儿童习惯奖励模板表-分页列表查询")
	@IgnoreAuth
	@Operation(summary = "儿童习惯奖励模板表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<KidRewards>> queryPageList(KidRewards kidRewards,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<KidRewards> queryWrapper = QueryGenerator.initQueryWrapper(kidRewards, req.getParameterMap());
		Page<KidRewards> page = new Page<KidRewards>(pageNo, pageSize);
		IPage<KidRewards> pageList = kidRewardsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param kidRewards
	 * @return
	 */
	@AutoLog(value = "儿童习惯奖励模板表-添加")
	@Operation(summary = "儿童习惯奖励模板表-添加")
	@RequiresPermissions("kid_rewards:kid_rewards:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody KidRewards kidRewards) {
		kidRewardsService.save(kidRewards);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param kidRewards
	 * @return
	 */
	@AutoLog(value = "儿童习惯奖励模板表-编辑")
	@Operation(summary = "儿童习惯奖励模板表-编辑")
	@RequiresPermissions("kid_rewards:kid_rewards:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody KidRewards kidRewards) {
		kidRewardsService.updateById(kidRewards);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "儿童习惯奖励模板表-通过id删除")
	@Operation(summary = "儿童习惯奖励模板表-通过id删除")
	@RequiresPermissions("kid_rewards:kid_rewards:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		kidRewardsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "儿童习惯奖励模板表-批量删除")
	@Operation(summary = "儿童习惯奖励模板表-批量删除")
	@RequiresPermissions("kid_rewards:kid_rewards:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.kidRewardsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "儿童习惯奖励模板表-通过id查询")
	@Operation(summary = "儿童习惯奖励模板表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<KidRewards> queryById(@RequestParam(name="id",required=true) String id) {
		KidRewards kidRewards = kidRewardsService.getById(id);
		if(kidRewards==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(kidRewards);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param kidRewards
    */
    @RequiresPermissions("kid_rewards:kid_rewards:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidRewards kidRewards) {
        return super.exportXls(request, kidRewards, KidRewards.class, "儿童习惯奖励模板表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("kid_rewards:kid_rewards:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidRewards.class);
    }

}
