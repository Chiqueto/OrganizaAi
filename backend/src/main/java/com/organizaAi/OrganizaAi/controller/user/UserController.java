package com.organizaAi.OrganizaAi.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/{id}")
    public String index() {
        return "Greetings from OrganizaAi!";
    }
}