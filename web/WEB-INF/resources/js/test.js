


$(document).ready(function () {
    connect();
});




var ws;

function connect() {
    ws = new WebSocket('ws://localhost:8080/matcha/name');
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
    console.log('something');
    ws.send('something');
}
