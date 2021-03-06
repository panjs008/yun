<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>客户跟进表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <link type="text/css" rel="stylesheet" href="plug-in/select2/css/select2.min.css"/>

	 <script type="text/javascript" src="plug-in/select2/js/select2.js"></script>
	 <script type="text/javascript" src="plug-in/select2/js/pinyin.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
      $("#contactId").val("${ymkCustomTracePage.contactId}");
      $("#traceType").val("${ymkCustomTracePage.traceType}");
      $("#traceState").val("${ymkCustomTracePage.traceState}");

      BindSelect("cusName","",1,"${ymkCustomTracePage.cusName}");

      $("#cusName").change(function(){
          $('#contactId').empty();

          $.ajax({
              url : "ymkCustomContactController.do?getContect&cusName="+$("#cusName").val(),
              type : 'post',
              cache : false,
              data: null,
              success : function(data) {
                  var d = $.parseJSON(data);
                  if (d.success) {
                      var msg = d.msg;
                      var dataItems = new Array(); //定义一数组
                      dataItems = d.obj.split(";"); //字符分割
                      //W.document.location.reload(true);
                      var option1='';
                      for (i=0;i<dataItems.length ;i++ ) {
                          var dataitem = new Array(); //定义一数组
                          dataitem = dataItems[i].split(","); //字符分割

                          if(dataitem[0] == '${ymkCustomTracePage.contactId}'){
                              option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
                          }else{
                              option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                          }
                      }
                      $("#contactId").append(option1);
                  }
              }
          });
      });
  });

  function BindSelect(ctrlName, url,type,categoryId) {
      var control = $('#' + ctrlName);
      //设置Select2的处理
      control.select2({
          formatResult: formatState,
          formatSelection: formatState,
          escapeMarkup: function (m) {
              return m;
          }
      });
      //绑定Ajax的内容
      $.getJSON('ymkCustomController.do?findCustomList', function (data) {
          control.empty();//清空下拉框
          control.append("<option value=''>请选择客户</option>");
          $.each(data.obj, function (i, item) {
              control.append("<option value='" + item.fname + "'>&nbsp;" + item.fname + "</option>");
          });

          if(type ==1){
              $("#"+ctrlName).select2('val',categoryId);
          }
      });
  }

  function formatState (state) {
      if (!state.id) { return state.text; }
      var $state = $(
          '<span>' + state.text + '</span>'
      );
      return $state;
  };
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" tiptype="1" layout="table" action="ymkCustomTraceController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${ymkCustomTracePage.id }"/>
			<input id="contactName" name="contactName" type="hidden" value="${ymkCustomTracePage.contactName }"/>
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						客户名称:
					</label>
				</td>
				<td class="value">
					<select class="form-control select2" id="cusName" name="cusName" datatype="*"  >
						<option value="">请选择客户</option>
					</select>
					<%--<input id="cusName" name="cusName" type="text" style="width: 150px" value="${ymkCustomTracePage.cusName}"  readonly class="inputxt"  ignore="ignore" />--%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">客户名称</label>
				</td>

				<td align="right">
					<label class="Validform_label">
						联系人:
					</label>
				</td>
				<td class="value">
					<select name="contactId" id="contactId">
						<%--<c:forEach items="${customContactEntities}" var="contact">
							<option value="${contact.id}">${contact.userName}</option>
						</c:forEach>--%>
					</select>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">联系人</label>
				</td>
			</tr>

			<tr>
				<td align="right">
					<label class="Validform_label">
						跟进方式:
					</label>
				</td>
				<td class="value">
					<t:dictSelect id="traceType" field="traceType" typeGroupCode="tracetype" datatype="*" defaultVal="default" hasLabel="false" title="跟进方式"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">跟进方式</label>
				</td>

				<td align="right">
					<label class="Validform_label">
						跟进状态:
					</label>
				</td>
				<td class="value">
					<t:dictSelect id="traceState" field="traceState" typeGroupCode="tarcestate" datatype="*" defaultVal="default" hasLabel="false" title="跟进状态"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">跟进状态</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						跟进时间:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="traceTime" name="traceTime" datatype="*" value="${ymkCustomTracePage.traceTime}"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">跟进时间</label>
				</td>
			</tr>

			<tr>
				<td align="right">
					<label class="Validform_label">
						跟进内容:
					</label>
				</td>
				<td class="value" colspan="3">
					<textarea id="traceContent" datatype="*"  style="width:90%;height:90px" class="inputxt" rows="6" name="traceContent">${ymkCustomTracePage.traceContent}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">跟进内容</label>
				</td>
			</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/service/customtrace/ymkCustomTrace.js"></script>		
