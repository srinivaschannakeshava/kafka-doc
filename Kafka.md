# Apache Kafka

## Topics
 - Topics - a particular stream of data
    - similar to  tables without any constraints
    - you can have as many topics you want
    - a topic is identified by a name
 - Topics are split in partitions
    - each partition is ordered
    - each message in the partitions get an incremental id called offset
    - offset only have a meaning for a specific partition
    - order is guaranteed only within a partition
    - data is kept only for a limited time
    - once data is written to a partition can't be changed
    - Data is assigned randomly to a partition unless a key is provided

## Broker
 - A Kafka cluster is composed of multiple brokers(servers)
 - Each broker is identified with its ID
 - Each broker contains certian topic partitions
 - After connecting to any broker (called a bootstrap broker), you will be connected to entire cluster
 - a good number to get started is 3 brokers

## Topic replication
 - At any time only one broker can be a leader for a given partition 
 - Only that leader can receive and serve data for a partition
 - the other brokers will synchronize the data
 - There for each partition has one leader and multiple ISR(InSync Replica)

## Producers
 - Producers write data to topics
 - Producer automatically know to which broker and partition to write to
 - In case of Broker failures, Producer will automatically recover
 - Producers can choose to receive ack of data writes
    - acks=0 - producer wont wait for ack - (possible data loss)
    - acks=1 - producer wait for leader ack(limited data loss)
    - ack=all - producer wait for leader+repicas ack (no data loss)

 - Producer can choose to send a key with messages
 - if key==null data is send round robin across brockers/partitions
 - if a key is sent, then all the messages for that key will always go to same partition
 - a key is basically sent if you need message ordering for a specific field

## Consumers
 - Consumer read data from topic
 - Knows which broker to read from
 - In case of broker failures, consumers know how to recover
 - Data is read in order within each partitions
 - Consumer group - Consumers read data in consumer groups
    - Each consumer within group reads from exclusive partitions
    - if you have more consumers than partitions, some consumer will be inactive
 - Consumer Offsets
    - Kafka stores the offsets at which a consumer group has been reading
    - the offsets committed live in a kafka topic nammed __consumer_offsets
    - When a consumer in a group has processed data received from kafka it should be committing the offsets
    - If a consumer dies, It will be able to read back from where it left off 
 - Delivery semantics
    - Consumers choose when to commit offsets
    - there are 3 delivery semantics
        - Atmost once - offsets are commited as soon as message is received
        - At least once - offsets are committed after the message is processed - if any error in processing message is re read
        - Exactly once   - can be achieved for kafka => kafka workflows using kafka streams api or idempotent consumer

## Kafka Broker
 - Every Kafka broker is also called a bootstrap server
 - That means that you only need to connect to one broker, and you will be connected to the entire cluster
 - Each broker knows about all brokers, topics and partitions

## Kafka Gaurantees
 - Messages Are appended to a topic-partition in the order they are sent
 - Consumers read messages in the order stored in a topic partion
 - with a replication factor of N, Producer and consumer can tolerate up to N-1 brokers being down
 - As long as the number of partitions remains constant for a topics the same key will always go to the same partition

 ## Kafka Configurations
 - https://kafka.apache.org/documentation/#configuration
 - configure producer: https://kafka.apache.org/documentation/#producerconfigs
 - configure consumers:  https://kafka.apache.org/documentation/#consumerconfigs


 > Note: - You cant create a kafka topic with replication factor greater than no of brokers
 > Some good to know plugins 
 > - Kafka conduktor - https://www.conduktor.io/  - desktop kafka client
 > KafkaCat as a replacement for Kafka CLI - https://github.com/edenhill/kafkacat
 
