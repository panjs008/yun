<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkPmColorList" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="emkPmColorController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品名"  field="pm" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="色号"  field="colorNum" query="true"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkPmColorController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkPmColorController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkPmColorController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkPmColorController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
       <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/product/pmcolor/emkPmColorList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });


 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:emkPmColorController.do?upload',
         zIndex: getzIndex(),
         title : '导入款号',
         cache:false,
         lock : true,
         width: 600,
         height: 300
     });

 }
//导出
function ExportXls() {
	JeecgExcelExport("emkPmColorController.do?exportXls","emkPmColorList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkPmColorController.do?exportXlsByT","emkPmColorList");
}

 </script>