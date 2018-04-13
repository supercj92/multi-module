package com.cfysu.multi.dao;

import com.cfysu.multi.util.Page;
import com.github.pagehelper.PageInfo;


public interface BaseDao<E, Q> {
   @Deprecated
   Page<E> queryForPage(Q query, Page<E> page);
   PageInfo<E> queryForPageByMybatisPlugin(Q query, PageInfo<E> page);
   @Deprecated
   int queryCount(Q query);
   int insert(E entity);
}
