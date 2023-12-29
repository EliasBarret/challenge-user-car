package com.challangepitang.systemcar.model.output;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarOutput {
    private Long id;
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;
}
