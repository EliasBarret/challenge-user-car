package com.challangepitang.systemcar.service.impl;

import com.challangepitang.systemcar.exception.InvalidFieldsException;
import com.challangepitang.systemcar.exception.LicensePlateAlreadyExistsException;
import com.challangepitang.systemcar.exception.MissingFieldsException;
import com.challangepitang.systemcar.exception.UnauthorizedException;
import com.challangepitang.systemcar.model.Car;
import com.challangepitang.systemcar.model.User;
import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.repository.ICarRepository;
import com.challangepitang.systemcar.service.ICarService;
import com.challangepitang.systemcar.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private IUserService _userService;

    @Autowired
    private ICarRepository _carRepository;

    @Override
    @Transactional
    public CarOutput create(String token, CarInput carRequest) throws Exception {
        checkCarProperties(carRequest, false);

        UserOutput userOutput = _userService.findAuthenticatedUser(token);

        User user = new User();
        user.setId(userOutput.getId());

        Car car = new Car();
        car.setYear(carRequest.getYear());
        car.setLicensePlate(carRequest.getLicensePlate());
        car.setUser(user);
        car.setModel(carRequest.getModel());
        car.setColor(carRequest.getColor());

        _carRepository.save(car);
        return convertToCarOutput(car);
    }

    @Override
    public CarOutput update(String token, Long idCarro, CarInput carRequest) throws Exception {
        checkCarProperties(carRequest, true);

        _userService.findAuthenticatedUser(token);

        Car car = _carRepository.findById(idCarro)
                .orElseThrow(() -> new InvalidFieldsException("Invalid fields"));

        car.setModel(carRequest.getModel());
        car.setColor(carRequest.getColor());
        car.setYear(carRequest.getYear());
        car.setLicensePlate(carRequest.getLicensePlate());

        _carRepository.save(car);
        return convertToCarOutput(car);
    }

    @Override
    @Transactional
    public List<CarOutput> findAll(String token) throws Exception {
        UserOutput userOutput = _userService.findAuthenticatedUser(token);

        List<Car> cars = _carRepository.findByUserId(Optional.ofNullable(userOutput.getId()));
        return cars.stream()
                .map(this::convertToCarOutput)
                .collect(Collectors.toList());
    }

    @Override
    public CarOutput findById(String token, Long id) throws Exception {
        UserOutput userOutput = _userService.findAuthenticatedUser(token);

        List<Car> carsOfUser = _carRepository.findByUserId(Optional.ofNullable(userOutput.getId()));

        return carsOfUser.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .map(this::convertToCarOutput)
                .orElseThrow(() -> new InvalidFieldsException("Invalid fields"));
    }

    @Override
    public boolean delete(String token, Long id) throws Exception {
        _userService.findAuthenticatedUser(token);

        Optional<Car> optionalCar = _carRepository.findById(id);
        if (optionalCar.isPresent()) {
            _carRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkCarProperties(CarInput carInput, boolean isUpdate) {
        if (!isUpdate && _carRepository.existsByLicensePlate(carInput.getLicensePlate())) {
            throw new LicensePlateAlreadyExistsException("License plate already exists");
        }

        int year = carInput.getYear();
        String licensePlate = carInput.getLicensePlate();
        String model = carInput.getModel();
        String color = carInput.getColor();

        if (year <= 0 || anyFieldIsInvalid(licensePlate, model, color)) {
            throw new MissingFieldsException("Missing fields");
        }
    }

    private boolean anyFieldIsInvalid(String... fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> field == null || field.isEmpty() || field.isBlank());
    }

    private CarOutput convertToCarOutput(Car car) {
        CarOutput carOutput = new CarOutput();
        BeanUtils.copyProperties(car, carOutput);

        return carOutput;
    }
}
