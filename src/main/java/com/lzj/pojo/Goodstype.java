package com.lzj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@TableName("goodstype")
@NoArgsConstructor
@AllArgsConstructor
public class Goodstype implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "goods_type_id", type = IdType.AUTO)
    private Long goodsTypeId;

    /**
     * 商品类型的名称
     */
    private String goodsTypeName;

}
