<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<jsp:include page="snippets/head.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>
<body>
<h1>Login</h1>
<form method="post" action="login">
    <input type="text" name="email">
    <input type="password" name="password">
    <input type="submit" value="Login">
</form>
<jsp:include page="snippets/show-error.jsp"/>
</body>
</html>




