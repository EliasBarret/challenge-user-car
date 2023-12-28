package com.challangepitang.systemcar.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginOutput {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String phone;
    private String createdAt;
    private String lastLogin;
    private String accessToken;
}
