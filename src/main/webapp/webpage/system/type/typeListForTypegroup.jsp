<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="typeValueList" title="common.type.list"
                    actionUrl="systemController.do?typeGrid&typegroupid=${typegroupid}" idField="id"
                    queryMode="group">
            <t:dgCol title="common.code" field="id" hidden="true" width="120"></t:dgCol>
            <t:dgCol title="common.type.name" field="typename" width="200"></t:dgCol>
            <t:dgCol title="common.type.code" field="typecode" width="150"></t:dgCol>
            <c:if test="${param.typegroupname eq '颜色'}">
                <t:dgCol title="色号" field="remark" width="150"></t:dgCol>
            </c:if>
            <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
            <t:dgDelOpt url="systemController.do?delType&id={id}" title="common.delete" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgDelOpt>
            <t:dgToolBar title="common.add.param" langArg="common.type" icon="icon-add" url="systemController.do?addorupdateType&typegroupname=${param.typegroupname}&typegroupid=${typegroupid}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="common.edit.param" langArg="common.type" icon="icon-edit" url="systemController.do?addorupdateType&typegroupname=${param.typegroupname}&typegroupid=${typegroupid}" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

