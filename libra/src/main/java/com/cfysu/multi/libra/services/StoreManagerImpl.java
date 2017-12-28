package com.cfysu.multi.libra.services;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service("storeManagerLibra")
public class StoreManagerImpl implements StoreManager {

    public String getStoreName(String name) {
        return new Date() + "===========" + name;
    }

}
