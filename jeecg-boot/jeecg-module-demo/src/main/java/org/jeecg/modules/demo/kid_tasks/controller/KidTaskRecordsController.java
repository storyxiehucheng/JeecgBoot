package org.jeecg.modules.demo.kid_tasks.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.kid_tasks.entity.KidTaskRecords;
import org.jeecg.modules.demo.kid_tasks.entity.KidTasks;
import org.jeecg.modules.demo.kid_tasks.service.IKidTaskRecordsService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.kid_tasks.service.IKidTasksService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.val;

import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 儿童习惯任务记录表
 * @Author: jeecg-boot
 * @Date: 2025-04-21
 * @Version: V1.0
 */
@Tag(name = "儿童习惯任务记录表")
@RestController
@RequestMapping("/kid_tasks/kidTaskRecords")
@Slf4j
public class KidTaskRecordsController extends JeecgController<KidTaskRecords, IKidTaskRecordsService> {
    @Autowired
    private IKidTaskRecordsService kidTaskRecordsService;

    @Autowired
    private IKidTasksService kidTasksService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 分页列表查询
     *
     * @param kidTaskRecords
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "儿童习惯任务记录表-分页列表查询")
    @Operation(summary = "儿童习惯任务记录表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<KidTaskRecords>> queryPageList(KidTaskRecords kidTaskRecords,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(name = "userName", defaultValue = "") String userName,
        @RequestParam(name = "taskRecordDate", defaultValue = "") String taskDate,
        HttpServletRequest req) {

        if (oConvertUtils.isEmpty(userName)) {
            //获取当前登录用户
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String loginUserUsername = loginUser.getUsername();
            // 增加查询条件，查询当前登录用户创建的数据
            if (oConvertUtils.isNotEmpty(loginUserUsername)) {
                kidTaskRecords.setUserName(loginUserUsername);
            }
        }


        QueryWrapper<KidTaskRecords> queryWrapper = QueryGenerator.initQueryWrapper(kidTaskRecords,
            req.getParameterMap());
        log.info("KidTaskRecords username:{}", userName);
        log.info("KidTaskRecords taskRecordDate:{}", taskDate);
        if(oConvertUtils.isNotEmpty(taskDate)) {
            queryWrapper.eq("task_date",  taskDate);
        }

        Page<KidTaskRecords> page = new Page<>(pageNo, pageSize);
        IPage<KidTaskRecords> pageList = kidTaskRecordsService.page(page, queryWrapper);
        log.info("pageList:{}", pageList.getRecords());
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param kidTaskRecords
     * @return
     */
    @AutoLog(value = "儿童习惯任务记录表-添加")
    @Operation(summary = "儿童习惯任务记录表-添加")
    @RequiresPermissions("kid_tasks:kid_task_records:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody KidTaskRecords kidTaskRecords) {
        String taskId = kidTaskRecords.getTaskId();
        // 根据任务ID查询任务信息
        KidTasks task = kidTasksService.getById(taskId);
        if (task == null) {
            return Result.error("任务不存在");
        }
        kidTaskRecords.setTaskName(task.getTitle());
        kidTaskRecords.setTaskPoint(task.getPoints());

        // 根据用户名称查询用户信息
        String userName = kidTaskRecords.getUserName();
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return Result.error("用户不存在或已被禁用");
        }

        kidTaskRecords.setUserId(sysUser.getId());
        kidTaskRecordsService.save(kidTaskRecords);
        return Result.OK("添加成功！");
    }

    /**
     *   添加
     *
     * @param kidTaskRecords
     * @return
     */
    @AutoLog(value = "儿童习惯任务记录表-更新")
    @Operation(summary = "儿童习惯任务记录表-更新")
    @PostMapping(value = "/update")
    public Result<String> update(@RequestBody KidTaskRecords kidTaskRecords) {
        // 先通过user_name查询用户id
        String userName = kidTaskRecords.getUserName();
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return Result.error("用户不存在或已被禁用");
        }
        String userId = sysUser.getId();
        kidTaskRecords.setUserId(userId);

        // 先通过task_id查询任务id
        String taskId = kidTaskRecords.getTaskId();
        // 根据任务ID查询任务信息
        KidTasks task = kidTasksService.getById(taskId);
        if (task == null) {
            return Result.error("任务id[" + taskId + "]对应的任务不存在");
        }
        kidTaskRecords.setTaskName(task.getTitle());
        kidTaskRecords.setTaskPoint(task.getPoints());

        // 通过user_id和task_id和task_date查询任务记录
        LambdaQueryWrapper<KidTaskRecords> queryWrapperForId = new LambdaQueryWrapper<>();
        queryWrapperForId.eq(KidTaskRecords::getUserId, userId);
        queryWrapperForId.eq(KidTaskRecords::getTaskId, taskId);
        queryWrapperForId.eq(KidTaskRecords::getTaskDate, kidTaskRecords.getTaskDate());
        KidTaskRecords existingRecord = kidTaskRecordsService.getOne(queryWrapperForId);
        // 如果存在，则更新记录
        if (existingRecord != null) {
            kidTaskRecords.setId(existingRecord.getId());
            // 如果kidTaskRecords的字段completeDate 为null ，也希望更新到数据库为null
            LambdaUpdateWrapper<KidTaskRecords> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(KidTaskRecords::getId, kidTaskRecords.getId());
            wrapper.set(KidTaskRecords::getCompletedDate, kidTaskRecords.getCompletedDate());
            kidTaskRecordsService.update(wrapper);
            kidTaskRecordsService.updateById(kidTaskRecords);
        } else {
            kidTaskRecordsService.save(kidTaskRecords);
        }
        return Result.OK("更新成功！");
    }

    /**
     *  编辑
     *
     * @param kidTaskRecords
     * @return
     */
    @AutoLog(value = "儿童习惯任务记录表-编辑")
    @Operation(summary = "儿童习惯任务记录表-编辑")
    @RequiresPermissions("kid_tasks:kid_task_records:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody KidTaskRecords kidTaskRecords) {
        kidTaskRecordsService.updateById(kidTaskRecords);
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "儿童习惯任务记录表-通过id删除")
    @Operation(summary = "儿童习惯任务记录表-通过id删除")
    @RequiresPermissions("kid_tasks:kid_task_records:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        kidTaskRecordsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "儿童习惯任务记录表-批量删除")
    @Operation(summary = "儿童习惯任务记录表-批量删除")
    @RequiresPermissions("kid_tasks:kid_task_records:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.kidTaskRecordsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "儿童习惯任务记录表-通过id查询")
    @Operation(summary = "儿童习惯任务记录表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<KidTaskRecords> queryById(@RequestParam(name = "id", required = true) String id) {
        KidTaskRecords kidTaskRecords = kidTaskRecordsService.getById(id);
        if (kidTaskRecords == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(kidTaskRecords);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param kidTaskRecords
     */
    @RequiresPermissions("kid_tasks:kid_task_records:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidTaskRecords kidTaskRecords) {
        return super.exportXls(request, kidTaskRecords, KidTaskRecords.class, "儿童习惯任务记录表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("kid_tasks:kid_task_records:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidTaskRecords.class);
    }
}
