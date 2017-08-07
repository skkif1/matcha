var home = "http://localhost:8080/matcha";

$(document).ready(function () {
    connect();
});


function connect() {
    ws = new WebSocket('ws://localhost:8080/matcha/user/');
    ws.onmessage = function(data){
        console.log(data);
    };
}


function test() {

    data =
        {
            message: "Hello world!",
            conv_id: 1
        };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(data),
        url: home + "/test",
        dataType: "json",
        success: function (json) {
            if (json.status === "OK")
            {
            }
        }
    });
}
