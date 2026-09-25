package org.example.Service;

import org.example.Model.Film;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LetterBoxdListScrapper {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";


    public List<Film> scrapeList(String baseurl) throws IOException {
        List<Film> filmList = new ArrayList<>();

        Document doc = Jsoup.connect(baseurl).userAgent(USER_AGENT).get();
        //Scrape First Page(the one that's guaranteed
        scrapeFilmsToAdd(doc, filmList);

        final int totalPages = extractTotalPages(doc);

        for (int i = 2; i <= totalPages; i++) {

            String urlOfPage = baseurl + "page/" + i + "/";

            doc = Jsoup.connect(urlOfPage)
                    .userAgent(USER_AGENT).get();

            scrapeFilmsToAdd(doc, filmList);
        }

        return filmList;
    }


    private void scrapeFilmsToAdd(Document doc, List<Film> filmList) {
        Elements links = doc.select("li.posteritem div.react-component");
        for (Element filme : links) {
            Film f = new Film(filme.attr("data-item-name"), filme.attr("data-item-link"));
            filmList.add(f);
        }

    }
    private int extractTotalPages(Document doc) {
        Elements pages = doc.select("li.paginate-page a");
        if (!pages.isEmpty())
            return Integer.parseInt(pages.last().text());
        else
            return 1;
    }
}
