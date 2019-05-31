package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;

import java.io.IOException;

public class test {
    public static void main(String[] args){
        try {
            TwitterScraper scraper=TwitterScraper.getInstance();
            scraper.getProfile("fs0c131y");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            scraper.getProfile("realDonaldTrump");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
