<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${applicationScope.loggedIn == 'false'}">
		<c:if test="${requestScope.logAttempt == 'true'}">
			<p>Wrong username or password</p>
		</c:if>
		<form method="post">
		<p>Login</p>
         Username: <input type = "text" name = "username">
         <br />
         Password: <input type = "password" name = "pswd" />
         <button type = "submit">Log in</button>         
      	</form>
	</c:if>
	<c:if test="${applicationScope.loggedIn == 'true'}">
		<form method="post" name = "update">
		<p>Update details:</p>
         Username: <input type = "text" name = "username">
         <br />
         Password: <input type = "password" name = "pswd" />
         <p><button type = "submit">Update</button></p>
      	</form>
      	<form method="post" name = "delete">
      	<button type = "submit" name = "delete">Delete account</button>
      	</form>	
      	<form method="post" name = "logout">
      	<button type = "submit" name = "logout">Log out</button>
      	</form>	
      </c:if>  
      <form action ="/">
      	<button type = "submit">Go back to start</button>
      </form>
</body>
</html>