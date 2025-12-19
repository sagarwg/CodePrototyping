<center>
<h1>Indian Stock Trading</h1>
<h2>You have been logged out, Kindly Re-Login</h2>
<h2>Login</h2>

<form id="loginForm">
    Username: <input type="text" name="username" required /><br/><br/>
    Password: <input type="password" name="password" required /><br/><br/>
    <button type="submit">Login</button>
</form>
<center>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$("#loginForm").submit(function(e){
    e.preventDefault(); // prevent default form submission

    const data = {
        username: $("input[name='username']").val(),
        password: $("input[name='password']").val()
    };
	console.log("username :"+data.username);
	console.log("password :"+data.password);
    $.ajax({
        url: '/api/user/getUserLogin',
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json', 
        data: JSON.stringify(data),
        success: function(res){
           // alert(res.message); // optional message
            debugger;
            console.log("AJAX response:"+res.message);
            
            if(res.message == "Ok"){
                // Redirect browser to another page after successful login
                window.location.href = '/AllStocksUser'; // change this to your target page
            }
        },
        error: function(xhr){
            alert(xhr.responseJSON.message);
            // Optional: redirect to a login-failed page
            // window.location.href = '/login-failed';
        }
    });
});
</script>
