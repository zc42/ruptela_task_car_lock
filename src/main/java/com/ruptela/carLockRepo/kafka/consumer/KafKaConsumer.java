package com.ruptela.carLockRepo.kafka.consumer;

import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.service.CarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Log4j2
public class KafKaConsumer {

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
