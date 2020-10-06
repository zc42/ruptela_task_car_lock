package com.ruptela.carLockRepo.kafka.consumer;

import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class KafKaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafKaConsumer.class);

    @Autowired
    private CarService cars;

    @KafkaListener(topics = "${resource.kafka_topic}")
    public void consume(LockCarDTO v) {
        Car car = cars.findById(v.getVin());
        if (car == null || !car.isExists()) {
            log.error("car == null || !car.isExists(): " + v.getVin());
            return;
        }
        car.setLocked(v.getLock());
        cars.save(car);
        log.info("received and updated: " + v);
    }

}
