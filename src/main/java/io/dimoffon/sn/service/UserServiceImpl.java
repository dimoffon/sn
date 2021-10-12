package io.dimoffon.sn.service;

import io.dimoffon.sn.entity.User;
import io.dimoffon.sn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(final User user) {
        User userCopy = user.toBuilder().build();
        userCopy.setPassword(passwordEncoder.encode(userCopy.getPassword()));
        userRepository.addUser(userCopy);
        userRepository.addUserRole(userCopy, "USER");
    }
}
