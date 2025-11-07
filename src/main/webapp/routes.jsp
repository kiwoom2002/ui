<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.bus.model.Route" %>
<%
  List<Route> routes = (List<Route>)request.getAttribute("routes");
  String dir = (String)request.getAttribute("dir");
%>
<html><head><meta charset="UTF-8"><title>노선조회</title>
<style>
.wrap{max-width:800px;margin:24px auto;font-family:system-ui}
.item{display:flex;justify-content:space-between;align-items:center;padding:10px;border-bottom:1px solid #eee}
.btn{background:#111;color:#fff;border:none;padding:8px 12px;border-radius:8px;text-decoration:none}
</style></head>
<body>
<div class="wrap">
  <h2>노선조회</h2>
  <form method="get" action="routes">
    <label><input type="radio" name="dir" value="AM" <%= "AM".equals(dir)?"checked":"" %>> 등교</label>
    <label><input type="radio" name="dir" value="PM" <%= "PM".equals(dir)?"checked":"" %>> 하교</label>
    <button class="btn">조회</button>
  </form>
  <hr>
  <% for(Route r: routes){ %>
    <div class="item">
      <div><b><%= r.name %></b></div>
      <div>
        <a class="btn" href="trips?routeId=<%= r.id %>">조회 시작(오늘 이후)</a>
        <form method="post" action="routes/fav" style="display:inline">
          <input type="hidden" name="routeId" value="<%= r.id %>">
          <button>즐겨찾기</button>
        </form>
      </div>
    </div>
  <% } %>
</div>
</body></html>
