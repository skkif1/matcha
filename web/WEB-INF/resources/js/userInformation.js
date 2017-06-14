var home = "http://localhost:8080/mvc";

window.onload = function () {

};

function changeUserInfo()
{
    var select_info = $(".input_info");
    var photo = $(".input_photo_info");

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
            url: home + "/info/edit/",
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




function uploadData()
{
    var files = $(".loader").files;
    console.log(files.length);
}


function btceRequest()
{

    $.get("https://btc-e.com/api/3/trades/btc_usd", function(data)
    {
        console.log(data.toString());
    });

}
