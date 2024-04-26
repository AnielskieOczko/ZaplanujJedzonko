<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="viewCommonParts/header.jsp"%>

<section class="mr-4 ml-4">
  <div class="row pt-4 pb-2">
    <i class="fas fa-users icon-users"></i>
    <h1>Przepisy naszych użytkowników:</h1>
    <hr>
    <div class="orange-line w-100"></div>
  </div>
</section>

<section class="width-medium text-color-darker">
  <div class="pb-2">
    <div class="border-dashed view-height w-100">
      <div class="mt-4 ml-4 mr-4">
        <div class="row border-bottom border-3">
          <div class="col"><h3 class="color-header text-uppercase">Szczegóły przepisu</h3></div>
          <div class="col d-flex justify-content-end mb-2"><a href="${pageContext.request.contextPath}/recipes" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a></div>
        </div>

        <table class="table borderless">
          <tbody>
          <tr class="d-flex">
            <th scope="row" class="col-2">Nazwa Przepisu</th>
            <td class="col-7">
              ${recipe.getName()}
            </td>
          </tr>
          <tr class="d-flex">
            <th scope="row" class="col-2">Opis przepisu</th>
            <td class="col-7">${recipe.getDescription()}</td>
          </tr>
          <tr class="d-flex">
            <th scope="row" class="col-2">Przygotowanie (minuty)</th>
            <td class="col-7">
              ${recipe.getPreparationTime()}
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
            <p>${recipe.getPreparation()}</p>
          </div>
          <div class="col-2"></div>
          <ul class="col-5 p-4 list-unstyled">
            <c:forEach var="ingredient" items="${ingredients}">
              <li>${ingredient}</li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </div>
  </div>
</section>

<section class="last-info-section padding-small">
  <div class="container">
    <div class="row">
      <div class="col">
        <h3 class="mb-4">Lorem ipsum dolor</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at porttitor sem.  Aliquam erat volutpat. Donec placerat nisl magna.</p>
      </div>
      <div class="col pl-4 ml-4">
        <h3 class="mb-4">Lorem ipsum dolor</h3>
        <ul class="container">
          <li>consectetur adipiscing elit</li>
          <li>sed do eiusmod tempor</li>
          <li>incididunt ut labore</li>
          <li>et dolore magna aliqua</li>
        </ul>
      </div>
      <div class="col">
        <h3 class="mb-4">Lorem ipsum dolor</h3>
        <div class="input-group mb-3">
          <input type="text" class="form-control border-0 rounded-0" placeholder=""
                 aria-label="Recipient's username" aria-describedby="basic-addon2">
          <div class="input-group-append">
            <button class="input-group-text btn-color border-0 rounded-0" type="submit" id="basic-addon2"><a
                    href="${pageContext.request.contextPath}/">Lorem</a></button>
          </div>
        </div>
        <div class="container d-flex-row">
          <a href="${pageContext.request.contextPath}/">
            <i class="fab fa-facebook-square mr-4 icon-social"></i>
          </a>
          <a href="${pageContext.request.contextPath}/">
            <i class="fab fa-twitter-square mr-4 icon-social"></i>

          </a>
          <a href="${pageContext.request.contextPath}/">
            <i class="fab fa-instagram icon-social"></i>
          </a>
        </div>
      </div>
    </div>
  </div>
</section>

<%@include file="viewCommonParts/footer.jsp"%>