package in.srini91.learn.kafkaclientex.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class KafkaProducerEx {

    private static Logger LOG = LogManager.getLogger(KafkaProducerEx.class);

    public static void main(String[] args) {

        produceData();
    }

    public static void produceData() {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        prop.setProperty(ProducerConfig.ACKS_CONFIG, "0");

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);

        Random rand = new Random();
        // create a producer record to be sent
        for (int i = 0; i < 100; i++) {
            int key_append=rand.nextInt(3) + 1;
            String key="KEY_"+key_append;
            String topic = "first-topic";
            String message = "Hello From Java CLient!! : Count_" + i;

            ProducerRecord<String, String> record =
                    new ProducerRecord<>(topic,key, message);
            //send data
            producer.send(record, (RecordMetadata metadata, Exception exception) -> {
                //executes everytime a record is successfully sent or an exception is thrown
                LOG.info("Recieved new Metatdata \n" +
                        "Topic : " + metadata.topic() + "\n" +
                        "Partition : " + metadata.partition() + "\n" +
                        "Offset : " + metadata.offset() + "\n" +
                        "Timestamp : " + new Date(metadata.timestamp()) + "\n"
                );
            });
            producer.flush();
        }
        producer.close();
    }

}
