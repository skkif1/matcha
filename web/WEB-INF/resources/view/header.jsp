<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<div class="header">
    <div class="content_holder">
        <div id="logo">
            <div id="res_menu" class="waves-effect waves-light" onclick="displayMenu()">
                <i class="material-icons">&#xE5D2;</i>
            </div>
            <span onclick="location.href = 'http://localhost:8080/matcha'">Matcha</span>
        </div>
        <div id="logout">
            <ul id="dropdown1" class="dropdown-content">
                <li><a href="http://localhost:8080/matcha/authorization/logout">log out</a></li>
            </ul>

            <ul>
                <li><a class="dropdown-button" data-activates="dropdown1">More<i
                        class="material-icons right">arrow_drop_down</i></a></li>
            </ul>
        </div>
    </div>
</div>

