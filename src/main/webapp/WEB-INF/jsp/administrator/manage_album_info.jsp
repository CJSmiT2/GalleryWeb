<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Album Info</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="top_menu.jsp" />

        <div class="container">
            <h3>Create Album Info</h3>

            <form method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">Title: </label>
                    <input type="text" class="form-control" id="title" name="title">
                </div>
                <div class="mb-3">
                    <label for="title" class="form-label">Alias: </label>
                    <input type="text" class="form-control" id="alias" name="alias">
                </div>
                <input type="submit" class="btn btn-sm btn-success" name="Create">
            </form>

        </div>

    </body>
</html>
