
package com.example.team_task_manager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, String> getCurrentUser(
            Authentication authentication) {

        return Map.of(
                "email", authentication.getName(),
                "message", "認証に成功しました。"
        );
    }
}