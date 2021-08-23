package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.AccessDeniedException;
import com.example.musicstoredemo.exception.InvalidInputException;
import com.example.musicstoredemo.exception.UserNotFoundException;
import com.example.musicstoredemo.model.access.Endpoint;
import com.example.musicstoredemo.model.user.User;
import com.example.musicstoredemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccessValidationForClient() {
        when(userRepositoryMock.findAll()).thenReturn(Collections.singletonList(new User(1, "aaa", "bbb", "client", "testtoken")));

        userService.validateAccess("testtoken", Endpoint.GET_GUITAR_CATALOGUE);

        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testAccessValidationForAdmin() {
        when(userRepositoryMock.findAll()).thenReturn(Collections.singletonList(new User(1, "aaa", "bbb", "admin", "testtoken")));

        userService.validateAccess("testtoken", Endpoint.POST_ADD_GUITAR);

        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testAccessViolation() {
        try {
            when(userRepositoryMock.findAll()).thenReturn(Collections.singletonList(new User(1, "aaa", "bbb", null, "testtoken")));

            userService.validateAccess("testtoken", Endpoint.POST_ADD_GUITAR);
        } catch (AccessDeniedException ade) {
            assertEquals("Access denied", ade.getMessage());
            verify(userRepositoryMock, times(1)).findAll();
        }

    }

    @Test
    public void testAccessValidationWithInvalidToken() {
        try {
            userService.validateAccess(" :> 2 a", Endpoint.GET_GUITAR_CATALOGUE);
        } catch (InvalidInputException iae) {
            assertEquals("Invalid input", iae.getMessage());
            verifyNoInteractions(userRepositoryMock);

        }
    }

    @Test
    public void testAccessValidationWithUnknownUser() {
        try {
            when(userRepositoryMock.findAll()
                    .stream()
                    .filter(u -> u.getAccessToken().equals(any(String.class)))
                    .findFirst()).thenThrow(new UserNotFoundException("No user found"));

            userService.validateAccess("aaa", Endpoint.GET_GUITAR_CATALOGUE);
        } catch (UserNotFoundException iae) {
            assertEquals("No user found", iae.getMessage());
            verify(userRepositoryMock, times(1)).findAll();
        }
    }

}
