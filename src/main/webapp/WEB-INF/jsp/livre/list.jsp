<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Liste des Livres
</h2>
<div class="border border-gray-400 shadow-sm rounded-lg overflow-hidden max-w-full mx-auto">
    <table class="w-full text-sm leading-5">
        <thead class="bg-gray-200">
        <tr>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                idLivre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Designation
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Exemplaire
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Actions
            </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="livre" items="${livres}">
            <tr>
                <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-900 text-center">${livre.idlivre}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${livre.designation}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${livre.exemplaire}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-center">
                    <a href="javascript:void(0);" onclick="openEditModal('${livre.idlivre}', '${livre.designation}', '${livre.exemplaire}')" class="text-blue-500 hover:text-indigo-800">Modifier</a>
                    <span class="mx-2">|</span>
                    <a href="/livre/delete/${livre.idlivre}" class="text-red-600 hover:text-red-900">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
