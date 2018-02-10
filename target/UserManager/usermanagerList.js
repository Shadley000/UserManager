
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
    console.log("buildPermissionsList entry");
    $("#select_Permissions").children().remove();
    $("#div_Permissions").hide();
    if (selectedAppRole != null)
    {
        $("#div_Permission").show();
        for (var i in selectedAppRole.appPermissionBeans)
        {
            var myAppRole = selectedAppRole.appPermissionBeans[i];
            var x = document.createElement("li");
            x.setAttribute("id", myAppRole.id);
            var t = document.createTextNode(myAppRole.permissionName);
            x.appendChild(t);
            $("#select_Permissions").append(x);
        }
        $("#select_Permissions li").click(selectApplication);
        $("#div_Permissions").show();
    } 
        
}

function selectRole()
{
    var selectedRoleID = this.id;

    console.log("select AppRoleID =" + selectedRoleID);
    for (var i in selectedApplication.roleBeans)
    {
        var thisAppRole = selectedApplication.roleBeans[i];
        if (thisAppRole.id == selectedRoleID)
        {
            selectedAppRole = thisAppRole;
            selectedAppPermission = null;
            $("#div_Permissions").hide();
            buildPermissionsList(selectedAppRole);
        }
    }
}

function buildRolesList(selectedApplication)
{
    console.log("buildRolesList");
    $("#select_Roles").children().remove();
    $("#select_Permissions").children().remove();
    $("#div_Roles").hide();
    $("#div_Permissions").hide();

    if (selectedApplication != null)
    {
        for (var i in selectedApplication.roleBeans)
        {
            var x = document.createElement("li");
            x.setAttribute("id", selectedApplication.roleBeans[i].id);
            var t = document.createTextNode(selectedApplication.roleBeans[i].roleName);
            x.appendChild(t);
            $("#select_Roles").append(x);
        }
        $("#select_Roles li").click(selectRole);
        $("#div_Roles").show();
    }
}

function selectApplication() {
    var selectedApplicationID = this.id;
    console.log("   application id =" + selectedApplicationID);
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
        var x = document.createElement("li");
        x.setAttribute("id", applicationArray[i].id);
        var t = document.createTextNode(applicationArray[i].applicationName);
        x.appendChild(t);
        $("#select_Applications").append(x);
    }
    $("#select_Applications li").click(selectApplication);

    if (applicationArray != null && applicationArray.length > 0 && selectedApplication != null)
    {
        selectedApplication = applicationArray[0];
    }
}


$(document).ready(function () {
    $.get("webapi/applications", buildApplicationList);


});

