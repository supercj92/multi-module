package com.cfysu.multi.dao;

import com.cfysu.multi.util.Page;


public interface BaseDao<E, Q> {
   Page<E> queryForPage(Q query, Page<E> page);
   int queryCount(Q query);
   int insert(E entity);
}
