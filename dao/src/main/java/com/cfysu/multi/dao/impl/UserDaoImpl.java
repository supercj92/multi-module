package com.cfysu.multi.dao.impl;


import com.cfysu.multi.dao.UserDao;
import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, UserQuery> implements UserDao, InitializingBean{

    public void afterPropertiesSet() throws Exception {
        setNameSpace(this.getClass().getName());
    }
}
