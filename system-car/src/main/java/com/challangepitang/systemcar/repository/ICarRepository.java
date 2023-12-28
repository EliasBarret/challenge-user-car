package com.challangepitang.systemcar.repository;

import com.challangepitang.systemcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarByUserId(@Param("idUser") Long userId);
    boolean existsByLicensePlate(String licensePlate);
}
