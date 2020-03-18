layui.use('upload', function () {
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#modifyHeadPortrait' //绑定元素
        , acceptMime: 'image/jpg, image/jpeg'
        , exts: 'jpg|jpeg'
        , url: '/userOperation/modifyHeadPortrait' //上传接口
        , before: function (obj) {

            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result);
            });
        }
        , done: function (res) {
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            layer.msg('上传成功');
            //上传完毕回调
            var fileupload = $("#demo1");
            fileupload.attr("src", res.data.src);
            console.log(fileupload.attr("src"));
        }
        , error: function () {
            //请求异常回调
        }
    });
});