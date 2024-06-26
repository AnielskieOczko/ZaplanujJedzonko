<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%@include file="viewCommonParts/header.jsp"%>

<html>
<body>


<section class="dashboard-section">
  <div class="container pt-4 pb-4">
    <div class="border-dashed view-height">
      <div class="container w-25">
        <form class="padding-small text-center" action="${pageContext.request.contextPath}/login" method="post">
          <h1 class="text-color-darker">Logowanie</h1>
          <div class="form-group">
            <input type="text" class="form-control" id="email" name="email" placeholder="Podaj adres email" required>
          </div>
          <div class="form-group">
            <input type="password" class="form-control" id="password" name="password" placeholder="Podaj hasło" required>
          </div>
          <button class="btn btn-color rounded-0" type="submit">Zaloguj</button>
        </form>
        <%-- Komunikaty dla błędnie wprowadzonych danych --%>
        <c:if test="${not empty error}">
          <div class="alert alert-danger text-center mt-3">
            <c:out value="${error}" />
          </div>
        </c:if>
      </div>
    </div>
  </div>
</section>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>