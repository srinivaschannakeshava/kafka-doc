package srini.learn.kafka.kafkacloudstreamex.dao.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import srini.learn.kafka.kafkacloudstreamex.dao.model.Person
@Repository
interface PersonRepository:JpaRepository<Person,Long> {
}