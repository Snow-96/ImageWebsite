package com.csci430.website.controller;

import com.csci430.website.entity.User;
import com.csci430.website.repository.UserRepository;
import com.csci430.website.security.WebSecurityConfig;
import com.csci430.website.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        return session.getAttribute(WebSecurityConfig.SESSION_KEY) != null ? "gallery" : "login";
    }

    @GetMapping("/logout")
    public @ResponseBody
    Map<String, String> logout(HttpSession session) {
        Map<String, String> map = new HashMap<>();
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        map.put("result", "success");
        map.put("message", "logout!");
        return map;
    }

    @PostMapping("/loginPost")
    public @ResponseBody
    Map<String, String> loginPost(HttpSession session, @RequestBody LoginVO loginVO) {
        Map<String, String> map = new HashMap<>();
        User user = userRepository.findByEmail(loginVO.getEmail());
        // todo hash password
        if (user == null || !user.getPassword().equals(loginVO.getPassword())) {
            map.put("result", "fail");
            map.put("message", "wrong password Or email");
            return map;
        }

        session.setAttribute(WebSecurityConfig.SESSION_KEY, loginVO.getEmail());

        map.put("result", "success");
        map.put("message", "login success");
        return map;
    }
}
