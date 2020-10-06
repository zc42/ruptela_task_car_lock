package com.ruptela.car_repo.rest_client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class VehicleClientProperties {

    @Value("${resource.rest_api}/vehicles/getallmakes?format=json")
    private String allMakers;
    @Value("${resource.rest_api}/vehicles/getmodelsformake/{maker}?format=json")
    private String getModels;
}
