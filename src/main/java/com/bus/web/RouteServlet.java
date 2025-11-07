// RouteServlet.java
package com.bus.web;
import com.bus.dao.FavoriteDAO; import com.bus.dao.RouteDAO; import com.bus.model.Route; import com.bus.model.User;
import javax.servlet.*; import javax.servlet.http.*; import java.io.IOException; import java.util.List;

public class RouteServlet extends HttpServlet {
    private final RouteDAO routeDAO=new RouteDAO();
    private final FavoriteDAO favDAO=new FavoriteDAO();

    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        String dir = "AM"; // 기본 등교
        if("PM".equals(req.getParameter("dir"))) dir="PM";
        try{
            List<Route> routes = routeDAO.listByDirection(dir);
            req.setAttribute("routes", routes);
            req.setAttribute("dir", dir);
            req.getRequestDispatcher("/routes.jsp").forward(req,resp);
        }catch(Exception e){ throw new ServletException(e); }
    }

    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException{
        String path = req.getPathInfo()==null? "": req.getPathInfo();
        User u=(User)req.getSession().getAttribute("user");
        if(u==null){ resp.sendRedirect(req.getContextPath()+"/login.jsp"); return; }
        try{
            if("/fav".equals(path)){
                int routeId = Integer.parseInt(req.getParameter("routeId"));
                favDAO.add(u.id, routeId);
                // **주의**: 바로 홈으로 가지 않음. 사용자 흐름 유지
                resp.sendRedirect(req.getHeader("Referer")); // 현재 화면 유지
            } else if("/unfav".equals(path)){
                int routeId = Integer.parseInt(req.getParameter("routeId"));
                favDAO.remove(u.id, routeId);
                resp.sendRedirect(req.getHeader("Referer"));
            }
        }catch(Exception e){ throw new ServletException(e); }
    }
}
