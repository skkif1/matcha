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

<h2>Thanks for signing up.</h2>

    <p>We require you to "confirm" your registration to ensure that the E-mail address you entered is correct. This prevents unwanted spam and malicious abuse.

        To activate your account, simply click on the following link:</p>

<form method="post" action="http://localhost:8080/matcha/authorization/confirm/">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="salt" value="${salt}">

    <input class="btn" type="submit" value="Activate acount">
</form>