package com.ruptela.carLockRepo.kafka.consumer;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafKaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafKaConsumer.class);

    @Autowired
    private CarService cars;

    @KafkaListener(topics = "${resource.kafka_topic}")
    public void consume(LockCarDTO p) {
        Car car = cars.findById(p.getVin());
        if (car == null || !car.isExists()) {
            log.error("car == null || !car.isExists(): " + p.getVin());
            return;
        }
        car.setLocked(p.getLock());
        cars.save(car);
    }

}
