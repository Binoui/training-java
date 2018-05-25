package com.excilys.formation.cdb.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

class NoRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url)
            throws IOException {
    }
}