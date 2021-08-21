package srini.learn.kafka.kafkacloudstreamex.dao.model

import lombok.Data
import javax.persistence.*

@Entity
@Table(name = "person")
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     var id: Long? = null,

     var name: String?=null,

     var age: Int? = null,

     var address: String? = null


)