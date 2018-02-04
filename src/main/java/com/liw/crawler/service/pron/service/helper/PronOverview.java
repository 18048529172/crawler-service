package com.liw.crawler.service.pron.service.helper;

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

}
