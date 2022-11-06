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

        <jsp:include page="top_menu.jsp" />

        <br>
        <div class="row" style="margin: 15px">
            <div class="col-sm-10">
                <h4>${album.getInfo().getTitle()}</h4>
            </div>
            <div class="col-sm-1">
                <a href="upload?alias=${album.getInfo().getAlias()}" class="btn btn-sm btn-primary">Upload</a>
            </div>
            <div class="col-sm-1">
                <div class="dropdown">
                    <button 
                        class="btn btn-danger dropdown-toggle btn-sm" 
                        type="button" 
                        id="dropdownMenuButton1" 
                        data-bs-toggle="dropdown" 
                        aria-expanded="false">
                        Delete album
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <li><a class="dropdown-item" href="delete_album?albumAlias=${album.getInfo().getAlias()}">DELETE</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <jsp:include page="images_rows.jsp" />

        <br>
        <jsp:include page="../footer.jsp" />
    </body>
</html>
