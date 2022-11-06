<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">${frontEndData.getSiteName()}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/albums?page=1" class="nav-link px-2 link-dark">Albums</a></li>
                <li><a href="${pageContext.request.contextPath}/models" class="nav-link px-2 link-dark">Photomodels</a></li>
                <li><a href="${pageContext.request.contextPath}/collectors_mode/collectors" class="nav-link px-2 link-dark">Collectors</a></li>
            </ul>
            <c:choose>
                <c:when test="${!user.isPresent()}">
                    <ul class="nav">
                        <li class="nav-item"><a href="${pageContext.request.contextPath}/signin" class="nav-link link-dark px-2">Sign In</a></li>
                        <li class="nav-item"><a href="${pageContext.request.contextPath}/signup" class="nav-link link-dark px-2">Sign Up</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="dropdown text-end">
                        <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
                        </a>
                        <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1">
                            <li><a class="dropdown-item" href="#">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/sign_out">Sign out</a></li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</nav>

<div class="b-divider"></div>