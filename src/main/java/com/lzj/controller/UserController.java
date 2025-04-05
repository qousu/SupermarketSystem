package com.lzj.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lzj.bean.CartGoods;
import com.lzj.mapper.CartMapper;
import com.lzj.mapper.GoodsMapper;
import com.lzj.mapper.GoodstypeMapper;
import com.lzj.mapper.UserMapper;
import com.lzj.pojo.Cart;
import com.lzj.pojo.Goods;
import com.lzj.pojo.Goodstype;
import com.lzj.pojo.User;
import com.lzj.service.impl.GoodsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/u")
public class UserController {
    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;
    private final CartMapper cartMapper;
    @Autowired
    private GoodstypeMapper goodstypeMapper;


    public UserController(UserMapper userMapper, GoodsMapper googsMapper, GoodsMapper goodsMapper, CartMapper cartMapper) {
        this.userMapper = userMapper;
        this.goodsMapper = goodsMapper;
        this.cartMapper = cartMapper;
    }

    //查看首页
    @GetMapping({"/index.html","/index"})
    private String index(){
        return "index";
    }

    //1.0查看商品列表
//    @GetMapping("/product")
//    private String product(Model model){
//        List<Goods> goodsList = goodsMapper.selectList(null);
//        System.out.println("商品列表========>" + goodsList);
//        model.addAttribute("goodsList", goodsList);
//        return "user/product";
//    }
//    @Autowired
//    GoodsServiceImpl goodsService;
    //2.0直接实现分页测试
//    @GetMapping("/product")
//    private String product(Model model){
//        Page<Goods> page = new Page<>(2,5);
//        Page<Goods> goodsPage = goodsMapper.selectPage(page, null);
//        System.out.println("分页商品列表========>" + goodsPage);
//        model.addAttribute("goodsList", goodsPage);
//        return "user/product";
//    }
    //3.0service实现分页查询,这是开始界面
//    @GetMapping("/goproduct")
//    private String product(
//            @RequestParam(defaultValue = "1",value = "page") int page,
//            @RequestParam(defaultValue = "10",value = "size") int size,
//            Model model
//    ){
//        Page<Goods> goodsPage = goodsService.getGoodsPage(page, size);
//        List<Goods> goodsList = goodsPage.getRecords();        //将分页查询的数据转为goodsList
//        List<Goodstype> goodstypeList = goodstypeMapper.selectList(null);
//        model.addAttribute("goodstypeList", goodstypeList); //商品类型列表
//        model.addAttribute("goodsList", goodsList);  //商品列表
//        model.addAttribute("currentPage", page); // 当前页码
//        model.addAttribute("size", size); // 每页大小
//        model.addAttribute("count", goodsPage.getTotal());    //总记录数
//        model.addAttribute("totalPages", goodsPage.getPages()); // 总页数
//        return "user/product";
//    }


//    //3.1下拉自动刷新界面(维护)
//    @GetMapping("/product")
//    @ResponseBody
//    public Map<String, Object> product(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5") int size
//    ) {
//        Page<Goods> goodsPage = goodsService.getGoodsPage(page, size);
//        List<Goods> goodsList = goodsPage.getRecords();
//        Map<String, Object> response = new HashMap<>();
//        response.put("goodsList", goodsList);
//        response.put("currentPage", page);
//        response.put("count", goodsPage.getTotal());
//        return response;
//    }
    //4.0筛选商品商品列表、初始展示
@GetMapping({"/goproduct"})
public String goproduct(
        @RequestParam(defaultValue = "1", value = "page") int page,
        @RequestParam(defaultValue = "10", value = "size") int size,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String priceRange,
        @RequestParam(required = false) String sortOrder,
        Model model) {

    QueryWrapper<Goods> wrapper = new QueryWrapper<>();
    System.out.println("category:" + category);

    // 1. 分类条件（支持多个分类，用逗号分隔）
    if (StringUtils.hasText(category)) {
        List<String> categories = Arrays.asList(category.split(","));
        wrapper.in("goods_type", categories);
    }

    // 2. 价格区间
    if (StringUtils.hasText(priceRange)) {
        switch (priceRange) {
            case "0-100":
                wrapper.between("Price", 0, 100);
                break;
            case "100-500":
                wrapper.between("Price", 100, 500);
                break;
            case "500":
                wrapper.ge("Price", 500);
                break;
            default:
                System.out.println("未知价格区间: " + priceRange);
                break;
        }
    }

    // 3. 排序
    if (StringUtils.hasText(sortOrder)) {
        switch (sortOrder) {
            case "price_asc":
                wrapper.orderByAsc("Price");
                break;
            case "price_desc":
                wrapper.orderByDesc("Price");
                break;
            case "sales":
                wrapper.orderByAsc("inventory");
                break;
            case "newest":
                wrapper.orderByDesc("datetime");
                break;
        }
    }

    // 分页查询
    Page<Goods> pageObj = new Page<>(page, size);
    Page<Goods> goodsPage = goodsMapper.selectPage(pageObj, wrapper);

    // 数据模型
    model.addAttribute("goodsList", goodsPage.getRecords()); // 商品列表
    model.addAttribute("goodstypeList", goodstypeMapper.selectList(null));//商品类型列表
    model.addAttribute("currentPage", page);    // 当前页码
    model.addAttribute("totalPages", goodsPage.getPages());   // 总页数
    model.addAttribute("pageSize", size);       // 每页大小

    // 参数回显
    model.addAttribute("category", category);      // 分类条件
    model.addAttribute("priceRange", priceRange);  // 价格区间
    model.addAttribute("sortOrder", sortOrder);     // 排序条件
//    System.out.println("分类条件==>"+category);
//    System.out.println("价格区间==>"+priceRange);
//    System.out.println("排序条件==>"+sortOrder);
    model.addAttribute("count", pageObj.getTotal()); // 总当前查询的记录数

    return "user/product";
}

//去新品页面
@GetMapping({"/gonewproduct"})
public String gonewproduct(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String priceRange,
        Model model) {

    QueryWrapper<Goods> wrapper = new QueryWrapper<>();

    // 1. 分类条件（支持多个分类，用逗号分隔）
    if (StringUtils.hasText(category)) {
        List<String> categories = Arrays.asList(category.split(","));
        wrapper.in("goods_type", categories);
    }

    // 2. 价格区间
    if (StringUtils.hasText(priceRange)) {
        switch (priceRange) {
            case "0-100":
                wrapper.between("Price", 0, 100);
                break;
            case "100-500":
                wrapper.between("Price", 100, 500);
                break;
            case "500":
                wrapper.ge("Price", 500);
                break;
            default:
                System.out.println("未知价格区间: " + priceRange);
                break;
        }
    }

    // 3. 排序
    wrapper.between("datetime", LocalDateTime.now().minusDays(3), LocalDateTime.now());
    List<Goods> goodsList = goodsMapper.selectList(wrapper);

    // 数据模型
    model.addAttribute("goodsList", goodsList); // 商品列表
    model.addAttribute("goodstypeList", goodstypeMapper.selectList(null));//商品类型列表

    // 参数回显
    model.addAttribute("category", category);      // 分类条件
    model.addAttribute("priceRange", priceRange);  // 价格区间
    model.addAttribute("count", goodsList.size());

    return "user/newproduct";
}

