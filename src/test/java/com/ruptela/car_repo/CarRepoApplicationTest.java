package com.ruptela.car_repo;

import com.ruptela.car_repo.controller.CarController;
import com.ruptela.car_repo.controller.LockController;
import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.controller.parts.RetCodes;
import com.ruptela.car_repo.entity.Car;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ComponentScan(basePackages = "com.ruptela.car_repo")
class CarRepoApplicationTest {

    private final String vin = "asdfg_123456_";

    @Autowired
    CarController carCntrl;
    @Autowired
    LockController lockCntrl;

    @Test
    @Transactional
    void create() throws ControllerException {
        Car car = car();
        assertTrue(car != null);
    }

    @Test
    @Transactional
    void list() throws ControllerException {
        List<Car> l0 = IntStream
                .range(0, 100)
                .boxed()
                .map(e -> car(vin + e))
                .collect(toList());

        List<Car> l1 = Streams
                .stream(carCntrl.listCars())
                .collect(toList());

        assertTrue(l1.containsAll(l0));
    }

    @Test
    @Transactional
    void lock() throws ControllerException {
        Car car = car();
        RetCodes r = lockCntrl.lockCar(car.getVin(), true);
        assertTrue(r.equals(RetCodes.ok));

        try {
            lockCntrl.lockCar(car.getVin(), true);
        } catch (ControllerException e) {
            assertTrue(e.equals(RetCodes.car_is_allready_in_state(true)));
        }
        r = lockCntrl.lockCar(car.getVin(), false);
        assertTrue(r.equals(RetCodes.ok));
    }

    @Test
    @Transactional
    void get_state() throws ControllerException {
        Car car = car();
        RetCodes state = lockCntrl.getCarState(car.getVin());
        assertTrue(state.equals(RetCodes.car_state_is(false)));
    }

    private Car car() throws ControllerException {
        return car(vin);
    }

    private Car car(String vin) {
        try {
            return carCntrl.createCar(vin, "BMW", "318iS", "0123456789");
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }
}
