var home = "http://10.111.7.1:8080/mvc";

function changePassword()
{
    var password = $("#change_form .password").val().trim();

    if (password !== $("#change_form .password_repeat").val().trim())
    {
        console.log("passwords are differ");
        return ;
    }


    // $.post({
    //     type:"POST",
    //     data: "password=" + password,
    //     url: home + "/authorization/changePassword",
    //     accepts: "application/json",
    //     success: function(json)
    //     {
    //
    //     }
    // });

    $.post( home + "/authorization/changePassword", { password: password})
        .done(function( json ) {

            if (json.action === "confirm")
                    {
                        window.location.href = home + "/authorization";
                    }else
                        window.location.href = home + "/error/404";  alert( "Data Loaded: " + data );
        });
}