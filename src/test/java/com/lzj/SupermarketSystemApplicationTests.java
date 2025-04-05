package com.lzj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzj.mapper.GoodsMapper;
import com.lzj.mapper.GoodstypeMapper;
import com.lzj.mapper.UserMapper;
import com.lzj.pojo.Goods;
import com.lzj.pojo.Goodstype;
import com.lzj.pojo.User;
import com.lzj.service.GoodsMapperService;
import com.lzj.service.impl.GoodsServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SupermarketSystemApplicationTests {

    @Autowired
    private UserMapper userMapper;

//    @Test
//    void contextLoads() {
//        List<User> users = userMapper.selectList(null);
//        System.out.println(users);
//    }
    //测试条件查询
//    @Test
//    void testSelect() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(User::getUserId,1);
//        List<User> userList = userMapper.selectList(queryWrapper);
//        assertTrue(userList.size()>0);
//        userList.forEach(System.out::println);
//    }
    //分页测试，成功
//    @Test
//    void Page(){
//        //当前页，页面显示数据
//        //在开发的过程中，传入数据inn，inn代表的是第几页，则
//        //只需(inn,5)即可
//        Page<User> page = new Page<>(2,5);
//        Page<User> userPage = userMapper.selectPage(page, null);
//        for (User user : userPage.getRecords()) {
//            System.out.println(user);
//        }
//        //查询一共有多少条数据
//        System.out.println(userPage.getTotal());
//    }


//    @Autowired
//    GoodsMapperService goodsMapperService;
//    //测试链表查询。成功！
//    @Test
//    void testGoodsMapperService(){
//        List<Map<String, Object>> goodsListWithType = goodsMapperService.getGoodsListWithType();
//        for (Map<String, Object> map : goodsListWithType) {
//            System.out.println(map);
//        }
//    }

    @Autowired
    GoodstypeMapper goodstypeMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    void testGoodstypeMapper(){
        List<Goodstype> goodstypes = goodstypeMapper.selectList(null);
        System.out.println(goodstypes);
    }
    //分页查询
//    @Test
//    public void testPage() {
//        //当前页，页面显示数据
//        //在开发的过程中，传入数据inn，inn代表的是第几页，则
//        //只需(inn,5)即可
//        Page<Goods> page = new Page<>(2,5);
//        Page<Goods> goodsPage = goodsMapper.selectPage(page, null);
//        System.out.println("分页商品列表========>" + goodsPage);
//
//        for (Goods goods : goodsPage.getRecords()) {
//            System.out.println("商品"+goods);
//        }
//        //查询一共有多少条数据
//        System.out.println(goodsPage.getTotal());
//    }

    @Autowired
    GoodsServiceImpl goodsService;
    @Test
    public void getGoodsPage(){
        int page = 2;
        int size = 5;
        Page<Goods> goodsPage = goodsService.getGoodsPage(page, size);
        List<Goods> goodsList = goodsPage.getRecords();        //将分页查询的数据转为goodsList
        for (Goods goods : goodsList) {
            System.out.println(goods);
        }

    }
}
