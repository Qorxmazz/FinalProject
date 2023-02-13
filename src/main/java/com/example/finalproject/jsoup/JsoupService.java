package com.example.finalproject.jsoup;

import com.example.finalproject.dto.TatoebaData;
import com.example.finalproject.dto.TelegramResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupService {

    public TelegramResponseType jsoupService(String languageCode, String word, String from, String to) throws IOException {
        try {
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

            String translation;
            if (data.getTranslations().get(0).size() > 0) {
                translation = data.getTranslations().get(0).get(0).getText();
            } else {
                translation = data.getTranslations().get(1).get(0).getText();
            }

            System.out.println(text);
            System.out.println(translation);


            return TelegramResponseType.builder()
                    .fromLanguage(text)
                    .toLanguage(translation)
                    .build();

        } catch (Exception exception) {
            String response = "";
            switch (languageCode) {
                case "az": {
                    response = "Heç bir nəticə tapılmadı";
                    break;
                }
                case "ru": {
                    response = "Не найдено";
                    break;
                }
                case "tr": {
                    response = "Hiç bir sonuç bulunmadı";
                    break;
                }
                default: {
                    response = "Could not found any result!";
                }
            }
            return TelegramResponseType.builder()
                    .fromLanguage(response)
                    .toLanguage("")
                    .build();
        }

    }
}
