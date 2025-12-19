<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock Data - WebSocket</title>
    <script>
        // Wait for the DOM to be fully loaded before running the WebSocket connection and other scripts
        document.addEventListener("DOMContentLoaded", function () {
            var socket = new WebSocket('ws://localhost:8080/socketApi');
            var stockListDiv = document.getElementById('stockList');

            // Check if stockListDiv exists
            if (!stockListDiv) {
                console.error("stockList div not found");
                return;
            }

            // Open WebSocket connection
            socket.onopen = function(event) {
                console.log("Connected to WebSocket");
            };

            // Handle messages received from WebSocket
            socket.onmessage = function(event) {
                var stockData = JSON.parse(event.data); // Parse the JSON string to JavaScript object
                updateStockList(stockData);
            };

            // Handle WebSocket errors
            socket.onerror = function(error) {
                console.error("WebSocket Error: " + error);
            };

            // Handle WebSocket close event
            socket.onclose = function(event) {
                console.log("Disconnected from WebSocket");
            };

            // Function to update the list of stocks in the DOM
            function updateStockList(stocks) {
                stockListDiv.innerHTML = ""; // Clear any existing stock data
                stocks.forEach(function(stock) {
                    var stockItem = document.createElement('div');
                    stockItem.classList.add('stock-item');
                    stockItem.innerHTML = `
                        <h3>${stock.tickerSymbol} - ${stock.companyName}</h3>
                        <p><strong>Exchange:</strong> ${stock.exchange}</p>
                        <p><strong>Sector:</strong> ${stock.sector}</p>
                        <p><strong>Current Price:</strong> ₹${stock.currentPrice}</p>
                        <p><strong>Trade-in Price:</strong> ₹${stock.tradeInPrice}</p>
                        <p><strong>Market Cap:</strong> ₹${stock.marketCap}</p>
                        <p><strong>Volume:</strong> ${stock.volume}</p>
                        <p><strong>P/E Ratio:</strong> ${stock.peRatio}</p>
                        <p><strong>EPS:</strong> ₹${stock.eps}</p>
                        <p><strong>Dividend Yield:</strong> ${stock.dividendYield * 100}%</p>
                        <p><strong>Last Updated:</strong> ${stock.lastUpdated ? stock.lastUpdated : 'N/A'}</p>
                        <hr>
                    `;
                    stockListDiv.appendChild(stockItem);
                });
            }
        });
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .stock-item {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        h3 {
            margin-top: 0;
        }

        hr {
            border: 1px solid #ddd;
        }

        p {
            margin: 5px 0;
        }

        strong {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Stock Data from WebSocket</h1>
    <div id="stockList">
        <!-- Stock data will be dynamically inserted here -->
    </div>
</body>
</html>
