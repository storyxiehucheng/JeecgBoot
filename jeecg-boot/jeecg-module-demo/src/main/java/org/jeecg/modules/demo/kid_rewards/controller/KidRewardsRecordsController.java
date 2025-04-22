package org.jeecg.modules.demo.kid_rewards.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
import org.jeecg.modules.demo.kid_rewards.entity.KidRewards;
import org.jeecg.modules.demo.kid_rewards.entity.KidRewardsRecords;
import org.jeecg.modules.demo.kid_rewards.service.IKidRewardsRecordsService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.kid_rewards.service.IKidRewardsService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
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
 * @Description: 儿童习惯奖励记录表
 * @Author: jeecg-boot
 * @Date: 2025-04-22
 * @Version: V1.0
 */
@Tag(name = "儿童习惯奖励记录表")
@RestController
@RequestMapping("/kid_rewards/kidRewardsRecords")
@Slf4j
public class KidRewardsRecordsController extends JeecgController<KidRewardsRecords, IKidRewardsRecordsService> {
    @Autowired
    private IKidRewardsRecordsService kidRewardsRecordsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IKidRewardsService kidRewardsService;

    /**
     * 分页列表查询
     *
     * @param kidRewardsRecords
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "儿童习惯奖励记录表-分页列表查询")
    @Operation(summary = "儿童习惯奖励记录表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<KidRewardsRecords>> queryPageList(KidRewardsRecords kidRewardsRecords,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<KidRewardsRecords> queryWrapper = QueryGenerator.initQueryWrapper(kidRewardsRecords,
            req.getParameterMap());
        Page<KidRewardsRecords> page = new Page<>(pageNo, pageSize);
        IPage<KidRewardsRecords> pageList = kidRewardsRecordsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param kidRewardsRecords
     * @return
     */
    @AutoLog(value = "儿童习惯奖励记录表-添加")
    @Operation(summary = "儿童习惯奖励记录表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody KidRewardsRecords kidRewardsRecords) {

        // 根据用户名称查询用户信息
        String userName = kidRewardsRecords.getUserName();
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return Result.error("用户不存在或已被禁用");
        }
        kidRewardsRecords.setUserId(sysUser.getId());

        String rewardId = kidRewardsRecords.getRewardId();
        KidRewards kidRewards = kidRewardsService.getById(rewardId);
        if (kidRewards == null) {
            return Result.error("查询不到奖励id[" + rewardId + "]对应的奖励信息");
        }
        kidRewardsRecords.setRewardName(kidRewards.getTitle());
        kidRewardsRecords.setRewardPoint(kidRewards.getReqPoints());
        // 设置当前日期
        if (kidRewardsRecords.getRewardDate() == null) {
            kidRewardsRecords.setRewardDate(new Date());
        }
        kidRewardsRecordsService.save(kidRewardsRecords);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     *
     * @param kidRewardsRecords
     * @return
     */
    @AutoLog(value = "儿童习惯奖励记录表-编辑")
    @Operation(summary = "儿童习惯奖励记录表-编辑")
    @RequiresPermissions("kid_rewards:kid_rewards_records:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody KidRewardsRecords kidRewardsRecords) {
        kidRewardsRecordsService.updateById(kidRewardsRecords);
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "儿童习惯奖励记录表-通过id删除")
    @Operation(summary = "儿童习惯奖励记录表-通过id删除")
    @RequiresPermissions("kid_rewards:kid_rewards_records:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        kidRewardsRecordsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "儿童习惯奖励记录表-批量删除")
    @Operation(summary = "儿童习惯奖励记录表-批量删除")
    @RequiresPermissions("kid_rewards:kid_rewards_records:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.kidRewardsRecordsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "儿童习惯奖励记录表-通过id查询")
    @Operation(summary = "儿童习惯奖励记录表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<KidRewardsRecords> queryById(@RequestParam(name = "id", required = true) String id) {
        KidRewardsRecords kidRewardsRecords = kidRewardsRecordsService.getById(id);
        if (kidRewardsRecords == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(kidRewardsRecords);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param kidRewardsRecords
     */
    @RequiresPermissions("kid_rewards:kid_rewards_records:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidRewardsRecords kidRewardsRecords) {
        return super.exportXls(request, kidRewardsRecords, KidRewardsRecords.class, "儿童习惯奖励记录表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("kid_rewards:kid_rewards_records:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidRewardsRecords.class);
    }
}
