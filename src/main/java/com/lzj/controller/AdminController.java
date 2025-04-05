package com.lzj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.mapper.GoodsMapper;
import com.lzj.mapper.GoodstypeMapper;
import com.lzj.mapper.UserMapper;
import com.lzj.pojo.Goods;
import com.lzj.pojo.Goodstype;
import com.lzj.pojo.User;
import com.lzj.service.GoodsMapperService;
import com.lzj.utils.ShortUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/a")
public class AdminController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsMapperService goodsMapperService;
    @Autowired
    private GoodstypeMapper goodstypeMapper;

    //管理员主页，在后期考虑添加数据展示
    @GetMapping("/system")
    private String system() {
        return "admin/admin-system";
    }

    //---------------------用户号管理-------------------------------
    //管理员查询用户列表
    @GetMapping("/userManagement")
    private String userManagement(Model model, Authentication auth) {
        System.out.println("Current user: " + auth.getPrincipal());
        List<User> users = userMapper.selectList(null);
        model.addAttribute("userList", users);
        return "admin/admin-userManagement";
    }

    // 删除用户方法
    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable("id") String userId, RedirectAttributes redirectAttributes) {
        try {
            userMapper.deleteById(userId);
            redirectAttributes.addFlashAttribute("successMessage", "用户删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "删除失败: " + e.getMessage());
        }
        return "redirect:/a/userManagement"; // 重定向到用户列表页
    }

    //添加用户中途转载界面控制
    @GetMapping("/addUser")
    public String GoaddUser() {
        return "/admin/admin-adduser";
    }

    //添加用户控制
    @PostMapping("/adduser")
    public String addUser(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email,
                          @RequestParam("gender") Integer gender,
                          @RequestParam("age") String age,       //年龄
                          @RequestParam("number") String number,    //电话号
                          @RequestParam("address") String address,  //地址
                          RedirectAttributes redirectAttributes) {
        try {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setGender(gender);
            user.setAge(Integer.parseInt(age));
            user.setNumber(Integer.parseInt(number));
            user.setAddress(address);
            user.setIsAdmin(0);    //添加用户默认是普通用户
            //gender,性别，之后添加
            int insert = userMapper.insert(user);
            System.out.println("insert=>" + insert);
            redirectAttributes.addFlashAttribute("successMessage", "用户添加成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "添加失败: " + e.getMessage());
        }
        return "redirect:/a/userManagement";
    }

    //用户修改中途转载界面控制
    @GetMapping("/updateUser/{id}")
    public String GoUpdate(@PathVariable("id") String userId, Model model) {
        User user = userMapper.selectById(userId);
        model.addAttribute("user", user);
        return "/admin/admin-updateuser";
    }

    //用户修改控制
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
        try {
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
            int update = userMapper.updateById(user);
            redirectAttributes.addFlashAttribute("successMessage", "用户修改成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "修改失败: " + e.getMessage());
        }
        return "redirect:/a/userManagement";
    }

    //查询控制
    @GetMapping("/selectUser") // 建议使用GET方法进行查询操作
    public String selectUser(
            @RequestParam(value = "field", required = false) String field,              //选择查询的条件
            @RequestParam(value = "queryType", required = false) String queryType,      //选择查询的类型
            @RequestParam(value = "keyword", required = false) String keyword,          //输入框的值:userId、username、email
            @RequestParam(value = "genderValue", required = false) String genderValue,  //性别
            @RequestParam(value = "roleValue", required = false) Boolean roleValue,     //权限
            @RequestParam(value = "dateValue", required = false)                        //创建日期
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateValue,
            Model model) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 增加字段校验
        if (field == null || field.isEmpty()) {
            model.addAttribute("message", "没有找到想要查询的内容");
            return "admin/admin-userManagement";
        }
        System.out.println("选择查询条件=>" + field);
        System.out.println("类型查询类型=>" + queryType);
        System.out.println("输入值=>" + keyword);
        System.out.println("性别=>" + genderValue);
        System.out.println("权限=>" + roleValue);
        System.out.println("日期=>" + dateValue);
        if (Objects.equals(queryType, "fuzzy")) {
            // 模糊查询的条件判断
            switch (field) {
                case "userId":
                    queryWrapper.like("user_id", "%" + keyword + "%");  // 数字字段需确认是否支持模糊
                    break;
                case "username":
                    queryWrapper.like("username", "%" + keyword + "%"); // 全模糊查询
                    break;
                case "email":
                    queryWrapper.like("email", "%" + keyword + "%");    // 全模糊查询
                    break;
                case "isAdmin":  // 枚举类型保持精确查询
                    queryWrapper.lambda().eq(User::getIsAdmin, roleValue);
                    break;
                case "gender":   // 性别保持精确查询
                    queryWrapper.eq("gender", genderValue);
                    break;
                case "createTime":  // 时间保持精确查询
                    queryWrapper.apply("DATE(create_time) = DATE({0})", dateValue);
                    break;
                case "age":
                    queryWrapper.like("age", "%" + keyword + "%");
                    break;
                case "number":
                    queryWrapper.like("number", "%" + keyword + "%");
                    break;
                case "address":
                    queryWrapper.like("address", "%" + keyword + "%");
                    break;
                default:
                    model.addAttribute("message", "无效的搜索类型");
                    return "admin/admin-userManagement";
            }
        } else {
            // 普通查询的条件判断
            switch (field) {
                case "userId":
                    queryWrapper.lambda().eq(User::getUserId, keyword); // 注意数据库实际字段名
                    break;
                case "username":
                    queryWrapper.eq("username", keyword);
                    break;
                case "email":
                    queryWrapper.eq("email", keyword);
                    break;
                case "isAdmin":
                    queryWrapper.lambda().eq(User::getIsAdmin, roleValue);
                    break;
                case "gender":
                    if (Objects.equals(genderValue, "1")) {
                        genderValue = "1";
                    } else {
                        genderValue = "0";
                    }
                    queryWrapper.eq("gender", genderValue);
                    break;
                case "createTime":
                    queryWrapper.apply("DATE(create_time) = DATE({0})", dateValue);
                    break;
                case "age":
                    queryWrapper.eq("age", keyword);
                    break;
                case "number":
                    queryWrapper.eq("number", keyword);
                    break;
                case "address":
                    queryWrapper.eq("address", keyword);
                    break;
                default:
                    model.addAttribute("message", "无效的搜索类型");
                    return "admin/admin-userManagement";
            }
        }

        List<User> userList = userMapper.selectList(queryWrapper);
        model.addAttribute("userList", userList);

        if (userList.isEmpty()) {
            model.addAttribute("message1", "未找到匹配的用户");
        } else {
            model.addAttribute("keyword", keyword);
            model.addAttribute("field", field);
            model.addAttribute("queryType", queryType);
            model.addAttribute("message2", "找到" + userList.size() + "条记录");
        }

        return "admin/admin-userManagement";
    }


    //---------------------商品管理-------------------------------
    //管理员查询商品列表
    @GetMapping("/goodsManagement")
    private String goodsManagement(Model model) {
        List<Goods> goodsListWithType = goodsMapperService.getGoodsListWithType();
        List<Goodstype> goodstypes = goodstypeMapper.selectList(null);
        model.addAttribute("goodstypes", goodstypes);
        model.addAttribute("goodsList", goodsListWithType);
        return "admin/admin-goodsManagement";
    }
    // 删除商品方法
    //这里删除是逻辑删除，实际数据库中并没有删除，只是将deleted字段设置为1
    @GetMapping("/goodsDelete/{id}")
    public String deleteGoods(@PathVariable("id") String goodsId, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("删除商品ID：" + goodsId);
            goodsMapper.deleteById(goodsId);
            redirectAttributes.addFlashAttribute("successMessage", "商品删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "删除失败: " + e.getMessage());
        }
        return "redirect:/a/goodsManagement"; // 重定向到用户列表页
    }

    //商品修改中途转载界面控制
    @GetMapping("/updateGoods/{id}")
    public String GoGoodsUpdate(@PathVariable("id") String goodsId, Model model) {
        Goods goods = goodsMapper.selectById(goodsId);
        Goodstype goodstype = goodstypeMapper.selectById(goods.getGoodsType());
        List<Goodstype> goodstypeList = goodstypeMapper.selectList(null);
        model.addAttribute("goods", goods);
        model.addAttribute("goodstype", goodstype);
        model.addAttribute("goodstypeList", goodstypeList);
        System.out.println("商品信息：" + goods);
        System.out.println("商品类型：" + goodstype);
        System.out.println("商品类型列表：" + goodstypeList);

        return "/admin/admin-updateuserGoods";
    }

    //商品修改控制
    @PostMapping("/updateGoods")
    public String updateGoods(@RequestParam("goodsId") String goodsId,  // 商品ID
                              @RequestParam("goodsName") String goodsName,  // 商品名称
                              @RequestParam("goodsType") Integer goodsType,     // 商品类型
                              @RequestParam("price") Double price,     // 商品价格
                              @RequestParam("description") String description,      // 商品描述
                              @RequestParam("inventory") Integer inventory,      // 库存数量
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, // 商品图片文件
                              Goods goods) {
        // 设置商品基本信息
        goods.setGoodsId(goodsId);
        goods.setGoodsName(goodsName);
        goods.setGoodsType(String.valueOf(goodsType));
        goods.setPrice(BigDecimal.valueOf(price));
        goods.setDescription(description);
        goods.setInventory(inventory);

        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 获取原始文件名及扩展名
                String originalFilename = imageFile.getOriginalFilename();
                String extension = ".png"; // 默认扩展名
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String fileName = goodsId + extension; // 商品ID + 扩展名

                // 构建保存路径
                String projectRoot = System.getProperty("user.dir");
                String uploadDir = projectRoot + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator + "goods" + File.separator;
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                // 保存文件
                File destFile = new File(dir, fileName);
                imageFile.transferTo(destFile);

                // 更新图片URL（路径无需包含src/main/resources/static）
                goods.setImageUrl("/img/goods/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // 错误处理，例如重定向到错误页面
                return "redirect:/error?msg=图片上传失败";
            }
        }
        // 更新数据库
        goodsMapper.updateById(goods);
        return "redirect:/a/goodsManagement";
    }


    //添加商品跳转
    @GetMapping("/addGoods")
    public String GoaddGoods(Model model){
        List<Goodstype> goodstypeList = goodstypeMapper.selectList(null);
        model.addAttribute("goodstypeList", goodstypeList);
        return "/admin/admin-addGoods";
    }
    //添加商品控制处理
    @PostMapping("/addGoods")
    public String addGoods(@RequestParam("goodsName") String goodsName,  // 商品名称
                           @RequestParam("goodsType") Integer goodsType,     // 商品类型
                           @RequestParam("price") Double price,     // 商品价格
                           @RequestParam("description") String description,      // 商品描述
                           @RequestParam("inventory") Integer inventory,      // 库存数量
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, // 商品图片文件
                           Goods goods,
                           Model model){
// 设置商品基本信息
        String goodsId = ShortUUID.generate();
        goods.setGoodsId(goodsId.toString());
        goods.setGoodsName(goodsName);
        goods.setGoodsType(String.valueOf(goodsType));
        goods.setPrice(BigDecimal.valueOf(price));
        goods.setDescription(description);
        goods.setInventory(inventory);
        System.out.println("新建商品信息==》"+goods);
        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 获取原始文件名及扩展名
                String originalFilename = imageFile.getOriginalFilename();
                String extension = ".png"; // 默认扩展名
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String fileName = goodsId + extension; // 商品ID + 扩展名

                // 构建保存路径
                String projectRoot = System.getProperty("user.dir");
                String uploadDir = projectRoot + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator + "goods" + File.separator;
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                // 保存文件
                File destFile = new File(dir, fileName);
                imageFile.transferTo(destFile);

                // 更新图片URL（路径无需包含src/main/resources/static）
                goods.setImageUrl("/img/goods/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // 错误处理，例如重定向到错误页面
                return "redirect:/error?msg=图片上传失败";
            }
        }


        goodsMapper.insert(goods);
        return "redirect:/a/goodsManagement";
    }

    // 查询商品
    @GetMapping("/selectGoods")
    public String selectGoods(@RequestParam(value = "field", required = false) String field,              // 选择查询的条件
                              @RequestParam(value = "queryType", required = false) String queryType,      // 选择查询的类型
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "goodsType", required = false) String goodsType,
                              @RequestParam(value = "dateValue", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateValue, // 创建日期
                              Model model) {
        String goodsName = null;
        String goodsTypeName = null;
        List<Goods> goodsList = null;
        if (Objects.equals(queryType, "fuzzy")) {
            switch (field) {
                case "goodsName":
                    goodsName = keyword; // 模糊查询商品名称
                    break;
                case "createTime":
                    // 时间保持精确查询，需要单独处理
                    List<Goods> goodsListByDate = goodsMapperService.getGoodsListByDate(dateValue);
                    model.addAttribute("goodsList", goodsListByDate);
                default:
                    model.addAttribute("message", "无效的搜索类型");
                    return "admin/admin-userManagement";
            }
            goodsList = goodsMapperService.selectGoodsWithTypeByCondition(goodsName, goodsTypeName);
        } else {
            // exact 普通查询的条件判断
            switch (field) {
                case "goodsName":
                    goodsName = keyword; // 精确查询商品名称
                    break;
                case "createTime":
                    // 时间保持精确查询，需要单独处理
                    List<Goods> goodsListByDate = goodsMapperService.getGoodsListByDate(dateValue);
                    model.addAttribute("goodsList", goodsListByDate);
                case "goodsType":
                    goodsTypeName = goodsType; // 精确查询商品类型名称
                    break;
                default:
                    model.addAttribute("message", "无效的搜索类型");
                    return "admin/admin-userManagement";
            }
            goodsList = goodsMapperService.selectGoodsWithTypeByConditionExact(goodsName, goodsTypeName);
        }

        // 调用 selectGoodsWithTypeByCondition 方法进行查询
        model.addAttribute("goodsList", goodsList); //查询展示的数据

        List<Goodstype> goodstypes = goodstypeMapper.selectList(null);
        model.addAttribute("keyword", keyword);      //搜索框的输入
        model.addAttribute("field", field);         //选择的条件
        model.addAttribute("goodstypes", goodstypes); //全部的商品类型
        System.out.println("选择了==》"+field);
        System.out.println("输入了==》"+keyword);
        System.out.println("类型是==》"+goodsType);
        if (goodsList.isEmpty()) {
            model.addAttribute("message1", "未找到匹配的商品");
        }else{
            model.addAttribute("message2", "找到" + goodsList.size() + "条记录");
        }
        return "/admin/admin-goodsManagement";

    }
    //添加商品分类中转
    @GetMapping("/addGoodsType")
    public String goaddgoodsType(Model model){
        return "/admin/admin-addGoodsType";
    }

    //添加商品分类
    @PostMapping("/addGoodsType")
    public String addgoodsType(@RequestParam(value ="goodsTypeName") String goodsTypeName,Model model){
        int insert = goodstypeMapper.insert(new Goodstype(null,goodsTypeName));
        if (insert > 0) {
            model.addAttribute("message2", "分类"+goodsTypeName+"添加成功");
        }
        return "redirect:/a/goodsManagement";
    }



}
