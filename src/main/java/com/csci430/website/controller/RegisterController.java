package com.csci430.website.controller;

import com.csci430.website.entity.User;
import com.csci430.website.function.SlowHash;
import com.csci430.website.repository.UserRepository;
import com.csci430.website.vo.RegisterVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registerPost")
    public @ResponseBody
    Map<String, String> registerPost(@RequestBody RegisterVO registerVO) {
        Map<String, String> map = new HashMap<>();
        if (userRepository.findByEmail(registerVO.getEmail()) != null) {
            map.put("result", "fail");
            map.put("message", "This email has been registered!");
        } else {
            map.put("result", "success");
            map.put("message", "Register Success");
            String hashed = SlowHash.generateHashedString(registerVO.getPassword());
            registerVO.setPassword(hashed);
            userRepository.save(new User(registerVO));
        }
        return map;
    }
}
