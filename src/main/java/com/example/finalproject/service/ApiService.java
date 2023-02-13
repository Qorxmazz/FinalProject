package com.example.finalproject.service;

import com.example.finalproject.dto.TatoebaData;
import com.example.finalproject.dto.TelegramResponseType;
import com.example.finalproject.repository.TelegramRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ApiService {
    @Autowired
    TelegramRepository repository;

    public String example(String word, String from, String to) throws IOException {
        String url = "https://tatoeba.org/en/sentences/search?" + "from=" + from +
                "&query=" + word + "&to=" + to;

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("div");
        String attr = elements.attr("ng-init");
        String s = attr.split("vm.init\\(\\[], ")[1];
        String s1 = s.split("\t")[0];
        s1 = s1.split(", \\[], \\[\\{")[0];

        TatoebaData data = new ObjectMapper().readValue(s1, TatoebaData.class);
        String text = data.getText();

        return data.getText();

    }
}
