package com.ruptela.carLockRepo.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Deserializer;

@Log4j2
public class KafkaJsonDeserializer<T> implements Deserializer {

    private Class<T> type;

    public KafkaJsonDeserializer(Class type) {
        this.type = type;
    }

    @Override
    public void configure(Map map, boolean b) {
    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(bytes, type);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {
    }
}
