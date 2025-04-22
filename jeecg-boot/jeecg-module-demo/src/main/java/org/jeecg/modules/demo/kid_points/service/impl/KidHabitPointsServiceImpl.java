package org.jeecg.modules.demo.kid_points.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.kid_points.entity.KidHabitPoints;
import org.jeecg.modules.demo.kid_points.mapper.KidHabitPointsMapper;
import org.jeecg.modules.demo.kid_points.service.IKidHabitPointsService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @Description: 儿童习惯积分表
 * @Author: jeecg-boot
 * @Date:   2025-04-22
 * @Version: V1.0
 */
@Service
@Slf4j
public class KidHabitPointsServiceImpl extends ServiceImpl<KidHabitPointsMapper, KidHabitPoints> implements IKidHabitPointsService {
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public Boolean checkTodayPointIfNewDayForUser(String userName) {
        if (oConvertUtils.isEmpty(userName)) {
            log.error("user name can not be empty");
            return false;
        }
        // 获取当前时间的年月日
        LocalDate today = LocalDate.now();
        int totalPoint = 0;
        // 查询最近的一条数据
        LambdaQueryWrapper<KidHabitPoints> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(KidHabitPoints::getUserName, userName)
            .orderByDesc(KidHabitPoints::getDate)
            .last("LIMIT 1");

        KidHabitPoints habitPoint = getOne(lambdaQueryWrapper);
        if (habitPoint != null) {
            // 数据不变，则将总积分设置为最新的积分
            totalPoint = habitPoint.getTotalPoints();
            // 判断日期是否为今日日期
            LocalDate recordDate = habitPoint.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            // 日期相同，说明已经设置过了，不需要设置了
            if (recordDate.equals(today)){
                return true;
            }
        }
        // 如果数据不存在，或者日期不是今天，则需要插入一条数据，设置今日积分为0
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result result = sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
            log.error("根据用户名【{}】查询用户失败", userName);
            return false;
        }

        KidHabitPoints kidHabitPoints = new KidHabitPoints();
        kidHabitPoints.setUserName(userName);
        kidHabitPoints.setUserId(sysUser.getId());
        kidHabitPoints.setDate(new Date());
        kidHabitPoints.setTodayPoints(0);
        kidHabitPoints.setTotalPoints(totalPoint);
        // 插入新数据
        return save(kidHabitPoints);
    }

    @Override
    public Boolean checkTodayPointIfNewDayForAll() {
        // 查询所有的用户
        LambdaQueryWrapper<KidHabitPoints> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(KidHabitPoints::getUserName).groupBy(KidHabitPoints::getUserName);
        List<KidHabitPoints> kidHabitPointsUserList = list(lambdaQueryWrapper);
        kidHabitPointsUserList.forEach(kidHabitPoints -> {
            boolean ret = checkTodayPointIfNewDayForUser(kidHabitPoints.getUserName());
            if  (!ret){
                log.error("配置username【{}】最新日期分数失败",kidHabitPoints.getUserName());
            }
        });
        return true;
    }
}
