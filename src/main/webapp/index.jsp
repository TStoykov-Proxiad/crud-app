<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test = "${sessionScope.username != null}">
<p>Hi there, ${sessionScope.username}</p>
</c:if>
<form action ="/list-users">
      	<button type = "submit">Show all registered users</button>
</form>
<form action ="/register">
      	<button type = "submit">Create a new account</button>
</form>
<form action ="/update">
      	<button type = "submit">Update profile</button>
</form>
</body>
</html>