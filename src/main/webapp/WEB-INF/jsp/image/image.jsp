<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${album.getInfo().getTitle()} (${image.getAlias()})</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <c:if test="${lastAlbum.isPresent()}">
            <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/album/${lastAlbum.get()}"> Return </a>
        </c:if>

        <br>
        <h4>${album.getInfo().getTitle()} (${image.getAlias()})</h4>
        <input type="hidden" name="imageAlias" id="imageAlias" value="${image.getAlias()}">
        <div class="container">
            <div class="row">
                <div class="col-sm-10">
                    <div class="row">
                        <div class="col-sm-2 text-rigth">
                            <c:if test="${album.getListener().getPrevious(image).isPresent()}">
                                <a href="${pageContext.request.contextPath}/image_view/${album.getInfo().getAlias()}/${album.getListener().getPrevious(image).get().getAlias()}">
                                    <i class="fas fa-angle-left"></i>Previous
                                </a>
                            </c:if>
                        </div>
                        <div class="col-sm-8">
                            <c:choose>
                                <c:when test="${album.getListener().getNext(image).isPresent()}">
                                    <a href="${pageContext.request.contextPath}/image_view/${album.getInfo().getAlias()}/${album.getListener().getNext(image).get().getAlias()}">
                                        <img 
                                            src="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/resized-${image.getAlias()}" 
                                            class="img-fluid"
                                            loading="lazy" 
                                            width="${image.getWidth()}"
                                            height="${image.getHeight()}">
                                    </a>
                                </c:when>    
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/resized-${image.getAlias()}" class="img-fluid">
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <div class="col-sm-2">
                            <c:if test="${album.getListener().getNext(image).isPresent()}">
                                <a href="${pageContext.request.contextPath}/image_view/${album.getInfo().getAlias()}/${album.getListener().getNext(image).get().getAlias()}">
                                    <i class="fas fa-angle-rigth"></i>Next
                                </a>
                            </c:if>
                        </div>
                    </div>

                </div>
                <div class="col-sm-2">
                    <c:forEach var="photomodel" items="${photomodels}">
                        <i>
                            <a href="${pageContext.request.contextPath}/model/${photomodel.getAlias()}">
                                ${photomodel.getNickName()}
                            </a>
                        </i>
                    </c:forEach>
                    <hr>
                    <p>Hits: ${image.getHits()}</p>
                    <p>Likes: ${image.getLikes()}</p>
                    <p>TimeView: ${image.getTimeViewsMins()} mins</p>
                    <hr>
                    <a href="${pageContext.request.contextPath}/img/${album.getInfo().getAlias()}/${image.getAlias()}" class="btn btn-success" target="_blank">Download</a>
                </div>
            </div>
        </div>
        <br>

        <jsp:include page="../footer.jsp" />
        <script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/hit_image.js"></script>
        <script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/timeCounter.js"></script>
    </body>
</html>
