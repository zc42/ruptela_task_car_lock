package com.ruptela.car_repo;

import com.ruptela.car_repo.controller.ControllerException;
import com.ruptela.car_repo.controller.MainController;
import com.ruptela.car_repo.controller.RetCodes;
import com.ruptela.car_repo.entity.Car;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ComponentScan(basePackages = "com.ruptela.car_repo")
class CarRepoApplicationTest {

//	@Test
//	void contextLoads() {
//	}

    @Autowired
    MainController c;

    @Test
    @Transactional
    void create() throws ControllerException {
        Car car = car();
        assertTrue(car !=null);
    }

    @Test
    @Transactional
    void list() throws ControllerException {
        int c0 = 100;
        IntStream.range(0, c0)
                .boxed()
                .forEach(e -> car("asdfg_123456_" + e));

        Iterable<Car> l = c.listCars();

        long c1 = Streams.stream(l).count();
        assertTrue(c1 >= c0);
    }

    @Test
    @Transactional
    void lock() throws ControllerException {
        Car car = car();
        RetCodes r = c.lockCar(car.getVin(), true);
        assertTrue(r.equals(RetCodes.ok));

        try {
            c.lockCar(car.getVin(), true);
        }catch (ControllerException e){
            assertTrue(e.getMessage().equalsIgnoreCase(RetCodes.car_is_allready_in_state(true).toString()));
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
