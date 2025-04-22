package org.jeecg.modules.demo.kid_tasks.entity;

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
 * @Description: 儿童习惯任务模板表
 * @Author: jeecg-boot
 * @Date:   2025-04-12
 * @Version: V1.0
 */
@Data
@TableName("kid_tasks")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="儿童习惯任务模板表")
public class KidTasks implements Serializable {
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
	/**任务标题*/
	@Excel(name = "任务标题", width = 15)
    @Schema(description = "任务标题")
    private java.lang.String title;
	/**任务描述*/
	@Excel(name = "任务描述", width = 15)
    @Schema(description = "任务描述")
    private java.lang.String description;
	/**任务分数*/
	@Excel(name = "任务分数", width = 15)
    @Schema(description = "任务分数")
    private java.lang.Integer points;
	/**任务图标*/
	@Excel(name = "任务图标", width = 15)
    @Schema(description = "任务图标")
    private java.lang.String icon;
	/**任务学习材料*/
	@Excel(name = "任务学习材料", width = 15)
    @Schema(description = "任务学习材料")
    private java.lang.String learningMaterials;
	/**是否启用*/
    @Excel(name = "是否启用", width = 15,replace = {"是_Y","否_N"} )
    @Schema(description = "是否启用")
    private java.lang.String status;
	/**是否为每日任务*/
    @Excel(name = "是否为每日任务", width = 15,replace = {"是_Y","否_N"} )
    @Schema(description = "是否为每日任务")
    private java.lang.String isDaily;
}
