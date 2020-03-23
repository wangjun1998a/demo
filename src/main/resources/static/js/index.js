$(function () {
    $.ajax({
        url: "/userOperation/getHeadImagePath",
        type: "POST",
        dataType: "json",
        success: function (msg) {
            var imgPath = $("#demo1");
            var displayName = $("#displayName");
            imgPath.attr("src", msg.img);
            displayName.html(msg.username);
            console.log(imgPath.attr("src"));
        }
    });
});