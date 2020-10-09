package com.ruptela.carLockRepo.kafka.producer;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.kafka.KafkaProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Service
public class KafkaProducer {

    @Autowired
    private KafkaProperties props;
    @Autowired
    private KafkaTemplate<String, LockCarDTO> kafkaTemplate;

    public void send(LockCarDTO v) {
        ListenableFuture<SendResult<String, LockCarDTO>> future = kafkaTemplate.send(props.getTopic(), v);
        future.addCallback(callback(v));
    }

    private ListenableFutureCallback<SendResult<String, LockCarDTO>> callback(LockCarDTO v) {
        return new ListenableFutureCallback<SendResult<String, LockCarDTO>>() {
            @Override
            public void onSuccess(SendResult<String, LockCarDTO> t) {
                log.info("sent message='{}'", v);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", v, ex);
            }
        };
    }

}
