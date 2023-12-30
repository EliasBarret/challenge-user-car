package com.challangepitang.systemcar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

