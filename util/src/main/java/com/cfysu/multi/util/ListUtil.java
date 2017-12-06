package com.cfysu.multi.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    /**
     * @Description: 把一个list转换成二维list
     * @param  list 待转换一维list
     * @param  subLength 二维list中单个list的长度
     * @return List<List<T>>
     * @throws
     */
    public static <T>List<List<T>> convertTwoDimensionalList(List<T> list, int subLength){
        List<List<T>> lists = new ArrayList<List<T>>();
        int subMax = subLength <= 0 ? 3 : subLength;
        int size = list.size();
        if(size > subMax){
            for(int i=0; i < list.size(); i += subMax){
                lists.add(list.subList(i, (i+subMax) > size ? size : (i+subMax)));
            }
        } else {
            lists.add(list);
        }
        return lists;
    }
}
