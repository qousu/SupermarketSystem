package com.lzj.service;

import com.lzj.mapper.GoodsMapper;
import com.lzj.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
public class GoodsMapperService {
    @Autowired
    private GoodsMapper goodsMapper;
    /**
     * 获取商品列表（包含商品分类信息）
     */
    public List<Goods> getGoodsListWithType() {
        return goodsMapper.selectGoodsWithType();
    }

    public List<Goods> selectGoodsWithTypeByConditionExact(String goodsName, String goodsType) {
        return goodsMapper.selectGoodsWithTypeByConditionExact(goodsName, goodsType);
    }

    //条件查询商品，可以模糊查询
    public List<Goods> selectGoodsWithTypeByCondition(String goodsName, String goodsType) {
        return goodsMapper.selectGoodsWithTypeByCondition(goodsName, goodsType);
    }
    //根据日期查询商品
    public List<Goods> getGoodsListByDate(LocalDate dateValue) {
        return goodsMapper.getGoodsListByDate(dateValue);
    }



}
