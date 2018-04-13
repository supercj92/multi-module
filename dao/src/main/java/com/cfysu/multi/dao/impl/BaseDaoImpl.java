package com.cfysu.multi.dao.impl;


import com.cfysu.multi.dao.BaseDao;
import com.cfysu.multi.util.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaseDaoImpl<E, Q> implements BaseDao<E, Q> {

    private String nameSpace;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public int insert(E entity){
        Boolean res = new TransactionTemplate(transactionManager).execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {

                Boolean isSucess = true;

                try {

                }catch (Exception e){
                    status.setRollbackOnly();
                    isSucess = false;
                }

                return isSucess;
            }
        });
        return 0;
    }

    @Override
    public  Page<E> queryForPage(Q query, Page<E> page) {
        //page.setTotalRow(queryCount(query));
        page.repaginateByStart();//传统分页传pageSize,curPage分页有问题
        //未配置分页插件时，内存分页有性能问题，数据量大时，有可能oom
        //配置mybatis分页插件后，可以实现物理分页
        //PageRowBounds pageRowBounds = new PageRowBounds(page.getStart(),page.getPageSize());
        //List<E> resList =sqlSessionTemplate.selectList(nameSpace + ".queryForPage", query, pageRowBounds);
        //mybatis插件会自动count并设置为pageRowBounds属性
        //page.setTotalRow(pageRowBounds.getTotal().intValue());
        //page.setResultList(resList);
        return page;
    }

    @Override
    public  PageInfo<E> queryForPageByMybatisPlugin(Q query, PageInfo<E> pageInfo) {
        //offsetAsPageNum已经关闭
        List<E> resList =sqlSessionTemplate.selectList(nameSpace + ".queryForPage", query, new PageRowBounds(pageInfo.getStartRow(), pageInfo.getPageSize()));
        return new PageInfo<E>(resList);
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
