<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@include file="viewCommonParts/appHeader.jsp" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="viewCommonParts/appSideMenu.jsp" %>

        <div class="m-4 p-4 width-medium">
            <div class="dashboard-header m-4">
                <div class="dashboard-menu">
                    <div class="menu-item border-dashed">
                        <a href="${pageContext.request.contextPath}/app/recipe/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="${pageContext.request.contextPath}/app/plan/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj plan</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="${pageContext.request.contextPath}/app/recipe/plan/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis do planu</span>
                        </a>
                    </div>
                </div>

                <div class="dashboard-alerts">
                    <div class="alert-item alert-info">
                        <i class="fas icon-circle fa-info-circle"></i>
                        <span class="font-weight-bold">Liczba przepisów: ${recipeCount}</span>
                    </div>
                    <div class="alert-item alert-light">
                        <i class="far icon-calendar fa-calendar-alt"></i>
                        <span class="font-weight-bold">Liczba planów: ${planCount}</span>
                    </div>
                </div>
            </div>
            <div class="m-4 p-4 border-dashed">
                <h2 class="dashboard-content-title">
                    <span>Ostatnio dodany plan:</span> ${lastPlan != null ? lastPlan.getName() : "Nie dodałeś jeszcze żadnego planu"}
                </h2>
                <c:if test="${lastPlan.getMealDetailsDtoList().size() == 0}">
                    <p>Nie dodałeś jeszcze żadnego przepisu do tego planu.</p>
                </c:if>

                <c:forEach var="meal" items="${lastPlan.getMealDetailsDtoList()}" varStatus="rowCounter" begin="0">

                    <table class="table">
                            <%-- added to adress item of out bounds exception for first iteration--%>
                        <c:choose>
                            <c:when test="${rowCounter.index == 0}">
                                <thead>
                                <tr class="d-flex">
                                    <th class="col-2">${meal.getDayName()}</th>
                                    <th class="col-8"></th>
                                    <th class="col-2"></th>
                                </tr>
                                </thead>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${lastPlan.getMealDetailsDtoList()[rowCounter.index].getDayName() != lastPlan.getMealDetailsDtoList()[rowCounter.index - 1].getDayName()}">
                                    <thead>
                                    <tr class="d-flex">
                                        <th class="col-2">${meal.getDayName()}</th>
                                        <th class="col-8"></th>
                                        <th class="col-2"></th>
                                    </tr>
                                    </thead>
                                </c:if>
                            </c:otherwise>
                        </c:choose>

                        <tbody>
                        <tr class="d-flex">
                            <td class="col-2">${meal.getMealName()}</td>
                            <td class="col-8">${meal.getRecipeName()}</td>
                            <td class="col-2">
                                <a href="/app/recipe/details?id=${meal.getRecipeId()}">
                                <button type="button" class="btn btn-primary rounded-0">Szczegóły</button>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </c:forEach>
            </div>
        </div>
    </div>
</section>
<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>