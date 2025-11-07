package com.bus.web;

import com.bus.dao.BookingDAO;
import com.bus.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookingServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        User u = (User) req.getSession().getAttribute("user");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // ✅ 1. 예약 생성
            if ("/create".equals(path)) {
                int tripId = Integer.parseInt(req.getParameter("tripId"));
                bookingDAO.create(u.id, tripId);
                resp.sendRedirect(req.getContextPath() + "/mybookings.jsp");
            }

            // ✅ 2. 예약 취소 (좌석 +1, 상태 CANCELLED)
            else if ("/cancel".equals(path)) {
                int bookingId = Integer.parseInt(req.getParameter("bookingId"));
                bookingDAO.cancel(bookingId, u.id);
                resp.sendRedirect(req.getContextPath() + "/mybookings.jsp");
            }

            // ✅ 3. 개별 취소 내역 삭제 (CANCELLED만 삭제)
            else if ("/delete".equals(path)) {
                int bookingId = Integer.parseInt(req.getParameter("bookingId"));
                bookingDAO.deleteIfCancelled(bookingId, u.id);
                resp.sendRedirect(req.getContextPath() + "/mybookings.jsp?all=1");
            }

            // ✅ 4. 취소 내역 모두 삭제
            else if ("/purgeCancelled".equals(path)) {
                bookingDAO.purgeCancelledByUser(u.id);
                resp.sendRedirect(req.getContextPath() + "/mybookings.jsp?all=1");
            }

            // ✅ 기타 잘못된 경로 처리
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
