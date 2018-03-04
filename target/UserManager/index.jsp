<html>
    <head>
        <title>User Manager</title>
        <link rel="stylesheet" href="styles.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="createDataTables.js"></script>       
        <script src="index.js"></script>       
    </head>

    <body>
        <h2>User Manager</h2>
        <UL>
            <li><Button type="button" id="button_health">Get Health</li>   
            <DIV id="div_prelogin">
                <li >User <input type='text' id ='user' value='guest' /></li>
                <li >Password<input type='password' id ='password' value='password' /></li>
                <li><Button type="button" id="button_token">Login</li>   
            </DIV>
            <DIV id="div_navigation">
                <li><Button type="button" id="button_userid">Get user id</li>   
                <li><Button type="button" id="button_allusers">Get All Users</li>   
                <li><Button type="button" id="button_allapplication">Get All Applications</li>   
                <li >Applications<input type='text' id ='application_id' value='' /></li>
                <li><Button type="button" id="button_rolesByApplication">Get Roles By Application</li>   
                <li >User<input type='text' id ='users_id' value='' /></li>
                <li><Button type="button" id="button_usersByApplication">Get Users By Application</li>   
                <li><Button type="button" id="button_rolesByUsersAndApplication">Get Roles By User and Application</li>   
                <li><Button type="button" id="button_logout">logout</li>   
            </div>
        </UL>               
        <DIV id="div_display">
        </div>                           
    </body>
</html>
