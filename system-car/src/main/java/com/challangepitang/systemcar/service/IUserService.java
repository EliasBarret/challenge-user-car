package com.challangepitang.systemcar.service;

import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;

import java.util.List;

public interface IUserService {
    LoginOutput authenticateUser(LoginInput loginInput);
    UserOutput create(UserInput userRequest) throws Exception;
    UserOutput update(Long id, UserInput userInput) throws Exception;
    List<UserOutput> findAll();
    UserOutput findById(Long id);
    UserOutput findAuthenticateUser(String token);
    void deleteById(Long id);
    boolean checkUserProperties(UserInput userInput) throws Exception;
}
