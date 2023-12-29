package com.challangepitang.systemcar.controller;

import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.service.ICarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final ICarService _carService;

    public CarController(ICarService carService) {
        this._carService = carService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok(_carService.findAll(token));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(_carService.findById(token, id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token,
                                    @RequestBody CarInput carInput) throws Exception {
        CarOutput carOutput = _carService.create(token, carInput);
        return ResponseEntity.ok(carOutput);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token,
                                    @PathVariable Long id,
                                    @RequestBody CarInput carRequest) throws Exception {
        return ResponseEntity.ok(_carService.update(token, id, carRequest));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return _carService.delete(token, id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }
}