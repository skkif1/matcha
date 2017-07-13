
function validateUserName(input)
{
    input = $(input);
    if (input.val().length > 32 || input.val().length < 3)
    {
        Materialize.toast('min length of user name is 3 char and max 32 char', 7000);
        return false;
    }
    return true;
}

function validateEmail(input)
{
    input = $(input);
    if(input.val().length < 3 || input.val().length > 255)
    {
        Materialize.toast('email can contains minimum 3 and maximum 255 chars<br>', 7000);
        return false;
    }
    return true;
}

function validatePassword(input)
{
    input = $(input);
    reg = new RegExp("^(?=.*[a-z])(?=.*[0-9])(?=.{8,})");
    if(!reg.test(input.val()))
    {
        Materialize.toast('Password must contain one numeric charecter one lowacase charecter<br>' +
            'and has minimum 8 char length', 7000);
        return false;
    }
    return true;
}

function validateLoginForm()
{
    if (validateEmail($("#login_form .email")) && validatePassword($("#login_form .password")))
            loginUser();
}

function validatePasswords(input) {
    input = $(input);
    var repeat = $("#auth").find(".password");

    if(input.val() !== repeat.val())
    {
        Materialize.toast('passwords do not match', 7000);
        return false;
    }
    return true;
}

function validateAuthForm() {
    if (validateEmail($("#signup_form .email")) && validateUserName($("#signup_form .first_name"))
                && validateUserName($("#signup_form .last_name")) && validatePassword($("#signup_form .password"))
                && validatePasswords($("#signup_form .password_repeat")))
    {
      signUpUser();
    }
}