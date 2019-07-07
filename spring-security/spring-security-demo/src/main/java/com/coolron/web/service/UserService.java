package com.coolron.web.service;


import com.coolron.web.domain.User;

import java.util.List;

/**
 * @Auther: xf
 * @Date: 2018/7/16 15:24
 * @Description:
 */
public interface UserService {
    List<User> getAllUser();

    int addUser(User user);

    User getUser(Integer id);

    int updateUser(User user);

    int deleteUser(Integer id);

    List<User> getUserListByCityId(Integer cityId);

    int saveUser(User user);
}
