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
                    <!-- fix action, method -->
                    <!-- add name attribute for all inputs -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger text-center mt-3">
                            <c:out value="${errorMessage}" />
                        </div>
                    </c:if>

                    <form METHOD="POST" action="${pageContext.request.contextPath}/app/recipe/edit?id=${recipe.getId()}">
                        <div class="row border-bottom border-3">
                            <div class="col"><h3 class="color-header text-uppercase">Edycja przepisu</h3></div>
                            <div class="col d-flex justify-content-end mb-2"><button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button></div>
                        </div>

                        <table class="table borderless">
                            <tbody>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Nazwa Przepisu</th>
                                <td class="col-7">
                                    <input class="w-100 p-1" type="text" id="recipe-name" name="recipe-name" value="${recipe.getName()}" required >
                                </td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Opis przepisu</th>
                                <td class="col-7"> <textarea class="w-100 p-1" id="recipe-description" name="recipe-description" rows="5" required>${recipe.getDescription()}</textarea></td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2">Przygotowanie (minuty)</th>
                                <td class="col-3">
                                    <input class="p-1" id="recipe-preparation-time" name="recipe-preparation-time" type="number" value="${recipe.getPreparationTime()}" min="1" required>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <div class="row d-flex">
                            <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Sposób przygotowania</h3></div>
                            <div class="col-2"></div>
                            <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Składniki</h3></div>
                        </div>
                        <div class="row d-flex">
                            <div class="col-5 p-4">
                                <textarea class="w-100 p-1" id="recipe-preparation" name="recipe-preparation" rows="10" required>${recipe.getPreparation()}</textarea>
                            </div>
                            <div class="col-2"></div>

                            <div class="col-5 p-4">
                                    <textarea class="w-100 p-1" id="recipe-ingredients" name="recipe-ingredients" rows="10" required>
                                        ${recipe.getIngredients()}
                                    </textarea>
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
