package com.lzj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.pojo.Goods;
import com.lzj.mapper.GoodsMapper;
import com.lzj.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzj
 * @since 2025-03-23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Transactional // 确保事务生效
    public Page<Goods> getGoodsPage(int pageNum, int pageSize) {
        Page<Goods> page = new Page<>(pageNum, pageSize);
        return goodsMapper.selectPage(page, null);
    }
}
