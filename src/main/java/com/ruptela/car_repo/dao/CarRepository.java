package com.ruptela.car_repo.dao;

import com.ruptela.car_repo.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CarRepository extends CrudRepository<Car, String> {
}
