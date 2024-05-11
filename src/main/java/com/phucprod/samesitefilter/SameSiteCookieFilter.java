// package com.phucprod.samesitefilter;

// import javax.servlet.*;
// import javax.servlet.http.*;
// import javax.servlet.annotation.WebFilter;

// import java.io.IOException;
// import java.net.http.HttpHeaders;
// import java.util.*;

// import org.springframework.http.HttpHeaders;

// public class SameSiteCookieFilter implements javax.servlet.Filter {

//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {

//     }

//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//         chain.doFilter(request, response);
//         addSameSiteCookieAttribute((HttpServletResponse) response); // add SameSite=strict cookie attribute
//     }

//     private void addSameSiteCookieAttribute(HttpServletResponse response) {
//         Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
//         boolean firstHeader = true;
//         for (String header : headers) { // there can be multiple Set-Cookie attributes
//             if (firstHeader) {
//                 response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
//                 firstHeader = false;
//                 continue;
//             }
//             response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
//         }
//     }

//     @Override
//     public void destroy() {

//     }

// }
