package com.example.yj_userservice.service;


import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import com.example.yj_userservice.model.Users;
import com.example.yj_userservice.model.entity.UsersEntity;
import com.example.yj_userservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    public void 회원가입테스트() {
        // Given
        String email = "test@example.com";
        String password = "testPassword";

        // When
        Users user = userService.join(email, password);

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertTrue(passwordEncoder.matches(password, user.getMemberPw()));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 이메일")
    public void 회원가입_실패_이미_존재하는_이메일() {
        // Given
        String email = "test@example.com";
        String password = "testPassword";

        // Mock UserRepository to return a user when findByEmail is called with the specific email
        UsersEntity existingUser = UsersEntity.of(email, passwordEncoder.encode(password));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // When
        ProchatException exception = assertThrows(ProchatException.class, () -> userService.join(email, password));

        // Then
        assertEquals(ErrorCode.DUPLICATED_MEMBER_ID, exception.getErrorCode());


        // Verify that userRepository.findByEmail was called once with the specific email
        verify(userRepository, times(1)).findByEmail(email);
    }
    @DisplayName("로그인테스트")
    @Test
    public void 로그인테스트() {
        // Given
        String email = "test@example.com";
        String password = "testPassword";
        userService.join(email, password);

        // When
        Users loadedUser = userService.loadUserByUsername(email);

        // Then
        assertNotNull(loadedUser);
        assertEquals(email, loadedUser.getEmail());
    }

    @Test
    public void 프로필업데이트() {
        // Given
        String email = "test@example.com";
        String newName = "New Name";
        String newPhone = "New Phone";
        String newAddress = "New Address";
        String newProfile = "New Profile";
        String newImage = "New Image";
        userService.join(email, "testPassword");

        // When
        Users updatedUser = userService.updateProfile(email, newName, newPhone, newAddress, newProfile, newImage);

        // Then
        assertNotNull(updatedUser);
        assertEquals(newName, updatedUser.getName());
        assertEquals(newPhone, updatedUser.getPhone());
        assertEquals(newAddress, updatedUser.getAddress());
        assertEquals(newProfile, updatedUser.getProfile());
        assertEquals(newImage, updatedUser.getImage());
    }
    @DisplayName("프로필업데이트 실패")
    @Test
    public void testUpdateProfile_UserNotFound_Failure() {
        // Given
        String email = "nonExistingUser";
        String newName = "New Name";
        String newPhone = "New Phone";
        String newAddress = "New Address";
        String newProfile = "New Profile";
        String newImage = "New Image";

        // When, Then
        assertThrows(ProchatException.class, () -> userService.updateProfile(email, newName, newPhone, newAddress, newProfile, newImage),
                "프로필 업데이트 시 존재하지 않는 유저는 예외를 던져야 합니다.");
    }
}
