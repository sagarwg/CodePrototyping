<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Dashboard</title>

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

/* Positive value: Green */
.positive {
    color: green;
}

/* Negative value: Red */
.negative {
    color: red;
}

/* Zero or Neutral: Gray */
.neutral {
    color: gray;
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
		<h1 style="flex-grow: 1; text-align: center;">User Subscribed
			Stocks</h1>
		<%-- Welcome,
		<%=request.getAttribute("username")%>! || <a href="/logout" align>Logout</a> --%>
	</div>
	<!-- <div class="horizontal-line" style="text-align: Left;"></div> -->
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

	<table border="1" id="UserStocksTable" align="center">
		<thead>
			<tr>
				<th>Ticker</th>
				<th>Company</th>
				<th>Price</th>
				<th>Trade-In Price</th>
				<th>No Of Shares</th>
				<th>Request Shares</th>
				<th>Difference</th>
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
                
                if (message.type === "all-user-stocks") {
                    // Handle 'all-stocks' data
                    updateUserStockTable(message.data);
                }
              };
                
                // Parse the incoming JSON data
               // var stockData = JSON.parse(event.data);

                function updateUserStockTable(stockData) {
                // Clear the existing table rows
                var tbody = $('#UserStocksTable tbody');
                tbody.empty();
                


                // Loop through each stock and append a new row
                $.each(stockData, function(i, stock) {
                	
                    // Determine the CSS class based on the difference
                    var differenceClass = 'neutral'; // Default class for neutral (0)
                    if (stock.difference > 0) {
                        differenceClass = 'positive';  // Apply 'positive' class for positive values
                    } else if (stock.difference < 0) {
                        differenceClass = 'negative';  // Apply 'negative' class for negative values
                    }
                    var row = "<tr>"
                        + "<td>" + stock.tickerSymbol + "</td>"
                        + "<td>" + stock.companyName + "</td>"
                        + "<td>" + stock.currentPrice + "</td>"
                        + "<td>" + stock.tradeInPrice + "</td>"  // Display Trade-In Price
                        /* + "<td><input type='number' value='" + stock.noOfShares + "' id='noOfShares'></td>" */
                        + "<td>" + stock.noOfShares + "</td>"
                        /* +  "<td><input type='number' value='" + stock.noOfShares + "' id='noOfShares'></td>" */
                        +  "<td><input type='number' id='requestShares'></td>"
                        + "<td>" + stock.difference + "</td>"
                        + "<td>" + stock.marketCap + "</td>"
                        + "<td><button class='subscribe-btn' data-ticker='" 
                        + stock.tickerSymbol + "'>Unsubscribe</button></td>" 
                        +"</tr>";
                    tbody.append(row);
                });
            }

            // Handle WebSocket error event
            socket.onerror = function(error) {
                console.error("WebSocket error:", error);
            };

            // Handle WebSocket close event
            socket.onclose = function(event) {
                console.log("WebSocket connection closed.");
            };

            $(document).on('click', '.subscribe-btn', function() {
                var btn = $(this);
                var ticker = btn.data('ticker');
                var action = btn.text().toLowerCase();
                $.ajax({
                    url: '/api/stockuser/stocksTradeApid',
                    type: 'DELETE',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        ticker: ticker,
                    }),
                    success: function(response) {
                        //alert(response);
                        //btn.text(action === 'subscribe' ? 'Buy' : 'Sell');
                        alert(response.message);  // Display whatever message the server sends back
            	        let currentText = btn.text(); // get current button text
            			//btn.text('Buy') change name after clicked on button.
                    }
                });
            });
        });
</script>
<!-- <script>
function loadStocks() {
    $.ajax({
        url: '/api/stockuser/allUserStockData',
        type: 'GET',
        dataType: 'json',
        success: function(response) {

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

            $.each(stocks, function(i, stock) {
            	var row = "<tr>" +
                "<td>" + stock.tickerSymbol + "</td>" +
                "<td>" + stock.companyName + "</td>" +
                "<td>" + stock.currentPrice + "</td>" +
                "<td>" + stock.tradeInPrice + "</td>" +
                "<td>" +  + "</td>" +
                "<td>" + stock.marketCap + "</td>" +
                "<td><button class='subscribe-btn' data-ticker='" 
                    + stock.tickerSymbol + "'>Sell</button></td>" +
                "</tr>";
                tbody.append(row);
            });
        },
        error: function(xhr, status, error) {
            console.error("AJAX Error:", error);
        }
    });
}


$(document).on('click', '.subscribe-btn', function() {
    var btn = $(this);
    var ticker = btn.data('ticker');
    var action = btn.text().toLowerCase();
    $.ajax({
        url: '/api/stockuser/stocksTradeApid',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify({
            ticker: ticker,
        }),
        success: function(response) {
            //alert(response);
            //btn.text(action === 'subscribe' ? 'Buy' : 'Sell');
            alert(response.message);  // Display whatever message the server sends back
	        let currentText = btn.text(); // get current button text
			btn.text('Buy')
        }
    });
});

$(document).ready(loadStocks);
</script> -->

</body>
</html>
