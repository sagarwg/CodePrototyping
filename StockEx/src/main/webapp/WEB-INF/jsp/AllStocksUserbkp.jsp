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
</style>
</head>
<body>
	<div
		style="display: flex; justify-content: space-between; width: 100%;">
		<h1 style="flex-grow: 1; text-align: center;">User Subscribed
			Stocks</h1>
		Welcome,
		<%=request.getAttribute("username")%>! || <a href="/logout" align>Logout</a>
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

	<table border="1" id="stocksTable" align="center">
		<thead>
			<tr>
				<th>Ticker</th>
				<th>Company</th>
				<th>Price</th>
				<th>Trade-In Price</th>
				<th>Market Cap</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>

	<script>
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
</script>
</body>
</html>
