package com.challangepitang.systemcar;

import com.challangepitang.systemcar.model.User;
import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.repository.IUserRepository;
import com.challangepitang.systemcar.security.JwtTokenProvider;
import com.challangepitang.systemcar.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser() {
        // Mocking dependencies
        User user = createUser();
        Mockito.when(userRepository.findByLogin(anyString())).thenReturn(user);
        Mockito.when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Mockito.when(jwtTokenProvider.generateToken(any(Authentication.class)))
                .thenReturn("seuTokenAqui");

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null));

        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        LoginOutput loginOutput = userService.authenticateUser(createLoginInput());

        // Assertions
        assertNotNull(loginOutput);
        assertNotNull(loginOutput.getAccessToken());
        assertEquals("John", loginOutput.getFirstName());
    }

    @Test
    public void testCreateUser() {
        // Mocking dependencies
        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(createUser());

        // Test
        UserOutput userOutput = userService.create(createUserInput());

        // Assertions
        assertNotNull(userOutput);
        assertEquals("John", userOutput.getFirstName());
    }

    @Test
    public void testUpdateUser() {
        // Mocking dependencies
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(createUser()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(createUser());

        // Test
        UserOutput userOutput = userService.update(1L, createUserInput());

        // Assertions
        assertNotNull(userOutput);
        assertEquals("John", userOutput.getFirstName());
    }

    @Test
    public void testFindAllUsers() {
        // Mocking dependencies
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(createUser(), createUser()));

        // Test
        List<UserOutput> userOutputs = userService.findAll();

        // Assertions
        assertNotNull(userOutputs);
        assertEquals(2, userOutputs.size());
    }

    @Test
    public void testFindUserById() {
        // Mocking dependencies
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(createUser()));

        // Test
        UserOutput userOutput = userService.findById(1L);

        // Assertions
        assertNotNull(userOutput);
        assertEquals("John", userOutput.getFirstName());
    }

    @Test
    public void testDeleteUser() {
        // Mocking dependencies
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(createUser()));
        Mockito.doNothing().when(userRepository).deleteById(anyLong());

        // Test
        userService.deleteById(1L);

        // Assertions
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    private LoginInput createLoginInput() {
        LoginInput loginInput = new LoginInput();
        loginInput.setLogin("username");
        loginInput.setPassword("password");
        return loginInput;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("hashedPassword");
        user.setLogin("AAAA");
        user.setPhone("123456789");
        user.setBirthday(new Date());
        return user;
    }

    private UserInput createUserInput() {
        UserInput userInput = new UserInput();
        userInput.setFirstName("John");
        userInput.setLastName("Doe");
        userInput.setEmail("john.doe@example.com");
        userInput.setPassword("password");
        userInput.setPhone("123456789");
        userInput.setLogin("AAAA");
        userInput.setBirthday(new Date());
        return userInput;
    }
}
