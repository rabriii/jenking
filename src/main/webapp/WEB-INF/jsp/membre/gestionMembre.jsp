<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Gestion des Membres</title>
    <link href="/css/tailwind-all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<div class="container mx-auto p-4">
    <div class="flex justify-end mb-4">
        <button
                id="openModalButton"
                class="text-white bg-blue-400 hover:bg-blue-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 text-center"
        >
            Ajouter un membre
        </button>
    </div>

    <!-- Modal -->
    <div id="formModal" class="fixed inset-0 z-50 hidden flex items-center justify-center">
        <div class="bg-transparent bg-opacity-50 absolute inset-0"></div>
        <div class="bg-white rounded-lg shadow-xl w-full max-w-lg p-6 relative z-10">
            <div class="flex justify-between items-center border-b pb-2">
                <h3 id="modalTitle" class="text-xl font-medium text-gray-900">Ajouter un membre</h3>
                <button id="closeModalButton" class="text-gray-400 hover:text-gray-600">
                    &times;
                </button>
            </div>
            <div class="mt-4">
                <%@ include file="/WEB-INF/jsp/membre/form.jsp" %>
            </div>
        </div>
    </div>

    <!-- Liste des membres -->
    <%@ include file="/WEB-INF/jsp/membre/list.jsp" %>
</div>

<script>
    document.getElementById('openModalButton').onclick = function() {
        document.getElementById('formModal').classList.remove('hidden');
        document.getElementById('modalTitle').innerText = 'Ajouter un membre';
        document.querySelector('#membreForm').action = '/membre/add';
        document.getElementById('idpers').value = '';
        document.getElementById('nom').value = '';
        document.getElementById('sexe').value = '';
        document.getElementById('age').value = '';
        document.getElementById('contact').value = ''
        document.getElementById('email').value = '';
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

    function openEditModal(id, nom, sexe, age, contact, email) {
        document.getElementById('formModal').classList.remove('hidden');
        document.getElementById('modalTitle').innerText = 'Modifier un Membre';
        document.querySelector('#membreForm').action = '/membre/edit';
        document.getElementById('idpers').value = id;
        document.getElementById('nom').value = nom;
        document.getElementById('sexe').value = sexe;
        document.getElementById('age').value = age;
        document.getElementById('contact').value = contact;
        document.getElementById('email').value = email;

    }
</script>

<style>
    .hidden { display: none; }
</style>
</body>
</html>
