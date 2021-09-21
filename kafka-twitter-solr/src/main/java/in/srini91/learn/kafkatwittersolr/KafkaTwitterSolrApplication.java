package in.srini91.learn.kafkatwittersolr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.*;

import java.util.List;

@SpringBootApplication
public class KafkaTwitterSolrApplication implements CommandLineRunner {
    @Autowired
    private Twitter4jExample t4jEx;

    Twitter twitter = TwitterFactory.getSingleton();


    public static void main(String[] args) {
        SpringApplication.run(KafkaTwitterSolrApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//        Status status = twitter.updateStatus("What are you doing?");
//        System.out.println("Successfully updated the status to [" + status.getText() + "].");
//        List<Status> statuses = twitter.getHomeTimeline();
//        System.out.println("Showing home timeline.");
//        for (Status status : statuses) {
//            System.out.println(status.getUser().getName() + ":" +
//                    status.getText());
//        }
//        Thread.sleep(3000);
//        Query query = new Query("India");
//        QueryResult result = twitter.search(query);
//        for (Status status : result.getTweets()) {
//            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
//        }
//        t4jEx.printGeoDetails(twitter);

//        t4jEx.tweetATweet("Hi Srinivas Channakeshava!! Welcome :)");
        t4jEx.searchTweets("Srinivas Channakeshava");
    }
}
