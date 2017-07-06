<h2>Thanks for signing up.

    We require you to "confirm" your registration to ensure that the E-mail address you entered is correct. This prevents unwanted spam and malicious abuse.

    To activate your account, simply click on the following link:</h2>

<form method="post" action="http://localhost:8080/matcha/authorization/confirm/">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="salt" value="${salt}">

    <input type="submit" value="Activate acount">
</form>