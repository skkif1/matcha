<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; minimum-scale=1.0; user-scalable=no; target-densityDpi=device-dpi" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/search.css"/>" rel="stylesheet">
</head>
<body>

<%@include file="header.jsp" %>

<div class="content">

    <%@include file="side_bar.jsp" %>

    <div class="search">
        <div class="search_creeterea">
            <nav>
                <div class="nav-wrapper sort">
                    <ul class="left hide-on-med-and-down sort_creeterea">
                        <li><a href="">age</a></li>
                        <li><a href="">location</a></li>
                        <li><a href="">rating</a></li>
                        <li><a href="">interests</a></li>
                    </ul>
                </div>
            </nav>
            <div class="gap_creeterea">
                <div class="creeterea">
                    <label>age</label>
                    <div class="input-field col s6">
                        <input placeholder="min age" id="age_min" type="text" class="validate">
                    </div>
                    <div class="input-field col s6">
                        <input placeholder="max age" id="age_max" type="text" class="validate">
                    </div>
                </div>
                <div class="creeterea">
                    <label>location range</label>
                    <div class="input-field col s6">
                        <input placeholder="location" id="location" type="text" class="validate">
                    </div>
                </div>
                <div class="creeterea">
                    <label>rating</label>
                    <div class="input-field col s6">
                        <input placeholder="min rating" id="rating" type="text" class="validate">
                    </div>
                </div>
                <div class="creeterea">
                    <label>tag</label>
                    <div class="input-field col s6">
                        <input placeholder="type and press enter" id="tag" type="text" class="validate" onkeypress="addTag(event);">
                    </div>
                    <div id="tag_holder">
                    </div>
                </div>
            </div>
            <div class="button_wrapper">
                <a class="waves-effect waves-light btn" onclick="searchRequest()"><i class="material-icons left">cloud</i>button</a>
            </div>
            <div id="tip"></div>
        </div>
    <div class="result">
        <ul class="collection" id="result_collection">

        </ul>
    </div>
    </div>

</div>
</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/search.js" />"></script>
</html>
