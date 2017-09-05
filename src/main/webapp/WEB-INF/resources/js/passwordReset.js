var home = "http://localhost:8080/matcha";

function changePassword() {
    var loader = $("#loader_reset");
    loader.css("visibility", "visible");

    var pass = $("#change_form").find(".password");
    var repeat = $("#change_form").find(".password_repeat");


    if (pass.val() === repeat.val() && validatePassword(pass)) {

        data =
            {
                password: pass.val()
            };
        $.ajax({
            type: "POST",
            data: data,
            url: home + "/authorization/newPassword",
            error: function () {
                loader.css("visibility", "hiden");
            },
            success: function (json) {
                loader.css("visibility", "hiden");
                if (json.status === "OK") {
                    $('#onSuccess').modal('open');
                } else {
                    for (var i = 0; i < json.data.length; i++) {
                        Materialize.toast(json.data[i], 7000);
                    }
                }
            }
        });
    } else {
        loader.css("visibility", "hiden");
        Materialize.toast("Passwords do not match", 7000);
    }
}
