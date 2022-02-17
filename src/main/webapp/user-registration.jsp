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
	 <c:if test = "${requestScope.registered == 'true'}">
		<p>Registered successfully!</p>
	</c:if> 
	<c:if test = "${requestScope.registered == 'false'}">
		<p>User already exists!</p>
	</c:if>
	<form method="post">
         Username: <input type = "text" name = "username" placeholder = "Enter Username" required />
         <br />
         Password: <input type = "password" name = "pswd" placeholder = "Enter Password" required />
         <br /><br />
         <button type = "submit" value = "Submit">Register</button>
      </form>
	<br />
<form action ="/">
      	<button type = "submit">Go back to start</button>
      </form>	</body>
</html>