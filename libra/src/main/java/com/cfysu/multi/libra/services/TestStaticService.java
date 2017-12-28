package com.cfysu.multi.libra.services;

import java.util.Arrays;
import java.util.List;

public class TestStaticService {

    public static String getListContent(List<Long> idList) {
        return Arrays.toString(idList.toArray());
    }
}
