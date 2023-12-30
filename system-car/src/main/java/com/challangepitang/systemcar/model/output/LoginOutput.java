package com.challangepitang.systemcar.model.output;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
