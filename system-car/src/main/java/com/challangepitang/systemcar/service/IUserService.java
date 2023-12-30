package com.challangepitang.systemcar.service;

import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.model.output.UserWithCarsOutput;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    LoginOutput authenticateUser(LoginInput loginInput) throws Exception;
    UserOutput create(UserInput userRequest) throws Exception;
    UserOutput update(Long id, UserInput userInput) throws Exception;
    List<UserOutput> findAll();
    UserOutput findById(Long id);
    UserOutput findAuthenticatedUser(String token) throws Exception;
    UserWithCarsOutput findAuthenticatedUserWithCars(String token) throws Exception;
    void deleteById(Long id);
    void checkUserProperties(UserInput userInput, Optional<Long> userId, boolean isUpdate) throws Exception;
    boolean isTokenValid(String token) throws Exception;
}
