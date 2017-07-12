var home = "http://localhost:8080/matcha";

window.onload = function () {
    $('.chips').material_chip(
        {
            placeholder: '+Interes'
        }
    );

    $('.carousel').carousel();
    $('select').material_select();
    $('#file').change(uploadFiles());
};

function changeUserInfo()
{
    var select_info = $(".input_info");

    var data = {};

    for (var i = 0; i < select_info.length; i++)
    {
        data[select_info[i].name] = select_info.eq(i).val();
    }

    for(var key in data)
    {
        console.log(key + " :" + data[key]);
    }

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type:"POST",
            data: JSON.stringify(data),
            dataType: "json",
            url: home + "/info/update/",
            success: function (json) {
                if (json.action === "error") {
                    for (var i = 0; i < json.data.length; i++) {
                        console.log(json.data[i]);
                    }
                }
            }
        }
    );
}

function uploadPhoto()
{
    var files = $(".loader")[0].files;
    var form = new FormData();

    $.each(files, function (i, file) {
        form.append("files", file);
        console.log(i);
    });

    $.ajax(
        {
            url: home + "/info/updatePhoto",
            data: form,
            type: 'POST',
            processData: false,
            contentType: false,
            success: function (json) {
                $.each(json.data, function (i, message) {
                    console.log(message);
                })
            }
        });
    console.log(form);
}


function editUser() {

    var data =
        {
            email: $(".input_email_info")[0].value.trim()
        };

    $.ajax({
        url: home + "/info/updateEmail",
        data: data,
        type: "POST",
        success: function (json) {
            json = JSON.parse(json);
            $.each(json.data, function (i, message) {
                console.log(message);
            })
        }
    });
}


function uploadFiles() {
        var files = $('#file').prop('files');
        var photoContainer = $('.carousel').prop('children');

        for (var i = 0; i < files.length; i++) {
            console.log(files[i]);
        }
}


function changeCategory(elem)
{
    var category = $('.collection').children;
    console.log(category.length);
    console.log(elem);

    for (var i = 0; i < category.length; i++)
    {
        category.addClass("active");

    }

}