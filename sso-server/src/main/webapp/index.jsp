<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
String service = request.getParameter("service");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
  <form action="/sso-server/user/login" method="post">
       用户名: <input type="text" name="username"/> <br>
       密码:  <input type="text" name="password"/> <br>
         <input type="hidden" name="service" value="<%=service%>"/>
  记住密码: <input type="checkbox" value="on" name="rememberMe" >
  <input type="submit" value="登录"/>
  </form>
</body>
</html>