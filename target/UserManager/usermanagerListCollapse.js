


function buildPermissionsList(myParent, myAppRole)
{
    console.log("buildPermissionsList entry");
    
    var html_list = document.createElement("ul");
    html_list.setAttribute("id", "select_Permissions");
    for (var i in myAppRole.appPermissionBeans)
    {
        var myAppPermission = myAppRole.appPermissionBeans[i];
        var listItem = document.createElement("li");
        listItem.setAttribute("id", myAppPermission.id);
        var t = document.createTextNode(myAppPermission.permissionName +" : "+myAppPermission.id);
        listItem.appendChild(t);
        html_list.append(listItem);

    }
     myParent.append(html_list);
}

function buildRolesList(myParent, myApplication)
{
    console.log("buildRolesList");

    var html_list = document.createElement("ul");
    html_list.setAttribute("id", "select_Roles");

    for (var i in myApplication.roleBeans)
    {
        var myAppRole = myApplication.roleBeans[i];
        var listItem = document.createElement("li");
        listItem.setAttribute("id", myAppRole.id);
        var t = document.createTextNode(myAppRole.roleName +" : "+myAppRole.id);
        listItem.appendChild(t);
        html_list.append(listItem);
        buildPermissionsList(listItem, myAppRole)
    }
    myParent.append(html_list);

}

function buildApplicationList(data, status) {
    applicationArray = data;

    for (var i in applicationArray) {
        var myApplication = applicationArray[i];
        var listItem = document.createElement("li");
        listItem.setAttribute("id", myApplication.id);
        var t = document.createTextNode(myApplication.applicationName +" : "+myApplication.id);
        buildRolesList(listItem, myApplication);
        listItem.appendChild(t);
        $("#select_Applications").append(listItem);
    }
}

$(document).ready(function () {
    $.get("webapi/applications", buildApplicationList);


});
