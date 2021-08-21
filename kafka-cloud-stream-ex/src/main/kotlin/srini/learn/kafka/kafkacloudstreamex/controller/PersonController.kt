package srini.learn.kafka.kafkacloudstreamex.controller

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import srini.learn.kafka.kafkacloudstreamex.dao.model.Person
import java.util.*

@RestController
@RequestMapping(path = ["person"])
class PersonController {

    private val LOG = LogManager.getLogger(PersonController::class.java)


//    @Autowired
//    private val kf: KafkaTemplate<String, Any>? = null
//
//    @PostMapping
//    fun publishPerson(@RequestBody person: Person) {
//        person?.name?.let { kf!!.send("person-topic", it, person) }
//            ?.addCallback(object : ListenableFutureCallback<SendResult<String?, Any?>?> {
//                override fun onFailure(ex: Throwable) {
//                    LOG.error(ex.message)
//                }
//
//                override fun onSuccess(result: SendResult<String?, Any?>?) {
//                    LOG.info(
//                        """
//                            Records Metatdata
//                            Topic : ${result!!.recordMetadata.topic()}
//                            Partition : ${result.recordMetadata.partition()}
//                            Offset : ${result.recordMetadata.offset()}
//                            Timestamp : ${Date(result.recordMetadata.timestamp())}
//                            """.trimIndent()
//                    )
//                }
//            })
//    }
}