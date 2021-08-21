package in.srini91.learn.kafkaclientex.controller;

import in.srini91.learn.kafkaclientex.dao.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "person")
public class PersonController {
    private static Logger LOG = LogManager.getLogger(PersonController.class);

    @Autowired
    private KafkaTemplate<String,Object> kf;

    @PostMapping
    public void publishPerson(@RequestBody Person person){
        ListenableFuture<SendResult<String, Object>> sendPerson = kf.send("person-topic", person.getName(), person);
        sendPerson.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.error(ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LOG.info("Records Metatdata \n" +
                        "Topic : " + result.getRecordMetadata().topic() + "\n" +
                        "Partition : " + result.getRecordMetadata().partition() + "\n" +
                        "Offset : " + result.getRecordMetadata().offset() + "\n" +
                        "Timestamp : " + new Date(result.getRecordMetadata().timestamp()));
            }
        });
    }
}
