# TwitterScraper4J

This is a Java library which lets you fetch Twitter public data without the need to use any API.

#### Pros
- The JAR file for this entire library is just 430 KB.
- Unlike the official Twitter API, TwitterScraper4J doesn't have any rate limits.
- Unlike the official Twitter API, TwitterScraper4J doesn't require any generation of Tokens, Keys etc. It's just a plug and play library.
- Can fetch around 3200 (or sometimes more) tweets for any public account.
- Get basic profile information.
- Search for users.
- Search for tweets (with keywords, hashtags etc.).
- Get all followers list
- Get all following list
- Streaming tweets (this is not as good as the official Twitter API and is still experimental)

#### Cons
- Cannot get the number of likes and retweets.
- Cannot get all the replies for a tweet.
- Cannot download videos attached to tweets.
- If multiple images are attached to a tweet, then this library can fetch only the first image.

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
    List<Tweet> tweets=twitterScraper.getUserTimeline("realDonaldTrump");  
      
    for(Tweet tweet:tweets){  
        System.out.println(tweet);  
    }
    ```
    

 - *Search a user*

    ```java
    TwitterScraper scraper = TwitterScraper.getInstance();  
    List<User> users =scraper.searchUser("Narendra Modi");  
    for (User result: users){  
        System.out.println(result);  
    }
   ```

 - *Get worldwide trends*

    ```java
    TwitterScraper scraper=TwitterScraper.getInstance();  
    System.out.println(scraper.getWorldwideTrends());
    ```
 
 - *Get all followers list*
 
     ```java
     TwitterScraper twitterScraper = TwitterScraper.getInstance();
     Iterator<List<User>> it=twitterScraper.getAllFollowers("realDonaldTrump");
     while(it.hasNext()){
         List<User> users=it.next();
         for(User user:users){
             System.out.println(user);
         }
         Thread.sleep(1000);
     }
     ```
 - *Fetch around 3200 tweets for a given profile*
  
      ```java
      TwitterScraper twitterScraper = TwitterScraper.getInstance();
      Iterator<List<Tweet>> it=twitterScraper.getAllTweets("realDonaldTrump");
      while(it.hasNext()){
          List<Tweet> tweets=it.next();
          for(Tweet tweet:tweets){
              System.out.println(tweet);
          }
          Thread.sleep(1000);
      }
      ```
 - *Get a stream of tweets for a particular keyword or hashtag (EXPERIMENTAL feature)*
  
      ```java
       TwitterScraper twitterScraper = TwitterScraper.getInstance();
       TweetStream stream=twitterScraper.getTweetStream("Kashmir");
       stream.setStreamListener(new TweetStreamListener() {
           @Override
           public void onPageRefresh(List<Tweet> tweets) {
               for(Tweet tweet:tweets){
                   System.out.println(tweet);
               }
           }
       });
       stream.start();
      ```

**There is a lot more this library can offer. Refer [tests](https://github.com/NandanDesai/TwitterScraper4J/tree/master/src/test/java/io/github/nandandesai/tests) for other examples.**
## To-do List
 - [x] Getting media links
 - [ ] Complete 'retweet with comment'
 - [ ] Timestamps
 - [x] Getting a stream of tweets containing a given keyword (EXPERIMENTAL)
 - [x] Followers of a given user
 - [x] Friends of a given user
 
## License
[MIT](https://github.com/NandanDesai/TwitterScraper4J/blob/master/LICENSE)
