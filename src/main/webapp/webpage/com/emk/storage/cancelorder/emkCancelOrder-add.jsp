<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>退货表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>
	<script src="${webRoot}/context/orderSelect.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			//$("#detailId").load("emkEnquiryController.do?orderMxList");
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkCancelOrderController.do?doAdd" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkCancelOrderPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					工厂:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="gysId"  datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkCancelOrderPage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="hidden"  value="${emkCancelOrderPage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					退货日期:
				</label>
			</td>
			<td class="value">
				<input id="cancelDate" name="cancelDate" readonly value="${kdDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="*"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">退货日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					订单号:
				</label>
			</td>
			<td class="value" >
				<select class="form-control select2" id="orderNo" name="orderNo" datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					出货批次:
				</label>
			</td>
			<td class="value">
				<input id="betch" name="betch" type="text"  style="width: 150px" class="inputxt" datatype="*"   value='${emkCancelOrderPage.betch}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出货批次</label>
			</td>
		</tr>

	</table>
	<t:tabs id="enquiryDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkCancelOrderController.do?orderMxList&state=0&proOrderId=${emkCancelOrderPage.id }" icon="icon-search" title="明细" id="detail"></t:tab>
	</t:tabs>
	<t:tabs id="qaDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkCancelOrderController.do?qaMxList&cancelId=${emkCancelOrderPage.id }" icon="fa fa-edit" title="退回问题反馈" id="detail"></t:tab>
	</t:tabs>
</t:formvalid>
</body>
