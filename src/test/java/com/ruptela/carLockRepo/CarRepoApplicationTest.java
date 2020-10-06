package com.ruptela.carLockRepo;

import com.ruptela.carLockRepo.controller.CarController;
import com.ruptela.carLockRepo.controller.LockController;
import com.ruptela.carLockRepo.entity.CarDTO;
import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.controller.parts.RetCodes;
import com.ruptela.carLockRepo.entity.Car;
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
@ComponentScan(basePackages = "com.ruptela.carLockRepo")
class CarRepoApplicationTest {

    private final String vin = "asdfg_123456_";

    @Autowired
    CarController carCntrl;
    @Autowired
    LockController lockCntrl;

    @Test
    @Transactional
    void create() throws ControllerException {

//        carCntrl.createCar_test("aaa", "bbb");
//        carCntrl.createCar_test("aaa", null);
//        carCntrl.createCar_test(null, null);
//
//        CarDTO v =  CarDTO.from(vin, "BMW", null, null);
//        carCntrl.createCar(v);

//        Car car = car();
//        car.setMake(null);
//        assertTrue(car != null);
//        car = car();
//        car.setPlateNb(null);
//        assertTrue(car != null);
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
        RetCodes r = lockCntrl.lockCar(LockCarDTO.from(car.getVin(), true));
        assertTrue(r.equals(RetCodes.ok));

        try {
            lockCntrl.lockCar(LockCarDTO.from(car.getVin(), true));
        } catch (ControllerException e) {
            assertTrue(e.equals(RetCodes.car_is_allready_in_state(true)));
        }
        r = lockCntrl.lockCar(LockCarDTO.from(car.getVin(), false));
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
            CarDTO v =  CarDTO.from(vin, "BMW", "318iS", "0123456789");
            return carCntrl.createCar(v);
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }
}
