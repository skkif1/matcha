var home = "http://localhost:8080/matcha";

$(document).ready(function () {
    connect();
});

var ws;

function connect() {
    ws = new WebSocket('ws://localhost:8080/matcha/user/');
    ws.onmessage = function(data){
        console.log(data);
    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    console.log("Disconnected");
}

function sendName() {

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
        url: home + "/chat/send",
        dataType: "json",
        success: function (json) {
            if (json.status === "OK")
            {
            }
        }
    });
}



function connectMore() {
    ws = new WebSocket('ws://localhost:8080/matcha/more/');
    ws.onmessage = function (data) {
        console.log(data);
    }
}


function sendMore() {
    ws.send('to one more');
}
