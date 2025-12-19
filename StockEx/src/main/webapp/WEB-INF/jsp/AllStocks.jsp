<!DOCTYPE html>
<html>
<head>
<title>Indian Stocks</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
.horizontal-line {
	display: flex; /* Turn the container into a flexbox */
	justify-content: center; /* Center child items horizontally */
	align-items: center; /* Center child items vertically */
	/* height: 100vh; /* Make the container take full viewport height */
	*/
}

.horizontal-line div {
	margin-right: 10px; /* Space between items */
	padding: 10px; /* Optional padding */
	/* background-color: lightblue; /* Optional background color */
	*/
}

table, th, td {
  border: 2px solid black;
  border-collapse: collapse;
}
</style>
</head>
<body>
	<div
		style="display: flex; justify-content: space-between; width: 100%;">
		<h1 style="flex-grow: 1; text-align: center;">Indian Stocks</h1>
		<%-- Welcome,
		<%=request.getAttribute("username")%>! || <a href="/logout" align>Logout</a> --%>
	</div>
	<div class="horizontal-line" style="text-align: center;">
		<div>
			<center>
				<a href="/home" align>Home</a>
			</center>
		</div>
		<div>
			<center>
				<a href=/AllStocksUser>My Portfolio</a>
			</center>
		</div>
	</div>

	<!-- <table border="1" id="stocksTable" align="center"> -->
	<table border="2" id="stockTable" align="center">
		<thead>
			<tr>
				<th>Ticker</th>
				<th>Company</th>
				<th>Price</th>
				<th>Trade-In Price</th>
				<th>No Of Shares</th>
				<th>Request Shares</th>
				<th>Market Cap</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
 <script>
        $(document).ready(function() {
            // Establish WebSocket connection
            var socket = new WebSocket('ws://localhost:8080/socketApi');  // Replace with your WebSocket URL

            // Handle WebSocket open event
            socket.onopen = function(event) {
                console.log("WebSocket connection established.");
            };

            // Handle WebSocket message event
            socket.onmessage = function(event) {
                console.log("Received WebSocket message:", event.data);

                // Parse the incoming JSON data
                var message = JSON.parse(event.data);  // Now `message` contains both `type` and `data`
                
                if (message.type === "all-stocks") {
                    // Handle 'all-stocks' data
                    updateStockTable(message.data);
                }};
                
                // Parse the incoming JSON data
               // var stockData = JSON.parse(event.data);

                function updateStockTable(stockData) {
                // Clear the existing table rows
                var tbody = $('#stockTable tbody');
                tbody.empty();

                // Loop through each stock and append a new row
                $.each(stockData, function(i, stock) {
                    var row = "<tr>"
                        + "<td>" + stock.tickerSymbol + "</td>"
                        + "<td>" + stock.companyName + "</td>"
                        + "<td>" + stock.currentPrice + "</td>"
                        + "<td>" + stock.tradeInPrice + "</td>"  // Display Trade-In Price
                        /* + "<td>" + stock.noOfShares + "</td>" */
                        + "<td>" + stock.noOfShares + "</td>"
                        /* +  "<td><input type='number' value='" + stock.noOfShares + "' id='noOfShares'></td>" */
                        +  "<td><input type='number' id='requestShares'></td>"
                        + "<td>" + stock.marketCap + "</td>"
                        + "<td><button class='subscribe-btn' data-ticker='" + stock.tickerSymbol + "'>Subscribe</button></td>"
                        + "</tr>";
                    tbody.append(row);
                });
            };
           
            // Handle WebSocket error event
            socket.onerror = function(error) {
                console.error("WebSocket error:", error);
            };

            // Handle WebSocket close event
            socket.onclose = function(event) {
                console.log("WebSocket connection closed.");
            };

            // Example of handling 'Buy' button click
            $(document).on('click', '.subscribe-btn', function() {
                var btn = $(this);
                var row = btn.closest('tr');
                var ticker = btn.data('ticker');
                var tradeInPrice = row.find('td:nth-child(4)').text();             
                var input = document.getElementById("requestShares")
             	// Get the current value
             	var requestShares = input.value;

            	console.log("Number of Shares:", requestShares);
                
				debugger;
				console.log("tradeInPrice: "+tradeInPrice);
                // Send the trade request to the server
                $.ajax({
                    url: '/api/stockuser/stocksTradeApiu',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ ticker: ticker,
                    	tradeInPrice: tradeInPrice,
                    	requestShares:requestShares}),
                  /*   success: function(response) {
                        // Handle success
                        alert(response.message);  // Show success message
                    },
                    error: function(xhr, status, error) {
                        // Handle error
                        alert("Error: " + error);
                    } */
                    success : function(response, status, xhr) {
    					// Universal success/failure message
    					alert(response.message); // Display whatever message the server sends back
    					let currentText = btn.text(); // get current button text
    					btn.text('Buy')
    				},
    				success : function(response) {
    					console.log("response.message :" + response.message);
    					alert(response.message);
    					//btn.text(action === 'subscribe' ? 'Buy' : 'Sell');
    					let currentText = btn.text(); // get current button text
    					//btn.text(currentText === 'Buy' ? 'Sell' : 'Buy');
    					btn.text('Buy')
    				},
    				error : function(xhr, status, error) {
    					// Handle any error (like 409, 500, etc.)
    					let errorMessage = "An unexpected error occurred"; // Default error message

    					// If response is JSON and has a message, use it
    					try {
    						if (xhr.responseJSON && xhr.responseJSON.message) {
    							errorMessage = xhr.responseJSON.message; // Use the error message from response
    						}
    					} catch (e) {
    						console.error("Error parsing error response:", e);
    					}

    					// Show the error message
    					alert(errorMessage);
    				}
                });
            });
        });
