package com.phucprod.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ClickjackFilter implements Filter {
    private String mode = "SAMEORIGIN";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("X-FRAME-OPTIONS", mode);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String configMode = filterConfig.getInitParameter("mode");
        if (configMode != null) {
            mode = configMode;
        }
    }

    @Override
    public void destroy() {
        // No additional resource cleanup needed
    }
}