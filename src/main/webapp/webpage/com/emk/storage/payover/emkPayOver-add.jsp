<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>结算表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkPayOverController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${emkPayOverPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结算日期:
						</label>
					</td>
					<td class="value">
					     	 <input id="overDate" name="overDate" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算日期</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderMoney" name="orderMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单金额</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							叫布费用:
						</label>
					</td>
					<td class="value">
					     	 <input id="jbMoney" name="jbMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">叫布费用</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							物料费用:
						</label>
					</td>
					<td class="value">
					     	 <input id="wlMoney" name="wlMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">物料费用</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发货总金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="fhMoney" name="fhMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发货总金额</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							付款总金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="fkMoney" name="fkMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">付款总金额</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/payover/emkPayOver.js"></script>		
