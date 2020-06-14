package com.github.kevinfan.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);

        String bootstrapServers = "192.168.1.203:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // create the producer

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // create a producer record
        final ProducerRecord<String, String> record = new ProducerRecord("first_topic", "hello world");

        // send data
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    // the record was successfully sent
                    logger.info("Received new matadata. \n" + "Topic: " + recordMetadata.topic() + "\n" + "Partition: " + recordMetadata.partition() + "\n" + "Offset: " + recordMetadata.offset() + "\n" + "Timestamp: " + recordMetadata.timestamp());
                } else {
                    logger.error("Error while producing", e);
                }
            }
        });

        // flush data
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
