<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkCancelOrderList" checkbox="false" sortName="cancelDate" sortOrder="desc" pagination="true" fitColumns="false" title="" actionUrl="emkCancelOrderController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="工厂名称"  field="gys"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true" queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="退货日期"  field="cancelDate" query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="退货批次"  field="betch"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkCancelOrderController.do?goAdd&winTitle=录入退货" funname="add" width="1200"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkCancelOrderController.do?goUpdate&winTitle=编辑退货" funname="update" width="1200"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" operationCode="delete" url="emkCancelOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkCancelOrderController.do?goUpdate" funname="detail" width="1200"></t:dgToolBar>
      <t:dgToolBar title="提交"  icon="fa fa-arrow-circle-up" operationCode="submit"  funname="doSubmitV"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/cancelorder/emkCancelOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkCancelOrderListtb").find("input[name='cancelDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkCancelOrderListtb").find("input[name='cancelDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:emkCancelOrderController.do?upload',
         zIndex: getzIndex(),
         title : '导入退货',
         cache:false,
         lock : true,
         width: 800,
         height: 400
     });

 }

 function doSubmitV() {
     var rowsData = $('#emkCancelOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的退货');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交退货?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkCancelOrderController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkCancelOrderList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="3"){
         return '<span style="color:	#FF0000;">退回</span>';
     }else if(row.state=="4"){
         return '<span style="color:	#0000FF;">工厂接收</span>';
     }else{
         return '创建';
     }
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkCancelOrderController.do?upload', "emkCancelOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkCancelOrderController.do?exportXls","emkCancelOrderList");
}

//模板下载
function ExportXlsByT() {
    window.open("${webRoot}/context/退货模板.xls");
}

 </script>