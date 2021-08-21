package in.srini91.learn.kafkaclientex.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Properties;

public class KafkaConsumerEx {
    private static Logger LOG = LogManager.getLogger(KafkaConsumerEx.class);

    public static void main(String[] args) {


        consumeData();

    }

    public static void consumeData() {
        Properties prop = new Properties();
        String bootstrapServer = "localhost:29092";
        String groupId="consumer-client-ex".toUpperCase(Locale.ROOT);

        String topic = "first-topic";


        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        // earliest -> from begining
        //latest-> read lastest messages
        //none -> no offset being saved
        prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //Create consumer
        KafkaConsumer<String,String> kc=new KafkaConsumer<String, String>(prop);
        // subscribe to topic
        kc.subscribe(Collections.singleton(topic));
        // poll for new data
        while(true){
            ConsumerRecords<String, String> records = kc.poll(Duration.ofMillis(100));
            for(ConsumerRecord rec:records){
                LOG.info("Key : "+rec.key()+" Value : "+ rec.value());
                LOG.info("Partition : "+rec.partition()+" Offset : "+ rec.offset());
            }

        }
    }

    public static void assignAndSeekConsume(){
        Properties prop = new Properties();
        String bootstrapServer = "localhost:29092";
        String topic = "first-topic";

        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String,String> kc=new KafkaConsumer<>(prop);
        // assign and seek are mostly used for replay data and fetch a specific message
        
        //assign
        TopicPartition partitionToReadFrom=new TopicPartition(topic,0);
        long offsetToReadFrom=15L;
        kc.assign(Collections.singleton(partitionToReadFrom));
        
        //seek
        kc.seek(partitionToReadFrom,offsetToReadFrom);

        int noOfMessageToRead=5;
        int noOfMessRead=0;
        boolean keepReading=true;
        while(keepReading){
            ConsumerRecords<String, String> records = kc.poll(Duration.ofMillis(100));
            for(ConsumerRecord rec:records){
                LOG.info("Key : "+rec.key()+" Value : "+ rec.value());
                LOG.info("Partition : "+rec.partition()+" Offset : "+ rec.offset());
                noOfMessRead=noOfMessRead+1;
                if (noOfMessRead>=noOfMessageToRead) {
                    keepReading = false;
                break;
                }
            }
        }

        LOG.info("Complete seeking assigned no of message");
    }
}
