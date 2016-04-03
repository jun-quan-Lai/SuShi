package com.sushi.news.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class NewsSpider {

    public NewsSpider(){

    }
    public ArrayList<News>getNewList(String url){
        ArrayList<News> articleList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
            Element singerListDiv = doc.select("div.newli").first();
            Elements alist = singerListDiv.select(" ul.gd_ul a");
            for(Element link:alist){
                News article = new News();
                String linkHref = "http://ss.zgfj.cn" +link.attr("href");
                String linkTitle = link.ownText().trim();
                article.setUrl(linkHref);
                article.setTitle(linkTitle);
                articleList.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleList;
    }
}
