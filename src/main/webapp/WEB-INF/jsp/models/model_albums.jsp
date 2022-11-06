<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Model ${photomodel.getInfo().getNickName()}</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <div class="container">
            <h4>${photomodel.getInfo().getNickName()}</h4>
            <c:forEach var="row" items="${rows}">
                <div class="row">
                    <c:forEach var="album" items="${row.get()}">
                        <div class="col-sm-4">
                            <div class="card" style="margin: 5px 0">
                                <img class="card-img-top" loading="lazy"
                                     src="${pageContext.request.contextPath}/img/${album.getAlias()}/thumb-${album.getPosterImageId()}">
                                <div class="card-body">
                                    <div>
                                        <span class="card-text">
                                            ${album.getTitle()}
                                        </span>
                                    </div>
                                    <div>
                                        <span class="card-text">
                                            <small>Created time: ${album.getUpdated()}</small>
                                        </span>
                                    </div>
                                    <div>
                                        <span class="card-text">
                                            <small>
                                                <i class="fas fa-image"></i>
                                                Hits: <span class="text-info">${album.getHits() / 1000}k</span>
                                                <i class="fas fa-eye"></i>
                                                Likes: <span class="text-warning">${album.getLikes()}</span>
                                                <i class="fas fa-heart"></i>
                                                Img. count: <span class="text-muted">${album.getImagesCount()}</span>
                                            </small>
                                        </span>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/album/${album.getAlias()}" class="btn btn-primary">view</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <br>
            </c:forEach>

        </div>

        <jsp:include page="../footer.jsp" />
    </body>
</html>