</script>
	<!-- <script>
	
		function loadStocks() {
			$
					.ajax({
						url : '/api/stockexchange/allStockData',
						type : 'GET',
						dataType : 'json',
						success : function(response) {

							// Debug logs (keep while testing)
							console.log("Full response:", response);
							console.log("Stock message:", response.message);
							console.log("Stock success:", response.success);
							console.log("Stock exception:", response.exception);
							console.log("Stock status:", response.status);
							console.log("Stock data:", response.data);

							var stocks = response.data; // 👈 extract array

							var tbody = $('#stocksTable tbody');
							tbody.empty();

							$
									.each(
											stocks,
											function(i, stock) {
												var row = "<tr>"
														+ "<td>"
														+ stock.tickerSymbol
														+ "</td>"
														+ "<td>"
														+ stock.companyName
														+ "</td>"
														+ "<td>"
														+ stock.currentPrice
														+ "</td>"
														+ "<td>"
														+ stock.currentPrice
														+ "</td>"
														+ "<td>"
														+ stock.marketCap
														+ "</td>"
														+ "<td><button class='subscribe-btn' data-ticker='" 
                    + stock.tickerSymbol + "'>Buy</button></td>"
														+ "</tr>";
												tbody.append(row);
											});
						},
						error : function(xhr, status, error) {
							console.error("AJAX Error:", error);
						}
					});
		}

		$(document).on('click', '.subscribe-btn', function() {
			var btn = $(this);
			var ticker = btn.data('ticker');
			var action = btn.text().toLowerCase();
			$.ajax({
				url : 'api/stockuser/stocksTradeApiu',
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify({
					ticker : ticker,
				}),

				success : function(response, status, xhr) {
					// Universal success/failure message
					alert(response.message); // Display whatever message the server sends back
					let currentText = btn.text(); // get current button text
					btn.text('Buy')
				},
				success : function(response) {
					console.log("response.message :" + response.message);
					alert(response.message);
					//btn.text(action === 'subscribe' ? 'Buy' : 'Sell');
					let currentText = btn.text(); // get current button text
					//btn.text(currentText === 'Buy' ? 'Sell' : 'Buy');
					btn.text('Buy')
				},
				error : function(xhr, status, error) {
					// Handle any error (like 409, 500, etc.)
					let errorMessage = "An unexpected error occurred"; // Default error message

					// If response is JSON and has a message, use it
					try {
						if (xhr.responseJSON && xhr.responseJSON.message) {
							errorMessage = xhr.responseJSON.message; // Use the error message from response
						}
					} catch (e) {
						console.error("Error parsing error response:", e);
					}

					// Show the error message
					alert(errorMessage);
				}
			});
		});

		$(document).ready(loadStocks);
	</script> -->
</body>
</html>
