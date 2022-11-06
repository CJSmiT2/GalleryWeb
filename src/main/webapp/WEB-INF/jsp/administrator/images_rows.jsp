<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="row" items="${rows}">
    <div class="row" style="margin: 15px">
        <c:forEach var="image" items="${row.get()}">
            <div class="col-sm text-center border" style="margin: 5px">
                <a href="${pageContext.request.contextPath}/image_view/${album.getInfo().getAlias()}/${image.getAlias()}">
                    <img class="img-fluid" loading="lazy" 
                         src="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/thumb-${image.getAlias()}"
                         width="500px" height="500px">
                </a>
                <div class="btn-group" role="group" style="margin: 10px">
                    <button id="manageBtn" type="button" class="btn btn-danger dropdown-toggle btn-sm" data-bs-toggle="dropdown" aria-expanded="false">
                        manage
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="manageBtn">
                        <li>
                            <a class="dropdown-item" 
                               href="${pageContext.request.contextPath}/admin_gallery/make_as_cover?albumAlias=${album.getInfo().getAlias()}&imageAlias=${image.getAlias()}">
                                Make as Cover</a>
                        </li>
                        <li><a class="dropdown-item" href="#">Replase</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin_gallery/delete_image?albumAlias=${album.getInfo().getAlias()}&imageAlias=${image.getAlias()}">Delete</a></li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </div>
</c:forEach>