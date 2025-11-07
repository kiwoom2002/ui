<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
body{font-family:system-ui;margin:0;background:#f6f6f6}
.wrap{max-width:540px;margin:40px auto;background:#fff;padding:24px;border-radius:10px}
h1{margin:0 0 16px}
input,button{width:100%;padding:12px;margin:8px 0;border:1px solid #ccc;border-radius:8px}
.btn{background:#111;color:#fff;border:none}
a.btn-link{display:block;text-align:center;padding:10px;background:#ddd;border-radius:8px;text-decoration:none;margin-top:8px;color:#000}
</style>
</head>
<body>
<div class="wrap">
  <h1>회원로그인</h1>
  <form method="post" action="<%=request.getContextPath()%>/auth/login">
    <input name="username" placeholder="아이디" required>
    <input name="password" type="password" placeholder="비밀번호" required>
    <button class="btn">로그인</button>
  </form>
  <!-- ✅ 회원가입 페이지 이동 -->
  <a class="btn-link" href="<%=request.getContextPath()%>/auth/register">회원가입</a>
  <p style="color:#d00"><%= request.getAttribute("error")==null?"":request.getAttribute("error") %></p>
</div>
</body>
</html>
