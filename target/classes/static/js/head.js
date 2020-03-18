const getMenus = function (data) {
    //回显选中
    var ul = $("<ul class='layui-nav layui-nav-tree' lay-filter='test'></ul>");
    for (var da = 0; da < data.length; da++) {
        var node = data[da];
        var roles = node.roles.split(",");
        var hasAnyRoles = "";
        // var hasAnyRoles = "hasAnyRole(";
        for (var ro = 0; ro < roles.length; ro++) {
            hasAnyRoles += "\'ROLE_" + roles[ro] + "\'";
            if (ro < roles.length) {
                hasAnyRoles += ",";
            }
        }
        hasAnyRoles = hasAnyRoles.substr(0, hasAnyRoles.length - 1);
        // hasAnyRoles += ")";

        var li = $("<li class='layui-nav-item' flag='" + node.id + "'></li>");
        var a = $("<a class='' href='javascript:;' target='centerFrame'>" + node.name + "</a>");
        li.append(a);
        //获取子节点
        var childArray = node.childrens;
        if (childArray.length > 0) {
            // a.append("<span class='layui-nav-more'></span>");
            var dl = $("<dl class='layui-nav-child'></dl>");
            for (var y in childArray) {
                var dd = $("<dd><a href='" + childArray[y].url + "' target='centerFrame'>" + childArray[y].name + "</a></dd>");
                dl.append(dd);
            }
            li.append(dl);
        }
        ul.append(li);
    }
    $(".layui-side-scroll").append(ul);
};
/**
 * 菜单
 * */
$(function () {
    layui.use('element', function () {
        var element = layui.element;
        // 左侧导航区域（可配合layui已有的垂直导航）
        $.get("/getData/findMenu", function (data) {
            if (data != null) {
                getMenus(data.menu);
                element.render('nav');
            } else {
                layer.alert("权限不足，请联系管理员", function () {
                    //退出
                    window.location.href = "/logout";
                });
            }
        });
    });
});