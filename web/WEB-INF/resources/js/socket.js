var home = "http://localhost:8080/matcha";

function connect(addr) {
    ws = new WebSocket(addr);
    ws.onmessage = function(data){
        console.log(data);
    };
}
