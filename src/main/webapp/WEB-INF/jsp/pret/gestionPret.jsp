<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Gestion des Pret</title>
    <link href="/css/tailwind-all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<div class="container mx-auto p-4">
    <div class="flex justify-end mb-4">
        <button
                id="openModalButton"
                class="text-white bg-blue-400 hover:bg-blue-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 text-center"
        >
            Ajouter un pret
        </button>
    </div>

    <form action="${pageContext.request.contextPath}/pret/gestionPret" method="get" class=" justify-end">
        <div class="flex justify-end">
            <div class="mr-10">
                <label for="start" class="block text-sm font-medium text-gray-700">
                    Date de début :
                </label>
                <input
                        type="date"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                        id="start"
                        name="start"
                        value="<c:out value="${param.start}"/>"
                        required
                >
            </div>
            <div class="mr-10">
                <label for="end" class="block text-sm font-medium text-gray-700">
                    Date de fin :
                </label>
                <input
                        type="date"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                        id="end"
                        name="end"
                        value="<c:out value="${param.end}"/>"
                        required
                >
            </div>
        </div>
        <div class="flex justify-end mt-4 mr-10">
            <button class="text-white bg-blue-400 hover:bg-blue-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-1 text-center" type="submit">
                Filtrer
            </button>
        </div>
    </form>

    <!-- Modal -->
    <div id="formModal" class="fixed inset-0 z-50 hidden flex items-center justify-center">
        <div class="bg-transparent bg-opacity-50 absolute inset-0"></div>
        <div class="bg-white rounded-lg shadow-xl w-full max-w-lg p-6 relative z-10">
            <div class="flex justify-between items-center border-b pb-2">
                <h3 id="modalTitle" class="text-xl font-medium text-gray-900">Ajouter un pret</h3>
                <button id="closeModalButton" class="text-gray-400 hover:text-gray-600">
                    &times;
                </button>
            </div>
            <div class="mt-4">
                <%@ include file="/WEB-INF/jsp/pret/form.jsp" %>
            </div>
        </div>
    </div>

    <!-- Liste des livres -->
    <%@ include file="/WEB-INF/jsp/pret/list.jsp" %>
</div>

<script>
    document.getElementById('openModalButton').onclick = function() {
        document.getElementById('formModal').classList.remove('hidden');
        document.getElementById('modalTitle').innerText = 'Ajouter un pret';
        document.querySelector('#pretForm').action = '/pret/add';
        document.getElementById('idpret').value = '';
        document.getElementById('idpers').value = '';
        document.getElementById('idlivre').value = '';
        document.getElementById('datepret').value = '';
    }

    document.getElementById('closeModalButton').onclick = function() {
        document.getElementById('formModal').classList.add('hidden');
    }
    document.getElementById('cancelButton').onclick = function() {
        document.getElementById('formModal').classList.add('hidden');
    }

    window.onclick = function(event) {
        if (event.target == document.getElementById('formModal').firstElementChild) {
            document.getElementById('formModal').classList.add('hidden');
        }
    }

    function openEditModal(idpret, idpers, idlivre, datepret) {
        document.getElementById('formModal').classList.remove('hidden');
        document.getElementById('modalTitle').innerText = 'Modifier un rendu';
        document.querySelector('#pretForm').action = '/pret/edit';
        document.getElementById('idpret').value = idpret;
        document.getElementById('idpers').value = idpers;
        document.getElementById('idlivre').value = idlivre;
        document.getElementById('datepret').value = datepret;
    }
</script>

<style>
    .hidden { display: none; }
</style>
</body>
</html>
