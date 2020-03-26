layui.use(['layer', 'form', 'table', 'util', 'tree'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var dtree = layui.tree;
    // var admin = layui.admin;


    // 多选
    // $(document).click(function (e) { // 在页面任意位置点击而触发此事件
    //     $(e.target).toggleClass("tree-txt-active"); // e.target表示被点击的目标
    // });


    // 单选
    $(document).click(function (e) { // 在页面任意位置点击而触发此事件
        if ($(e.target).attr('class') === "layui-tree-txt") { // 防止因为点击展开按钮把已选中的样式取消
            $(".layui-tree-txt").removeClass("tree-txt-active"); // 移除点击样式
            $(e.target).addClass("tree-txt-active"); // e.target表示被点击的目标
        }
    });


    $(function () {
        ins1(1);
    });
    // 渲染表格
    var ins1 = function (dingTalkId) {
        table.render({
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
    // table.on('tool(rtTable)', function (obj) {
    //     var data = obj.data; //获得当前行数据
    //     var layEvent = obj.event; //获得 lay-event 对应的值
    //
    //     debugger;
    //     if (layEvent === 'edit') { // 查看
    //         showEditModel(data);
    //         //layer.msg('点击了修改');
    //     } else if (layEvent === 'del') { // 删除
    //         var deleteData = new Array();
    //         deleteData.push(data.dictId);
    //         deleteDictionary(deleteData);
    //     }
    // });

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
        // ins1(dingTalkId);
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
            console.log(msg);
            // 树形渲染
            var dictTree = dtree.render({
                elem: '#ltTree',
                data: msg.data,
                onlyIconControl: true, // 允许节点左侧图标控制展开收缩
                click: function (obj) {

                    // // _evaluation.search(currTreeId);
                    // //当前点击的树节点
                    // var currTreeId = obj.data.id;
                    // //上一次点击的树节点id
                    // var beforeTreeId = $('#tree_id').val();
                    // //对比前后id，不相同时移除上一个节点的样式，并设置当前点击的节点样式
                    // if (currTreeId !== beforeTreeId) {
                    //     $('div [data-id="' + currTreeId + '"] div').eq(1).last().css('background-color', '#87eaa3');
                    //     $('div [data-id="' + beforeTreeId + '"] div').eq(1).last().css('background-color', '');
                    //     $('#tree_id').val(obj.data.id);
                    // }

                    console.log(obj.data); //得到当前点击的节点数据
                    console.log(obj.state); //得到当前节点的展开状态：open、close、normal
                    console.log(obj.elem); //得到当前节点元素
                    console.log(obj.data.children); //当前节点下是否有子节点
                    ins1(obj.data.id);
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