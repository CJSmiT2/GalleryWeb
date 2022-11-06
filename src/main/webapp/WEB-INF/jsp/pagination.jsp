<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
<ul class="pagination">
    <c:if test="${not empty pagination.getPreviousPages()}">
        <li class="page-item">
            <a class="page-link btn-info" href="?page=${pagination.getFirsPage()}"> First </a>
        </li>
    </c:if>

    <c:if test="${not empty pagination.getPreviousPages()}">
        <li class="page-item">
            <a class="page-link" href="?page=${pagination.getCurrentPageNumber() - 1}"> Previous </a>
        </li>
    </c:if>

    <c:forEach var="previousPage" items="${pagination.getPreviousPagesByLimit(4)}">
        <li class="page-item d-none d-sm-block">
            <a class="page-link" href="?page=${previousPage}">${previousPage}</a>
        </li>
    </c:forEach>

    <li class="page-item active">
        <a class="page-link" href="?page=${pagination.getCurrentPageNumber()}">${pagination.getCurrentPageNumber()}</a>
    </li>

    <c:forEach var="nextPage" items="${pagination.getNextPagesByLimit(4)}">
        <li class="page-item d-none d-sm-block">
            <a class="page-link" href="?page=${nextPage}">${nextPage}</a>
        </li>
    </c:forEach>

    <c:if test="${not empty pagination.getNextPages()}">
        <li class="page-item">
            <a class="page-link" href="?page=${pagination.getCurrentPageNumber() + 1}"> Next </a>
        </li>
    </c:if>

    <c:if test="${not empty pagination.getNextPages()}">
        <li class="page-item">
            <a class="page-link btn-info" href="?page=${pagination.getLastPage()}"> Last </a>
        </li>
    </c:if>
</ul>