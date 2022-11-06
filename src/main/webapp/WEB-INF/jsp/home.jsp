<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${frontEndData.getSiteName()}</title>
        <jsp:include page="head.jsp" />
    </head>
    <body>

        <jsp:include page="top_menu.jsp" />

        <div class="container">
            
            <div class="px-4 pt-5 my-5 text-center border-bottom">
                ${homePageMainText}
            </div>

            <h4>Latest albums:</h4>
            <c:forEach var="row" items="${rows}">
                <div class="row">
                    <c:forEach var="album" items="${row.get()}">
                        <div class="col-sm-4">
                            <div class="card" style="margin: 5px 0">
                                <img class="card-img-top" 
                                     loading="lazy"
                                     src="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/thumb-${album.getInfo().getPosterImageId()}">
                                <div class="card-body">
                                    <div>
                                        <span class="card-text">
                                            ${album.getInfo().getTitle()}
                                        </span>
                                    </div>
                                    <div>
                                        <span class="card-text">
                                            <small>Created time: ${album.getInfo().getUpdated()}</small>
                                        </span>
                                    </div>
                                    <div>
                                        <span class="card-text">
                                            <small>
                                                <i class="fas fa-image"></i>
                                                Hits: <span class="text-info">${album.getInfo().getHitsInThousends()} k</span>
                                                <i class="fas fa-eye"></i>
                                                Likes: <span class="text-warning">${album.getInfo().getLikes()}</span>
                                                <i class="fas fa-heart"></i>
                                                Img. count: <span class="text-muted">${album.getInfo().getImagesCount()}</span>
                                            </small>
                                        </span>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/album/${album.getInfo().getAlias()}" class="btn btn-primary">view</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <br>
            </c:forEach>

            <div class="p-5 mb-4 bg-light rounded-3">
                <div class="container-fluid py-5">
                    <h4 class="display-5 fw-bold">Images by tags</h4>
                    <p class="col-sm-8 fs-4">
                        <c:forEach var="tag" items="${tags}">
                            <a href="${pageContext.request.contextPath}/tags/get_by_tag/${tag.getName()}">${tag.getName()}</a>,
                        </c:forEach>
                    </p>
                </div>
            </div>

        </div>


        <jsp:include page="footer.jsp" />
    </body>
</html>
