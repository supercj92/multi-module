package com.cfysu.multi.libra.services;

import com.alibaba.fastjson.JSON;
import com.cfysu.multi.libra.models.Article;
import com.cfysu.multi.libra.models.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("testServiceLibra")
public class TestService {
    public static enum EnumStudent{
        Junior, Senior;
    }
    private String testPrivateMethod() {
        return "private";
    }
    protected String testProtectedMethod(String name) {
        return name + " welcome you!";
    }
    public static String testStaticMethod(String name) {
        return "static " + name;
    }
    
    public static String testStaticDate(Date date) {
        return "static: " + JSON.toJSONString(date);
    }

    public List<Article> getArticleList(String studentName) {
            List<Article> articles = new ArrayList<Article>();
            articles.add(new Article("title1", "content1", studentName));
            return articles;
    }
    
    public List<Article> getArticleByNameBatch(List<String> nameList) {
        List<Article> articles = new ArrayList<Article>();
        int i = 0;
        for(String name : nameList) {
            articles.add(new Article("title" + i, "content" + i, name));
            i++;
        }
        return articles;
    }
    
    public Map<String, Object> getGrouponProducts(Map<String, Object> searchParams) {
        
        return searchParams;
    }
    
    public String getArticleByStudent(Student student) {
        return "success";
    }
    
    public List getArticleByBoolean(boolean flag) {
        List list = new ArrayList();
        list.add("weichao");
        list.add(flag);
        return list;
    }
    public Date getArticleByBoolean(Date date) {
        return date;
    }
    
    public String getStudent(EnumStudent enumStudent) {
        int ordinal = enumStudent.ordinal();
        return "success:" + ordinal;
    }
}
