package org.ecos.logic;

import java.io.Serializable;

public class Book implements Serializable {

    int year;
    float score;
    String title;
    String author;


    public Book(int year, float score, String title, String author) {
        this.year = year;
        this.score = score;
        this.title = title;
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Book{" +
                "year=" + year +
                ", score=" + score +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
