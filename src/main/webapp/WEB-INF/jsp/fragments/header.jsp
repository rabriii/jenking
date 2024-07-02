<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%!
    String isActive(String link, String requestURI) {
        if (link.equals("/") && (requestURI.equals("") || requestURI.equals("/"))) {
            return "text-blue-700";
        } else if (requestURI.endsWith(link)) {
            return "text-blue-700";
        } else {
            return "";
        }
    }
    String getSearchAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/livre")) {
            return request.getContextPath() + "/livre/gestion";
        } else if (requestURI.contains("/membre")) {
            return request.getContextPath() + "/membre/gestionMembre";
        } else {
            return "#";
        }
    }


%>

<nav class="w-full bg-white border shadow-md rounded-xl border-white/80 bg-opacity-80 backdrop-blur-2xl backdrop-saturate-200 lg:px-8 lg:py-2">
    <div class="hidden lg:flex lg:justify-between lg:items-center">
        <!-- Logo -->
        <div class="flex flex-col items-center mr-20">
            <a href="/">
                <img src="${pageContext.request.contextPath}/images/logo.jfif" alt="Logo" class="h-20" />
            </a>
            <span class="text-lg font-bold">Hamaky</span>
        </div>

        <!-- Barre de recherche -->
        <div class="flex-grow mx-30 mr-10">
            <form action="<%= getSearchAction(request) %>" method="get" class="flex">
                <input type="text" name="query" placeholder="Rechercher..." value="${param.query}" class="w-64 p-2 border border-gray-300 rounded-l-lg mr-2 hover:border-blue-500 focus:border-blue-500 transition-all" />
                <button type="submit" class="select-none rounded-lg bg-gray-400 py-2 px-3 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-gray-300/10 transition-all hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none">Rechercher</button>
            </form>
        </div>


        <!-- Menu -->
        <div class="flex gap-2 mr-10">
            <a href="/" class="text-lg hover:text-blue-700 <%= isActive("/", request.getRequestURI()) %>" style="margin-right: 10px;">
                <span class="flex items-center transition-colors">
                    Home
                </span>
            </a>
            <a href="/livre/gestion" class="text-lg hover:text-blue-700 <%= isActive("/livre/gestion", request.getRequestURI()) %>" style="margin-right: 10px;">
                <span class="flex items-center transition-colors">
                    Livres
                </span>
            </a>
            <a href="/membre/gestionMembre" class="text-lg hover:text-blue-700 <%= isActive("/membre/gestionMembre", request.getRequestURI()) %>" style="margin-right: 10px;">
                <span class="flex items-center transition-colors">
                    Membres
                </span>
            </a>
            <a href="/pret/gestionPret" class="text-lg hover:text-blue-700 <%= isActive("/pret/gestionPret", request.getRequestURI()) %>" style="margin-right: 10px;">
                <span class="flex items-center transition-colors">
                    Prêts
                </span>
            </a>
            <a href="/rendre/gestionRendu" class="text-lg hover:text-blue-700 <%= isActive("/rendre/gestionRendu", request.getRequestURI()) %>">
                <span class="flex items-center transition-colors">
                    Rendus
                </span>
            </a>
            <a href="/pret/retardataire" class="text-lg hover:text-blue-700 ml-5 <%= isActive("/pret/retardataire", request.getRequestURI()) %>">
                <span class="flex items-center transition-colors">
                    Non rendu
                </span>
            </a>
        </div>
    </div>
</nav>
