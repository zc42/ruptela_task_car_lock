package com.ruptela.car_repo.rest_client;

import com.ruptela.car_repo.entity.Maker;
import com.ruptela.car_repo.entity.Model;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.toList;

@Component
public class VehileClient {

    @Autowired
    private VehicleClientProperties props;

    @Autowired
    private RestTemplate restTemplate;

    public List<Maker> GetAllMakes() {
        Object o = restTemplate.getForObject(props.getAllMakers(), Object.class);
        return o_2_dto(o, e -> Maker.from(e));
    }

    public List<Model> GetModels(String maker) {
        Object o = restTemplate.getForObject(props.getGetModels(), Object.class, maker);
        return o_2_dto(o, e -> Model.from(e));
    }

    private <T> List<T> o_2_dto(Object m, Function<Map<String, Object>, T> f) {
        List<Map<String, Object>> l = (List<Map<String, Object>>) ((Map) m).get("Results");
        return l.stream()
                .map(e -> f.apply(e))
                .collect(toList());
    }
}
