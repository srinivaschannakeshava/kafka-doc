# Kafka cli commands

## Kafka Topic cmds
- `kafka-topics --list --zookeeper {zookeeperIP:Port}`  - lists the kafka topics
- `kafka-topics --zookeeper zookeeper:2181 --topic topicName --create --partition 3 --replication-factor 1` - create a topic with topicName, partition 3 and replication 1
- `kafka-topics --zookeeper zookeeper:2181 --topic topicName --describe` - fetch info about topic
- note how to get brooker id info cli into zookeeper- `zookeeper-shell zookeeper:2181`  and then `ls /brokers/ids` this list broker ids of kafka brokers
- `kafka-topics --zookeeper zookeeper:2181 --topic second-topic --delete` delete topic .. here the topic will not be deleted if  delete.topic.enable is not set to true

## Kafka Producer cmds

- ` kafka-console-producer --broker-list localhost:9092 --topic topicName `
- ` kafka-console-producer --bootstrap-server localhost:9092 --topic first-topic --request-required-acks 1 ` - here the acks can take -1, 0 , 1

## Kafka Consumer

- ` kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic `
- ` kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning `
-` kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group groupName`

## kafka consumer groups

- ` kafka-consumer-groups --bootstrap-server localhost:9092 --list `
- ` kafka-consumer-groups --bootstrap-server localhost:9092 --group grp1 --describe ` - LAG is the no of messages pending to be read

- `kafka-consumer-groups --bootstrap-server localhost:9092 --group grp1  --reset-offsets --shift-by -2 --execute --topic first-topic` - offset consumer grp by a specific no
- `kafka-consumer-groups --bootstrap-server localhost:9092 --group grp1  --reset-offsets --to-earliest --execute --topic first-topic` - offset to starting 

> Note:
> Producer with keys
> `kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic --property parse.key=true --property key.separator=,`
> Consumer with keys
> ` kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,`