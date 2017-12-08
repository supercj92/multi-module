package com.cfysu.multi.service.impl;

import com.cfysu.multi.dao.UserDao;
import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.service.UserService;
import com.cfysu.multi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public Page<User> queryForPage(UserQuery query, Page<User> page) {
        return userDao.queryForPage(query, page);
    }
}