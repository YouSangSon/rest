package yousang.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yousang.backend.rest.entity.User.User;
import yousang.backend.rest.service.User.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // @Autowired
    // private UserService userService;

    // @PostMapping("/join")
    // public void join(@RequestBody User user) {
    //     userService.join(user);
    // }

    // @PostMapping("/login")
    // public String login(@RequestParam String username, @RequestParam String password) {
    //     return userService.login(username, password);
    // }

    // @DeleteMapping("/withdraw")
    // public void withdraw(@RequestParam String username) {
    //     userService.withdraw(username);
    // }
}