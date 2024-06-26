package com.phucprod.login;

import com.phucprod.database_query.LoginAuth;
import com.phucprod.csrf.CSRFUtil;
import struct.loginauth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_email = request.getParameter("uemail");
        String user_pass = request.getParameter("   ");
        
        RequestDispatcher dispatcher = null;

        HttpSession session = request.getSession();

        String sessionCsrfToken = (String) session.getAttribute("csrfToken");

        String submittedCsrfToken = request.getParameter("csrfToken");

        // // Validate CSRF token
        if (sessionCsrfToken == null || !sessionCsrfToken.equals(submittedCsrfToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token invalid");
            return;
        }

        try {
            LoginAuth login = new LoginAuth();
            loginauth LoginAttempt = login.Query(user_email, user_pass);
            if (LoginAttempt.CheckAuth == 1) {
                session.setAttribute("admin", LoginAttempt.CheckAdmin == 1 ? "admin" : "user");
                session.setAttribute("name", LoginAttempt.UserName);
                
                // Authentication successful, set user attributes

                String cookieValue = "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly; Secure; SameSite=Strict";

                if (!request.isSecure()) {
                    cookieValue = cookieValue.replace("Secure; ", "");
                }
                                
                dispatcher = request.getRequestDispatcher("index.jsp");
                
                response.setHeader("Set-Cookie", cookieValue);

            } else {
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Giải pháp đưa ra là tạo 1 CSRF Token độc quyền đi kèm theo mỗi request

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String csrfToken = CSRFUtil.generateCSRFToken();

        session.setAttribute("csrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}