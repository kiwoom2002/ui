<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bus.dao.FavoriteDAO,com.bus.model.*,java.util.*" %>
<%
 User user=(User)session.getAttribute("user");
 if(user==null){ response.sendRedirect("login.jsp"); return; }
 FavoriteDAO fdao=new FavoriteDAO();
 List<Route> favAM=fdao.list(user.id,"AM");
 List<Route> favPM=fdao.list(user.id,"PM");
%>
<html><head><meta charset="UTF-8"><title>홈</title>
<style>
.container{max-width:900px;margin:24px auto;font-family:system-ui}
.grid{display:grid;grid-template-columns:1fr 1fr;gap:20px}
.card{background:#fff;border-radius:12px;box-shadow:0 2px 8px rgba(0,0,0,.06);padding:16px}
a.btn,button.btn{background:#111;color:#fff;padding:10px 14px;border-radius:8px;text-decoration:none;border:none}
.badge{background:#eee;padding:2px 8px;border-radius:10px}
</style></head>
<body>
<div class="container">
  <div style="display:flex;justify-content:space-between;align-items:center">
    <h2>노선조회</h2>
    <form method="post" action="auth/logout"><button class="btn">로그아웃</button></form>
  </div>

  <div class="grid">
    <div class="card">
      <h3>즐겨찾기(등교)</h3>
      <ul>
        <% for(Route r : favAM){ %>
          <li><span class="badge">등교</span> <%= r.name %>
            <a class="btn" href="trips?routeId=<%=r.id%>">예약</a>
            <form style="display:inline" method="post" action="routes/unfav">
              <input type="hidden" name="routeId" value="<%=r.id%>"><button>즐겨찾기 해제</button>
            </form>
          </li>
        <% } %>
        <% if(favAM.isEmpty()){ %><li>즐겨찾기 없음</li><% } %>
      </ul>
    </div>

    <div class="card">
      <h3>즐겨찾기(하교)</h3>
      <ul>
        <% for(Route r : favPM){ %>
          <li><span class="badge">하교</span> <%= r.name %>
            <a class="btn" href="trips?routeId=<%=r.id%>">예약</a>
            <form style="display:inline" method="post" action="routes/unfav">
              <input type="hidden" name="routeId" value="<%=r.id%>"><button>즐겨찾기 해제</button>
            </form>
          </li>
        <% } %>
        <% if(favPM.isEmpty()){ %><li>즐겨찾기 없음</li><% } %>
      </ul>
    </div>

    <div class="card">
      <h3>노선 선택 후 조회</h3>
      <form method="get" action="routes">
        <label><input type="radio" name="dir" value="AM" checked> 등교</label>
        <label><input type="radio" name="dir" value="PM"> 하교</label>
        <button class="btn">노선목록 보기</button>
      </form>
    </div>

    <div class="card">
      <h3>내 예약</h3>
      <a class="btn" href="mybookings.jsp">예약 조회 / 취소</a>
    </div>
  </div>
</div>
</body></html>
