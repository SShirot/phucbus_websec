package com.phucprod.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ParameterFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String priceParam = req.getParameter("price");

        if (priceParam != null) {
            try {
                int price = Integer.parseInt(priceParam);
                if (price > 0) {
                    chain.doFilter(request, response);
                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price parameter.");
                }
            } catch (NumberFormatException e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price parameter.");
            }
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing price parameter.");
        }
    }

    public void destroy() {}
}

