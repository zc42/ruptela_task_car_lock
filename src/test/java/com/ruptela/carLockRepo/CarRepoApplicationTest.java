package com.ruptela.carLockRepo;

import com.ruptela.carLockRepo.controller.CarController;
import com.ruptela.carLockRepo.controller.LockController;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.controller.parts.RetCodes;
import com.ruptela.carLockRepo.dao.CarRepository;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.CarDTO;
import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.kafka.consumer.KafKaConsumer;
import com.ruptela.carLockRepo.redis.repos.RedisCarRepo;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ComponentScan(basePackages = "com.ruptela.carLockRepo")
class CarRepoApplicationTest {

    private static final Logger log = LoggerFactory.getLogger(CarRepoApplicationTest.class);

    private final String vin = "asdfg_123456_";

    @Autowired
    CarController carCntrl;
    @Autowired
    LockController lockCntrl;
    @Autowired
    KafKaConsumer kafkaConsumer;
    @Autowired
    CarRepository carRepo;
    @Autowired
    RedisCarRepo carRedisRepo;

    @Test
    @Transactional
    void create() throws ControllerException {
        Car car = car();
        assertTrue(car != null);

        Optional<Car> v0 = carRepo.findById(car.getVin());
        assertTrue(v0.isPresent());
        assertTrue(v0.get().equals(car));

        Car v1 = carRedisRepo.findById(car.getVin());
        assertTrue(v1.equals(car));
    }

    @Test
    @Transactional
    void list() throws ControllerException {
        List<CarDTO> l0 = IntStream
                .range(0, 100)
                .boxed()
                .map(e -> car(vin + e))
                .map(e -> CarDTO.from(e))
                .collect(toList());

        List<CarDTO> l1 = carCntrl.listCars();
        assertTrue(l1.containsAll(l0));
    }

    @Test
    @Transactional
    void lock() throws ControllerException {
        Car car = car();
        RetCodes r = lockCntrl.lockCar(LockCarDTO.from(car.getVin(), true));
        assertTrue(r.equals(RetCodes.ok));

        try {
            lockCntrl.lockCar(LockCarDTO.from(car.getVin(), true));
        } catch (ControllerException e) {
            assertTrue(e.equals(RetCodes.car_is_allready_in_state(true)));
        }
        r = lockCntrl.lockCar(LockCarDTO.from(car.getVin(), false));
        assertTrue(r.equals(RetCodes.ok));
        r = lockCntrl.lockCar(LockCarDTO.from(car.getVin(), true));
        assertTrue(r.equals(RetCodes.ok));
    }

    @Test
    @Transactional
    void kafkaConsumer() throws ControllerException {
        Car car = car();
        assertTrue(!car.isLocked());
        kafkaConsumer.consume(LockCarDTO.from(car.getVin(), true));
        assertTrue(carRepo.findById(vin).get().isLocked());
    }

    @Test
    @Transactional
    void getState() throws ControllerException {
        Car car = car();
        RetCodes state = lockCntrl.getCarState(car.getVin());
        assertTrue(state.equals(RetCodes.car_state_is(false)));

        Optional<Car> v = carRepo.findById(car.getVin());
        assertTrue(v.isPresent());

        assertTrue(!v.get().isLocked());

    }

    private Car car() throws ControllerException {
        return car(vin);
    }

    private Car car(String vin) {
        try {
            CarDTO v = CarDTO.from(vin, "BMW", "318iS", "0123456789");
            return carCntrl.createCar(v);
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }
}
