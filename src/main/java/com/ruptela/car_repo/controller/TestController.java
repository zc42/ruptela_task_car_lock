package com.ruptela.car_repo.controller;

import com.ruptela.car_repo.dao.CarRepository;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.entity.Maker;
import com.ruptela.car_repo.entity.Model;
import com.ruptela.car_repo.kafka.producer.KafkaProducer;
import com.ruptela.car_repo.redis.RedisModelRepo;
import com.ruptela.car_repo.rest_client.VechileAPIClient;
import com.ruptela.car_repo.service.CarLockService;
import com.ruptela.car_repo.service.CarService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping(path = "/demo")
public class TestController {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private VechileAPIClient vechileAPIClient;
    @Autowired
    private RedisModelRepo modelRepo;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private CarService carService;
    @Autowired
    private CarLockService carLockService;

    @GetMapping(path = "/get_makes")
    public @ResponseBody
    List<Maker> GetAllMakes() {
        List<Maker> l = vechileAPIClient.GetAllMakes();
        return l;
    }

    @GetMapping(path = "/get_models")
    public @ResponseBody
    List<Model> GetModels(String maker) {
        List<Model> l = vechileAPIClient.GetModels(maker);
        l.forEach(e -> modelRepo.save(e));

        List<Model> r = modelRepo.findAll().values().stream().collect(toList());
        return r;
    }

    @GetMapping(path = "/get_model")
    public @ResponseBody
    Model GetModel(String maker, String model) {
        Model v = modelRepo.findById(Model.from(maker.toUpperCase(), model).ID());
        return v;
    }

    @GetMapping(path = "/get_all_models")
    public @ResponseBody
    Map<String, Model> GetAllModel() {
        return modelRepo.findAll();
    }

    @GetMapping(path = "/get_all_cars")
    public @ResponseBody
    List<Car> GetAllCars() {
        return carRepository.all_cars();
    }

    @GetMapping(path = "/msg_2_kafka")
    public @ResponseBody
    String msg_2_kafka(String msg) {
        kafkaProducer.sendMessage(msg, true);
        return "ok";
    }
}
