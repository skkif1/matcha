function validateUserName(input) {
    input = $(input);

    if (input.val().trim() === '')
        return false;

    req = new RegExp("^[a-zA-Z0-9_-]{3,32}$");
    if (!req.test(input.val())) {

        removeToast();
        var $toastContent = $('<section>min length of user name is 3 char and max 32 char and contain only alphabetical letters,' +
            ' disgits, and "_" , "-"</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return false;
    }
    return true;
}

function validateEmail(input) {
    input = $(input);

    if (input.val().trim() === '')
        return false;

    if (input.val().length < 3 || input.val().length > 255) {

        removeToast();
        var $toastContent = $('<section>email can contains minimum 3 and maximum 255 chars<br></section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return false;
    }
    return true;
}

function validatePassword(input) {
    input = $(input);

    if (input.val().trim() === '')
        return false;

    reg = new RegExp("^(?=.*[a-z])(?=.*[0-9])(?=.{8,})");
    if (!reg.test(input.val())) {

        removeToast();

        var $toastContent = $('<section>Password must contain one numeric charecter one lowercase charecter<br> and has minimum 8 char length</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return false;
    }
    return true;
}

function validateLoginForm() {
    if (validateEmail($("#login_form .email")) && validatePassword($("#login_form .password")))
        loginUser();
}

function validatePasswords(input) {
    input = $(input);
    var repeat = $("#auth").find(".password");

    if (input.val() !== repeat.val()) {
        removeToast();

        var $toastContent = $('<section>passwords do not match</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return false;
    }
    return true;
}

function validateAuthForm() {
    if (validateEmail($("#signup_form .email")) && validateUserName($("#signup_form .first_name"))
        && validateUserName($("#signup_form .last_name")) && validatePassword($("#signup_form .password"))
        && validatePasswords($("#signup_form .password_repeat"))) {
        signUpUser();
    }
}

function removeToast() {
    if ($('.toast').length === 3)
        $('.toast').each(function () {
            this.remove();
        });
}

function validateTags(tag) {
    tag = $(tag);
    req = new RegExp("^[a-zA-Z0-9_-]{3,32}$");
    if (!req.test(tag.val())) {

        removeToast();

        var $toastContent = $('<section>min length of #tag is 3 char and max 32 char <br/> and contain only alphabetical' +
            ' letters, disgits, and "_" , "-"</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return false;
    }
    return true;
}