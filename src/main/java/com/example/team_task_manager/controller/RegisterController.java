
package com.example.team_task_manager.controller;

import com.example.team_task_manager.dto.UserCreateRequest;
import com.example.team_task_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userCreateRequest", new UserCreateRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("userCreateRequest")
            UserCreateRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "入力内容を確認してください。");
            return "register";
        }

        try {
            userService.create(request);
            return "redirect:/login?registered";
        } catch (org.springframework.web.server.ResponseStatusException e) {
            if (e.getStatusCode().value() == 409) {
                model.addAttribute(
                    "error",
                    "このメールアドレスは既に登録されています。"
                );
                return "register";
            }
            throw e;
        }
    }
}