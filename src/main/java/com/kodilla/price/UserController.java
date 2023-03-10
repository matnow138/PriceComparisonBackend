package com.kodilla.price;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.User;
import com.kodilla.price.exception.UserNotFoundException;
import com.kodilla.price.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok().body(createdUser);
    }

    @GetMapping(value = "/searchUser/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PatchMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/blockUser/{id}")
    public ResponseEntity<Void> blockUser(@PathVariable long id) throws UserNotFoundException {
        userService.blockUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/activateUser/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable long id) throws UserNotFoundException {
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userList = userService.getAll();
        return ResponseEntity.ok(userList);

    }

    @GetMapping(value = "/getOffers/{id}")
    public ResponseEntity<List<AmazonOfferDto>> getOffersForUser(@PathVariable long id) throws UserNotFoundException {
        List<AmazonOfferDto> amazonOfferDtoList = userService.findOffersForUser(id);
        return ResponseEntity.ok(amazonOfferDtoList);
    }


}
