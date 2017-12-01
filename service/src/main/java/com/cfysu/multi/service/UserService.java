package com.cfysu.multi.service;

import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.util.Page;

import java.util.List;

public interface UserService {
    Page<User> queryForPage(UserQuery query, Page<User> Page);
}
