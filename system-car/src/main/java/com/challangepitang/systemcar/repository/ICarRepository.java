package com.challangepitang.systemcar.repository;

import com.challangepitang.systemcar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUserId(@Param("idUser") Optional<Long> userId);
    boolean existsByLicensePlate(String licensePlate);
}
