
var applicationArray;
var selectedApplication;
var selectedAppRole;
var selectedAppPermission;

function selectPermission()
{
    var selectedPermissionID = $(this).children(":selected").attr("id");

    console.log("select Permission id " + selectedPermissionID);
}

function buildPermissionsList(selectedAppRole)
{
    console.log("build Permissions List entry");
    $("#select_Permissions").children().remove();
    $("#div_Permissions").hide();
    if (selectedAppRole != null)
    {
        $("#div_Permission").show();
        for (var i in selectedAppRole.appPermissionBeans)
        {
            var myAppRole = selectedAppRole.appPermissionBeans[i];
            var x = document.createElement("OPTION");
            x.setAttribute("id", myAppRole.id);
            var t = document.createTextNode(myAppRole.permissionName);
            x.appendChild(t);
            $("#select_Permissions").append(x);
        }
        $("#select_Permissions").change(selectPermission);
        $("#div_Permissions").show();
    } 
}

function selectRole()
{
    var selectedRoleID = $(this).children(":selected").attr("id");

    console.log("select AppRoleID " + selectedRoleID);
    for (var i in selectedApplication.roleBeans)
    {
        var myAppRole = selectedApplication.roleBeans[i];
        if (myAppRole.id == selectedRoleID)
        {
            selectedAppRole = myAppRole;
            selectedAppPermission = null;
            $("#div_Permissions").hide();
            buildPermissionsList(selectedAppRole);
        }
    }
}

function buildRolesList(selectedApplication)
{
    console.log("build Roles List");
    $("#select_Roles").children().remove();
    $("#select_Permissions").children().remove();
    $("#div_Roles").hide();
    $("#div_Permissions").hide();
    
    if (selectedApplication != null)
    {
        for (var i in selectedApplication.roleBeans)
        {
            var x = document.createElement("OPTION");
            x.setAttribute("id", selectedApplication.roleBeans[i].id);
            var t = document.createTextNode(selectedApplication.roleBeans[i].roleName);
            x.appendChild(t);
            $("#select_Roles").append(x);
        }
        $("#select_Roles").change(selectRole);
        $("#div_Roles").show();
    }
}

function selectApplication() {
    var selectedApplicationID = $(this).children(":selected").attr("id");
    console.log("select Application " + selectedApplicationID);

    for (var i in applicationArray) {
        if (applicationArray[i].id == selectedApplicationID)
        {
            selectedApplication = applicationArray[i];
            selectedAppRole = null;
            selectedAppPermission = null;
            buildRolesList(selectedApplication);
        }
    }

}

function buildApplicationList(data, status) {
    console.log("build Application List");

    applicationArray = data;

    
    for (var i in applicationArray) {
        var x = document.createElement("OPTION");
        x.setAttribute("id", applicationArray[i].id);
        var t = document.createTextNode(applicationArray[i].applicationName);
        x.appendChild(t);
        $("#select_Applications").append(x);
    }
    $("#select_Applications").change(selectApplication);

    if (applicationArray != null && applicationArray.length > 0 && selectedApplication != null)
    {
        selectedApplication = applicationArray[0];
    }
}


$(document).ready(function () {
    $.get("webapi/applications", buildApplicationList);

});

