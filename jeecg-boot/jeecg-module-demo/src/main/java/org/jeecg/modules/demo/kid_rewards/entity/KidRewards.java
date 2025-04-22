package org.jeecg.modules.demo.kid_rewards.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 儿童习惯奖励模板表
 * @Author: jeecg-boot
 * @Date:   2025-04-12
 * @Version: V1.0
 */
@Data
@TableName("kid_rewards")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="儿童习惯奖励模板表")
public class KidRewards implements Serializable {
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
	/**奖励名称*/
	@Excel(name = "奖励名称", width = 15)
    @Schema(description = "奖励名称")
    private java.lang.String title;
	/**奖励描述*/
	@Excel(name = "奖励描述", width = 15)
    @Schema(description = "奖励描述")
    private java.lang.String description;
	/**所需分数*/
	@Excel(name = "所需分数", width = 15)
    @Schema(description = "所需分数")
    private java.lang.Integer reqPoints;
	/**图标*/
	@Excel(name = "图标", width = 15)
    @Schema(description = "图标")
    private java.lang.String icon;
	/**是否启用*/
    @Excel(name = "是否启用", width = 15,replace = {"是_Y","否_N"} )
    @Schema(description = "是否启用")
    private java.lang.String status;
	/**库存数量*/
	@Excel(name = "库存数量", width = 15)
    @Schema(description = "库存数量")
    private java.lang.Integer stock;
	/**是否限时*/
    @Excel(name = "是否限时", width = 15,replace = {"是_Y","否_N"} )
    @Schema(description = "是否限时")
    private java.lang.String isLimited;
	/**生效日期*/
	@Excel(name = "生效日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "生效日期")
    private java.util.Date startTime;
	/**结束日期*/
	@Excel(name = "结束日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "结束日期")
    private java.util.Date endTime;
}
