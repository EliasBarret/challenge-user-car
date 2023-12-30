package com.challangepitang.systemcar.service;

import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;

import java.util.List;

public interface ICarService {
    CarOutput create(String token, CarInput carRequest) throws Exception;
    CarOutput update(String token, Long id, CarInput carRequest) throws Exception;
    List<CarOutput> findAll(String token) throws Exception;
    CarOutput findById(String token, Long id) throws Exception;
    boolean delete(String token, Long id) throws Exception;
    void checkCarProperties(CarInput carInput, boolean isUpdate);
}
