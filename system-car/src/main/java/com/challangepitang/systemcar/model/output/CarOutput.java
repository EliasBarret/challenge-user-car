package com.challangepitang.systemcar.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CarOutput {
    private Long id;
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;
}
