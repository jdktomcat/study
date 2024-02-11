package com.jdktomcat.demo.dead.lock.restart.transaction.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.mapper.UserMapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.User;
import com.jdktomcat.demo.dead.lock.restart.transaction.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2020-05-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public Result<?> test(){
        //条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //等于sql  where id=1
        wrapper.eq("id",1);
        //Mybatis-Plus方法
        User user = userMapper.selectOne(wrapper);
        if (user != null){
            //统一返回值
            return Result.success(user);
        }else {
            return Result.failed("查询失败","");
        }
    }
}
