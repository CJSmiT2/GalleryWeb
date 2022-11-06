<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="row" items="${rows}">
    <div class="row" style='--bs-gutter-x: 0'>
        <c:forEach var="image" items="${row.get()}">
            <div class="col-sm">
                <a href="${pageContext.request.contextPath}/image_view/${image.getAlbumInfo().get().getAlias()}/${image.getAlias()}">
                    <img class="img-fluid" loading="lazy" 
                         src="${pageContext.request.contextPath}/img/${image.getAlbumInfo().get().getAlias()}/thumb-${image.getAlias()}"
                         width="500px" height="500px">
                </a>
            </div>
        </c:forEach>
    </div>
</c:forEach>