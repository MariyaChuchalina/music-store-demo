package com.example.musicstoredemo.model.access;

import com.example.musicstoredemo.exception.AccessDeniedException;
import com.example.musicstoredemo.model.user.UserRole;

public class ClientEndpointAccess implements EndpointAccess {

    @Override
    public void grantEndpointAccess(Endpoint endpoint) {
        if (endpoint.getMinimalRole() != UserRole.CLIENT) {
            throw new AccessDeniedException("Access denied");
        }
    }

}
