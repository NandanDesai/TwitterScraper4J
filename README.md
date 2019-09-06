# TwitterScraper4J

This is a Java library which lets you fetch Twitter public data without the need to use any API. Just download the JAR file, add it to your project and start using!

## Code Examples

 - *Getting Profile information like name, description, location, number of followers, verified status, profile picture etc.*

    TwitterScraper scraper = TwitterScraper.getInstance();  
    Profile profile = scraper.getProfile("realDonaldTrump");

 - *Getting the user's timeline*

    TwitterScraper twitterScraper = TwitterScraper.getInstance();  
    List< Tweet > tweets=twitterScraper.getUserTimeline("realDonaldTrump");  
      
    for(Tweet tweet:tweets){  
        System.out.println(tweet);  
    }

 - *Search a user*

    TwitterScraper scraper = TwitterScraper.getInstance();  
    List< UserSearchResult > userSearchResults =scraper.searchUser("Narendra Modi");  
    for (UserSearchResult result: userSearchResults){  
        System.out.println(result);  
    }

 - *Get worldwide trends*

    TwitterScraper scraper=TwitterScraper.getInstance();  
    System.out.println(scraper.getWorldwideTrends());

Refer tests for more examples.



