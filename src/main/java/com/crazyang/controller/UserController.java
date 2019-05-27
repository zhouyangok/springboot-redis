package com.crazyang.controller;

import com.crazyang.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName UserController
 * @Description: TODO
 * @Author zhouyang
 * @Date 2019/5/26 下午10:49.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/getUser/{name}")
    @ResponseBody
    public User getUser(@PathVariable String name){
       User redisUser = (User) redisTemplate.opsForValue().get(name);
       if(redisUser!=null){
           return redisUser;
       }
        User user = new User();
        user.setId(100L);
        user.setUsername("helloRedis");
        user.setAge(123);
        redisTemplate.opsForValue().set(user.getUsername(),user);
        return user;
    }

}
