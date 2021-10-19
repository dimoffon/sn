package io.dimoffon.sn.service;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    User getUserById(Long userId);

    List<User> getUsers(UserFilter filter);

    List<User> getFriends(UserFilter filter);

    List<User> getStrangers(UserFilter filter);

    void addFriend(Long userId, Long friendId);

    void removeFriend(Long userId, Long friendId);
}
