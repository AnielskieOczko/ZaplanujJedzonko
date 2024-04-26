<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@include file="viewCommonParts/appHeader.jsp" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="viewCommonParts/appSideMenu.jsp" %>

        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="${pageContext.request.contextPath}/app/plan/list" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${plan.getName()}</p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">
                                    ${plan.getDescription()}
                                </p>
                            </div>
                        </div>
                    </div>

                    <c:if test="${plan.getMealDetailsDtoList().size() == 0}">
                        <p>Nie dodałeś jesze żadnych posiłków do tego planu</p>
                    </c:if>

                    <c:forEach items="${plan.getMealDetailsDtoList()}" var="meal" varStatus="rowCounter" begin="0">
                        <table class="table">
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

                            <tbody class="text-color-lighter">
                            <tr class="d-flex">
                                <td class="col-2">${meal.getMealName()}</td>
                                <td class="col-7">${meal.getRecipeName()}</td>
                                <td class="col-1 center">
                                    <a href="${pageContext.request.contextPath}/app/plan/recipe/delete?id=${meal.getRecipePlanId()}" class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                </td>
                                <td class="col-2 center">
                                    <a href="/app/recipe/details?id=${meal.getRecipeId()}" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>