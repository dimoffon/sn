package io.dimoffon.sn.service;

import io.dimoffon.sn.dto.UserFilter;
import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.repository.InterestRepository;
import io.dimoffon.sn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final InterestRepository interestRepository;
    final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final InterestRepository interestRepository,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(final User user) {
        User userCopy = user.toBuilder().build();
        userCopy.setPassword(passwordEncoder.encode(userCopy.getPassword()));
        userRepository.addUser(userCopy);
        userRepository.addUserRole(userCopy, "USER");
        User createdUser = userRepository.getUsers(UserFilter.builder().username(user.getUsername()).build()).stream().findFirst().get();
        user.setId(createdUser.getId());
        interestRepository.removeInterests(createdUser);
        interestRepository.addInterests(user);
    }

    @Override
    public User getUserById(final Long userId) {
        User u = userRepository.getUserById(userId);
        u.setInterests(interestRepository.getUserInterests(u));
        return u;
    }

    @Override
    public List<User> getUsers(final UserFilter filter) {
        List<User> users = userRepository.getUsers(filter);
        users.forEach(u -> u.setInterests(interestRepository.getUserInterests(u)));
        return users;
    }

    @Override
    public List<User> getFriends(final UserFilter filter) {
        return userRepository.getFriends(filter);
    }

    @Override
    public List<User> getStrangers(final UserFilter filter) {
        return userRepository.getStrangers(filter);
    }

    @Override
    public void addFriend(final Long userId, final Long friendId) {
        userRepository.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(final Long userId, final Long friendId) {
        userRepository.removeFriend(userId, friendId);
    }
}
