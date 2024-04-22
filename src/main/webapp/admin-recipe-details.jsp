<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@include file="viewCommonParts/appHeader.jsp" %>


<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <%@include file="viewCommonParts/appSideMenu.jsp" %>


        <div class="m-4 p-3 width-medium text-color-darker">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="mt-4 ml-4 mr-4">
                    <div class="row border-bottom border-3">
                        <div class="col"><h3 class="color-header text-uppercase">Szczegóły przepisu</h3></div>
                        <div class="col d-flex justify-content-end mb-2"><a href="${pageContext.request.contextPath}/app/recipe/list"
                                                                            class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                        </div>
                    </div>

                    <table class="table borderless">
                        <tbody>
                        <tr class="d-flex">
                            <th scope="row" class="col-2">Nazwa Przepisu</th>
                            <td class="col-7">
                                ${recipeDetails.getName()}
                            </td>
                        </tr>
                        <tr class="d-flex">
                            <th scope="row" class="col-2">Opis przepisu</th>
                            <td class="col-7">
                                ${recipeDetails.getDescription()}
                            </td>
                        </tr>
                        <tr class="d-flex">
                            <th scope="row" class="col-2">Przygotowanie (minuty)</th>
                            <td class="col-7">
                                ${recipeDetails.getPreparationTime()}
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="row d-flex">
                        <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Sposób przygotowania</h3>
                        </div>
                        <div class="col-2"></div>
                        <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Składniki</h3></div>
                    </div>
                    <div class="row d-flex">
                        <div class="col-5 p-4">
                            <p>${recipeDetails.getPreparation()}</p>
                        </div>
                        <div class="col-2"></div>
                        <ul class="col-5 p-4 list-unstyled">
                            <c:forEach items="${recipeIngredients}" var="ingredient">
                                <li>${ingredient}</li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>