<html>
<head class="body">
    <title>Edit page</title>
</head>
<div align="center">
<body>
<h2> Username: ${username} </h2>
<h2> First name: ${first_name}</h2>
<h2>Last name: ${last_name}</h2>
<h2> Date of birth: ${dob} </h2>

<form method="post">
    <p>${error}</p>
    <label >New Username:</label><br>
    <input type="hidden" name="edit_form" value="change_username"/>
    <input type="text" name="new_username" required />
    <input type="submit" name="edit_username" value="change username" required/>
</form>


<form method="post">
    <p>${password_error}</p>
    <label >New Password:</label><br>
    <input type="password" name="new_password" required />
    <input type="submit" name="edit_password" value="change password" required/>
    <br>
    <label >Retype Password to Confirm:</label><br>
    <input type="password" name="confirm_password"   required />
    <input type="submit" name="edit_password" value="confirm password" required/>
</form>

<form method="post">
    <label >Change First Name:</label><br>
    <input type="text" name="new_first_name"required />
    <input type="submit" name="edit_first_name" value="change" required/>
</form>

<form method="post">
    <label >Change Last Name:</label><br>
    <input type="text" name="new_last_name"  required />
    <input type="submit" name="edit_last_name" value="change" required/>
</form>

<form method="post">
    <label >Change Date of Birth:</label><br>
    <input type="text" name="new_dob" placeholder="YYYY/MM/DD" required />
    <input type="submit" name="edit_dob" value="change" required/>
</form>

<form method="post">
    <input type="submit" name="back" value="Back to home page"/>
</form>
</body>
</div>
</html>


    <style>
        .body {
            background-image: url("https://image.freepik.com/free-vector/pattern-abstract-hand-drawn-collection_23-2148591063.jpg");
        }
    </style>
