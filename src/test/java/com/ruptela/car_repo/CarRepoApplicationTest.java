package com.ruptela.car_repo;

import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.controller.TopUseCaseController;
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

    @Autowired
    TopUseCaseController c;

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
                .map(e->car("asdfg_123456_" + e))
                .collect(toList());

        List<Car> l1 = Streams
                .stream(c.listCars())
                .collect(toList());

        assertTrue(l1.containsAll(l0));
    }

    @Test
    @Transactional
    void lock() throws ControllerException {
        Car car = car();
        RetCodes r = c.lockCar(car.getVin(), true);
        assertTrue(r.equals(RetCodes.ok));

        try {
            c.lockCar(car.getVin(), true);
        } catch (ControllerException e) {
            assertTrue(e.equals(RetCodes.car_is_allready_in_state(true)));
        }
        r = c.lockCar(car.getVin(), false);
        assertTrue(r.equals(RetCodes.ok));
    }

    @Test
    @Transactional
    void get_state() throws ControllerException {
        Car car = car();
        RetCodes state = c.get_car_state(car.getVin());
        assertTrue(state.equals(RetCodes.car_state_is(false)));
    }

    private Car car() throws ControllerException {
        return car("asdf_1234");
    }

    private Car car(String vin) {
        try {
            return c.createCar(vin, "BMW", "318iS", "0123456789");
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }
}
