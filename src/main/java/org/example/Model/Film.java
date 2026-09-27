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

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        Film toCompare = (Film) other;

        return toCompare.getUrl().equals(this.getUrl());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(url);
    }
}
