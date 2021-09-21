package in.srini91.learn.kafkatwittersolr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.List;

@Component
public class Twitter4jExample {

    private static Logger LOG=LogManager.getLogger(Twitter4jExample.class);
    @Autowired
    private Twitter twitter ;

    public void printGeoDetails() throws Exception {
        Place place = twitter.getGeoDetails("BLR");
        System.out.println("name: " + place.getName());
        System.out.println("country: " + place.getCountry());
        System.out.println("country code: " + place.getCountryCode());
        System.out.println("full name: " + place.getFullName());
        System.out.println("id: " + place.getId());
        System.out.println("place type: " + place.getPlaceType());
        System.out.println("street address: " + place.getStreetAddress());
        Place[] containedWithinArray = place.getContainedWithIn();
        if (containedWithinArray != null && containedWithinArray.length != 0) {
            System.out.println("  contained within:");
            for (Place containedWithinPlace : containedWithinArray) {
                System.out.println("  id: " + containedWithinPlace.getId() + " name: " + containedWithinPlace.getFullName());
            }
        }
    }

    public void searchTweets(String queryText) throws Exception {
        LOG.info("Searching Tweet : "+queryText);
//        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query(queryText);
        QueryResult result;
        do {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                ObjectMapper om=new ObjectMapper();
                System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(tweet));
            }
        } while ((query = result.nextQuery()) != null);
        LOG.info("End Searching");
    }

    public void tweetATweet(String tweet) throws TwitterException {
        LOG.info("Tweeting Tweet : "+tweet);
//        Twitter twitter = new TwitterFactory().getInstance();

        Status status = twitter.updateStatus(tweet);
        LOG.info("Tweeting Tweet Complete");
//        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }
}