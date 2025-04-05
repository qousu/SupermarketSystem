package com.lzj.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author lzj
 * @since 2025-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id（雪花算法生成）
     */
    @TableId(value = "goods_id", type = IdType.ASSIGN_UUID)
    private String goodsId;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品类型
     */
    @TableField("goods_type")
    private String goodsType;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 主图
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 商品介绍
     */
    @TableField("description")
    private String description;

    /**
     * 库存
     */
    @TableField("inventory")
    private Integer inventory;

    /**
     * 入库时间
     */
    @TableField(value = "datetime",fill = FieldFill.INSERT)
    private LocalDateTime datetime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 新增方法：获取时间差（小时）
    public long getUpdateHoursDiff() {
        if (this.updateTime == null) return Long.MAX_VALUE;
        return ChronoUnit.HOURS.between(
                updateTime.atZone(ZoneId.systemDefault()),
                ZonedDateTime.now()
        );
    }
    public long getDatetimeHoursDiff() {
        if (this.datetime == null) return Long.MAX_VALUE;
        return ChronoUnit.HOURS.between(
                datetime.atZone(ZoneId.systemDefault()),
                ZonedDateTime.now()
        );
    }
}
