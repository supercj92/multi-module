package com.cfysu.multi.libra.models;

import java.util.Date;

/**
 * 文章
 * @author weichao
 *
 */
public class Article {

    private String title;
    private String content;
    private String author;
    private Date publishDate;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    public Article(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = new Date();
    }
    
    public Article() {
        
    }
    
    
}
