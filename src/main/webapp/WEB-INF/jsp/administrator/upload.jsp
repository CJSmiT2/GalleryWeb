<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="top_menu.jsp" />

        <br>
        <div class="container">
            <h4>${album.getInfo().getTitle()}</h4>
            <form enctype="multipart/form-data" method="post">
                <div class="mb-3">
                    <label for="imagesType" class="form-label">Type of images</label>
                    <select id="imagesType" class="form-select" name="quality">
                        <option value="not_selected">Not selected</option>
                        <option value="thumbnail">Thumbnails</option>
                        <option value="resized">Resized</option>
                        <option value="original">Original</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="formFile" class="form-label">Select images</label>
                    <input class="form-control" type="file" name="files" multiple="multiple" required="required" id="formFile">
                </div>
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
        <br>

        <jsp:include page="../footer.jsp" />
    </body>
</html>
