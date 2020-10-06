package com.ruptela.carLockRepo.kafka.producer;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.kafka.KafkaProperties;
import com.ruptela.carLockRepo.kafka.serialization.KafkaJsonSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties props;

    @Bean
    public Map producerConfigs() {
        Map m = new HashMap();
        m.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getHost());
        m.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);
        m.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        m.put("key.serializer", StringSerializer.class.getName());

        return m;
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory(producerConfigs());
    }

    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    @Bean
    public KafkaProducer<String, LockCarDTO> sender() {
        Map props = producerConfigs();
        KafkaProducer<String, LockCarDTO> kafkaProducer = new KafkaProducer<>(
                props,
                new StringSerializer(),
                new KafkaJsonSerializer()
        );
        return kafkaProducer;
    }
}
