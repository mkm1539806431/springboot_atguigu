package com.xjtu.utils;

import com.xjtu.bean.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlParse {


    public static void main(String[] args) throws IOException {
        List<Content> java = new HtmlParse().parseHtmlJd("java");
        System.out.println(java);
        System.out.println("============");
        System.out.println(java.size());
    }

public List<Content> parseHtmlJd(String key) throws IOException {
    String url="https://search.jd.com/Search?keyword="+key;
    Document document=  Jsoup.parse(new URL(url),30000);


    Element element=document.getElementById("J_goodsList");

//        System.out.println(element.text());

    Elements elements = element.getElementsByTag("li");

    List<Content> list=new ArrayList<>();

    for (Element e1 : elements) {
        String img = e1.getElementsByTag("img").eq(0).attr("data-lazy-img");
        String price = e1.getElementsByClass("p-price").eq(0).text();
        String title = e1.getElementsByClass("p-name").eq(0).text();
        Content content = new Content();
        content.setImg(img);
        content.setPrice(price);
        content.setTitle(title);
        list.add(content);
    }
    return list;
}


}
