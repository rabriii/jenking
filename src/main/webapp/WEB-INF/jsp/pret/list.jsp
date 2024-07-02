<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Prêts</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Gestion des Prets
</h2>
<div class="border border-gray-400 shadow-sm rounded-lg overflow-hidden max-w-full mx-auto">
    <table class="w-full text-sm leading-5">
        <thead class="bg-gray-200">
        <tr>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                ID Prêt
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                ID Membre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                ID Livre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Nom du Membre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Date Prêt
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Date Rendu
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Actions
            </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="pret" items="${prets}">
            <tr>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${pret.idpret}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${pret.membre.idpers}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${pret.livre.idlivre}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${pret.membre.nom}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">
                        <fmt:formatDate value="${pret.datepret}" pattern="dd/MM/yyyy HH:mm" />
                    </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">
                        <fmt:formatDate value="${pret.daterendu}" pattern="dd/MM/yyyy HH:mm" />
                    </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-center">
                    <a href="javascript:void(0);" onclick="openEditModal('${pret.idpret}', '${pret.membre.idpers}', '${pret.livre.idlivre}', '${pret.datepret}')" class="text-blue-500 hover:text-blue-800">Modifier</a>
                    <span class="mx-2">|</span>
                    <a href="${pageContext.request.contextPath}/pret/delete/${pret.idpret}" class="text-red-600 hover:text-red-900">Supprimer</a>
                    <span class="mx-2">|</span>
                    <a href="${pageContext.request.contextPath}/pret/receipt/${pret.idpret}" class="text-blue-600 hover:text-blue-900">Réçu</a>
                    <span class="mx-2">|</span>
                    <a href="javascript:void(0);" onclick="sendReminder('${pret.membre.email}', '${pret.membre.nom}', '${pret.idpret}')" class="text-green-600 hover:text-green-900">Rappeler</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    function sendReminder(email, nomMembre, idPret) {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/pret/sendReminder",
            data: {
                email: email,
                idPret: idPret,
                nomMembre: nomMembre
            },
            success: function(response) {
                alert("Email de rappel envoyé avec succès !");
            },
            error: function(xhr, status, error) {
                alert("Erreur lors de l'envoi de l'email de rappel : " + error);
            }
        });
    }
</script>
</body>
</html>
