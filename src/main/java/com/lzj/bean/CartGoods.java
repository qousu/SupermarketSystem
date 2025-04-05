package com.lzj.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartGoods {
    private String goodsId;         // 商品ID
    private String goodsName;    // 商品名称
    private BigDecimal price;        // 商品价格
    private String imageUrl;     // 商品图片地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // 关键注解
    private Date addTime;        // 加入购物车时间（使用 Date 类型）
    private int quantity;      // 商品数量

    public CartGoods(Date addTime, String goodsId, String goodsName, String imageUrl, BigDecimal price, int quantity) {
        this.addTime = addTime;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }
    public CartGoods() {
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
