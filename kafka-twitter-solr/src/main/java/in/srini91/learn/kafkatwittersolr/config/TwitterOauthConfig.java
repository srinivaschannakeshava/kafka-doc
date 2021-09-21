package in.srini91.learn.kafkatwittersolr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@ConfigurationProperties(prefix = "twitter.oauth")
@Data
public class TwitterOauthConfig {

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;


}
