
function signUpUser()
{
    data ={
        login: $("#signup_form .login").val().trim(),
        password: $("#signup_form .password").val().trim(),
        email: $("#signup_form .email").val().trim()
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(data),
        url:"http://10.111.7.1:8080/mvc/authorization/signUp",
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
        url:"http://10.111.7.1:8080/mvc/authorization/login",
        dataType: "json",
        success: function (json) {
            if (json.action === "confirm")
            {
                window.location.href = "http://10.111.7.1:8080/mvc/user";
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

function resetPasswrd() {
    data = {
        email: $("#reset_form").find(".email").val().trim(),
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(data),
        url:"http://10.111.7.1:8080/mvc/authorization/resetPassword",
        dataType: "json",
        success: function (json) {
            if (json.action === 'error')
            {
                for (var i = 0; i < json.data.length; i++)
                {
                    console.log(json.data[i]);
                }
            }
            if (json.action === 'confirm')
            {
                $(status).innerHTML = json.data;
                console.log(json.data);
            }
        }
    });
}