package org.example.Service;

import org.example.Model.Film;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class LetterBoxdUserListWatched {

    private  final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";


    public  Set<Film> scrapeUserFilmsWatched(String username) throws IOException {
        Set<Film> userFilms = new HashSet<>();
        String linkUserProfile = "https://letterboxd.com/" + username + "/films/";


        Document doc = Jsoup.connect(linkUserProfile)
                .userAgent(USER_AGENT)
                .get();

        scrapeFilmsToAdd(doc, userFilms);
        ChromeOptions options = new ChromeOptions();
// Remove a barra amarela de "Chrome is being controlled by automated test software"
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
// Desativa a flag principal que a Cloudflare procura
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        int totalPages = extractTotalPages(doc);
        System.out.println(totalPages);

        for(int i = 2; i <= totalPages; i++) {

            String urlOfPage = linkUserProfile + "page/" + i + "/";

            driver.get(urlOfPage);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String htmlPage = driver.getPageSource();

            doc = Jsoup.parse(htmlPage);

            scrapeFilmsToAdd(doc, userFilms);
        }
        driver.quit();

        return userFilms;

    }

    private void scrapeFilmsToAdd(Document doc, Set<Film> userFilmsList) {
        Elements links = doc.select("li.griditem div.react-component");
        for (Element filme : links) {
            Film f = new Film(filme.attr("data-item-name"), filme.attr("data-item-link"));
            userFilmsList.add(f);
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
