


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

function acceptToken(data, status) {
    console.log("build Application List");
    console.log("data" + status);
    console.log("data" + status);

}

function getToken()
{
    console.log("getToken");
    $.get("webapi/token/shadley000?password=password1", acceptToken);

}
$(document).ready(function () {
    $.get("webapi/applications", buildApplicationList);

    $("#button_token").click(getToken);

});