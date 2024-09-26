const socket = new WebSocket("ws://localhost:8080/click"); // Adjust the URL as necessary

socket.onopen = () => {
    console.log("Connected to WebSocket server");
};

socket.onmessage = (event) => {
    if (event.data === "performClick") {
        console.log("Click event received");
        updateUI("Click event received");
    }
};

function updateUI(message) {
    const logContainer = document.getElementById("log-container");
    const timestamp = new Date().toLocaleString();
    const logEntry = document.createElement("p");
    logEntry.textContent = `${timestamp} - ${message}`;
    logContainer.appendChild(logEntry);
}
