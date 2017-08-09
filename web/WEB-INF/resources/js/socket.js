var home = "http://localhost:8080/matcha";

function connect(addr, callback) {
    ws = new WebSocket(addr);
    ws.onmessage = callback;
}
