<html>
<head>
    <title>Login</title>
</head>
<body>
<center>
<h1>Indian Stock Trading</h1>
<h2>Login</h2>

<form action="api/user/getUserLogin" method="get">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required />
    <br/><br/>
    
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required />
    <br/><br/>
    
    <button type="submit">Login</button>
</form>
</center>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

</body>
</html>
