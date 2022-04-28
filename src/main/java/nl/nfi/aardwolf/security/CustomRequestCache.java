/*
 * Copyright (c) 2021 Netherlands Forensic Institute
 * All rights reserved.
 */

package nl.nfi.aardwolf.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * Extension of the HttpSessionRequestCache to ensure that framework-internal requests are not cached.
 * The purpose of this cache is to save unauthenticated requests so we can redirect the user to the page 
 * they were trying to access once they’re logged in.
 */
class CustomRequestCache extends HttpSessionRequestCache {

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) { 
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }

}