    //查看商品详情
    @GetMapping("/detail/{goodsId}")
    private String detail(@PathVariable String goodsId, Model model){
        System.out.println("商品id========>" + goodsId);
        Goods goods = goodsMapper.selectById(goodsId);
        model.addAttribute("goods",goods);
        return "user/product-detail";
    }
// ---------购物车--------------
    //查看购物车
    @GetMapping("/cart")
    private String cart(Principal principal, Model model) {
        String name = principal.getName(); // 当前用户名
        QueryWrapper<User> userwrapper = new QueryWrapper<>();
        QueryWrapper<User> username = userwrapper.eq("username", name);
        User user = userMapper.selectOne(username); // 当前用户具体信息

        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        QueryWrapper<Cart> userCartId = cartWrapper.eq("cart_user_id", user.getUserId()); // 根据用户id查询购物车
        Cart userCart = cartMapper.selectOne(userCartId);

        if (userCart == null) {
            // 当用户第一次点击购物车时，创建一个空的购物车
            Cart cart = new Cart();
            cart.setCartUserId(user.getUserId());
            cart.setCartGoods("[]"); // 初始化为空的 JSON 数组
            cartMapper.insert(cart);
        } else {
            model.addAttribute("cart", userCart.getCartGoods()); // 购物车商品
            System.out.println("购物车商品===>" + userCart.getCartGoods());
        }

        return "user/cart";
    }

