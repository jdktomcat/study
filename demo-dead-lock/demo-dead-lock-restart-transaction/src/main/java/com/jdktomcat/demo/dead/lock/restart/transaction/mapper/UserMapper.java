package com.jdktomcat.demo.dead.lock.restart.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2020-05-13
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> findAllUser();
}
