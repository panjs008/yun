<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    $(document).ready(function() {
        var height = window.top.document.body.offsetHeight * 0.79;
        //$("#processFrm").css("height",height);
        //$("#processHisFrm").css("height",height*0.3);

    });
</script>
<div id="orderDiv" title="流程图" style="width:100%;height:560px;overflow:hidden" >
    <iframe scrolling="no" id="processFrm" frameborder="0"  src="uRepairController.do?showProcess&id=${param.id}" width="99%" height="100%"></iframe>
</div>
<%--<t:datagrid name="uRepairList" height="150px" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="flowController.do?hisProcessDatagrid&id=${param.id}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="流程节点"  field="NAME_"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="操作人"  field="workname"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="开始时间"  field="START_TIME_"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="结束时间"  field="END_TIME_"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="DELETE_REASON_" replace="完成_completed"  queryMode="single"  width="120"></t:dgCol>
</t:datagrid>--%>
<%--<div id="processHisDiv"  style="width:100%;height:230px;overflow:hidden;" >
    <iframe scrolling="yes" id="processHisFrm" frameborder="0" src="flowController.do?processHis&id=${param.id}" width="99%" height="100%"></iframe>
</div>--%>
<%--<div class="easyui-layout" fit="true" style="height: 100px">
    <div region="center" style="padding:0px;border:0px;">
        <t:datagrid name="uRepairList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="flowController.do?hisProcessDatagrid&id=${param.id}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="流程节点"  field="NAME_"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="操作人"  field="workname"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="开始时间"  field="START_TIME_"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="结束时间"  field="END_TIME_"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="流程状态"  field="DELETE_REASON_" replace="完成_completed"  queryMode="single"  width="120"></t:dgCol>

        </t:datagrid>
    </div>
</div>--%>
<script src = "webpage/com/process/repair/uRepairList.js"></script>
<script type="text/javascript">


</script>