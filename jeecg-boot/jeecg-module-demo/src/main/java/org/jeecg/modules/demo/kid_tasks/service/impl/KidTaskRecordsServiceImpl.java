package org.jeecg.modules.demo.kid_tasks.service.impl;

import org.jeecg.modules.demo.kid_tasks.entity.KidTaskRecords;
import org.jeecg.modules.demo.kid_tasks.mapper.KidTaskRecordsMapper;
import org.jeecg.modules.demo.kid_tasks.service.IKidTaskRecordsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 儿童习惯任务记录表
 * @Author: jeecg-boot
 * @Date:   2025-04-21
 * @Version: V1.0
 */
@Service
@Slf4j
public class KidTaskRecordsServiceImpl extends ServiceImpl<KidTaskRecordsMapper, KidTaskRecords> implements IKidTaskRecordsService {
}
