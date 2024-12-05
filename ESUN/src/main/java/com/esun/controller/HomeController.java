package com.esun.controller;

import com.esun.repository.UserRepository;
import com.esun.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home() {
        System.err.println("success 成功進入系統");
        List<User> allUser = userRepository.findAllUser();
        System.out.println("全部使用者" + allUser);
        return "OK";
    }
}