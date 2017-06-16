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



    <label>name :</label>
    <input class="input_info" name="age"/>

    <label>last name :</label>
    <input class="input_info" name="age"/>


    <label>sex :</label>
    <select class="input_info" name="sex">
        <option>Man</option>
        <option>Women</option>
        <option>Trans</option>
    </select>

    <label>age :</label>
    <input class="input_info" name="age"/>

    <label>country :</label>0
    <input class="input_info" id="country" name="country"/>

    <label>state :</label>
    <input class="input_info" name="state"/>

    <label>about me :</label>
    <input class="input_info" name="aboutMe"/>

    <label>intrests :</label>
    <input class="input_info" name="interests"/>

    <label>sex prefernces :</label>
    <select class="input_info sex_pref" name="sexPref">
        <option>Heterosexual</option>
        <option>Homosexual</option>
        <option>Bisexual</option>
        <option>Transgender</option>
    </select>


    <button id="edit_info" onclick="changeUserInfo()">save</button>
</div>


<div id="user_photo">
    <label>photo :</label>
    <div class="photo_info">
        <div class="photo"><img src=""></div>
    </div>

    <div class="input_photo_info">
        <input type="file"  class="loader" multiple>
    </div>

    <button  onclick="uploadPhoto()">submit changes</button>

</div>


<div id="edit_email">

    <label>email :</label>
    <input class="input_email_info" name="lastName"/>

</div>

<button onclick="editUser()">save</button>

</body>
</html>
