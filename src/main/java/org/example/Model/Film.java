package org.example.Model;

public class Film {
    private final String title;
    private final String url;

    public Film(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
