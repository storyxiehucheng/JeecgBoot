package org.jeecg.modules.demo.kid_deduction.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.kid_deduction.entity.KidDeductions;
import org.jeecg.modules.demo.kid_deduction.entity.KidDetuctionsRecords;
import org.jeecg.modules.demo.kid_deduction.service.IKidDeductionsService;
import org.jeecg.modules.demo.kid_deduction.service.IKidDeductionsRecordsService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 儿童习惯扣分记录表
 * @Author: jeecg-boot
 * @Date: 2025-04-22
 * @Version: V1.0
 */
@Tag(name = "儿童习惯扣分记录表")
@RestController
@RequestMapping("/kid_deduction/kidDetuctionsRecords")
@Slf4j
public class KidDeductionsRecordsController
    extends JeecgController<KidDetuctionsRecords, IKidDeductionsRecordsService> {
    @Autowired
    private IKidDeductionsRecordsService kidDeductionsRecordsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IKidDeductionsService kidDeductionsService;

    /**
     * 分页列表查询
     *
     * @param kidDetuctionsRecords
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "儿童习惯扣分记录表-分页列表查询")
    @Operation(summary = "儿童习惯扣分记录表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<KidDetuctionsRecords>> queryPageList(KidDetuctionsRecords kidDetuctionsRecords,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(name = "userName", defaultValue = "") String userName,
        @RequestParam(name = "deductionDate", defaultValue = "") String deductionDate,
        HttpServletRequest req) {
        QueryWrapper<KidDetuctionsRecords> queryWrapper = QueryGenerator.initQueryWrapper(kidDetuctionsRecords,
            req.getParameterMap());
        if(oConvertUtils.isNotEmpty(userName)) {
            queryWrapper.eq("user_name", userName);
        }
        if(oConvertUtils.isNotEmpty(deductionDate)) {
            queryWrapper.eq("task_date", deductionDate);
        }
        Page<KidDetuctionsRecords> page = new Page<>(pageNo, pageSize);
        IPage<KidDetuctionsRecords> pageList = kidDeductionsRecordsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param kidDetuctionsRecords
     * @return
     */
    @AutoLog(value = "儿童习惯扣分记录表-添加")
    @Operation(summary = "儿童习惯扣分记录表-添加")
    @RequiresPermissions("kid_deduction:kid_detuctions_records:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody KidDetuctionsRecords kidDetuctionsRecords) {
        String taskId = kidDetuctionsRecords.getTaskId();
        // 根据任务ID查询任务信息
        KidDeductions deductionTask = kidDeductionsService.getById(taskId);
        if (deductionTask == null) {
            return Result.error("任务不存在");
        }
        kidDetuctionsRecords.setTaskName(deductionTask.getTitle());
        kidDetuctionsRecords.setTaskPoint(deductionTask.getPoints());

        // 根据用户名称查询用户信息
        String userName = kidDetuctionsRecords.getUserName();
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
            return Result.error("用户不存在或已被禁用");
        }
        kidDetuctionsRecords.setUserId(sysUser.getId());
        kidDeductionsRecordsService.save(kidDetuctionsRecords);
        return Result.OK("添加成功！");
    }

    /**
     *   添加
     *
     * @param kidDetuctionsRecords
     * @return
     */
    @AutoLog(value = "儿童习惯扣分记录表-添加")
    @Operation(summary = "儿童习惯扣分记录表-添加")
    @PostMapping(value = "/update")
    public Result<String> update(@RequestBody KidDetuctionsRecords kidDetuctionsRecords) {
        String taskId = kidDetuctionsRecords.getTaskId();
        // 根据任务ID查询任务信息
        KidDeductions deductionTask = kidDeductionsService.getById(taskId);
        if (deductionTask == null) {
            return Result.error("任务不存在");
        }
        kidDetuctionsRecords.setTaskName(deductionTask.getTitle());
        kidDetuctionsRecords.setTaskPoint(deductionTask.getPoints());

        // 根据用户名称查询用户信息
        String userName = kidDetuctionsRecords.getUserName();
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
            return Result.error("用户不存在或已被禁用");
        }

        String userId = sysUser.getId();
        kidDetuctionsRecords.setUserId(userId);

        // 通过user_id和task_id和task_date查询任务记录
        LambdaQueryWrapper<KidDetuctionsRecords> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(KidDetuctionsRecords::getUserId, userId);
        queryWrapper1.eq(KidDetuctionsRecords::getTaskId, taskId);
        queryWrapper1.eq(KidDetuctionsRecords::getTaskDate, kidDetuctionsRecords.getTaskDate());
        KidDetuctionsRecords existingRecord = kidDeductionsRecordsService.getOne(queryWrapper1);
        if (existingRecord != null) {
            // 则直接修改记录
            kidDetuctionsRecords.setId(existingRecord.getId());
            // 如果kidTaskRecords的字段completeDate 为null ，也希望更新到数据库为null
            LambdaUpdateWrapper<KidDetuctionsRecords> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(KidDetuctionsRecords::getId, kidDetuctionsRecords.getId());
            wrapper.set(KidDetuctionsRecords::getCompletedDate, kidDetuctionsRecords.getCompletedDate());
            kidDeductionsRecordsService.update(wrapper);
            kidDeductionsRecordsService.updateById(kidDetuctionsRecords);
        }else {
            kidDeductionsRecordsService.save(kidDetuctionsRecords);
        }

        return Result.OK("更新成功！");
    }

    /**
     *  编辑
     *
     * @param kidDetuctionsRecords
     * @return
     */
    @AutoLog(value = "儿童习惯扣分记录表-编辑")
    @Operation(summary = "儿童习惯扣分记录表-编辑")
    @RequiresPermissions("kid_deduction:kid_detuctions_records:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody KidDetuctionsRecords kidDetuctionsRecords) {
        kidDeductionsRecordsService.updateById(kidDetuctionsRecords);
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "儿童习惯扣分记录表-通过id删除")
    @Operation(summary = "儿童习惯扣分记录表-通过id删除")
    @RequiresPermissions("kid_deduction:kid_detuctions_records:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        kidDeductionsRecordsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "儿童习惯扣分记录表-批量删除")
    @Operation(summary = "儿童习惯扣分记录表-批量删除")
    @RequiresPermissions("kid_deduction:kid_detuctions_records:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.kidDeductionsRecordsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "儿童习惯扣分记录表-通过id查询")
    @Operation(summary = "儿童习惯扣分记录表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<KidDetuctionsRecords> queryById(@RequestParam(name = "id", required = true) String id) {
        KidDetuctionsRecords kidDetuctionsRecords = kidDeductionsRecordsService.getById(id);
        if (kidDetuctionsRecords == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(kidDetuctionsRecords);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param kidDetuctionsRecords
     */
    @RequiresPermissions("kid_deduction:kid_detuctions_records:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidDetuctionsRecords kidDetuctionsRecords) {
        return super.exportXls(request, kidDetuctionsRecords, KidDetuctionsRecords.class, "儿童习惯扣分记录表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("kid_deduction:kid_detuctions_records:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidDetuctionsRecords.class);
    }
}
