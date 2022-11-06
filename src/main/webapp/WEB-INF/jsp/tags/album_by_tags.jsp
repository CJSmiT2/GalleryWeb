<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Images by tag: ${tag.getName()}</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <h4>Images by tag: ${tag.getName()}</h4>
        <jsp:include page="../images_rows_2.jsp" />


        <jsp:include page="../footer.jsp" />
    </body>
</html>
