<html>
<head>
    <style>
        body {
            background-image: url("https://image.freepik.com/free-vector/pattern-abstract-hand-drawn-collection_23-2148591063.jpg");
        }
    </style>
    <title>Edit page</title>
</head>
<div align="center">
<body>
<h1> Home Page </h1>
<h2>Welcome, ${user.getUsername()}</h2>
<h2>first name : ${user.getFirstName()}</h2>
<h2>last name : ${user.getLastName()}</h2>
<h2>Date of Birth : ${user.getDob()}</h2>

<p>Register new user here </p>
<form method="post">
    <input type="text" name="adding_username" placeholder="username" required /><br>
    <input type="password" name="adding_password" placeholder="password" required/><br>
    <input type="password" name="confirm_password" placeholder="confirm password" required><br>
    <input type="submit" name="add_user" value="Add user" required/>
</form>
    <p>${adding_error}</p>

<p>${removing_error}</p>


<table border="2">
    <tr><th>Users</th><th>Actions</th></tr>
    <c:forEach items="${userList}" var="usr">
        <tbody style="vertical-align: center">
        <tr>
            <td>
                    ${usr}
            <td>
                <form method="post">
                    <input type="hidden" name="user_to_use" value="${usr}"/>
                    <c:choose>
                        <c:when test="${usr!=username}">
                            <input type="submit" name="removing_user" value="remove" onclick="{return confirm('Are you sure you want to remove this user?')}"/>
                        </c:when>
                        <c:otherwise> Sorry, cant remove yourself <br> </c:otherwise>
                    </c:choose>
                    <input type="submit" name="do_edit" value="edit" />
                </form>
            </td>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>

    <br>

    <form method="post">
        <input type="submit" name="logout" value="Log out"/>
    </form>

</body>
</div>
</html>

