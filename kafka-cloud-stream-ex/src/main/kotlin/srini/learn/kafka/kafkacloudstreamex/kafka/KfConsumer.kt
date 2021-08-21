package srini.learn.kafka.kafkacloudstreamex.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.DataAccessException
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.messaging.Message
import srini.learn.kafka.kafkacloudstreamex.dao.model.Person
import srini.learn.kafka.kafkacloudstreamex.dao.repository.PersonRepository
import java.util.concurrent.TimeoutException
import java.util.function.Consumer


@Configuration
class KfConsumer(
    val personRepo:PersonRepository
) {
    private val LOG = LogManager.getLogger(KfConsumer::class)

    @Bean
    open fun kfConsumerOne(): Consumer<Message<Person>> {
        return Consumer { person: Message<Person> ->
            run {
               LOG.info("${person.headers} ${person.payload}")
//                throw TimeoutException()
//                personRepo.save(ObjectMapper().readValue(person.payload,Person::class.java))
            }
        }
    }


//    @Bean
//    open fun kfConsumerOne(): (Message<String>) -> Unit{
//
//        return { LOG.info("${it.headers} : : ${it.payload}")
//            personRepo.save(ObjectMapper().readValue(it.payload,Person::class.java))
//        }
//
//    }
}