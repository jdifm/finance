package com.iksun.finance.user.service;

import com.iksun.finance.user.dto.User;
import com.iksun.finance.user.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByIdAndpassword(String userId, String password) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }

            return null;
        }
        return null;
    }
    public User getUserByToken(String token) {
        return userRepository.findFirstByTokenIs(token);
    }
}
