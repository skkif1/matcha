var home = "http://localhost:8080/matcha";

function signUpUser()
{
    data ={
        login: $("#signup_form .login").val().trim(),
        password: $("#signup_form .password").val().trim(),
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
        success: function (json) {

            if (json.action === "error")
            {
                for (var i = 0; i < json.data.length; i++)
                {
                    console.log(json.data[i]);
                }
            }
        }
    });
}

function loginUser()
{
    data ={
        login: $("#login_form .login").val().trim(),
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
        success: function (json) {
            if (json.action === "confirm")
            {
                window.location.href = home + "/user";
            }else
            {
                for (var i =0; i < json.data.length; i++)
                {
                    console.log(json.data[i]);
                }
            }

        }
    });
}
