package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;

public class TrendTest {

    public static void main(String[] args){
        try{

            TwitterScraper scraper=TwitterScraper.getInstance();
            System.out.println(scraper.getWorldwideTrends());

        }catch(Exception e){e.printStackTrace();}
    }
}
