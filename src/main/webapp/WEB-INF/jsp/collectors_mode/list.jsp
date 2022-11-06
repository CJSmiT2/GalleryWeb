<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Collector page by '${collector_account.getNickName()}'</title>
        <jsp:include page="../head.jsp" />
    </head>
    <body>

        <jsp:include page="../top_menu.jsp" />

        <h4>Collector page by '${collector_account.getNickName()}'</h4>
        <div class="card">
            <div class="card-body">
                <div class="row small">
                    <div class="col">Current sum: <span class="font-weight-bold text-info">+${collector_account.getCurrent()} pt.</span></div>
                    <div class="col">Debit: <span class="font-weight-bold text-success">${collector_account.getDebit().getCr().getValue()}  pt.</span></div>
                    <div class="col">Credit: <span class="font-weight-bold text-white">${collector_account.getCredit().getCr().getValue()}  pt.</div>
                    <div class="col">From sale: <span class="font-weight-bold text-white">+${collector_account.getCrFromSale().getCr().getValue()}  pt.</span></div>
                    <div class="col">Spent: <span class="font-weight-bold text-warning">-${collector_account.getSpent().getCr().getValue()}  pt.</span></div>
                    <div class="col">Images in collection: <span class="font-weight-bold text-white">${collector_account.getImageCollection().getImagesCollect().size()}</span></div>
                </div>
            </div>
        </div>

        <jsp:include page="../images_rows_2.jsp" />

        <jsp:include page="../footer.jsp" />
    </body>
</html>
