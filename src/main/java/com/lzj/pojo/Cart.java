package com.lzj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author lzj
 * @since 2025-04-02
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 用户Id，用于绑定用户

     */
    private Integer cartUserId;

    /**
     * 购物车信息，这是一个列表用于保存商品Id，以及加入购物车的时间
     */
    private String cartGoods;

}
