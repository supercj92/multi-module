package com.cfysu.multi.manager;

import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.util.Page;


public interface UserManager {
    Page<User> queryForPage(UserQuery userQuery, Page<User> page);
}
