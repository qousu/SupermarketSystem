package com.lzj.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartGoods {
    private String goodsId;         // 商品ID
    private String goodsName;    // 商品名称
    private BigDecimal price;        // 商品价格
    private String imageUrl;     // 商品图片地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // 关键注解
    private Date addTime;        // 加入购物车时间（使用 Date 类型）
    private int quantity;      // 商品数量
}
