package com.challangepitang.systemcar.service.impl;

import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.service.ICarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements ICarService {

    @Override
    public CarOutput create(String token, CarInput carRequest) {
        return null;
    }

    @Override
    public CarOutput update(String token, Long id, CarInput carRequest) {
        return null;
    }

    @Override
    public List<CarOutput> findAll(String token) {
        return null;
    }

    @Override
    public CarOutput findById(String token, Long id) {
        return null;
    }

    @Override
    public boolean delete(String token, Long id) {
        return false;
    }

    @Override
    public boolean checkCarProperties(CarInput carInput) {
        return false;
    }
}
