<head>
    <style>
        body
        {
            display:flex;
            flex-direction: column;
        }
        h2
        {
            font-family:  "Courier";
            background:  rgb(43,187,173);
            color: white;
            border-top: 25%;
            text-align: center;
        }
        p
        {
            border-color: rgb(43,187,173);
            border-width: 4px;
            text-align:center;
        }

        .btn {

            background: rgb(43,187,173);
            border: unset;
            border-radius: 28px;
            font-family: "Courier";
            color: #ffffff;
            font-size: 20px;
            padding: 10px 20px 10px 20px;
            text-decoration: none;
            margin-left: 45%;
        }
    </style>

</head>

<body>

<h2>password reset on matcha.com</h2>

<p>You (or someone else) entered this email address when trying to change the password on matcha account.
    If you want to reset your password tap on link bellow to create new password.</p>

<form method="post" action="http://localhost:8080/matcha/authorization/change/">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="salt" value="${salt}">
    <input class="btn"type="submit" value="Change password">
</form>
</body>