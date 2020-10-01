var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
}

function connect() {
	ws = new WebSocket('ws://localhost:9091/socket');
	setConnected(true);
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Websocket is in disconnected state");
}

function sendData() {
	var data = JSON.stringify($("#subscription").val());
	ws.send(data);
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendData();
	});
});