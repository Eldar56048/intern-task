var stompClient = null;
 function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/rooms', async function (room) {
            const data = JSON.parse(room.body)
            console.log("DATA", data)
            showRoom(data);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
function updateLight(id) {
    console.log("ID ",id)
    stompClient.send("/app/rooms/"+id, {}, JSON.stringify({}));
}
function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val(), 'user':$("#username").val()}));
    $("#name").value="";
}

function showMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showRoom(room) {
     if($("#light")) {
         if($("#roomId").val()==room.id) {
             $("#light").html('Свет: ' + room.light.toString());
         }
     }
}

function showFile(file){
    $("#greetings").append(file);
}

$('#contactForm').submit(function () {
    sendContactForm();
    return false;
});