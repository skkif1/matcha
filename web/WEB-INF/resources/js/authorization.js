var home = "http://localhost:8080/matcha";

function signUpUser()
{
    var loader = $("#loader_auth");
    loader.css("visibility", "visible");

    data ={
        password: $("#signup_form").find(".password").val().trim(),
        email: $("#signup_form .email").val().trim(),
        firstName: $("#signup_form .first_name").val().trim(),
        lastName: $("#signup_form .last_name").val().trim()
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(data),
        url:home + "/authorization/signUp",
        dataType: "json",
        error: function () {
            loader.css("visibility", "hiden");
        },
        success: function (json) {
            loader.css("visibility", "hiden");
            if (json.status === "OK")
            {
                console.log("sadasdasdasdofj[aoisdjhg");
                console.log($('#onSuccess'));
                $('#onSuccess').modal('open');
            }else
            {
                for (var i = 0; i < json.data.length; i++)
                {
                    Materialize.toast(json.data[i], 7000);
                }
            }
        }
    });
}

function loginUser()
{
    var loader = $("#loader_log");
    loader.css("visibility", "visible");
    data ={
        email: $("#login_form .email").val().trim(),
        password:$("#login_form .password").val().trim()
    };
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type:"POST",
            data: JSON.stringify(data),
            url: home + "/authorization/login",
            dataType: "json",
            error: function () {
                loader.css("visibility", "hiden");
            },
            success: function (json) {
                loader.css("visibility", "hiden");
                if (json.status === "OK")
                {
                    var log = $("#log").addClass("scale-out");
                    setTimeout(function(){
                        window.location.href = home;
                    }, 300);
                }else
                {
                    for (var i = 0; i < json.data.length; i++)
                    {
                        Materialize.toast(json.data[i], 7000);
                    }
                }
            }
        });
}

function resetPassword()
{
    var loader = $("#loader_reset");
    loader.css("visibility", "visible");

    data ={
        email: $("#reset .email").val().trim()
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(data),
        url: home + "/authorization/reset",
        dataType: "json",
        error: function () {
            loader.css("visibility", "hiden");
        },
        success: function (json) {
            loader.css("visibility", "hiden");
            if (json.status === "OK")
            {

            }else
            {
                for (var i = 0; i < json.data.length; i++)
                {
                    Materialize.toast(json.data[i], 7000);
                }
            }
        }
    });
}

function toSignUp() {
    var log = $("#log").addClass("scale-out");
    var auth = $("#auth");

    setTimeout(function(){
        log.css("display", "none");
        auth.css("display", "flex");
        auth.removeClass("scale-out");
        }, 250);
}

function toReset()
{
    var log = $("#log");
    var reset = $("#reset");
    log.addClass("scale-out");

    setTimeout(function(){
        log.css("display", "none");
        reset.css("display", "flex");
        reset.removeClass("scale-out");
    }, 250);
}

function toLogin() {
    var auth = $("#auth").addClass("scale-out");
    var reset = $("#reset").addClass("scale-out");
    var log = $("#log");

    setTimeout(function(){
        auth.css("display", "none");
        reset.css("display", "none");
        log.css("display", "flex");
        log.removeClass("scale-out");
        }, 250);
}
