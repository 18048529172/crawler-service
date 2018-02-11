package com.liw.crawler.service.pron.service.helper;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 *  概览
 *
 *  @author 李伟
 */
public class PronOverview {

    /**
     * 标题
     */
    private String title;

    /**
     *  作者
     */
    private String author;

    /**
     *  视频唯一标识
     */
    private String viewkey;
    /**
     *  封面图片
     */
    private String coverImage;
    /**
     * 封面图片
     */
    private Date uploadDate;
    /**
     * 视频时长
     */
    private String videoTimeSize;
    /**
     *  回调id
     */
    private String callId;



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

    public String getViewkey() {
        return viewkey;
    }

    public void setViewkey(String viewkey) {
        this.viewkey = viewkey;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
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

    public String getVideoTimeSize() {
        return videoTimeSize;
    }

    public void setVideoTimeSize(String videoTimeSize) {
        this.videoTimeSize = videoTimeSize;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
