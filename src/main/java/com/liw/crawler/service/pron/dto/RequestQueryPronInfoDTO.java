package com.liw.crawler.service.pron.dto;

import java.io.Serializable;

public class RequestQueryPronInfoDTO implements Serializable {

    private String title;

    private String content;

    private String author;

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
}
