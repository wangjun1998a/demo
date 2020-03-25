layui.use(['layer', 'form', 'table', 'util', 'tree'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var dtree = layui.tree;
    // var admin = layui.admin;
    // 渲染表格
    var ins1 = function (pid, cloumn, Keyword) {
        table.render({
            elem: '#rtTable',
            url: '/dictionary/dictionaryInfoByParentId',
            page: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'checkbox'},
                {field: 'dictCode', align: 'center', sort: true, title: '字典编码'},
                {field: 'dictName', align: 'center', sort: true, title: '字典值'},
                {field: 'dictOrder', align: 'center', sort: true, title: '字典序列'},
                {field: 'dictDescription', align: 'center', sort: true, title: '字典备注'},
                {align: 'center', align: 'center', toolbar: '#user-table-bar', title: '操作', minWidth: 120}
            ]],
            where: {nodeId: pid, Keyword: Keyword, cloumn: cloumn}
        });
    };
    // 监听工具条
    table.on('tool(rtTable)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值

        if (layEvent === 'edit') { // 查看
            showEditModel(data);
            //layer.msg('点击了修改');
        } else if (layEvent === 'del') { // 删除
            var deleteData = new Array();
            deleteData.push(data.dictId);
            deleteDictionary(deleteData);
        }
    });

    // 增加
    // $('#btnAdd').click(function () {
    //     var param = dtree.getNowParam(dictTree);
    //     if (param.context) {
    //         admin.putTempData('selectdictionaryId', param.nodeId);
    //     } else {
    //         layer.msg('请选择一个节点', {icon: 2});
    //     }
    //     showEditModel();
    // });
    // 批量删除
    $('#btnDel').click(function () {
        var checkRows = table.checkStatus('rtTable');
        if (checkRows.data.length == 0) {
            layer.msg('请选择要删除的数据', {icon: 2});
        } else {
            var deletedata = [];
            for (var i = 0; i < checkRows.data.length; i++) {
                deletedata.push(checkRows.data[i].dictId);
            }
            deleteDictionary(deletedata);
        }
    });

    // 搜索按钮点击事件
    $('#btnSearchByCode').click(function () {
        var value = $('#edtSearch').val();
        ins1("", "dict_code", value);
    });
    // 搜索按钮点击事件
    $('#btnSearchByName').click(function () {
        var value = $('#edtSearch').val();
        ins1("", "dict_name", value);
    });


    $.ajax({
        url: "/dept/getDept",
        type: "GET",
        dataType: "json",
        success: function (msg) {
            var dictTree = dtree.render({
                elem: '#ltTree',
                data: msg.data,
                onlyIconControl: true, // 允许节点左侧图标控制展开收缩
                click: function (obj) {
                    debugger;
                    console.log(obj.data); //得到当前点击的节点数据
                    console.log(obj.state); //得到当前节点的展开状态：open、close、normal
                    console.log(obj.elem); //得到当前节点元素
                    console.log(obj.data.children); //当前节点下是否有子节点
                }
                // url: '/dept/getDept',
                // type: 'all',
                // initLevel: '1',//默认展开层级，当该值大于level时，则会展开树的节点，直到不大于当前待展开节点的level
                //dot: false,
                // method: 'GET',
                // request: {nodeId: "0"}//自定义参数，组件进行参数拼接时，会将该属性的串拼接到url,然后传递到后台进行数据查询
            });
            // console.log(msg);
            // debugger;
        }
    });
    // 树形渲染
    var dictTree = dtree.render({
        elem: '#ltTree'
        // url: '/dept/getDept',
        // type: 'all',
        // initLevel: '1',//默认展开层级，当该值大于level时，则会展开树的节点，直到不大于当前待展开节点的level
        //dot: false,
        // method: 'GET',
        // request: {nodeId: "0"}//自定义参数，组件进行参数拼接时，会将该属性的串拼接到url,然后传递到后台进行数据查询
    });
    // 树形点击事件
    dtree.on('node("ltTree")', function (obj) {
        var data = obj.param;
        debugger;

        //layer.msg('你选择了：' + data.nodeId + '-' + data.context);
        //table.reload('rtTable', {where: {nodeId: data.nodeId}});
        ins1(data.nodeId, "", "");
    });


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


    function deleteDictionary(data) {
        var postdata = {"Ids": data};
        if (data.constructor == Array || data instanceof Array) {
            $.post(
                "delete",
                postdata,
                function (result) {
                    if (result.code == 200) {
                        top.layer.msg(result.msg, {icon: 1});
                        table.reload('rtTable') && dictTree.menubarMethod().refreshTree();
                    } else {
                        top.layer.msg(result.msg, {icon: 2});
                    }
                }, "json"
            );
        }
    }
});