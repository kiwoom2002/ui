<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.bus.dao.BookingDAO,com.bus.model.*" %>
<%
 User user=(User)session.getAttribute("user");
 if(user==null){ response.sendRedirect("login.jsp"); return; }
 boolean showAll = "1".equals(request.getParameter("all"));
 BookingDAO dao=new BookingDAO();
 List<Booking> list=dao.listByUser(user.id, showAll);
 String base = request.getContextPath();
%>
<html><head><meta charset="UTF-8"><title>내 예약</title>
<style>
.wrap{max-width:900px;margin:24px auto;font-family:system-ui}
table{width:100%;border-collapse:collapse}
th,td{border-bottom:1px solid #eee;padding:12px 10px;text-align:center}
.btn{padding:6px 10px;border-radius:6px;border:none}
.tools{display:flex;gap:12px;align-items:center}
a.link{color:#5a2ec2;text-decoration:none}
</style></head>
<body>
<div class="wrap">
  <div style="display:flex;justify-content:space-between;align-items:center">
    <h2>내 예약</h2>
    <div class="tools">
      <% if(showAll){ %>
        <form method="post" action="bookings/purgeCancelled" onsubmit="return confirm('취소 내역을 모두 삭제할까요?');">
          <button class="btn" type="submit">취소 내역 모두 삭제</button>
        </form>
        <a class="link" href="<%=base%>/mybookings.jsp">CONFIRMED만 보기</a>
      <% } else { %>
        <a class="link" href="<%=base%>/mybookings.jsp?all=1">취소 포함 보기</a>
      <% } %>
      <a class="link" href="<%=base%>/home.jsp">홈</a>
    </div>
  </div>

  <table>
    <tr><th>노선</th><th>출발일시</th><th>요금</th><th>상태</th><th>동작</th></tr>
    <% for(Booking b : list){ %>
      <tr>
        <td><%= b.routeName %></td>
        <td><%= b.date %> <%= b.time.substring(0,5) %></td>
        <td><%= String.format("%,d원", b.fare) %></td>
        <td><%= b.status %></td>
        <td>
          <% if("CONFIRMED".equals(b.status)){ %>
            <form method="post" action="bookings/cancel" style="display:inline">
              <input type="hidden" name="bookingId" value="<%= b.id %>">
              <button class="btn">취소</button>
            </form>
          <% } else { %>
            <!-- CANCELLED 행에는 삭제 버튼 표시 -->
            <form method="post" action="bookings/delete" style="display:inline" 
                  onsubmit="return confirm('이 취소 내역을 삭제할까요?');">
              <input type="hidden" name="bookingId" value="<%= b.id %>">
              <button class="btn">삭제</button>
            </form>
          <% } %>
        </td>
      </tr>
    <% } %>
    <% if(list.isEmpty()){ %>
      <tr><td colspan="5"><%= showAll ? "표시할 예약이 없습니다." : "확정된 예약이 없습니다." %></td></tr>
    <% } %>
  </table>
</div>
</body></html>
