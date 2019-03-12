package hu.vattila.insight.controller;

import hu.vattila.insight.entity.User;
import hu.vattila.insight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(@RequestParam String nameFragment) {
        List<User> users = userRepository.findAllByNameContaining(nameFragment);
        return ResponseEntity.ok(users);
    }
}
