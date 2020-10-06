package com.ruptela.carLockRepo.dao;

import com.ruptela.carLockRepo.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends CrudRepository<Car, String> {
}
