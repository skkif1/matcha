var home = "http://10.111.8.3:8080/mvc";

function changePassword()
{
    var password = $("#change_form .password").val().trim();

    if (password !== $("#change_form .password_repeat").val().trim())
    {
        console.log("passwords are differ");
        return ;
    }

    $.post( home + "/authorization/changePassword", { password: password})
        .done(function( json ) {

            if (json.action === "confirm")
                    {
                        window.location.href = home + "/authorization";
                    }else
                        window.location.href = home + "/error/404";  alert( "Data Loaded: " + data );
        });
}