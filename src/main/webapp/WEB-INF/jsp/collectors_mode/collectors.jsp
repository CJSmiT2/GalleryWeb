<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Collectors list</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <ul class="list-group">
            <c:forEach var="collector" items="${collectors}">
                <li class="list-group-item">
                    <a href="${pageContext.request.contextPath}/collectors_mode/list?nick_name=${collector.getNickName()}">${collector.getNickName()}</a>
                </li>
            </c:forEach>
        </ul>


        <jsp:include page="../footer.jsp" />
    </body>
</html>
