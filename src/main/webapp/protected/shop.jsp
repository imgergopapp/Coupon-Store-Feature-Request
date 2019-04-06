<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <jsp:include page="../snippets/head.jsp">
        <jsp:param name="title" value="Shop"/>
    </jsp:include>
    <body>
        <h1>Shop</h1>
        <c:if test="${empty error}">
            <p>ID: ${shop.id}</p>
            <p>Name: ${shop.name}</p>
            <table border="1" border-color="white">
                <tr>
                    <th>Coupon's name</th>
                    <th>Percentage</th>
                </tr>
                    <c:forEach var="coupon" items="${myCouponsByStore}">
                        <tr>
                        <td>${coupon.name}</td>
                        <td>${coupon.percentage}</td>
                        </tr>
                    </c:forEach>
            </table>
        </c:if>
        <jsp:include page="../snippets/show-error.jsp"/>
        <jsp:include page="../snippets/to-profile.jsp"/>
        <jsp:include page="../snippets/logout.jsp"/>
    </body>
</html>
