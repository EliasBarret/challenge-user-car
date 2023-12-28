package com.challangepitang.systemcar.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CarInput {
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;
}
