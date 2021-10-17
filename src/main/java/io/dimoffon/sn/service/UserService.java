package io.dimoffon.sn.service;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void addUser(User user);

    User getUserById(Long userId);

    Collection<User> getUsers(UserFilter filter);

    List<User> getFriends(UserFilter filter);

    Collection<User> getStrangers(UserFilter filter);
}
