package com.challangepitang.systemcar.model.output;

import com.challangepitang.systemcar.model.Car;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserWithCarsOutput {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private String phone;
    private Date createdAt;
    private Date lastLogin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CarOutput> cars;
}
