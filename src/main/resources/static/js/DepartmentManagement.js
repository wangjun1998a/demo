layui.use(['layer', 'form', 'table', 'util', 'tree'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var dtree = layui.tree;
    var selTree = "1";
    // var admin = layui.admin;


    // 单选
    $(document).click(function (e) { // 在页面任意位置点击而触发此事件
        if ($(e.target).attr('class') === "layui-tree-txt") { // 防止因为点击展开按钮把已选中的样式取消
            $(".layui-tree-txt").removeClass("tree-txt-active"); // 移除点击样式
            $(e.target).addClass("tree-txt-active"); // e.target表示被点击的目标
        }
    });

    $.ajax({
        url: "/dept/getDept",
        type: "GET",
        dataType: "json",
        success: function (msg) {
            console.log(msg);
            // 树形渲染
            dtree.render({
                id: 'layTree',
                elem: '#ltTree',
                data: msg.data,
                onlyIconControl: true, // 允许节点左侧图标控制展开收缩
                click: function (obj) {
                    selTree = obj.data;
                    ins1(obj.data.id);
                }
            });
        }
    });


    // 渲染表格
    var ins1 = function (dingTalkId) {
        table.render({
            id: 'demoTable',
            elem: '#rtTable'
            // , height: 'full-30' //full 全部铺满
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: [] // 自定义工具栏右边的属性
            , url: '/dept/getTableDept'
            , where: {dingTalkId: dingTalkId}
            , page: true
            // , cellMinWidth: 100
            , cols: [[
                // {type: 'checkbox'},
                // {align: 'center', align: 'center', toolbar: '#user-table-bar', title: '操作', minWidth: 120},
                {field: 'id', align: 'center', sort: true, title: '部门id'},
                {field: 'name', align: 'center', sort: true, title: '部门名称'},
                {field: 'createDeptGroup', align: 'center', title: '是否创建企业群'},
                {field: 'autoAddUser', align: 'center', title: '新人加入部门自动入群'},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]]
        });
    };
    // 监听工具条
    table.on('tool(rtTable)', function (obj) {
        var data = obj.data; //获得当前行数据
        var deptId = obj.data.id;
        var deptName = obj.data.name;
        var layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'edit') { // 编辑
            layer.open({
                title: '更新部门信息',
                type: 2,
                area: ['500px', '400px'],
                maxmin: true,
                shadeClose: false, //点击遮罩关闭
                content: 'UpdateDept.html?deptId=' + deptId + '&deptName=' + deptName,
                success: function (layero, index) {
                    layer.iframeAuto(index);
                    var div = layero.find('iframe').contents().find('#useradmin');
                    div.find("#deptName").val(data.name);

                }
            });

        } else if (layEvent === 'del') { // 删除
            layer.open({
                // type: 2 取消iframe层
                title: '信息'
                , shadeClose: false //点击遮罩关闭
                , content: '是否删除部门: ' + data.name
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    //按钮【确定】的回调
                    var id = data.id;
                    $.ajax({
                        url: "/dept/deleteDept",
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (msg) {
                            debugger;
                            if (msg.errcode == "0") {
                                layer.msg('删除成功', {time: 1800, icon: 6}, function () {
                                    layer.close(index);
                                    // dtree.reload('layTree', {});
                                    // table.reload('demoTable');
                                    location.reload();//刷新父页面
                                });
                            } else {
                                layer.msg(msg.errmsg, {icon: 5});
                            }
                        }
                    });

                }
                , btn2: function (index, layero) {
                    //开启该代码可禁止点击该按钮关闭
                    // return false;
                    layer.close(index);

                }
            });
            // var deleteData = new Array();
            // deleteData.push(data.dictId);
            // deleteDictionary(deleteData);
        }
    });

    // 批量删除
    // $('#btnDel').click(function () {
    //     var checkRows = table.checkStatus('rtTable');
    //     if (checkRows.data.length == 0) {
    //         layer.msg('请选择要删除的数据', {icon: 2});
    //     } else {
    //         var deletedata = [];
    //         for (var i = 0; i < checkRows.data.length; i++) {
    //             deletedata.push(checkRows.data[i].dictId);
    //         }
    //         deleteDictionary(deletedata);
    //     }
    // });

    // 搜索按钮点击事件
    // $('#btnSearchByCode').click(function () {
    //     var value = $('#edtSearch').val();
    //     // ins1(dingTalkId);
    // });
    // 搜索按钮点击事件
    // $('#btnSearchByName').click(function () {
    //     var value = $('#edtSearch').val();
    //     ins1("", "dict_name", value);
    // });


    // 显示表单弹窗
    // function showEditModel(data) {
    //     admin.putTempData('dictionaryEditData', data);
    //     admin.putTempData('formOk', false);
    //     top.layui.admin.open({
    //         type: 2,
    //         title: data ? '修改字典' : '添加字典',
    //         area: ['480px', '492px'],
    //         content: '/dictionary/editForm',
    //         end: function () {
    //             admin.getTempData('formOk') && table.reload('rtTable') // 成功刷新表格
    //             if (admin.getTempData('formOk')) {
    //                 dictTree.menubarMethod().refreshTree();
    //             }
    //
    //         }
    //     });
    // }


    // $(document).ready(function () {
    //     var click = $('#btnAdd').click(function () {
    //         var aa = 1;
    //         debugger;
    //         var checked = selTree.id;
    //         // var checked = dtree.getChecked("ltTree");
    //         // alert(checked);
    //         debugger;
    //         // Dept.search();
    //     });
    // });

    $(function () {
        //首次加载默认
        ins1(1);
    });

    $(document).on('click', "#btnAdd", function () {
        var deptId = selTree.id;
        //首次进入的时候默认是根目录
        if (deptId == null) {
            deptId = "1";
        }
        layer.open({
            type: 2,
            title: '新增记录',
            area: ['500px', '400px'],
            shadeClose: false, //点击遮罩关闭
            content: 'CreateDept.html?deptId=' + deptId,
        });
    });
});