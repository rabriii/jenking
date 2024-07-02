<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Gestion des Rendus
</h2>
<div class="border border-gray-400 shadow-sm rounded-lg overflow-hidden max-w-full mx-auto">
    <table class="w-full text-sm leading-5">
        <thead class="bg-gray-200">
        <tr>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                id Rendu
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                id Pret
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Designation
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                id Membre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                id Livre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Nom du Membre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Date rendu
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Actions
            </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="rendre" items="${rendus}">
            <tr>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.idrendu}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.pret.idpret}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.pret.livre.designation}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.membre.idpers}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.pret.livre.idlivre}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${rendre.membre.nom}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">
                        <fmt:formatDate value="${rendre.daterendu}" pattern="dd/MM/yyyy HH:mm" />
                    </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-center">
                    <a href="javascript:void(0);" onclick="openEditModal('${rendre.idrendu}', '${rendre.pret.idpret}', '${rendre.membre.idpers}', '${rendre.livre.idlivre}', '${rendre.daterendu}')" class="text-blue-500 hover:text-indigo-800">Modifier</a>
                    <span class="mx-2">|</span>
                    <a href="${pageContext.request.contextPath}/rendre/delete/${rendre.idrendu}" class="text-red-600 hover:text-red-900">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
