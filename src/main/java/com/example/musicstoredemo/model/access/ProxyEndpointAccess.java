package com.example.musicstoredemo.model.access;

import com.example.musicstoredemo.exception.AccessDeniedException;
import com.example.musicstoredemo.model.user.UserRole;

public class ProxyEndpointAccess implements EndpointAccess {

    private AdminEndpointAccess adminEndpointAccess;
    private ClientEndpointAccess clientEndpointAccess;
    private String userRole;

    public ProxyEndpointAccess(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public void grantEndpointAccess(Endpoint endpoint) {

        switch (UserRole.parse(userRole)) {
            case ADMIN: {
                adminEndpointAccess = new AdminEndpointAccess();
                adminEndpointAccess.grantEndpointAccess(endpoint);
                break;
            }
            case CLIENT: {
                clientEndpointAccess = new ClientEndpointAccess();
                clientEndpointAccess.grantEndpointAccess(endpoint);
                break;
            }
            default:
                throw new AccessDeniedException("Access denied");
        }
    }

}
