package in.srini91.learn.kafkatwittersolr.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class BeanConfig {
    @Autowired
    private TwitterOauthConfig toConf;

    @Bean
    public Twitter getTwitterFactory(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(toConf.getConsumerKey())
                .setOAuthConsumerSecret(toConf.getConsumerSecret())
                .setOAuthAccessToken(toConf.getAccessToken())
                .setOAuthAccessTokenSecret(toConf.getAccessTokenSecret());
        return new TwitterFactory(cb.build()).getInstance();
    }
}
