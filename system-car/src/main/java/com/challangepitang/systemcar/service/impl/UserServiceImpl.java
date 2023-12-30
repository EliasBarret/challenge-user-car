package com.challangepitang.systemcar.service.impl;

import com.challangepitang.systemcar.exception.*;
import com.challangepitang.systemcar.model.Car;
import com.challangepitang.systemcar.model.User;
import com.challangepitang.systemcar.model.input.CarInput;
import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.CarOutput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.model.output.UserWithCarsOutput;
import com.challangepitang.systemcar.repository.ICarRepository;
import com.challangepitang.systemcar.repository.IUserRepository;
import com.challangepitang.systemcar.security.JwtTokenProvider;
import com.challangepitang.systemcar.service.ICarService;
import com.challangepitang.systemcar.service.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository _userRepository;

    @Autowired
    private ICarRepository _carsRepository;

    @Autowired
    private ICarService _carService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public LoginOutput authenticateUser(LoginInput loginInput) {
        User user = Optional.ofNullable(_userRepository.findByLogin(loginInput.getLogin()))
                .orElseThrow(() -> new InvalidLoginOrPasswordException("Invalid login or password"));

        if (!bCryptPasswordEncoder.matches(loginInput.getPassword(), user.getPassword())) {
            throw new InvalidLoginOrPasswordException("Invalid login or password");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginInput.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);

        user.setLastLogin(new Date());
        user = _userRepository.save(user);

        LoginOutput loginOutput = convertToLoginOutput(user);
        loginOutput.setAccessToken(accessToken);

        return loginOutput;
    }

    @Override
    public UserOutput findAuthenticatedUser(String token) {
        User user = findAuthenticatedUserDetails(token);
        return convertToUserOutput(user);
    }

    @Override
    public UserWithCarsOutput findAuthenticatedUserWithCars(String token) {
        User user = findAuthenticatedUserDetails(token);
        List<Car> userLoggedCars = _carsRepository.findByUserId(Optional.ofNullable(user.getId()));
        List<CarOutput> carOutputList = convertCarListToCarOutputList(userLoggedCars);
        return convertToUserWithCarsOutput(user, carOutputList);
    }

    @Override
    @Transactional
    public UserOutput create(UserInput userInput) {
        checkUserProperties(userInput, Optional.empty(), false);

        User userEntity = convertToUser(userInput);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));

        Instant currentTimestamp = Instant.now();
        userEntity.setCreatedAt(Date.from(currentTimestamp));
        userEntity.setLastLogin(Date.from(currentTimestamp));

        _userRepository.save(userEntity);

        return convertToUserOutput(userEntity);
    }

    @Override
    public UserOutput update(Long id, UserInput userInput) {
        checkUserProperties(userInput, Optional.ofNullable(id), true);

        if(id != null){
            User userEntity =
                    _userRepository.findById(id).orElseThrow(() ->
                            new InvalidFieldsException("Invalid fields"));

            userEntity.setFirstName(userInput.getFirstName());
            userEntity.setLastName(userInput.getLastName());
            userEntity.setEmail(userInput.getEmail());
            userEntity.setBirthday(userInput.getBirthday());
            userEntity.setLogin(userInput.getLogin());
            userEntity.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
            userEntity.setPhone(userInput.getPhone());

            Instant currentTimestamp = Instant.now();
            userEntity.setCreatedAt(Date.from(currentTimestamp));
            userEntity.setLastLogin(Date.from(currentTimestamp));

            _userRepository.save(userEntity);

            return convertToUserOutput(userEntity);
        }else{
            throw new InvalidFieldsException("Invalid fields");
        }
    }

    @Override
    public List<UserOutput> findAll() {
        return _userRepository.findAll()
                .stream()
                .map(this::convertToUserOutput)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput findById(Long id) {
        User user = _userRepository.findById(id)
                .orElseThrow(() -> new InvalidFieldsException("Invalid fields"));

        return convertToUserOutput(user);
    }

    @Override
    public void deleteById(Long id) {
        _userRepository.deleteById(id);
    }

    @Override
    public void checkUserProperties(UserInput userInput, Optional<Long> userId, boolean isUpdate) {
        if (!isUpdate && _userRepository.existsByEmail(userInput.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (!isUpdate &&_userRepository.existsByLogin(userInput.getLogin())) {
            throw new LoginAlreadyExistsException("Login already exists");
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
            throw new MissingFieldsException("Missing fields");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = _userRepository.findByEmail(login);

        if(user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }

    @Override
    public boolean isTokenValid(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public User findAuthenticatedUserDetails(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new UnauthorizedException("Unauthorized");
        }

        try {
            String email = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
            return _userRepository.findByEmail(email);
        } catch (ExpiredJwtException e) {
            throw new InvalidSessionException("Unauthorized - invalid session");
        } catch (JwtException e) {
            throw new UnauthorizedException("Unauthorized");
        }
    }

    private UserOutput convertToUserOutput(User user) {
        UserOutput userOutput = new UserOutput();
        BeanUtils.copyProperties(user, userOutput);

        return userOutput;
    }

    private UserWithCarsOutput convertToUserWithCarsOutput(User user, List<CarOutput> cars) {
        UserWithCarsOutput userWithCarsOutput = new UserWithCarsOutput();
        BeanUtils.copyProperties(user, userWithCarsOutput);
        userWithCarsOutput.setCars(cars);
        return userWithCarsOutput;
    }

    private User convertToUser(UserInput userInput) {
        User user = new User();
        BeanUtils.copyProperties(userInput, user);
        return user;
    }

    private LoginOutput convertToLoginOutput(User user) {
        if (user == null) {
            throw new InvalidFieldsException("Invalid fields");
        }

        LoginOutput loginOutput = new LoginOutput();
        BeanUtils.copyProperties(user, loginOutput);
        return loginOutput;
    }

    private List<CarOutput> convertCarListToCarOutputList(List<Car> cars) {
        return cars.stream()
                .map(car -> {
                    CarOutput carOutput = new CarOutput();
                    BeanUtils.copyProperties(car, carOutput);
                    return carOutput;
                })
                .collect(Collectors.toList());
    }
}
