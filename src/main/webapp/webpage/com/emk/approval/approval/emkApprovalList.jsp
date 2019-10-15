<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkApprovalList" checkbox="false" pagination="true" fitColumns="true" pageSize="15" title="审批业务表" sortName="commitTime" sortOrder="desc" actionUrl="emkApprovalController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="80" ></t:dgCol>
      <t:dgCol title="单号"  field="workNum"  query="true" queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="工单类型"  field="type" replace="订单_0" queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="提交人"  field="createName"    queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="申请人ID"  field="commitId" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="提交时间"  field="commitTime"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="当前节点名称"  field="processName" formatterjs="formatProcessName" queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="当前节点代码"  field="processNode"  hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="formId"  field="formId"  hidden="true" queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="节点状态"  field="status" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="bpmSher"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="审核状态"  field="bpmStatus" formatterjs="formatBpmStateColor" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="审核人ID"  field="bpmSherId"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核时间"  field="bpmDate"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title=""  field="state1" hidden="true"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title=""  field="state2" hidden="true"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title=""  field="state3" hidden="true"  queryMode="single"  width="100"></t:dgCol>
      <t:dgFunOpt funname="goToProcess(workNum,formId,commitId,processNode,status,type,state1,state2,state3)" title="审批"  urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>
      <%--<t:dgToolBar title="录入" icon="fa fa-plus" url="emkApprovalController.do?goAdd" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkApprovalController.do?goUpdate" funname="update"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" url="emkApprovalController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/approval/approval/emkApprovalList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function formatColor(val,row){
     if(row.status=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.status=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.status=="3"){
         return '<span style="color:	#0000FF;">提交叫布</span>';
     }else if(row.status=="4"){
         return '<span style="color:	#0000FF;">核实通过</span>';
     }else if(row.status=="5"){
         return '<span style="color:	#FF0000;">回退叫布</span>';
     }else if(row.status=="6"){
         return '<span style="color:	#0000FF;">提交其他物料发放</span>';
     }else if(row.status=="7"){
         return '<span style="color:	#0000FF;">工厂确认通过</span>';
     }else if(row.status=="8"){
         return '<span style="color:	#FF0000;">回退其他物料发放</span>';
     }else if(row.status=="9"){
         return '<span style="color:	#0000FF;">提交发货</span>';
     }else if(row.status=="10"){
         return '<span style="color:	#FF0000;">回退发货</span>';
     }else if(row.status=="11"){
         return '<span style="color:	#0000FF;">出货通过退货</span>';
     }else if(row.status=="12"){
         return '<span style="color:	#0000FF;">提交退货</span>';
     }else if(row.status=="13"){
         return '<span style="color:	#FF0000;">回退退货</span>';
     }else if(row.status=="14"){
         return '<span style="color:	#0000FF;">退货通过</span>';
     }else if(row.status=="15"){
         return '<span style="color:	#0000FF;">提交付款</span>';
     }else if(row.status=="16"){
         return '<span style="color:	#FF0000;">回退付款</span>';
     }else if(row.status=="17"){
         return '<span style="color:	#0000FF;">工厂确认付款</span>';
     }else if(row.status=="19"){
         return '<span style="color:	#FF0000;">财务回退付款</span>';
     }else if(row.status=="18"){
         return '<span style="color:	#0000FF;">出货通过付款</span>';
     }else{
         return '创建';
     }
 }
 function formatBpmStateColor(val,row){
     if(row.bpmStatus=="1"){
         return '<span style="color:	#FF0000;">驳回</span>';
     }else{
         return '<span style="color:	#0000FF;">通过</span>';
     }
 }
function formatProcessName(val,row){
    if(row.processNode != null){
        if(row.processNode.indexOf('-') > 0){
            var processNameVal = row.processNode.substring(0,row.processNode.indexOf('-'));
            return processNameVal;
        }
    }
}
 function goToProcess(workNum,id,createBy,processName,state,type){
     var height =window.top.document.body.offsetHeight*0.85;
     var processNameVal='',node='',w=1280;
     var processUrl,initProcessName;
     if(type == '0'){
         processUrl = 'com/emk/storage/enquiry/emkEnquiry-process';
         initProcessName = '业务部审核';
         w = 1320;
     }

     if(processName != null){
         if(processName.indexOf('-') > 0){
             processNameVal = processName.substring(0,processName.indexOf('-'));
             node = processName.substring(processName.indexOf('-')+1);
             initProcessName = processNameVal;
         }
     }
     if(state == '1' || state == '4' || state == '5' || state == '7'  || state == '8' || state == '10' || state == '13' || state == '14' || state == '16' || state == '18' || state == '19'){
         createdetailwindow(workNum+"--流程进度--当前环节："+initProcessName, "flowController.do?goProcess&node="+node+"&state="+state+"&processUrl="+processUrl+"&id=" + id, w, height);
     }else{
         createwindow(workNum+"--流程进度--当前环节："+initProcessName, "flowController.do?goProcess&node="+node+"&state="+state+"&processUrl="+processUrl+"&id=" + id, w, height);
     }

 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkApprovalController.do?upload', "emkApprovalList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkApprovalController.do?exportXls","emkApprovalList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkApprovalController.do?exportXlsByT","emkApprovalList");
}

 </script>