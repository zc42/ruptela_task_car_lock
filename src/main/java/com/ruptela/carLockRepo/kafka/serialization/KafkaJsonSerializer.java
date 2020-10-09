package com.ruptela.carLockRepo.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serializer;

@Log4j2
public class KafkaJsonSerializer implements Serializer {

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
