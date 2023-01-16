package com.example.finalproject.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;

public class JsoupService  {


    public static void main(String[] args)throws IOException  {
        String word = "test";
        String url = "https://tatoeba.org/en/sentences/search?from=tur&query=test&to=eng" + "from=" + "eng" +
                "&query=" + word + "&to=" + "tur";

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("div");
        String attr = elements.attr("ng-init");
        String s = attr.split("vm.init\\(\\[], ")[1];
        String s1 = s.split("\t")[0];
//        System.out.println(s1.split(", \\[\\{")[0] );
        System.out.println(s1);

    }
}
