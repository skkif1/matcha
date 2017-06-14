<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Your profile info</title>

    <title>Welcome to matcha</title>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="<c:url value="/resources/js/userInformation.js" />"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

</head>
<body>


<div id="user_info">
    <label>sex :</label>
    <select class="input_info sex" name="sex">
        <option>Man</option>
        <option>Women</option>
        <option>Trans</option>

    </select>

    <label>age :</label>
    <input class="input_info age" name="age">age </input>

    <label>country :</label>0
    <input class="input_info " id="country" name="country">country </input>

    <label>state :</label>
    <input class="input_info state" name="state">state</input>

    <label>about me :</label>
    <input class="input_info about_me" name="aboutMe">about me</input>

    <label>intrests :</label>
    <input class="input_info intrest" name="interests">intrests </input>

    <label>sex prefernces :</label>
    <select class="input_info sex_pref" name="sexPref">

        <option>Heterosexual</option>
        <option>Homosexual</option>
        <option>Bisexual</option>
        <option>Transgender</option>

    </select>


    <button id="edit_info" onclick="changeUserInfo()">Save changes</button>
</div>


<div id="user_photo">
    <label>photo :</label>
    <div class="photo_info">
        <div class="photo"><img src=""></div>
    </div>

    <div class="input_photo_info">
        <input type="file"  class="loader" multiple>
    </div>

    <button  onclick="uploadData()">submit changes</button>

</div>

<button onclick="btceRequest(this)">btce</button>

</body>
</html>
