<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${album.getInfo().getTitle()}</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />
        <br>
        <h4>${album.getInfo().getTitle()}</h4>
        <input type="hidden" name="albumAlias" id="albumAlias" value="${album.getInfo().getAlias()}">
        <jsp:include page="../images_rows.jsp" />

        <br>
        <jsp:include page="../footer.jsp" />
        <script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/hit_album.js"></script>
    </body>
</html>
