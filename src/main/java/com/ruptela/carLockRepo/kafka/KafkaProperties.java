package com.ruptela.carLockRepo.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class KafkaProperties {

    @Value("${resource.kafka_topic}")
    private String topic;
    @Value("${resource.kafka_host}")
    private String host;
    @Value("${resource.kafka_group_id}")
    private String groupId;

}
