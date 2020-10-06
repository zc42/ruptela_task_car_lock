package com.ruptela.carLockRepo.kafka.consumer;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.kafka.KafkaProperties;
import com.ruptela.carLockRepo.kafka.serialization.KafkaJsonDeserializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties props;

    @Bean
    public Map consumerConfigs() {
        Map m = new HashMap();
        m.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getHost());
        m.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        m.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        m.put(ConsumerConfig.GROUP_ID_CONFIG, props.getGroupId());
        return m;
    }

    @Bean
    public ConsumerFactory consumerFactory() {
        Map m = consumerConfigs();
        StringDeserializer dsrlzr = new StringDeserializer();
        KafkaJsonDeserializer dsrlzr1 = new KafkaJsonDeserializer(LockCarDTO.class);
        DefaultKafkaConsumerFactory fctry = new DefaultKafkaConsumerFactory(m, dsrlzr, dsrlzr1);
        return fctry;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafKaConsumer receiver() {
        return new KafKaConsumer();
    }
}
