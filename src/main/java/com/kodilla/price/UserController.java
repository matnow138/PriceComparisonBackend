package com.kodilla.price;

import com.kodilla.price.domain.UserDto;
import com.kodilla.price.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<UserDto> findUser(@RequestBody long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }
}
