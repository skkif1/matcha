
function signUpUser()
{
    data ={
        login: $("#signup_form .login").val().trim(),
        password: $("#signup_form .password").val().trim(),
        email: trim($("#signup_form .email").val().trim())
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type:"POST",
        data: JSON.stringify(data),
        url:"http://localhost:8080/mvc/authorization/signUp",
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
        url:"http://localhost:8080/mvc/authorization/login",
        dataType: "json"
    });
}
