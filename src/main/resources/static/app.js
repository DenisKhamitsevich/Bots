const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/logs'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/logs', (message) => {
        selectMessageHandler(message.body)
    });
    getBots()
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
    disconnect()
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
    if (stompClient.isConnected) {
        getBots()
    }
}

function disconnect() {
    stompClient.deactivate();
    console.log("Disconnected");
}

function selectMessageHandler(message) {
    let parsedMessage = JSON.parse(message);
    return parsedMessage.type === 'LOG' ? showLogs(parsedMessage.data) : showBots(parsedMessage.data)
}

function showLogs(message) {
    $("#logs").append("<tr><td>" + message + "</td></tr>");
}

function showBots(bots) {
    $("#bots").empty()
    bots.forEach(function (bot) {
        const botInfo = "id: " + bot.id + ", name: " + bot.name;
        $("#bots").append("<tr><td>" + botInfo + "</td></tr>");
    })
}

function getBots() {
    stompClient.publish({
        destination: "/app/bots",
    });
}

function destroyBot() {
    stompClient.publish({
        destination: "/app/delete/bot",
        body: $("#delete-bot-id").val()
    });
}

function createTaskForBotById() {
    stompClient.publish({
        destination: "/app/task/create/by-id",
        body: $("#create-task-bot-id").val()
    });
}

function createTaskForBotByType() {
    stompClient.publish({
        destination: "/app/task/create/by-type",
        body: $("#create-task-bot-type").val()
    });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#delete-bot-button").click(() => destroyBot());
    $("#create-task-for-bot-by-id-button").click(() => createTaskForBotById());
    $("#create-task-for-bot-by-type-button").click(() => createTaskForBotByType());
});

