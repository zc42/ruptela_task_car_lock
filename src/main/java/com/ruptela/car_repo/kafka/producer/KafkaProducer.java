package com.ruptela.car_repo.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.ruptela.car_repo.kafka.consumer.KafkaConsumer.msgSplit;

@Component
public class KafkaProducer {

    @Value("${resource.kafka_topic}")
    private String topic;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessage(String vin, boolean lock) {
        final String msg = vin + msgSplit + lock;
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, msg);
        future.addCallback(listenableFutureCallback(msg));
    }

    private ListenableFutureCallback<SendResult<Integer, String>> listenableFutureCallback(String msg) {
        return new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("sent message='{}'", msg);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", msg, ex);
            }
        };
    }

}
