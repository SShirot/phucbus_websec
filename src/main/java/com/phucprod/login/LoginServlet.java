package com.phucprod.login;

import com.phucprod.csrfutil.CSRFUtil;
import com.phucprod.database_query.LoginAuth;
import struct.loginauth;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_email = request.getParameter("uemail");
        String user_pass = request.getParameter("pass");
        String submittedCsrfToken = request.getParameter("csrfToken");
        HttpSession session = request.getSession();
        String sessionCsrfToken = (String) session.getAttribute("csrfToken");

        // Validate CSRF token
        if (sessionCsrfToken == null || !sessionCsrfToken.equals(submittedCsrfToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token invalid");
            return;
        }

        try {
            LoginAuth login = new LoginAuth();
            loginauth LoginAttempt = login.Query(user_email, user_pass);
            if (LoginAttempt.CheckAuth == 1) {
                // Authentication successful, set user attributes
                session.setAttribute("userType", LoginAttempt.CheckAdmin == 1 ? "admin" : "user");
                session.setAttribute("name", LoginAttempt.UserName);

                String cookieValue = "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly; Secure; SameSite=Strict";

                if (!request.isSecure()) {
                    cookieValue = cookieValue.replace("Secure; ", "");
                }
                response.setHeader("Set-Cookie", cookieValue);

                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.setAttribute("status", "failed");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String csrfToken = CSRFUtil.generateCSRFToken();
        session.setAttribute("csrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
