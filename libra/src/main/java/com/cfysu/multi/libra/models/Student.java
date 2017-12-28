package com.cfysu.multi.libra.models;

import java.util.List;
import java.util.Map;

public class Student {

    private String name;
    private Integer age;
    private List<Article> articleList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList, int a) {
        this.articleList = articleList;
    }
    
    public void getMap(Map<String, Article> map, String name) {
        
    }

}
