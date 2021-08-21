package srini.learn.kafka.kafkacloudstreamex.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.RecoverableDataAccessException
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.retry.RetryContext
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import java.util.*
import java.util.concurrent.TimeoutException

@Configuration
class KafkaConfiguration {
    private val LOG: Logger = LogManager.getLogger(KafkaConfiguration::class)

    @Bean
    fun kafkaListenerContainerFactory(
        configurer: ConcurrentKafkaListenerContainerFactoryConfigurer,
        kafkaConsumerFactory: ConsumerFactory<Any?, Any?>?
    ): ConcurrentKafkaListenerContainerFactory<*, *>? {
        val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
        configurer.configure(factory, kafkaConsumerFactory)
        factory.setRetryTemplate(retryTemplate()!!)
        factory.setRecoveryCallback { context: RetryContext ->
            if (context.lastThrowable.cause is RecoverableDataAccessException) {
                LOG.error("=============> Recoverable data access Exception")
                //here you can do your recovery mechanism where you can put back on to the topic using a Kafka producer
            } else {
                // here you can log things and throw some custom exception that Error handler will take care of ..
                LOG.error("=============>Throw RuntimeException")
                throw RuntimeException(context.lastThrowable.message)
            }
            Optional.empty<Any>()
        }
        factory.setErrorHandler { exception: Exception?, data: ConsumerRecord<*, *>? ->
            /* here you can do you custom handling, I am just logging it same as default Error handler does
             If you just want to log. you need not configure the error handler here. The default handler does it for you.
             Generally, you will persist the failed records to DB for tracking the failed records.  */
            LOG.error(
                "Error in process with Exception {} and the record is {}",
                exception,
                data
            )
        }
        return factory
    }

    private fun retryTemplate(): RetryTemplate? {
        val retryTemplate = RetryTemplate()
        val fixedBackOffPolicy = FixedBackOffPolicy()
        fixedBackOffPolicy.backOffPeriod = 5 * 1000L
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy)
        /* here retry policy is used to set the number of attempts to retry and what exceptions you wanted to try and what you don't want to retry.*/retryTemplate.setRetryPolicy(
            getSimpleRetryPolicy()
        )
        return retryTemplate
    }

    private fun getSimpleRetryPolicy(): SimpleRetryPolicy? {
        val exceptionMap: MutableMap<Class<out Throwable?>, Boolean> = HashMap()

        // the boolean value in the map determines whether exception should be retried
        exceptionMap[IllegalArgumentException::class.java] = false
        exceptionMap[TimeoutException::class.java] = true
        return SimpleRetryPolicy(3, exceptionMap, true)
    }
}