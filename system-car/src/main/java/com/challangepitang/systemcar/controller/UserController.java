package com.challangepitang.systemcar.controller;

import com.challangepitang.systemcar.model.input.LoginInput;
import com.challangepitang.systemcar.model.input.UserInput;
import com.challangepitang.systemcar.model.output.LoginOutput;
import com.challangepitang.systemcar.model.output.UserOutput;
import com.challangepitang.systemcar.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService _userService;

    @GetMapping(value = "/me")
    public ResponseEntity<UserOutput> findAuthenticateUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(_userService.findAuthenticateUser(token));
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserOutput>> findAll() {
        return ResponseEntity.ok(_userService.findAll());
    }
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserOutput> findById(@PathVariable Long id) {
        return ResponseEntity.ok(_userService.findById(id));
    }
    @PostMapping("/users")
    ResponseEntity<UserOutput> create(@RequestBody UserInput userInput) throws Exception {
        return ResponseEntity.ok(_userService.create(userInput));
    }
    @PostMapping(value = "/signin")
    ResponseEntity<LoginOutput> authenticateUser(@RequestBody LoginInput loginInput) {
        return ResponseEntity.ok(_userService.authenticateUser(loginInput));
    }
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserOutput> update(@PathVariable Long id, @RequestBody UserInput userInput) throws Exception {
        return ResponseEntity.ok(_userService.update(id, userInput));
    }
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        _userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}