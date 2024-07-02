<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Livre
</h2>
<div class="flex justify-center px-4 sm:px-6 lg:px-8">
    <div class="bg-white shadow-md rounded-md p-6 w-full max-w-lg">
        <form id="membreForm" method="post" action="">
            <div class="mb-4" style="display: none;">
                <label for="idpers" class="block text-sm font-medium text-gray-700">Id Membre :</label>
                <input
                        type="text"
                        id="idpers"
                        name="idpers"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                >
            </div>
            <div class="mb-4">
                <label for="nom" class="block text-sm font-medium text-gray-700">Nom :</label>
                <input
                        type="text"
                        id="nom"
                        name="nom"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="sexe" class="block text-sm font-medium text-gray-700">Sexe :</label>
                <select
                        id="sexe"
                        name="sexe"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
                    <option value="Homme">Homme</option>
                    <option value="Femme">Femme</option>
                </select>
            </div>
            <div class="mb-4">
                <label for="age" class="block text-sm font-medium text-gray-700">Age :</label>
                <input
                        type="number"
                        id="age"
                        name="age"
                        min="0"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="contact" class="block text-sm font-medium text-gray-700">Contact :</label>
                <input
                        type="text"
                        id="contact"
                        name="contact"
                        maxlength="10"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700">Email :</label>
                <input
                        type="email"
                        id="email"
                        name="email"
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
