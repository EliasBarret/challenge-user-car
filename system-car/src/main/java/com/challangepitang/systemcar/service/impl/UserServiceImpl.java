package com.challangepitang.systemcar.service.impl;

import com.challangepitang.systemcar.model.User;
import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.repository.ICarRepository;
import com.challangepitang.systemcar.repository.IUserRepository;
import com.challangepitang.systemcar.service.ICarService;
import com.challangepitang.systemcar.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository _userRepository;

    @Autowired
    private ICarRepository _carRepository;

    @Autowired
    private ICarService _carService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public LoginOutput authenticateUser(LoginInput loginInput) {
        return null;
    }

    @Override
    public UserOutput findAuthenticateUser(String token) {
        return null;
    }

    @Override
    public UserOutput create(UserInput userInput) throws Exception {
        checkUserProperties(userInput);

        User userEntity = convertToUser(userInput);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));

        _userRepository.save(userEntity);

        return convertToOutput(userEntity);
    }

    @Override
    public UserOutput update(Long id, UserInput userInput) throws Exception {
        checkUserProperties(userInput);

        User userEntity =
                _userRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + id));

        userEntity.setFirstName(userInput.getFirstName());
        userEntity.setLastName(userInput.getLastName());
        userEntity.setEmail(userInput.getEmail());
        userEntity.setBirthday(userInput.getBirthday());
        userEntity.setLogin(userInput.getLogin());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        userEntity.setPhone(userInput.getPhone());

        _userRepository.save(userEntity);

        return convertToOutput(userEntity);
    }

    @Override
    public List<UserOutput> findAll() {
        return _userRepository.findAll()
                .stream()
                .map(this::convertToOutput)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput findById(Long id) {
        return _userRepository.findById(id).map(this::convertToOutput).get();
    }

    @Override
    public void deleteById(Long id) {
        _userRepository.deleteById(id);
    }

    @Override
    public boolean checkUserProperties(UserInput userInput) throws Exception {
        if (_userRepository.existsByEmail(userInput.getEmail())) {
            throw new Exception("Email already exists");
        }

        if (_userRepository.existsByLogin(userInput.getLogin())) {
            throw new Exception("Login already exists");
        }

        Objects.requireNonNull(userInput.getFirstName());
        Objects.requireNonNull(userInput.getLastName());
        Objects.requireNonNull(userInput.getEmail());
        Objects.requireNonNull(userInput.getBirthday());
        Objects.requireNonNull(userInput.getLogin());
        Objects.requireNonNull(userInput.getPassword());
        Objects.requireNonNull(userInput.getPhone());

        if (Stream.of(userInput.getFirstName(), userInput.getLastName(), userInput.getEmail(),
                        userInput.getLogin(), userInput.getPassword(), userInput.getPhone())
                .anyMatch(s -> s == null || s.isEmpty() || s.isBlank())) {
            throw new Exception("Missing fields");
        }

        //userInput.getCars().forEach(_carService::checkCarProperties);

        return true;
    }

    private UserOutput convertToOutput(User user) {
        UserOutput userOutput = new UserOutput();
        BeanUtils.copyProperties(user, userOutput);

        return userOutput;
    }

    private User convertToUser(UserInput userInput) {
        User user = new User();
        BeanUtils.copyProperties(userInput, user);
        return user;
    }
}
