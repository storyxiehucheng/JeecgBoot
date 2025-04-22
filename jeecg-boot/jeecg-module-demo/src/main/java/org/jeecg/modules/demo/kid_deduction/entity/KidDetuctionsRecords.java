package org.jeecg.modules.demo.kid_deduction.entity;

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
 * @Description: 儿童习惯扣分记录表
 * @Author: jeecg-boot
 * @Date:   2025-04-22
 * @Version: V1.0
 */
@Data
@TableName("kid_detuctions_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="儿童习惯扣分记录表")
public class KidDetuctionsRecords implements Serializable {
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
	/**扣分项id*/
	@Excel(name = "扣分项id", width = 15)
    @Schema(description = "扣分项id")
    private java.lang.String taskId;
	/**是否被扣分*/
    @Excel(name = "是否被扣分", width = 15,replace = {"是_Y","否_N"} )
    @Schema(description = "是否被扣分")
    private java.lang.String isCompleted;
	/**日期*/
	@Excel(name = "日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "日期")
    private java.util.Date taskDate;
	/**扣分日期*/
	@Excel(name = "扣分日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Schema(description = "扣分日期")
    private java.util.Date completedDate;
	/**扣分分数*/
	@Excel(name = "扣分分数", width = 15)
    @Schema(description = "扣分分数")
    private java.lang.Integer taskPoint;
	/**扣分项名称*/
	@Excel(name = "扣分项名称", width = 15)
    @Schema(description = "扣分项名称")
    private java.lang.String taskName;
}
