package com.lzj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.pojo.Goods;
import com.lzj.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzj
 * @since 2025-03-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
