package com.cfysu.multi.service.impl;

import com.cfysu.multi.dao.UserDao;
import com.cfysu.multi.domain.domain.User;
import com.cfysu.multi.domain.query.UserQuery;
import com.cfysu.multi.service.UserService;
import com.cfysu.multi.util.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Page<User> queryForPage(UserQuery query, Page<User> page) {
        //兼容老代码
        //转换入参
        PageInfo<User> pageInfo = new PageInfo<User>();
        //pageInfo.setPageNum(page.getCurPage());
        pageInfo.setStartRow(page.getStart());
        pageInfo.setPageSize(page.getPageSize());

        //执行sql
        PageInfo<User> pageInfoRes = userDao.queryForPageByMybatisPlugin(query, pageInfo);

        //转换出参
        page.setTotalRow((int)pageInfoRes.getTotal());
        page.setTotalPage(pageInfoRes.getPages());
        page.setResultList(pageInfoRes.getList());
        return page;
    }

    @Override
    public int insertUser(User user) {
        return 0;
    }
}
