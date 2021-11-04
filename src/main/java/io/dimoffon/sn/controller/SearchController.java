package io.dimoffon.sn.controller;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.service.InterestService;
import io.dimoffon.sn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    final UserService userService;
    final InterestService interestService;

    @Autowired
    public SearchController(final UserService userService,
                          final InterestService interestService) {
        this.userService = userService;
        this.interestService = interestService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> search(
            @RequestParam(required = false) final String firstName,
            @RequestParam(required = false) final String lastName) {
        UserFilter filter = UserFilter.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return userService.getUsers(filter);
    }

}
