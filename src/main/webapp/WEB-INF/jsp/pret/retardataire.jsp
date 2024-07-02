<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des livres en retard</title>
    <link href="/css/tailwind-all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<div class="container mx-auto p-4">
    <!-- Liste des livres -->
    <%@ include file="/WEB-INF/jsp/pret/listRetard.jsp" %>
</div>

<style>
    .hidden { display: none; }
</style>
</body>
</html>
