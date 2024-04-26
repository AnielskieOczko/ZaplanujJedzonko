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
                                    <div class="error mx-auto" data-text="404"><h1>404</h1></div>
                                        <p class="lead text-gray-800 mb-5">Nie ma takiej strony</p>
                                        <p class="text-gray-500 mb-0">Strona z adresem: <strong>${requestScope['javax.servlet.error.request_uri']}</strong> nie istnieje</p>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/">← Wróć do strony głównej</a>
                                    </div>
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