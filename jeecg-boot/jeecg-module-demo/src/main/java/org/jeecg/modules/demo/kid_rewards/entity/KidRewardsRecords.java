package org.jeecg.modules.demo.kid_rewards.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 儿童习惯奖励记录表
 * @Author: jeecg-boot
 * @Date:   2025-04-22
 * @Version: V1.0
 */
@Data
@TableName("kid_rewards_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="儿童习惯奖励记录表")
public class KidRewardsRecords implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private java.lang.String id;
	/**创建人*/
    @Schema(description = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private java.lang.String sysOrgCode;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @Schema(description = "用户id")
    private java.lang.String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Schema(description = "用户名")
    private java.lang.String userName;
	/**奖励项id*/
	@Excel(name = "奖励项id", width = 15)
    @Schema(description = "奖励项id")
    private java.lang.String rewardId;
	/**获得奖励日期*/
	@Excel(name = "获得奖励日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "获得奖励日期")
    private java.util.Date rewardDate;
	/**奖励所需分数*/
	@Excel(name = "奖励所需分数", width = 15)
    @Schema(description = "奖励所需分数")
    private java.lang.Integer rewardPoint;
	/**奖励项名称*/
	@Excel(name = "奖励项名称", width = 15)
    @Schema(description = "奖励项名称")
    private java.lang.String rewardName;
}
