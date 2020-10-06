package com.ruptela.carLockRepo.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaJsonSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(KafkaJsonSerializer.class);

    public KafkaJsonSerializer() {
    }

    @Override
    public void configure(Map map, boolean b) {
    }

    @Override
    public byte[] serialize(String s, Object o) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return retVal;
    }

    @Override
    public void close() {
    }
}
