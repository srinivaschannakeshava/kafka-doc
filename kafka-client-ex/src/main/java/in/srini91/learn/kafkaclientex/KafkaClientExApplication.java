package in.srini91.learn.kafkaclientex;

import in.srini91.learn.kafkaclientex.simple.KafkaConsumerEx;
import in.srini91.learn.kafkaclientex.simple.KafkaProducerEx;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Properties;

@SpringBootApplication
public class KafkaClientExApplication implements CommandLineRunner {
    private static Logger LOG = LogManager.getLogger(KafkaClientExApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaClientExApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        KafkaProducerEx.produceData();
        Thread th=new Thread(()->{
//            KafkaConsumerEx.consumeData();
//            KafkaConsumerEx.assignAndSeekConsume();
        });
        th.run();
    }
}
