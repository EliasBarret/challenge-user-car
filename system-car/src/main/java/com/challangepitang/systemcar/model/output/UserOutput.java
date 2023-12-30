package com.challangepitang.systemcar.model.output;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOutput {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private String phone;
    private Date createdAt;
    private Date lastLogin;
}
