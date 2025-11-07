// TripServlet.java
package com.bus.web;
import com.bus.dao.TripDAO; import com.bus.model.Trip; import javax.servlet.*; import javax.servlet.http.*;
import java.io.IOException; import java.sql.Date; import java.time.LocalDate; import java.util.List;

public class TripServlet extends HttpServlet {
    private final TripDAO tripDAO=new TripDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LocalDate today = LocalDate.now();
        Date from = Date.valueOf(today); // 항상 오늘부터
        try{
            List<Trip> trips = tripDAO.findTrips(routeId, from);
            req.setAttribute("trips", trips);
            req.getRequestDispatcher("/trips.jsp").forward(req,resp);
        }catch(Exception e){ throw new ServletException(e); }
    }
}
