package com.cfysu.multi.dao.impl;


import com.cfysu.multi.dao.BaseDao;
import com.cfysu.multi.util.Page;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaseDaoImpl<E, Q> implements BaseDao<E, Q> {

    private String nameSpace;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public  Page<E> queryForPage(Q query, Page<E> page) {
        page.setTotalRow(queryCount(query));
        page.repaginate();
        List<E> resList =sqlSessionTemplate.selectList(nameSpace + ".queryForPage", query, new RowBounds(page.getStart(),page.getPageSize()));
        page.setResultList(resList);
        return page;
    }

    @Override
    public int queryCount(Q query){
        Integer count = sqlSessionTemplate.selectOne(nameSpace + ".queryCount", query);
        return count == null ? 0 : count;
    }

    public void setNameSpace(String nameSpace){
        Assert.notNull(nameSpace);
        this.nameSpace = nameSpace;
    }
}
