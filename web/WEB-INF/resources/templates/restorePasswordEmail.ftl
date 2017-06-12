<h2>You (or someone else) entered this email address when trying to change the password on matcha account.
        If you want to reset your password tap on link bellow to create new password.</h2>

<form method="post" action="http://10.111.7.3:8080/mvc/authorization/changeRequest/">
    <input type="hidden" name="email" value="${email}">
    <input type="hidden" name="salt" value="${salt}">
    <input type="submit" value="Change password">
</form>
