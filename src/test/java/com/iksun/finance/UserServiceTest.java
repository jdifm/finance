package com.iksun.finance;

import com.iksun.finance.user.dto.User;
import com.iksun.finance.user.respository.UserRepository;
import com.iksun.finance.user.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SignService signService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_Signup_SUCCESS() {
        //given
        User user = new User();
        user.setId("id");
        user.setPassword("password");


        when(userRepository.findById(any())).thenReturn(Optional.empty());
        //when
        signService.signUp(user);

        //then
        verify(userRepository, times(1)).save(any());

    }

    @Test
    public void test_Signup_FAIL() {
        //given
        User user = new User();
        user.setId("id");
        user.setPassword("password");

        //then
        thrown.expect(RuntimeException.class);
        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        signService.signUp(user);
    }

    @Test
    public void test_Signin_SUCCESS() {
        //given
        User user = new User();
        user.setId("id");
        user.setPassword("password");

        //then
        thrown.expect(RuntimeException.class);
        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        signService.signIn(user);
    }
}