    //添加商品到购物车
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") String goodsId,
                            Principal principal,
                            Model model) {
        // 1. 获取用户信息
        String username = principal.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", username);
        User user = userMapper.selectOne(userWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 获取商品信息
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 创建 CartGoods 对象
        CartGoods cartGoods = new CartGoods();
        cartGoods.setGoodsId(goods.getGoodsId());
        cartGoods.setGoodsName(goods.getGoodsName());
        cartGoods.setPrice(goods.getPrice());
        cartGoods.setImageUrl(goods.getImageUrl());
        cartGoods.setAddTime(new Date());
        cartGoods.setQuantity(1); // 默认数量为 1

        // 4. 处理购物车逻辑
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.eq("cart_user_id", user.getUserId());
        Cart userCart = cartMapper.selectOne(cartWrapper); // 根据用户id查询购物车

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        // 5. 如果购物车不存在，创建新购物车
        if (userCart == null) {
            userCart = new Cart();
            userCart.setCartUserId(user.getUserId());
            userCart.setCartGoods("[]"); // 初始化为空数组
            cartMapper.insert(userCart);
            // 重新查询确保获取到新插入的购物车
            userCart = cartMapper.selectOne(cartWrapper);
        }

        // 6. 解析原有购物车数据
        List<CartGoods> goodsList = new ArrayList<>();
        if (userCart.getCartGoods() != null && !userCart.getCartGoods().isEmpty()) {
            // 将 JSON 字符串转为 List
            goodsList = gson.fromJson(userCart.getCartGoods(), new TypeToken<List<CartGoods>>(){}.getType());
        }

        // 7. 检查是否已存在相同商品
        boolean isExist = false;
        for (CartGoods item : goodsList) {
            if (item.getGoodsId().equals(cartGoods.getGoodsId())) {
                // 如果存在相同商品，更新数量
                item.setQuantity(item.getQuantity() + 1); // 数量加 1
                isExist = true;
                break;
            }
        }

        // 8. 如果不存在相同商品，添加新商品
        if (!isExist) {
            goodsList.add(cartGoods);
        }

        // 9. 重新序列化为 JSON 数组
        String updatedJson = gson.toJson(goodsList);

        // 10. 更新购物车数据
        userCart.setCartGoods(updatedJson);
        int result = cartMapper.updateById(userCart);
        if (result > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

        model.addAttribute("cart", userCart);
        return "redirect:/u/cart";
    }

    //删除购物车商品
    @GetMapping("/deleteFromCart/{id}")
    public String deleteFromCart(@PathVariable("id") String goodsId, Principal principal) {
        // 获取用户和购物车信息（原有逻辑）
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", principal.getName()));
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("cart_user_id", user.getUserId()));

        // 解析购物车商品列表
        List<CartGoods> cartItems = JSON.parseArray(cart.getCartGoods(), CartGoods.class);

        // 删除指定商品
        cartItems.removeIf(item -> goodsId.equals(item.getGoodsId()));

        // 重新序列化为 JSON（此时日期会按注解格式输出）
        cart.setCartGoods(JSON.toJSONString(cartItems));
        cartMapper.updateById(cart);

        return "redirect:/u/cart";
    }

//    //查看我的订单
//    @GetMapping("/order")
//    private String order(){
//        return "user/order";
//    }
    //查看个人信息
    @GetMapping("/userInfo")
    private String userInfo(Model model, Principal principal){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",principal.getName());       //eq ，查询字段等于
        User user = userMapper.selectOne(wrapper);//selectOne 查询出一条数据时使用，如果多条则报错
        model.addAttribute("user",user);
        return "user/userInfo";
    }
    //用户个人信息修改控制
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("userId") Integer userId,
                             @RequestParam("userName") String userName,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("age") String age,       //年龄
                             @RequestParam("number") String number,    //电话号
                             @RequestParam("address") String address,  //地址
                             @RequestParam("gender") Integer gender,     //性别
                             @RequestParam("createTime") LocalDateTime createTime,
                             RedirectAttributes redirectAttributes) {
        System.out.println("age===>"+age);
        try {
            System.out.println("修改前用户信息========>" + userMapper.selectById(userId));
            User user = new User();
            user.setUserId(userId);
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setAge(Integer.parseInt(age));
            user.setNumber(Integer.parseInt(number));
            user.setAddress(address);
            user.setGender(gender);
            user.setCreateTime(createTime);
            System.out.println("修改后用户信息========>" + user);
            userMapper.updateById(user);
            redirectAttributes.addFlashAttribute("successMessage", "用户修改成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "修改失败: " + e.getMessage());
        }
        return "redirect:/u/userInfo";
    }

    //跳转到修改密码界面
    @GetMapping("/gopasswordUpdate")
    public String gopasswordUpdate(Model model,Principal principal){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",principal.getName());       //eq ，查询字段等于
        User user = userMapper.selectOne(wrapper);//selectOne 查询出一条数据时使用，如果多条则报错
        String password = user.getPassword();
        return "user/updatePassword";
    }
    //取消修改密码
    @GetMapping("/NoPasswordUpdate")
    private String NoPasswordUpdate(Model model, Principal principal){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",principal.getName());       //eq ，查询字段等于
        User user = userMapper.selectOne(wrapper);//selectOne 查询出一条数据时使用，如果多条则报错
        model.addAttribute("user",user);
        return "redirect:/u/userInfo";
}
    //修改密码逻辑处理
    @PostMapping("/passwordUpdate")
    public String passwordUpdate(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 Principal principal,
                                 HttpServletRequest request) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", principal.getName());
        User user = userMapper.selectOne(wrapper);
        String password = user.getPassword();

        if (!Objects.equals(currentPassword, password)) {
            model.addAttribute("Nmsg", "当前密码错误");
            return "/user/updatePassword";
        }

        if (!Objects.equals(newPassword, confirmPassword)) {
            model.addAttribute("Nmsg", "新密码与确认密码不一致");
            return "/user/updatePassword";
        }

        try {
            user.setPassword(newPassword);
            userMapper.updateById(user);

            // 强制登出逻辑
            new SecurityContextLogoutHandler().logout(
                    request,
                    null,
                    null
            );

            // 添加成功提示,修改成功后自动跳转到login界面
            redirectAttributes.addFlashAttribute("Ymsg", "密码修改成功，请重新登录");
            return "redirect:user/login";

        } catch (Exception e) {
            model.addAttribute("Nmsg", "密码修改失败，请重试");
            return "/user/updatePassword";
        }
    }

}
