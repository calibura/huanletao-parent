package com.huanletao.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huanletao.pojo.JsonResult;
import com.huanletao.pojo.TbSeller;
import com.huanletao.sellergoods.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private SellerService userService;

    @RequestMapping("/regist")
    public JsonResult regist(@RequestBody TbSeller seller){
        try{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            seller.setPassword(encoder.encode(seller.getPassword()));
            userService.add(seller);
            return new JsonResult(true,"注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"注册失败");
        }
    }

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 10; i++) {
            String encode = encoder.encode("abcdefg");
            System.out.println(encode);
        }

    }
}
