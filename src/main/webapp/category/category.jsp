<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    //延迟加载
    $(function(){
        pageInit();
    });
    //表格
    function pageInit(){
        //父表格
        $("#cateTable").jqGrid(
            {
                url : "${path}/category/queryByOnePage",
                editurl: "${path}/category/edit",
                datatype : "json",
                rowNum : 8,
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#catePage',
                viewrecords : true,
                colNames : [ 'Id', '名称', '级别' ],
                colModel : [
                    {name : 'id',index : 'id',  width : 55},
                    {name : 'cateName',editable:true,index : 'invdate',width : 90},
                    {name : 'levels',index : 'name',width : 100}
                ],
                subGrid : true, //是否开启子表格

                //1.subgrid_id   点击一行时会在子表格中创建一个div 用来容纳子表格 subgrid_id就是div的id
                //2.row_id    点击行的id
                subGridRowExpanded : function(subgridId, rowId) { //设置子表格相关的属性
                    //复制子表格内容
                    addSubGrid(subgridId,rowId);
                }
            });
        //父表格的分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,edit : true,del : true},
            {
                closeAfterEdit:true
            },
            {
                closeAfterAdd:true
            },
            {
                closeAfterdel:true,
                //提交之后的操作
                afterSubmit:function (response) {
                    //向警告框中写入内容
                    $("#showMsg").html(response.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function () {
                        //关闭提示框
                        $("#deleteMsg").hide();
                    },3000);

                    return "hello";
                }
            }

        );
    }
    //子表格
    function addSubGrid(subgridId,rowId) {
        //改成驼峰命名
        var subgridTableId = subgridId + "Table";  //定义子表格 Table的id
        var pagerId= subgridId+"Page";   //定义子表格工具栏的id

        //在子表格容器中创建子表格分页和子表格工具栏
        $("#" + subgridId).html("<table id='"+subgridTableId+"'/> <div id='"+pagerId+"'>");


        //子表格里面的内容
        $("#" + subgridTableId).jqGrid({
            url:"${path}/category/queryByTwoPage?parentId="+rowId,
            editurl: "${path}/category/edit?parentId="+rowId,
            datatype : "json",
            rowNum : 20,
            pager : "#" +pagerId,
            styleUI: "Bootstrap",
            height: "auto",
            autowidth: true,
            viewrecords : true,  //总条数
            colNames : [ 'Id', '类别名', '级别', '上一级id' ],
            colModel : [
                {name : "id",  index : "num",width : 80,key : true},
                {name : "cateName",editable:true,index : "item",  width : 130},
                {name : "levels",index : "qty",width : 70,align : "right"},
                {name : "parentId",index : "unit",width : 70,align : "right"}
            ],

        });

        //子表格分页工具栏
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : true,add : true,del : true},
            {
                closeAfterEdit:true
            },
            {
                closeAfterAdd:true
            },
            {
                closeAfterdel:true,
                //提交之后的操作
                afterSubmit:function (response) {
                    //向警告框中写入内容
                    $("#showMsg").html(response.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function () {
                        //关闭提示框
                        $("#deleteMsg").hide();
                    },3000);

                    return "hello";
                }
            }
        );

    }
</script>

<%--初始化面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="#">类别管理</a></li>
    </div>

    <br>
    <%-- 警告框 --%>
    <div id="deleteMsg" class="alert alert-warning alert-dismissible" role="alert" style="width: 300px;display: none">
        <span id="showMsg"/>
    </div>

    <%--按钮--%>
    <button class="btn btn-info">导出类别信息</button>
    <table id="cateTable"></table>

    <%-- 分页工具栏 --%>
    <div id="catePage">

    </div>
</div>