package com.phucprod.CSPFilter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CSPFilter", urlPatterns = "/*")
public class CSPFilter implements Filter {

    //public static final String POLICY = "default-src 'self'; script-src 'self'; img-src 'self' data:; style-src 'self'";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
        //    ((HttpServletResponse)response).setHeader("Content-Security-Policy", CSPFilter.POLICY);
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            ((HttpServletResponse)response).setHeader("X-Content-Type-Options", "nosniff");

        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

}