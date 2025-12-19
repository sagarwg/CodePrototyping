<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebSocket with JSP</title>
    <script type="text/javascript">
        var socket;
        var messageArea;

        // Function to establish the WebSocket connection
        function connectWebSocket() {
            // Connect to the WebSocket
            socket = new WebSocket('ws://localhost:8080/socketApi');

            // When WebSocket is connected
            socket.onopen = function(event) {
                console.log('WebSocket is connected!');
                document.getElementById("status").innerText = "Connected to WebSocket!";
            };

            // When WebSocket receives a message
            socket.onmessage = function(event) {
                // Update the page with the received message
                var message = event.data;
                messageArea.innerHTML += "<p>" + message + "</p>";
            };

            // When WebSocket encounters an error
            socket.onerror = function(event) {
                console.log('WebSocket Error: ', event);
                document.getElementById("status").innerText = "Error connecting to WebSocket!";
            };

            // When WebSocket is closed
            socket.onclose = function(event) {
                console.log('WebSocket connection closed');
                document.getElementById("status").innerText = "Disconnected from WebSocket!";
            };
        }

        // Function to close the WebSocket connection
        function closeWebSocket() {
            if (socket) {
                socket.close();
            }
        }

        // Initialize WebSocket connection when page loads
        window.onload = function() {
            messageArea = document.getElementById("messages");
            connectWebSocket(); // Establish connection when page loads
        };

        // Close WebSocket on page unload
        window.onbeforeunload = function() {
            closeWebSocket();
        };
    </script>
</head>
<body>
<center>
    <h1>Today's Stock Market</h1>

    <div id="status" style="color: green;">Connecting to WebSocket...</div> <button onclick="closeWebSocket()">Close WebSocket</button>

    <div id="messages" style="margin-top: 20px; font-family: Arial, sans-serif; font-size: 16px;">
        <!-- Messages from the WebSocket server will appear here -->
    </div>

    
    
  </center>
</body>
</html>
