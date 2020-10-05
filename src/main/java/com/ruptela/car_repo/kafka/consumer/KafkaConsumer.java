package com.ruptela.car_repo.kafka.consumer;

import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.service.CarService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;



public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private CountDownLatch latch = new CountDownLatch(2);

    public static final String msg_split = "@@@@";

    @Autowired
    private CarService cars;

    @KafkaListener(topics = "${resource.kafka_topic}")
    public void receiveMessage(String msg) {

        if (msg == null || msg.isEmpty()) {
            log.error("msg canot be empty");
            return;
        }

        List<String> l = Arrays.asList(msg.split(msg_split));
        if (l == null || l.isEmpty() || l.size() != 2) {
            log.error("bad msg: " + msg);
            return;
        }

        Car car = cars.findById(l.get(0));
        if (car == null || !car.isExists()) {
            log.error("car == null || !car.isExists(): " + msg);
            return;
        }
        car.setLocked(Boolean.valueOf(l.get(1)));
        cars.save(car);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
