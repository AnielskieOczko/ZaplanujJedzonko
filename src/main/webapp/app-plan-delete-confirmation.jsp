<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pl">

<head>
    <meta charset="UTF-8">
    <title>Potwierdzenie usunięcia przepisu</title>
    <%@include file="viewCommonParts/appHeader.jsp" %>
</head>

<body>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <div class="container pt-4 pb-4">
            <h1></h1>

            <h2 class="text-color-darker text-center">Czy na pewno chcesz usunąć ten plan?</h2>

            <div class="row justify-content-center">
                <form method="post"
                      action="${pageContext.request.contextPath}/app/plan/delete?id=${planId}"
                      class="col-4">
                    <input name="delete" hidden value="true">
                    <input type="submit" value="OK" class="btn btn-color rounded-0 float-right">
                </form>

                <form method="post"
                      action="${pageContext.request.contextPath}/app/plan/delete?id=${planId}"
                      class="col-4">
                    <input name="delete" hidden value="false">
                    <input type="submit" value="Anuluj" class="btn btn-color rounded-0 float-left">
                </form>
            </div>


        </div>

    </div>
</section>

<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>