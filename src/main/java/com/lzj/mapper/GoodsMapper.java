package com.lzj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzj
 * @since 2025-03-23
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 自定义 SQL 查询：联表查询 goods 和 goodsType 表
     */
    @Select("SELECT g.goods_id, g.goods_name, gt.goods_type_name as goods_type, g.price, g.image_url, g.description, g.inventory,g.datetime, g.updateTime " +
            "FROM goods g JOIN goodsType gt ON g.goods_type = gt.goods_type_id")
    @Results({
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "goodsName", column = "goods_name"),
            @Result(property = "goodsType", column = "goods_type"),  // 这里映射的是 goods_type_name
            @Result(property = "price", column = "price"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "datetime", column = "datetime"),
            @Result(property = "updateTime", column = "updateTime")
    })
    List<Goods> selectGoodsWithType();

    /**
     * 自定义 SQL 查询：联表查询 goods 和 goodsType 表
     * 根据 goodsName 和 goodsType 进行条件查询，返回一个 Goods 列表
     */
    @Select("<script>" +
            "SELECT g.goods_id, g.goods_name, gt.goods_type_name as goods_type, " +
            "g.price, g.image_url, g.description, g.inventory, " +
            "g.datetime, g.updateTime " +
            "FROM goods g JOIN goodsType gt ON g.goods_type = gt.goods_type_id " +
            "<where>" +
            "   <if test='goodsName != null'> AND g.goods_name = #{goodsName} </if>" +
            "   <if test='goodsType != null'> AND gt.goods_type_name = #{goodsType} </if>" +
            "</where>" +
            "</script>")

    @Results({
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "goodsName", column = "goods_name"),
            @Result(property = "goodsType", column = "goods_type"),
            @Result(property = "price", column = "price"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "datetime", column = "datetime"),
            @Result(property = "updateTime", column = "updateTime")
    })
    List<Goods> selectGoodsWithTypeByConditionExact(
            @Param("goodsName") String goodsName,
            @Param("goodsType") String goodsType
    );



    /**
     * 自定义 SQL 查询：联表查询 goods 和 goodsType 表
     * 根据 goodsName 和 goodsType 进行条件查询，并且可以模糊查询，返回一个 Goods 列表
     */
    @Select("<script>" +
            "SELECT g.goods_id, g.goods_name, gt.goods_type_name as goods_type, g.price, g.image_url, g.description, g.inventory, g.datetime, g.updateTime " +
            "FROM goods g JOIN goodsType gt ON g.goods_type = gt.goods_type_id " +
            "<where>" +
            "   <if test='goodsName != null'> AND g.goods_name LIKE CONCAT('%', #{goodsName}, '%') </if>" +
            "   <if test='goodsType != null'> AND gt.goods_type_name = #{goodsType} </if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "goodsName", column = "goods_name"),
            @Result(property = "goodsType", column = "goods_type"),  // 这里映射的是 goods_type_name
            @Result(property = "price", column = "price"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "datetime", column = "datetime"),
            @Result(property = "updateTime", column = "updateTime")
    })
    List<Goods> selectGoodsWithTypeByCondition(@Param("goodsName") String goodsName, @Param("goodsType") String goodsType);

    // 在 GoodsMapper 中修改时间方法
    @Select("SELECT g.goods_id, g.goods_name, gt.goods_type_name as goods_type, g.price, g.image_url, g.description, g.inventory, g.datetime, g.updateTime " +
            "FROM goods g JOIN goodsType gt ON g.goods_type = gt.goods_type_id " +
            "WHERE DATE(g.datetime) = DATE(#{dateValue})")
    @Results({
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "goodsName", column = "goods_name"),
            @Result(property = "goodsType", column = "goods_type"),
            @Result(property = "price", column = "price"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "description", column = "description"),
            @Result(property = "inventory", column = "inventory"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "datetime", column = "datetime"),
            @Result(property = "updateTime", column = "updateTime")
    })
    List<Goods> getGoodsListByDate(@Param("dateValue") LocalDate dateValue);
}
