<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工厂出货表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			//$("#detailId").load("emkEnquiryController.do?orderMxList");
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkOutOrderController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkOutOrderPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					订单号:
				</label>
			</td>
			<td class="value" >
				<input type="text" id="orderNo" readonly name="orderNo"  value="${emkOutOrderPage.orderNo }" style="width: 150px" />
				<%--<select class="form-control select2" id="orderNo"  value="${emkOutOrderPage.orderNo }"  name="orderNo" datatype="*"  >
					<option value=''>请选择</option>
				</select>--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					出货日期:
				</label>
			</td>
			<td class="value">
				<input id="outDate" name="outDate" readonly value="${emkOutOrderPage.outDate }"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出货日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					工厂:
				</label>
			</td>
			<td class="value">
				<%--<select class="form-control select2" id="gysId"  datatype="*"  >
					<option value=''>请选择</option>
				</select>--%>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkOutOrderPage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="text" readonly  value="${emkOutOrderPage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					出货批次:
				</label>
			</td>
			<td class="value">
				<input id="betch" name="betch" type="text" value="${emkOutOrderPage.betch }"  readonly  style="width: 150px" class="inputxt" datatype="*"   value='${emkOutOrderPage.betch}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出货批次</label>
			</td>
		</tr>

	</table>
	<t:tabs id="enquiryDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkOutOrderController.do?orderMxList&state=0&proOrderId=${emkOutOrderPage.id }" icon="icon-search" title="明细" id="detail"></t:tab>
	</t:tabs>

</t:formvalid>
</body>
