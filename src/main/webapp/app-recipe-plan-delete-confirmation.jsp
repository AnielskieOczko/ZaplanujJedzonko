
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

      <h2 class="text-color-darker text-center" >Czy na pewno chcesz usunąć przepis z planu?</h2>

      <div class="row justify-content-center">
        <form id="confirmationForm" method="post"
              action="${pageContext.request.contextPath}/app/plan/recipe/delete?id=${recipePlanId}"
              class="col-4">
          <input name="delete" hidden value="true">
          <input type="submit" value="OK" class="btn btn-color rounded-0 float-right">
        </form>

        <form method="post"
              action="${pageContext.request.contextPath}/app/plan/recipe/delete?id=${recipePlanId}"
              class="col-4">
          <input name="delete" hidden value="false">
          <input type="submit" value="Anuluj" class="btn btn-color rounded-0 float-left">
        </form>
      </div>

      <h1></h1>

      <div id="gifContainer" style="display: none;" class="container w-50">

        <div class="tenor-gif-embed" data-postid="27186872" data-share-method="host" data-aspect-ratio="1" data-width="100%">
          <a href="https://tenor.com/view/dumping-trash-daniel-labelle-frustrated-garbage-trash-gif-27186872">Dumping Trash Daniel Labelle GIF</a>
          from <a href="https://tenor.com/search/dumping+trash-gifs">Dumping Trash GIFs</a>
        </div> <script type="text/javascript" async src="https://tenor.com/embed.js"></script>

      </div>
    </div>

  </div>
</section>

<%@include file="viewCommonParts/appFooter.jsp" %>
</body>
</html>