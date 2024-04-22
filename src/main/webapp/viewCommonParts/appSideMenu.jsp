<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="nav flex-column long-bg">
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/app/dashboard">
            <span>Pulpit</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/app/recipe/list">
            <span>Przepisy</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
<%--    TODO: update link for plan list--%>
    <li class="nav-item">
        <a class="nav-link" href="">
            <span>Plany</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/data/edit">
            <span>Edytuj dane</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link disabled" href="${pageContext.request.contextPath}/app/admin/password/edit">
            <span>Zmień hasło</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
</ul>