<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<%@include file="viewCommonParts/appHeader.jsp" %>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="viewCommonParts/appSideMenu.jsp" %>

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">DODAJ PRZEPIS DO PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <button class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4" type="submit" form="form1">Zapisz</button>
                    </div>
                </div>

                <div class="schedules-content">
                    <form id="form1" method="POST" action="${pageContext.request.contextPath}/app/recipe/plan/add">
                        <div class="form-group row">
                            <label for="choosePlan" class="col-sm-2 label-size col-form-label">
                                Wybierz plan
                            </label>
                            <div class="col-sm-3">
                                <select class="form-control" id="choosePlan" name="choosePlan">
                                    <c:forEach var="plan" items="${plans}">
                                        <option value="${plan.getId()}" >${plan.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="meal-name" class="col-sm-2 label-size col-form-label">
                                Nazwa posiłku
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="" id="meal-name" name="meal-name" placeholder="Nazwa posiłku" maxlength="245" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="meal-number" class="col-sm-2 label-size col-form-label">
                                Numer posiłku
                            </label>
                            <div class="col-sm-2">
                                <input type="number" class="form-control" value="" id="meal-number" name="meal-number" min="1" required
                                       placeholder="Numer posiłku">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="recipe" class="col-sm-2 label-size col-form-label">
                                Przepis
                            </label>
                            <div class="col-sm-4">
                                <select class="form-control" id="recipe" name="recipe">
                                    <c:forEach var="recipe" items="${recipes}">
                                        <option value="${recipe.getId()}">${recipe.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="day" class="col-sm-2 label-size col-form-label">
                                Dzień
                            </label>
                            <div class="col-sm-2">
                                <select class="form-control" id="day" name="day">
                                    <c:forEach var="day" items="${dayNames}">
                                        <option value="${day.getId()}">${day.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>