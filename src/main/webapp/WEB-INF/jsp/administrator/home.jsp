<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="top_menu.jsp" />

        <div class="container">
            <h3>Admin</h3>

            <ul class="list-group">
                <c:forEach var="album" items="${albums}">
                    <li class="list-group-item">
                        <div class="row">
                            <div class="col-sm-1">
                                <c:if test="${album.getInfo().getPosterImageId() > 0}">
                                    <img class="card-img-top" 
                                         loading="lazy"
                                         src="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/thumb-${album.getInfo().getPosterImageId()}">
                                </c:if>
                            </div>
                            <div class="col-sm-7">
                                ${album.getInfo().getTitle()} / Created time: ${album.getInfo().getUpdated()}
                            </div>
                            <div class="col-sm-4">
                                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/admin_gallery/edit_album?alias=${album.getInfo().getAlias()}">Manage</a>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

    </body>
</html>
