<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Registration</title>
	</head>
	<body>
	 <c:if test = "${requestScope.registered}">
		<p>Registered successfully!</p>
	</c:if> 
	<form method="post">
         Username: <input type = "text" name = "username">
         <br />
         Password: <input type = "password" name = "pswd" />
         <input type = "submit" value = "Submit" />
      </form>
      <a href="/">Go back start</a>
	</body>
</html>