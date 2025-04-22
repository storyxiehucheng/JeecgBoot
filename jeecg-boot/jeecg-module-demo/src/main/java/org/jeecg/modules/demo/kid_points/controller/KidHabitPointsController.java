package org.jeecg.modules.demo.kid_points.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.kid_points.entity.KidHabitPoints;
import org.jeecg.modules.demo.kid_points.service.IKidHabitPointsService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 儿童习惯积分表
 * @Author: jeecg-boot
 * @Date: 2025-04-18
 * @Version: V1.0
 */
@Tag(name = "儿童习惯积分表")
@RestController
@RequestMapping("/kid_points/kidHabitPoints")
@Slf4j
public class KidHabitPointsController extends JeecgController<KidHabitPoints, IKidHabitPointsService> {
    @Autowired
    private IKidHabitPointsService kidHabitPointsService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 分页列表查询
     *
     * @param kidHabitPoints
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "儿童习惯积分表-分页列表查询")
    @Operation(summary = "儿童习惯积分表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<KidHabitPoints>> queryPageList(KidHabitPoints kidHabitPoints,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "userName", defaultValue = "") String userName,
        @RequestParam(name = "queryDate", defaultValue = "") String queryDate,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<KidHabitPoints> queryWrapper = QueryGenerator.initQueryWrapper(kidHabitPoints,
            req.getParameterMap());
        // 设置查询条件
        if (oConvertUtils.isNotEmpty(userName)) {
            queryWrapper.eq("user_name", userName);
            boolean ret = kidHabitPointsService.checkTodayPointIfNewDayForUser(userName);
            if (!ret) {
                return Result.error("check and update user [" + userName + "] today points failed");
            }
        } else {
            kidHabitPointsService.checkTodayPointIfNewDayForAll();
        }

        if (oConvertUtils.isNotEmpty(queryDate)){
            queryWrapper.eq("date", queryDate);
        }

        Page<KidHabitPoints> page = new Page<>(pageNo, pageSize);
        IPage<KidHabitPoints> pageList = kidHabitPointsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param kidHabitPoints
     * @return
     */
    @AutoLog(value = "儿童习惯积分表-添加")
    @Operation(summary = "儿童习惯积分表-添加")
    @RequiresPermissions("kid_points:kid_habit_points:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody KidHabitPoints kidHabitPoints) {
        String username = kidHabitPoints.getUserName();
        if (oConvertUtils.isEmpty(username)) {
            return Result.error("username不能为空！");
        }
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }
        kidHabitPoints.setUserId(sysUser.getId());
        kidHabitPointsService.save(kidHabitPoints);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     *
     * @param kidHabitPoints
     * @return
     */
    @AutoLog(value = "儿童习惯积分表-编辑")
    @Operation(summary = "儿童习惯积分表-编辑")
    @RequiresPermissions("kid_points:kid_habit_points:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody KidHabitPoints kidHabitPoints) {
        String username = kidHabitPoints.getUserName();
        if (oConvertUtils.isEmpty(username)) {
            return Result.error("username不能为空！");
        }
        // 查询用户id
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        if (oConvertUtils.isEmpty(kidHabitPoints.getId())) {
            // 如果id为空，则根据user_id的值sysUser.getId()查询id
            LambdaQueryWrapper<KidHabitPoints> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(KidHabitPoints::getUserId, sysUser.getId());
            queryWrapper1.eq(KidHabitPoints::getDate, kidHabitPoints.getDate());
            KidHabitPoints tempKidHabitPointsEntity = kidHabitPointsService.getOne(queryWrapper1);
            if (tempKidHabitPointsEntity != null) {
                kidHabitPoints.setId(tempKidHabitPointsEntity.getId());
            } else {
                return Result.error("未找到对应id数据");
            }
        }

        KidHabitPoints kidHabitPointsEntity = kidHabitPointsService.getById(kidHabitPoints.getId());
        if (kidHabitPointsEntity == null) {
            return Result.error("未找到对应数据");
        }
        // 设置用户id
        kidHabitPoints.setUserId(sysUser.getId());
        // 不希望更改日期
        kidHabitPoints.setDate(null);
        boolean updateById = kidHabitPointsService.updateById(kidHabitPoints);
        if (!updateById) {
            return Result.error("编辑失败");
        }
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "儿童习惯积分表-通过id删除")
    @Operation(summary = "儿童习惯积分表-通过id删除")
    @RequiresPermissions("kid_points:kid_habit_points:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        kidHabitPointsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "儿童习惯积分表-批量删除")
    @Operation(summary = "儿童习惯积分表-批量删除")
    @RequiresPermissions("kid_points:kid_habit_points:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.kidHabitPointsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "儿童习惯积分表-通过id查询")
    @Operation(summary = "儿童习惯积分表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<KidHabitPoints> queryById(@RequestParam(name = "id") String id) {
        KidHabitPoints kidHabitPoints = kidHabitPointsService.getById(id);
        if (kidHabitPoints == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(kidHabitPoints);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param kidHabitPoints
     */
    @RequiresPermissions("kid_points:kid_habit_points:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, KidHabitPoints kidHabitPoints) {
        return super.exportXls(request, kidHabitPoints, KidHabitPoints.class, "儿童习惯积分表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("kid_points:kid_habit_points:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, KidHabitPoints.class);
    }
}
