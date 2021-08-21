package in.srini91.learn.kafkaclientex.config;

import in.srini91.learn.kafkaclientex.service.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Configuration
public class BeanConfig {
    private static Logger LOG = LogManager.getLogger(BeanConfig.class);


    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setRetryTemplate(retryTemplate());
        factory.setRecoveryCallback(context -> {
            if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException){
                LOG.error("=============> Recoverable data access Exception");
                //here you can do your recovery mechanism where you can put back on to the topic using a Kafka producer
            } else{
                // here you can log things and throw some custom exception that Error handler will take care of ..
                LOG.error("=============>Throw RuntimeException");
                throw new RuntimeException(context.getLastThrowable().getMessage());
            }
            return Optional.empty();
        });
        factory.setErrorHandler(((exception, data) -> {
           /* here you can do you custom handling, I am just logging it same as default Error handler does
          If you just want to log. you need not configure the error handler here. The default handler does it for you.
          Generally, you will persist the failed records to DB for tracking the failed records.  */
            LOG.error("Error in process with Exception {} and the record is {}", exception, data);
        }));
        return factory;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5*1000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        /* here retry policy is used to set the number of attempts to retry and what exceptions you wanted to try and what you don't want to retry.*/
        retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
        return retryTemplate;
    }

    private SimpleRetryPolicy getSimpleRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();

        // the boolean value in the map determines whether exception should be retried
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        return new SimpleRetryPolicy(3, exceptionMap, true);
    }
}
