
var token = null;
var userId = null;

function getToken() {
    // console.log("getToken");
    var login = $("#user").val();
    var password = $("#password").val();

    $.get("webapi/token/users/" + login + "?password=" + password, function (data, status) {
        token = data.token;
        console.log("token " + token);
        var p = document.createElement("P");
        p.innerHTML = "token:" + token;
        $("#div_display").append(p);

        $("#div_prelogin").hide();
        $("#div_navigation").show();
    }).fail(failed);   
}

function getHealth() {
    $.get("webapi/token/health", function (data, status) {
        console.log("token count " + data);
        var p = document.createElement("P");
        p.innerHTML = "token count:" + data;
        $("#div_display").append(p);
    }).fail(failed);
}

function getUserId() {
    $.get("webapi/token/userIDs/" + token, function (data, status) {
        userId = data.userId;
        console.log("user id = " + userId);
        var p = document.createElement("P");
        p.innerHTML = "userId:" + userId;
        $("#div_display").append(p);
    }).fail(failed);
}

function getAllUsers() {
    console.log("getAllUsers");
    $.get("webapi/users?token=" + token, function (data, status) {
        $("#div_display").append(buildUsersTable(data));
    }).fail(failed);
}

function getAllApplications() {
    console.log("getAllApplications");
    $.get("webapi/applications?token=" + token, function (data, status) {
        $("#div_display").append(buildApplicationsTable(data));
    }).fail(failed);
}

function getRolesByApplication() {
    console.log("getRolesByApplication");
    var applicationId = $("#application_id").val();
    $.get("webapi/applications/" + applicationId + "/roles?token=" + token, function (data, status) {
        $("#div_display").append(buildRolesTable(data));
    }).fail(failed);
}

function getUsersByApplication() {
    console.log("getUsersByApplication");
    var applicationId = $("#application_id").val();
    $.get("webapi/applications/" + applicationId + "/users?token=" + token, function (data, status) {
        $("#div_display").append(buildUsersTable(data));
    }).fail(failed);
}

function getRolesByUsersAndApplication() {
    console.log("getUsersByApplication");
    var applicationId = $("#application_id").val();
    var usersId = $("#users_id").val();
    $.get("webapi/users/"+usersId+"/applications/" + applicationId + "/roles?token=" + token, function (data, status) {
        $("#div_display").append(buildRolesTable(data));
    }).fail(failed);
}

function logout() {
    token = null;
    userId = null;
    $("#div_prelogin").show();
    $("#div_navigation").hide();
}

$(document).ready(function () {

    $("#button_token").click(getToken);
    $("#button_health").click(getHealth);

    $("#button_userid").click(getUserId);
    $("#button_allusers").click(getAllUsers);
    $("#button_allapplication").click(getAllApplications);
    $("#button_rolesByApplication").click(getRolesByApplication);
    $("#button_usersByApplication").click(getUsersByApplication);
    $("#button_rolesByUsersAndApplication").click(getRolesByUsersAndApplication);
    $("#button_logout").click(logout);

    $("#div_prelogin").show();
    $("#div_navigation").hide();
});