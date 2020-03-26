layui.use(['layer', 'form'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    // 让当前iframe弹层高度适应
    layer.iframeAuto();


    form.on('submit(submit-psw)', function (data) {
        $.ajax({
            url: "/userOperation/modifyPwd",
            type: "POST",
            dataType: "json",
            data: {
                oldPwd: data.field.oldPwd,
                newPwd: data.field.newPwd
                // secNewPwd: data.field.secNewPwd
            },
            success: function (msg) {
                if (msg == "200") {
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    layer.msg('修改密码成功。', {time: 1800, icon: 6}, function () {
                        parent.layer.close(index);

                    });
                } else {
                    layer.msg("修改密码失败。", {icon: 5});
                }
            }
        });
        return false;
    });

// 添加表单验证方法
    form.verify({
        psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
        repsw: function (t) {
            var secVal = $('#form-psw input[name=newPwd]').val();
            if (t !== secVal) {
                return '两次密码输入不一致';
            }
        }
    });
});