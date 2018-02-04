package com.liw.crawler.service.pron.service.helper;

import com.liw.crawler.service.pron.entity.PronInfoOverview;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            Elements imgs = element.getElementsByTag("img");
            for(int i=0;i<imgs.size();i++){
                Element imgElement = imgs.get(i);
                String src = imgElement.attr("src");
                if(StringUtils.isNotBlank(src) && StringUtils.contains(src,"img.t6k.co")){
                    pronOverview.setCoverImage(src);
                    break;
                }
            }
            Elements aEles = element.getElementsByTag("a");
            for(Iterator<Element> iterator1 = aEles.iterator();iterator1.hasNext();) {
                Element aelement = iterator1.next();
                String tagName = aelement.tagName().toLowerCase();
                if(!"a".equals(tagName)){
                    continue;
                }
                String href = aelement.attr("href");
                if(StringUtils.contains(href,"viewkey")){
                    String title = aelement.attr("title");
                    String viewkey = getParamByName(href,"viewkey");
                    pronOverview.setTitle(title);
                    pronOverview.setViewkey(viewkey);
                }
                if(StringUtils.contains(href,"UID")){
                    String author = aelement.text();
                    pronOverview.setAuthor(author);
                }
            }
            overviews.add(pronOverview);
        }
        return overviews;
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
