package org.jeecg.modules.demo.wechatpulic.entity;

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
 * @Description: 微信公众号文章管理
 * @Author: jeecg-boot
 * @Date:   2025-04-08
 * @Version: V1.0
 */
@Data
@TableName("wechart_public_article")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="微信公众号文章管理")
public class WechartPublicArticle implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private String sysOrgCode;
	/**文章id*/
	@Excel(name = "文章id", width = 15)
    @Schema(description = "文章id")
    private String articleId;
	/**作者*/
	@Excel(name = "作者", width = 15)
    @Schema(description = "作者")
    private String author;
	/**图文消息摘要*/
	@Excel(name = "图文消息摘要", width = 15)
    @Schema(description = "图文消息摘要")
    private String digest;
	/**文章标题*/
	@Excel(name = "文章标题", width = 15)
    @Schema(description = "文章标题")
    private String title;
	/**文章内容*/
	@Excel(name = "文章内容", width = 15)
    @Schema(description = "文章内容")
    private String content;
	/**文章链接*/
	@Excel(name = "文章链接", width = 15)
    @Schema(description = "文章链接")
    private String titleUrl;
	/**文章分类*/
	@Excel(name = "文章分类", width = 15)
    @Schema(description = "文章分类")
    private String category;
	/**封面图片的URL*/
	@Excel(name = "封面图片的URL", width = 15)
    @Schema(description = "封面图片的URL")
    private String thumbUrl;
}
