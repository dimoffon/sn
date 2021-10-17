package io.dimoffon.sn.controller;

import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.service.InterestService;
import io.dimoffon.sn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    final UserService userService;
    final InterestService interestService;

    @Autowired
    public UserController(final UserService userService,
                          final InterestService interestService) {
        this.userService = userService;
        this.interestService = interestService;
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", User.builder().build());
        model.addAttribute("allInterests", interestService.getInterests());
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.addUser(user);
        } catch (Exception e) {
            ObjectError error = new ObjectError("globalError", e.getMessage());
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "register";
        }
        return "redirect:/index";
    }

}
