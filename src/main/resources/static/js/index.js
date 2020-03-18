$(function () {
    $.ajax({
        url: "/userOperation/getHeadImagePath",
        type: "POST",
        dataType: "json",
        success: function (msg) {
            var imgPath = $("#demo1");
            imgPath.attr("src", msg.data);
            console.log(imgPath.attr("src"));
        }
    });
});