# TwitterScraper4J

This is a Java library which lets you fetch Twitter public data without the need to use any API.
## Getting Started
JAR file is available in the [release](https://github.com/NandanDesai/TwitterScraper4J/releases) section. Download the JAR file, add it to your Java project and start using it!

#### Code Examples

 - *Getting Profile information like name, description, location, number of followers, verified status, profile picture etc.*

    ```java
    TwitterScraper scraper = TwitterScraper.getInstance();  
    Profile profile = scraper.getProfile("realDonaldTrump");
   ```

 - *Getting the user's timeline*

    ```java
    TwitterScraper twitterScraper = TwitterScraper.getInstance();  
    List< Tweet > tweets=twitterScraper.getUserTimeline("realDonaldTrump");  
      
    for(Tweet tweet:tweets){  
        System.out.println(tweet);  
    }
    ```
    

 - *Search a user*

    ```java
    TwitterScraper scraper = TwitterScraper.getInstance();  
    List< UserSearchResult > userSearchResults =scraper.searchUser("Narendra Modi");  
    for (UserSearchResult result: userSearchResults){  
        System.out.println(result);  
    }
   ```

 - *Get worldwide trends*

    ```java
    TwitterScraper scraper=TwitterScraper.getInstance();  
    System.out.println(scraper.getWorldwideTrends());
    ```
    
Refer [tests](https://github.com/NandanDesai/TwitterScraper4J/tree/master/src/test/java/io/github/nandandesai/tests) for more examples.
## To-do List
- Getting media links
- Timestamps
- Getting a stream of tweets containing a given keyword (EXPERIMENTAL)

## License
[MIT](https://github.com/NandanDesai/TwitterScraper4J/blob/master/LICENSE)
