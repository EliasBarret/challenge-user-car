package com.challangepitang.systemcar.controller;

import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private ICarService _carService;

    @GetMapping
    public ResponseEntity<List<CarOutput>> findAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(_carService.findAll(token));
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<CarOutput> findById(@RequestHeader("Authorization") String token, @PathVariable Long id){
        return ResponseEntity.ok(_carService.findById(token, id));
    }
    @PostMapping(value = "/api/cars")
    ResponseEntity<CarOutput> create(@RequestHeader("Authorization") String token,
                                     @RequestBody CarInput carRequest){
        return ResponseEntity.ok(_carService.create(token, carRequest));
    }
    @PutMapping(value = "/api/cars/{id}")
    public ResponseEntity<CarOutput> update(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id,
                                            @RequestBody CarInput carRequest){
        return ResponseEntity.ok(_carService.update(token, id, carRequest));
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return _carService.delete(token, id) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
