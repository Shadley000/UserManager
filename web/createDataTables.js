
function failed(){
    $("#div_display").append(buildFailed());
}

function buildFailed()
{   console.log("Failed");
    var p = document.createElement("P");
        p.innerHTML = "Failed";
        return p;
}

function buildUsersTable(usersList) {
    var p = document.createElement("TABLE");
    {
        var headerRow = document.createElement("TR");
        var idCol = document.createElement("TH");
        idCol.innerHTML = "User Id";
        var loginCol = document.createElement("TH");
        loginCol.innerHTML = "Login";
        var firstNameCol = document.createElement("TH");
        firstNameCol.innerHTML = "First";
        var lastNameCol = document.createElement("TH");
        lastNameCol.innerHTML = "Last"
        var emailCol = document.createElement("TH");
        emailCol.innerHTML = "Email"
        headerRow.appendChild(idCol);
        headerRow.appendChild(loginCol);
        headerRow.appendChild(firstNameCol);
        headerRow.appendChild(lastNameCol);
        headerRow.appendChild(emailCol);
        p.appendChild(headerRow);
    }
    for (var i in usersList)
    {
        var users = usersList[i];
        var row = document.createElement("TR");
        var idCol = document.createElement("TD");
        idCol.innerHTML = users.usersId;
        var loginCol = document.createElement("TD");
        loginCol.innerHTML = users.login;
        var firstNameCol = document.createElement("TD");
        firstNameCol.innerHTML = users.firstName;
        var lastNameCol = document.createElement("TD");
        lastNameCol.innerHTML = users.lastName;
        var emailCol = document.createElement("TD");
        emailCol.innerHTML = users.email;
        row.appendChild(idCol);
        row.appendChild(loginCol);
        row.appendChild(firstNameCol);
        row.appendChild(lastNameCol);
        row.appendChild(emailCol);
        p.appendChild(row);
    }
    return p;
    $("#div_display").append(p);
}


function buildApplicationsTable(applicationList)
{
    var p = document.createElement("TABLE");
    {
        var headerRow = document.createElement("TR");
        var idCol = document.createElement("TH");
        idCol.innerHTML = "Application Id";
        var nameCol = document.createElement("TH");
        nameCol.innerHTML = "Name";
        var descriptionCol = document.createElement("TH");
        descriptionCol.innerHTML = "Description"
        headerRow.appendChild(idCol);
        headerRow.appendChild(nameCol);
        headerRow.appendChild(descriptionCol);
        p.appendChild(headerRow);
    }
    for (var i in applicationList)
    {
        var application = applicationList[i];
        var row = document.createElement("TR");
        var idCol = document.createElement("TD");
        idCol.innerHTML = application.applicationId;
        var nameCol = document.createElement("TD");
        nameCol.innerHTML = application.name;
        var descriptionCol = document.createElement("TDH");
        descriptionCol.innerHTML = application.description;
        row.appendChild(idCol);
        row.appendChild(nameCol);
        row.appendChild(descriptionCol);
        p.appendChild(row);
    }
    return p;
    $("#div_display").append(p);
}

function buildRolesTable(rolesList)
{
    var p = document.createElement("TABLE");
    {
        var row = document.createElement("TR");
        var roleIdCol = document.createElement("TH");
        roleIdCol.innerHTML = "Role Id";
        var applicationIdCol = document.createElement("TH");
        applicationIdCol.innerHTML = "Application Id";
        var roleTypeNameCol = document.createElement("TH");
        roleTypeNameCol.innerHTML = "Role Type";
        var nameCol = document.createElement("TH");
        nameCol.innerHTML = "Name";
        var ud1Col = document.createElement("TH");
        ud1Col.innerHTML = "UD1"
        row.appendChild(roleIdCol);
        row.appendChild(applicationIdCol);
        row.appendChild(roleTypeNameCol);
        row.appendChild(nameCol);
        row.appendChild(ud1Col);
        p.appendChild(row);
    }
    for (var i in rolesList)
    {
        var role = rolesList[i];
        var row = document.createElement("TR");
        var roleIdCol = document.createElement("TD");
        roleIdCol.innerHTML = role.roleId;
        var applicationIdCol = document.createElement("TD");
        applicationIdCol.innerHTML = role.applicationId;
        var roleTypeNameCol = document.createElement("TD");
        roleTypeNameCol.innerHTML = role.roleTypeName;
        var nameCol = document.createElement("TD");
        nameCol.innerHTML =role.name;
        var ud1Col = document.createElement("TD");
        ud1Col.innerHTML = role.ud1;
        row.appendChild(roleIdCol);
        row.appendChild(applicationIdCol);
        row.appendChild(roleTypeNameCol);
        row.appendChild(nameCol);
        row.appendChild(ud1Col);
        p.appendChild(row);
    }
    return p;
    
}
