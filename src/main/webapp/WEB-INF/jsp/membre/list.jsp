<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Liste des Membres
</h2>
<div class="border border-gray-400 shadow-sm rounded-lg overflow-hidden max-w-full mx-auto">
    <table class="w-full text-sm leading-5">
        <thead class="bg-gray-200">
        <tr>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                idMembre
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Nom
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Age
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Sexe
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Contact
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                Email
            </th>
            <th scope="col" class="py-3 px-4 text-center font-medium text-gray-600">
                email
            </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="membre" items="${membres}">
            <tr>
                <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-900 text-center">${membre.idpers}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${membre.nom}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${membre.age}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${membre.sexe}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${membre.contact}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-center">
                    <div class="text-sm text-gray-900">${membre.email}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-center">
                    <a href="javascript:void(0);" onclick="openEditModal('${membre.idpers}', '${membre.nom}', '${membre.age}', '${membre.sexe}', '${membre.contact}', '${membre.email}')" class="text-blue-500 hover:text-indigo-800">Modifier</a>
                    <span class="mx-2">|</span>
                    <a href="/membre/delete/${membre.idpers}" class="text-red-600 hover:text-red-900">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
