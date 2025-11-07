<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
body{font-family:system-ui;margin:0;background:#f6f6f6}
.wrap{max-width:540px;margin:40px auto;background:#fff;padding:24px;border-radius:10px}
h2{margin-bottom:16px}
input,button{width:100%;padding:12px;margin:8px 0;border:1px solid #ccc;border-radius:8px}
.btn{background:#111;color:#fff;border:none}
a.btn-link{display:block;text-align:center;padding:10px;background:#ddd;border-radius:8px;text-decoration:none;margin-top:8px;color:#000}
</style>
</head>
<body>
<div class="wrap">
  <h2>회원가입</h2>
  <!-- ✅ POST로 가입 요청 -->
  <form method="post" action="<%=request.getContextPath()%>/auth/register">
    <input name="username" placeholder="아이디" required>
    <input type="password" name="password" placeholder="비밀번호" required>
    <button class="btn">가입하기</button>
  </form>
  <a class="btn-link" href="<%=request.getContextPath()%>/login.jsp">로그인으로 돌아가기</a>
  <p style="color:#d00"><%= request.getAttribute("error")==null?"":request.getAttribute("error") %></p>
</div>
</body>
</html>
