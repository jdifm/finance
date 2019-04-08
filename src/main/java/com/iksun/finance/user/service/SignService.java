package com.iksun.finance.user.service;

import com.iksun.finance.user.dto.User;
import com.iksun.finance.user.respository.UserRepository;
import com.iksun.finance.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
public class SignService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signUp(User user) {
        Optional<User> existUser = userRepository.findById(user.getId());
        if (existUser.isPresent()) {
            throw new RuntimeException("Exist User Id");
        }

        user.setPassword(PasswordUtil.encrypt(user.getPassword())); // password 암호화
        userRepository.save(user);
    }

    @Transactional
    public String signIn(User user) {
        Optional<User> existUserOptional = userRepository.findById(user.getId());

        if (existUserOptional.isPresent() == false) {
            throw new RuntimeException("Can't find user");
        }

        if (user.getPassword().equals(PasswordUtil.encrypt(user.getPassword()))) {
            throw new RuntimeException("Password Fail");
        }

        User existUser = existUserOptional.get();
        // token 생성
        existUser.setToken(TokenService.makeToken(user));
        userRepository.save(existUser);

        return existUser.getToken();
        // 성공? 실패? 확인

    }
}
