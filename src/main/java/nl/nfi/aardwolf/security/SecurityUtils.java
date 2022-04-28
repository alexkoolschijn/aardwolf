/*
 * Copyright (c) 2021 Netherlands Forensic Institute
 * All rights reserved.
 */

package nl.nfi.aardwolf.security;

import java.security.Principal;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;

/**
 * Contains a number of security-related utility methods.
 */
public final class SecurityUtils {

    private SecurityUtils() {
        // Util methods only
    }

    /**
     * Determines if the supplied request is an internal Vaadin request
     * 
     * @param request the {@link HttpServletRequest} to inspect
     * @return true if the request is a Vaadin request, false otherwise.
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) { 
        // Get the value of the Request Type parameter. The ApplicationContants.REQUEST_TYPE_PARAMETER contains 
        // the name of the request parameter that Vaadin uses to pass the request type.
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        
        // We have a Vaadin request if the parameter value was found and contains a value that matches 
        // one of the Request Type values used in Vaadin.
        return parameterValue != null
            && Stream.of(HandlerHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    /**
     * @return true if we have a valid authentication token
     */
    static boolean isUserLoggedIn() { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
            && !(authentication instanceof AnonymousAuthenticationToken)
            && authentication.isAuthenticated();
    }
    
    /**
     * Gets the Principal object for the current user
     * @return a {@link Principal} for the current user.
     * @throws SecurityException if no user was logged in
     */
    public static UserDetails getUserDetails() {
        if(isUserLoggedIn()) {
            return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        throw new SecurityException("No user is currently logged in");
    }

}