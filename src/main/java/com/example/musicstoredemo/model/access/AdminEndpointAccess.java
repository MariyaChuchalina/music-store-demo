package com.example.musicstoredemo.model.access;

import com.example.musicstoredemo.exception.AccessDeniedException;
import com.example.musicstoredemo.model.user.UserRole;

public class AdminEndpointAccess implements EndpointAccess {

    @Override
    public void grantEndpointAccess(Endpoint endpoint) {
        if (endpoint.getMinimalRole() != UserRole.CLIENT && endpoint.getMinimalRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("Access denied");
        }
    }

}
