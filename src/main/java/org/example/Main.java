package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;

import org.example.Model.Film;
import org.example.Service.FilmRoullete;
import org.example.Service.FilmRoulleteClass;
import org.example.Service.LetterBoxdListScrapper;
import org.example.Util.UrlListValidator;



public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        List<Film> films = new ArrayList<>();
        String url = getUrlListInput(sc);

        LetterBoxdListScrapper scrapper = new LetterBoxdListScrapper();
        try {
            films = scrapper.scrapeList(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total films in list: " + films.size());

        FilmRoullete filmRoullete = new FilmRoulleteClass();
        Film randomFilm = filmRoullete.getRandomFilm(films);
        System.out.println("Film chosen: " + randomFilm.getTitle() );

        System.out.println("Link of the Film:");
        System.out.println("https://letterboxd.com" + randomFilm.getUrl());
    }

    private static String getUrlListInput(Scanner sc) {
        System.out.println("Enter your list Link: ");
        UrlListValidator validator = new UrlListValidator();
        String link = sc.nextLine();
        while (!validator.isListLinkValid(link.trim())){
            System.out.println("That Link is Invalid....");
            System.out.println("Please Input a link with the format 'https://letterboxd.com/official/list/<name>'");
            link = sc.nextLine();
        }
        
        System.out.println("Thank You! That Link is Valid.");
        return link;
    }
}