package com.challangepitang.systemcar;

import com.challangepitang.systemcar.model.Car;
import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.repository.ICarRepository;
import com.challangepitang.systemcar.service.IUserService;
import com.challangepitang.systemcar.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class CarServiceImplTest {

    @Mock
    private IUserService userService;

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testCreateCar() throws Exception {
        // Mocking dependencies
        Mockito.when(userService.findAuthenticatedUser(Mockito.anyString())).thenReturn(createUserOutput());
        Mockito.when(carRepository.save(any())).thenReturn(createCar());

        // Test
        CarInput carInput = createCarInput();
        CarOutput carOutput = carService.create("token", carInput);

        // Assertions
        assertNotNull(carOutput);
        assertEquals("ABC123", carOutput.getLicensePlate());
    }

    @Test
    public void testUpdateCar() throws Exception {
        // Mocking dependencies
        Mockito.when(userService.findAuthenticatedUser(Mockito.anyString())).thenReturn(createUserOutput());
        Mockito.when(carRepository.findById(anyLong())).thenReturn(Optional.of(createCar()));
        Mockito.when(carRepository.save(any())).thenReturn(createCar());

        // Test
        CarInput carInput = createCarInput();
        CarOutput carOutput = carService.update("token", 1L, carInput);

        // Assertions
        assertNotNull(carOutput);
        assertEquals("ABC123", carOutput.getLicensePlate());
    }

    @Test
    public void testFindAllCars() throws Exception {
        // Mocking dependencies
        Mockito.when(userService.findAuthenticatedUser(Mockito.anyString())).thenReturn(createUserOutput());
        Mockito.when(carRepository.findByUserId(any())).thenReturn(Arrays.asList(createCar(), createCar()));

        // Test
        List<CarOutput> carOutputs = carService.findAll("token");

        // Assertions
        assertNotNull(carOutputs);
        assertEquals(2, carOutputs.size());
    }

    @Test
    public void testFindCarById() throws Exception {
        // Mocking dependencies
        Mockito.when(userService.findAuthenticatedUser(Mockito.anyString())).thenReturn(createUserOutput());
        Mockito.when(carRepository.findByUserId(any())).thenReturn(Arrays.asList(createCar(), createCar()));

        // Test
        CarOutput carOutput = carService.findById("token", 1L);

        // Assertions
        assertNotNull(carOutput);
        assertEquals("ABC123", carOutput.getLicensePlate());
    }

    @Test
    public void testDeleteCar() throws Exception {
        // Mocking dependencies
        Mockito.when(userService.findAuthenticatedUser(Mockito.anyString())).thenReturn(createUserOutput());
        Mockito.when(carRepository.findById(anyLong())).thenReturn(Optional.of(createCar()));
        Mockito.doNothing().when(carRepository).deleteById(anyLong());

        // Test
        boolean result = carService.delete("token", 1L);

        // Assertions
        assertTrue(result);
    }

    private CarInput createCarInput() {
        CarInput carInput = new CarInput();
        carInput.setYear(2022);
        carInput.setLicensePlate("ABC123");
        carInput.setModel("Model");
        carInput.setColor("Color");
        return carInput;
    }

    private Car createCar() {
        Car car = new Car();
        car.setId(1L);
        car.setLicensePlate("ABC123");
        car.setModel("Model X");
        return car;
    }

    private UserOutput createUserOutput() {
        return new UserOutput();
    }
}