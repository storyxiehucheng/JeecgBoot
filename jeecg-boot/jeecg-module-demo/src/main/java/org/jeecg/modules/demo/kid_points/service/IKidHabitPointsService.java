package org.jeecg.modules.demo.kid_points.service;

import org.jeecg.modules.demo.kid_points.entity.KidHabitPoints;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 儿童习惯积分表
 * @Author: jeecg-boot
 * @Date:   2025-04-22
 * @Version: V1.0
 */
public interface IKidHabitPointsService extends IService<KidHabitPoints> {
    Boolean checkTodayPointIfNewDayForUser(String userName);
    Boolean checkTodayPointIfNewDayForAll();
}
