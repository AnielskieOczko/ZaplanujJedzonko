<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="viewCommonParts/header.jsp" %>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <section class="dashboard-section">
                    <div class="row dashboard-nowrap">

                        <div class="container pt-4 pb-4">




                            <div class="row justify-content-center">
                                <div class="text-center">
                                    <div class="error mx-auto" data-text="404"><h1>500</h1></div>
                                    <p class="lead text-gray-800 mb-5">Coś poszło nie tak</p>
                                    <p class="text-gray-500 mb-0">Przepraszamy, spróbuj ponownie za chwilę</p>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/">← Wróć do strony głównej</a>
                                    </div>
                                </div>


                            </div>

                        </div>
                </section>
            </div>
        </div>
    </div>
</section>
<%@ include file="viewCommonParts/footer.jsp" %>
