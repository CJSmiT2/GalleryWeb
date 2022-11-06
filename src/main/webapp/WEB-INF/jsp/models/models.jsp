<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Models list</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <ul class="list-group">
            <c:forEach var="photomodel" items="${photomodels}">
                <li class="list-group-item">
                    <a href="${pageContext.request.contextPath}/model/${photomodel.getAlias()}">${photomodel.getNickName()}</a>
                    <c:forEach var="id" items="${photomodel.getAlbumsIds()}">
                        ${id},
                    </c:forEach>
                </li>
            </c:forEach>
        </ul>


        <jsp:include page="../footer.jsp" />
    </body>
</html>
