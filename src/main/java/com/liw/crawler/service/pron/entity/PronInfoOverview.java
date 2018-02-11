package com.liw.crawler.service.pron.entity;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liwei
 */
@Entity
@Table(name = "pronInfo")
public class PronInfoOverview implements Serializable {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    private String title;

    private String author;

    private String viewKey;

    @Column(length = 8000)
    private String content;

    private String coverImage;

    private Date uploadDate;

    private String videoTimeSize;

    private String callId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getVideoTimeSize() {
        return videoTimeSize;
    }

    public void setVideoTimeSize(String videoTimeSize) {
        this.videoTimeSize = videoTimeSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
