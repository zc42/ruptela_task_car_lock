package com.ruptela.car_repo.dao;

import com.ruptela.car_repo.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarRepository extends CrudRepository<Car, String> {

    @Query("SELECT a FROM cars a")
    List<Car> all_cars();

//    @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
//    User findUserByStatusAndName(Integer status, String name);
}