<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title><c:out value="${header}" /></title>
    <link href="/css/tailwind-all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<div class="container mx-auto p-4">
    <jsp:include page="${content}" />
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
</body>
</html>
