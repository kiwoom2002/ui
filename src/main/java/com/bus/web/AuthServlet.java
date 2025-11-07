package com.bus.web;

import com.bus.dao.UserDAO;
import com.bus.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    // ✅ GET 요청 처리 (회원가입 페이지 열기)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        if ("/register".equals(path)) {
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    // ✅ POST 요청 처리 (로그인 / 회원가입 / 로그아웃)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        req.setCharacterEncoding("UTF-8");

        try {
            if ("/login".equals(path)) {
                String u = req.getParameter("username");
                String p = req.getParameter("password");
                User found = userDAO.findByUsername(u);

                if (found != null && found.password.equals(p)) {
                    req.getSession().setAttribute("user", new User(found.id, found.username));
                    resp.sendRedirect(req.getContextPath() + "/home.jsp");
                } else {
                    req.setAttribute("error", "아이디/비밀번호를 확인하세요.");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }

            } else if ("/register".equals(path)) {
                String u = req.getParameter("username");
                String p = req.getParameter("password");
                int id = userDAO.create(u, p);
                req.getSession().setAttribute("user", new User(id, u));
                resp.sendRedirect(req.getContextPath() + "/home.jsp");

            } else if ("/logout".equals(path)) {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
