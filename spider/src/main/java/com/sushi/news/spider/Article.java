package com.sushi.news.spider;

/**
 * Created by Administrator on 2016/3/24.
 */
public class Article {
    private String imageUrl;
    private String title;
    private String summary;
    private String url;
    private String content;

    public Article() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article(String imageUrl, String title, String summary) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.summary = summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
