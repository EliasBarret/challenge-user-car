package com.challangepitang.systemcar.model.input;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarInput {
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;
}
