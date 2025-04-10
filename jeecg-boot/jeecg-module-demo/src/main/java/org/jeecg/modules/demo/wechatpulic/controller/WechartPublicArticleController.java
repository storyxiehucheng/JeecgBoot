package org.jeecg.modules.demo.wechatpulic.controller;

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
import org.jeecg.modules.demo.wechatpulic.entity.WechartPublicArticle;
import org.jeecg.modules.demo.wechatpulic.service.IWechartPublicArticleService;

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
 * @Description: 微信公众号文章管理
 * @Author: jeecg-boot
 * @Date:   2025-04-08
 * @Version: V1.0
 */
@Tag(name="微信公众号文章管理")
@RestController
@RequestMapping("/wechatpulic/wechartPublicArticle")
@Slf4j
public class WechartPublicArticleController extends JeecgController<WechartPublicArticle, IWechartPublicArticleService> {
	@Autowired
	private IWechartPublicArticleService wechartPublicArticleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wechartPublicArticle 微信公众号文章
	 * @param pageNo 文章页数
	 * @param pageSize 文章大小
	 * @param req 请求参数
	 * @return 文章列表
	 */
	//@AutoLog(value = "微信公众号文章管理-分页列表查询")
	@Operation(summary = "微信公众号文章管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<WechartPublicArticle>> queryPageList(WechartPublicArticle wechartPublicArticle,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<WechartPublicArticle> queryWrapper = QueryGenerator.initQueryWrapper(wechartPublicArticle, req.getParameterMap());
		Page<WechartPublicArticle> page = new Page<WechartPublicArticle>(pageNo, pageSize);
		IPage<WechartPublicArticle> pageList = wechartPublicArticleService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param wechartPublicArticle
	 * @return
	 */
	@AutoLog(value = "微信公众号文章管理-添加")
	@Operation(summary = "微信公众号文章管理-添加")
	@RequiresPermissions("wechatpulic:wechart_public_article:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody WechartPublicArticle wechartPublicArticle) {
		wechartPublicArticleService.save(wechartPublicArticle);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param wechartPublicArticle
	 * @return
	 */
	@AutoLog(value = "微信公众号文章管理-编辑")
	@Operation(summary = "微信公众号文章管理-编辑")
	@RequiresPermissions("wechatpulic:wechart_public_article:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody WechartPublicArticle wechartPublicArticle) {
		wechartPublicArticleService.updateById(wechartPublicArticle);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "微信公众号文章管理-通过id删除")
	@Operation(summary = "微信公众号文章管理-通过id删除")
	@RequiresPermissions("wechatpulic:wechart_public_article:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		wechartPublicArticleService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "微信公众号文章管理-批量删除")
	@Operation(summary = "微信公众号文章管理-批量删除")
	@RequiresPermissions("wechatpulic:wechart_public_article:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wechartPublicArticleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "微信公众号文章管理-通过id查询")
	@Operation(summary = "微信公众号文章管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<WechartPublicArticle> queryById(@RequestParam(name="id",required=true) String id) {
		WechartPublicArticle wechartPublicArticle = wechartPublicArticleService.getById(id);
		if(wechartPublicArticle==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wechartPublicArticle);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wechartPublicArticle
    */
    @RequiresPermissions("wechatpulic:wechart_public_article:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WechartPublicArticle wechartPublicArticle) {
        return super.exportXls(request, wechartPublicArticle, WechartPublicArticle.class, "微信公众号文章管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("wechatpulic:wechart_public_article:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WechartPublicArticle.class);
    }

}
