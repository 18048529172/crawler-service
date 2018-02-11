package com.liw.crawler.service.pron.service.helper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PronDocUtils {

    private PronDocUtils(){}

    public static List<PronOverview> parser(Document doc){
        List<PronOverview> overviews = new ArrayList<>();
        Elements listchannels = doc.getElementsByClass("listchannel");
        for(Iterator<Element> iterator = listchannels.iterator(); iterator.hasNext();){
            Element element = iterator.next();
            PronOverview pronOverview = new PronOverview();
            pronOverview.setCoverImage(getCoverImage(element));
            setTitleAndViewKeyAndAuthorAndTimeSize(element,pronOverview);
            overviews.add(pronOverview);
        }
        return overviews;
    }

    private static void setTitleAndViewKeyAndAuthorAndTimeSize(Element listchannel, PronOverview pronOverview) {
        //单独的一个（listchannel）
        //标题和作者都在A标签里
        Elements linkElements = listchannel.getElementsByTag("a");
        for(Iterator<Element> iterator = linkElements.iterator();iterator.hasNext();) {
            Element linkElenent = iterator.next();
            String attrHref = linkElenent.attr("href");
            if(StringUtils.contains(attrHref,"viewkey")){
                String attrTitle = linkElenent.attr("title");
                String viewkey = getParamByName(attrHref,"viewkey");
                pronOverview.setTitle(attrTitle);
                pronOverview.setViewkey(viewkey);
            }
            //包含UID说明是作者
            if(StringUtils.contains(attrHref,"UID")){
                String author = linkElenent.text();
                pronOverview.setAuthor(author);
            }
        }
        //时长在<span class="info">时长:</span>10:00
        //这里获取所有的span标签
        Elements children = listchannel.children();
        for(Iterator<Element> iterator = children.iterator();iterator.hasNext();){
            Element element = iterator.next();
            String attrText = element.text();
            if(StringUtils.contains(attrText,"时长:")){
                Node timeNode = element.nextSibling();
                if(timeNode instanceof TextNode){
                    TextNode runTimeSize = (TextNode) timeNode;
                    String videoTimeSize = StringUtils.trim(runTimeSize.text());
                    pronOverview.setVideoTimeSize(videoTimeSize);
                    break;
                }
            }
        }

    }

    private static String getCoverImage(Element element) {
        String coverImage = null;
        Elements imgs = element.getElementsByTag("img");
        for(int i=0;i<imgs.size();i++){
            Element imgElement = imgs.get(i);
            String src = imgElement.attr("src");
            if(StringUtils.isNotBlank(src) && StringUtils.contains(src,"img.t6k.co")){
                coverImage = src;
                break;
            }
        }
        return coverImage;
    }

    private static String getParamByName(String href, String name) {
        if(!StringUtils.contains(href,"?")){
            return null;
        }
        String paramString = StringUtils.split(href,"?")[1];
        if(StringUtils.contains(paramString,"&")){
            String [] params = StringUtils.split(paramString,"&");
            for(String p : params){
                String [] paramEl = StringUtils.split(p,"=");
                if(name.equals(paramEl[0])){
                    return  paramEl[1];
                }
            }
        }else{
            String [] paramEl = StringUtils.split(paramString,"=");
            if(name.equals(paramEl[0])){
                return  paramEl[1];
            }
        }
        return null;
    }





}
