<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<jsp:include page="../snippets/head.jsp">
    <jsp:param name="title" value="Shops"/>
</jsp:include>
<body>
<h1>Shops</h1>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="shop" items="${shops}">
            <tr>
                <td>${shop.id}</td>
                <td><a href="shop?id=<c:out value="${shop.id}"/>">${shop.name}</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<h2>Add new shop</h2>
<form action="shops" method="post">
    <input type="text" name="name">
    <input type="submit" value="Add">
</form>
<jsp:include page="../snippets/show-info.jsp"/>
<jsp:include page="../snippets/show-error.jsp"/>
<jsp:include page="../snippets/to-profile.jsp"/>
<jsp:include page="../snippets/logout.jsp"/>
</body>
</html>
