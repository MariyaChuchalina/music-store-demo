package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.InvalidInputException;
import com.example.musicstoredemo.exception.UserNotFoundException;
import com.example.musicstoredemo.model.access.Endpoint;
import com.example.musicstoredemo.model.access.EndpointAccess;
import com.example.musicstoredemo.model.access.ProxyEndpointAccess;
import com.example.musicstoredemo.model.user.User;
import com.example.musicstoredemo.model.user.UserRole;
import com.example.musicstoredemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String ACCESS_TOKEN_REGEX = "^[a-zA-Z0-9]+$";

    @Autowired
    private UserRepository userRepository;

    private EndpointAccess endpointAccess;

    public void validateAccess(String accessToken, Endpoint endpoint) {
        validateInput(accessToken);
        User user = getUserByAccessToken(accessToken);

        endpointAccess = new ProxyEndpointAccess(!user.getRole().isBlank() ? user.getRole() : UserRole.UNKNOWN.name());
        endpointAccess.grantEndpointAccess(endpoint);
    }

    private User getUserByAccessToken(String accessToken) {
        return Optional.ofNullable(userRepository.findAll()
                .stream()
                .filter(u -> u.getAccessToken().equals(accessToken))
                .findFirst()).get()
                .orElseThrow(() -> new UserNotFoundException("No user found"));
    }

    private void validateInput(String accessToken) {
        if (!accessToken.matches(ACCESS_TOKEN_REGEX)) {
            throw new InvalidInputException("Invalid input");
        }
    }

}
