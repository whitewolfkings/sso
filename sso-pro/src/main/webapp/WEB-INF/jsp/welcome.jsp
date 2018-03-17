<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pro List</title>
</head>
<body>
<%
String username = (String) request.getSession().getAttribute("username");
%>
<%=username %>, welcome to Pro system!!!
</body>
</html>