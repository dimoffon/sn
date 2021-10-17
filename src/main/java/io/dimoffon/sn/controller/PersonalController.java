package io.dimoffon.sn.controller;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.service.InterestService;
import io.dimoffon.sn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final UserService userService;
    private final InterestService interestService;

    @Autowired
    public PersonalController(final UserService userService,
                              final InterestService interestService) {
        this.userService = userService;
        this.interestService = interestService;
    }

    @ModelAttribute("friends")
    public List<User> listFriends(HttpServletRequest request) {
        Long userId = getUserId(request);
        return userService.getFriends(UserFilter.builder().id(userId).build());
    }

    @GetMapping("")
    public void personalPage(HttpServletRequest request, Model model) {
        Long userId = getUserId(request);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
    }

    private Long getUserId(final HttpServletRequest request) {
        return Long.valueOf(request.getSession().getAttribute("userId").toString());
    }

}
