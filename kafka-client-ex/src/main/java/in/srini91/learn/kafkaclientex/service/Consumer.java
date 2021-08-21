package in.srini91.learn.kafkaclientex.service;

import in.srini91.learn.kafkaclientex.dao.model.Person;
import in.srini91.learn.kafkaclientex.dao.repository.PersonRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeoutException;

@Service
public class Consumer {
    private static Logger LOG = LogManager.getLogger(Consumer.class);

    @Autowired
    private PersonRepository personRepo;

    @KafkaListener(topics = "first-topic")
    public void consume(ConsumerRecord<String, String> record) throws Exception {
        LOG.info(String.format("Consumed key and message ->%s %s",record.key(), record.value()));
//        throw new Exception("testing retry");
    }


    @KafkaListener(topics = "person-topic")
    public void consumePerson(ConsumerRecord<String, Person> record) throws Exception {
        LOG.info(String.format("Consumed key and message ->%s %s",record.key(), record.value()));
        Person sPerson = personRepo.save(record.value());
        if(ObjectUtils.isEmpty(sPerson)){
            throw new TimeoutException("Person Not Saved");
        }

    }

}
