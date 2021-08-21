package srini.learn.kafka.kafkacloudstreamex.kafka

import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.FailedDeserializationInfo
import srini.learn.kafka.kafkacloudstreamex.dao.model.Person
import java.util.function.Function

open class PoisonPillProvider():Function<FailedDeserializationInfo,Person> {

    override fun apply(t: FailedDeserializationInfo): Person {

        return PoisonPill(t)
    }
}

class PoisonPill(var failedDeserialization:FailedDeserializationInfo?=null) :Person()