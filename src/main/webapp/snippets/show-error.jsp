<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
