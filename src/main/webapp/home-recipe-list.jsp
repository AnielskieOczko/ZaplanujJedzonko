<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@include file="viewCommonParts/header.jsp"%>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Lista Przepisów</h3></div>
                </div>
                <table class="table border-bottom schedules-content">
                    <thead>
                    <tr class="d-flex text-color-darker">
                        <th scope="col" class="col-2">NAZWA</th>
                        <th scope="col" class="col-7">OPIS</th>
                        <th scope="col" class="col-2 center">AKCJE</th>
                    </tr>
                    </thead>
                    <c:if test="${recipeList.size() == 0}">
                        <p>Nie ma przepisow</p>
                    </c:if>
                    <tbody class="text-color-lighter">
                    <c:forEach var="recipe" items="${recipeList}">
                        <tr class="d-flex">
                            <td class="col-2">
                                ${recipe.getName()}
                            </td>
                            <td class="col-7">${recipe.getDescription()}</td>
                            <td class="col-2 d-flex align-items-center justify-content-center flex-wrap">
                                <a href="${pageContext.request.contextPath}/recipe/details?id=${recipe.getId()}" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
<%@include file="viewCommonParts/footer.jsp"%>