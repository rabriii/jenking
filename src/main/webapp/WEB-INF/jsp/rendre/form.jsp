<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Rendu
</h2>
<c:if test="${not empty message}">
    <div class="text-red-600 text-center">
        <c:out value="${message}" />
    </div>
</c:if>
<div class="flex justify-center px-4 sm:px-6 lg:px-8">
    <div class="bg-white shadow-md rounded-md p-6 w-full max-w-lg">
        <form id="rendreForm" action="" method="post">
            <div class="mb-4" style="display: none;">
                <label for="idrendu" class="block text-sm font-medium text-gray-700">Id Rendu :</label>
                <input
                        type="text"
                        id="idrendu"
                        name="idrendu"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                >
            </div>
            <div class="mb-4">
                <label for="idpret" class="block text-sm font-medium text-gray-700">Id Pret :</label>
                <input
                        type="text"
                        id="idpret"
                        name="idpret"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                >
            </div>
            <div class="mb-4">
                <label for="idpers" class="block text-sm font-medium text-gray-700">Id Membre :</label>
                <input
                        type="text"
                        id="idpers"
                        name="idpers"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="idlivre" class="block text-sm font-medium text-gray-700">Id Livre :</label>
                <input
                        type="text"
                        id="idlivre"
                        name="idlivre"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="daterendu" class="block text-sm font-medium text-gray-700">Date rendu :</label>
                <input
                        type="datetime-local"
                        id="daterendu"
                        name="daterendu"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="flex items-center">
                <button type="submit" class="text-white bg-blue-400 hover:bg-blue-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-blue-800">
                    Enregistrer
                </button>
                <button type="button" id="cancelButton" class="text-gray-600 bg-gray-300 hover:text-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 text-center border border-gray-300 ml-2">
                    Annuler
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Include Local Flatpickr CSS and JS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/flatpickr.min.css">
<script src="${pageContext.request.contextPath}/flatpickr.min.js"></script>

<!-- Initialize Flatpickr -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        flatpickr("#daterendu", {
            dateFormat: "Y-m-d",
            defaultDate: "today"
        });
    });
</script>
