package com.coolron.web.dao;

import com.coolron.web.domain.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getAllUser();

    List<User> getUserListByCityId(Integer cityId);

    User getUserByUsername(String username);
}