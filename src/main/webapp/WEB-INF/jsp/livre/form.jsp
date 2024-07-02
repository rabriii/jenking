<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="my-3 text-center text-3xl font-bold tracking-tight text-gray-900">
    Livre
</h2>
<div class="flex justify-center px-4 sm:px-6 lg:px-8">
    <div class="bg-white shadow-md rounded-md p-6 w-full max-w-lg">
        <form id="livreForm" method="post">
            <div class="mb-4" style="display: none;">
                <label for="idlivre" class="block text-sm font-medium text-gray-700">Id livre :</label>
                <input
                        type="text"
                        id="idlivre"
                        name="idlivre"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400"
                >
            </div>
            <div class="mb-4">
                <label for="designation" class="block text-sm font-medium text-gray-700">Designation :</label>
                <input
                        type="text"
                        id="designation"
                        name="designation"
                        class="border border-gray-300 p-2 w-full rounded-lg focus:outline-none focus:border-blue-400">
            </div>
            <div class="mb-4">
                <label for="exemplaire" class="block text-sm font-medium text-gray-700">Exemplaire :</label>
                <input
                        type="number"
                        id="exemplaire"
                        min="0"
                        name="exemplaire"
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
