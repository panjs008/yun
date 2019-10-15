<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkPayOverList" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="emkPayOverController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  query="true" field="orderNo"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算日期" query="true" field="overDate"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="订单金额"  field="orderMoney"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="叫布费用"  field="jbMoney"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="物料费用"  field="wlMoney"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="发货总金额"  field="fhMoney"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="付款总金额"  field="fkMoney"  queryMode="single"  width="90"></t:dgCol>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkPayOverController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/payover/emkPayOverList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkPayOverListtb").find("input[name='overDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkPayOverListtb").find("input[name='overDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });


 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkPayOverController.do?upload', "emkPayOverList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkPayOverController.do?exportXls","emkPayOverList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkPayOverController.do?exportXlsByT","emkPayOverList");
}

 </script>