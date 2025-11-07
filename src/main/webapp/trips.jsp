<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.bus.model.Trip" %>
<%
 List<Trip> trips=(List<Trip>)request.getAttribute("trips");
%>
<html><head><meta charset="UTF-8"><title>배차조회</title>
<style>
.wrap{max-width:900px;margin:24px auto;font-family:system-ui}
table{width:100%;border-collapse:collapse}
th,td{border-bottom:1px solid #eee;padding:10px;text-align:center}
.badge{background:#aaa;color:#fff;border-radius:6px;padding:2px 8px}
.btn{background:#b31d1d;color:#fff;border:none;border-radius:6px;padding:6px 10px}
</style></head>
<body>
<div class="wrap">
  <div style="display:flex;justify-content:space-between;align-items:center">
    <h2>배차조회 / 선택</h2>
    <a href="home.jsp">홈</a>
  </div>
  <table>
    <tr><th>출발일시</th><th>구분</th><th>요금</th><th>잔여</th><th>예약</th></tr>
    <% for(Trip t: trips){ %>
      <tr>
        <td><%= t.date %> <br> <%= t.time.substring(0,5) %></td>
        <td><span class="badge"><%= t.kind %></span></td>
        <td><%= String.format("%,d원", t.fare) %></td>
        <td><%= t.seatsLeft %></td>
        <td>
          <form method="post" action="bookings/create">
            <input type="hidden" name="tripId" value="<%= t.id %>">
            <button class="btn">예약</button>
          </form>
          <form method="post" action="routes/fav" style="margin-top:6px">
            <input type="hidden" name="routeId" value="<%= request.getParameter("routeId") %>">
            <button>즐겨찾기</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
</div>
</body></html>
