package com.jdktomcat.demo.dead.lock.restart.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-05-13
 */
public interface IUserService extends IService<User> {

    List<User> findAllUser();
}
