package com.challangepitang.systemcar.service;

import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;

import java.util.List;

public interface ICarService {
    CarOutput create(String token, CarInput carRequest);
    CarOutput update(String token, Long id, CarInput carRequest);
    List<CarOutput> findAll(String token);
    CarOutput findById(String token, Long id);
    boolean delete(String token, Long id);
    boolean checkCarProperties(CarInput carInput);
}
