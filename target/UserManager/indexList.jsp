<html>
    <head>
        <title>User Manager</title>
        <link rel="stylesheet" href="styles.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="usermanagerList.js"></script>       
    </head>

    <body>
        <h2>User Manager</h2>
        <DIV id="div_Applications">    
            <h3>Applications</h3>
            <UL id="select_Applications" >

            </UL>
        </DIV>
        <DIV id="div_Roles" >
            <h3>Roles</h3>
            <UL id="select_Roles">

            </UL>

        </DIV>
        <DIV id="div_Permissions" >
            <h3>Permissions</h3>
            <UL id="select_Permissions">

            </UL>

        </DIV>
        <DIV id="div_User">
            <h3>Users</h3>
            <input type="button" id="button_refreshUsers" value="refresh">
            <UL id="select_Users">

            </UL>

        </DIV>

    </body>
</html>
