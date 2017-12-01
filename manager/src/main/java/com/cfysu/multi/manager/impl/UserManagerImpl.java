package com.cfysu.multi.manager.impl;

import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.manager.UserManager;
import com.cfysu.multi.service.UserService;
import com.cfysu.multi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userManager")
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserService userService;

    @Override
    public Page<User> queryForPage(UserQuery userQuery, Page<User> page) {
        return userService.queryForPage(userQuery, page);
    }
}
