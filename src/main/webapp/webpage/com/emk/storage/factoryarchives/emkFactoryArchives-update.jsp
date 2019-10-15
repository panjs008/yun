<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工厂档案</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码

		$(function(){
			$("#level").val("${emkFactoryArchivesPage.level}");
			$.ajax({
				url : "ymkCustomController.do?getCity&code=A01A05",
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
						$('#shengFen').empty();
						var option1 = '<option value="">--选择--</option>';
						var firstJgmc;
						for (i=0;i<dataItems.length ;i++ ) {
							var dataitem = new Array(); //定义一数组
							dataitem = dataItems[i].split(","); //字符分割
							if(dataitem[0]!="") {
								if(dataitem[0] == '${emkFactoryArchivesPage.shengFen}'){
									option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
								}else{
									option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
						}
						$("#shengFen").append(option1);

						if(${emkFactoryArchivesPage.shengFen ne '' && emkFactoryArchivesPage.shengFen ne null}){
							$.ajax({
								url : "ymkCustomController.do?getCity&code=${emkFactoryArchivesPage.shengFen}",
								type : 'post',
								cache : false,
								data: null,
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success) {
										var msg = d.msg;
										var dataItems = new Array(); //定义一数组
										dataItems = d.obj.split(";"); //字符分割
										$('#chengShi').empty();
										var option1='';
										for (i=0;i<dataItems.length ;i++ ) {
											var dataitem = new Array(); //定义一数组
											dataitem = dataItems[i].split(","); //字符分割
											if(dataitem[0] == '${emkFactoryArchivesPage.chengShi}'){
												option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
											}else{
												option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
											}
										}
										if(option1 != ''){
											$("#chengShi").append(option1);
										}
									}
								}
							});
						}

					}
				}
			});


			$("#shengFen").change(function(){
				$.ajax({
					url : "ymkCustomController.do?getCity&code="+$("#shengFen").val(),
					type : 'post',
					cache : false,
					data: null,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							console.log("chengshi:"+ d.obj);
							var dataItems = new Array(); //定义一数组
							dataItems = d.obj.split(";"); //字符分割
							//W.document.location.reload(true);
							$('#chengShi').empty();
							$('#pianQu').empty();

							var option3='';
							var firstJgmc;

							for (i=0;i<dataItems.length ;i++ ) {
								var dataitem = new Array(); //定义一数组
								dataitem = dataItems[i].split(","); //字符分割
								if(i == 0){
									firstJgmc = dataitem[0];
								}
								if(dataitem[0]!="") {
									option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
							$("#chengShi").append(option3);
						}
					}
				});
			});

		});

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkFactoryArchivesController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkFactoryArchivesPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td class="value" align="right" colspan="3">
				<label class="Validform_label">
					档案编号:
				</label>
			</td>
			<td class="value">
				<input id="archivesNo" name="archivesNo" value="${emkFactoryArchivesPage.archivesNo }"  type="text" readonly style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">档案编号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					国家:
				</label>
			</td>
			<td class="value" colspan="3">
				<t:dictSelect id="guoJia" field="guoJia" typeGroupCode="trade" datatype="*" defaultVal="${emkFactoryArchivesPage.guoJia }" hasLabel="false" title="国家"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">国家</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					省份:
				</label>
			</td>
			<td class="value">
				<select id="shengFen" name="shengFen"  style="width:150px;">
					<option>--选择--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">省份</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					城市:
				</label>
			</td>
			<td class="value">
				<select id="chengShi" name="chengShi" style="width:150px;">
					<option>--选择--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">城市</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					产业类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="productClassification" field="productClassification" typeGroupCode="cylx" datatype="*" defaultVal="${emkFactoryArchivesPage.productClassification }" hasLabel="false" title="国家"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">产业类型</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					产品类型:
				</label>
			</td>
			<td class="value">
				<input id="proTypeTree" value="${emkFactoryArchivesPage.proTypeName }">
				<input id="proTypeName" name="proTypeName" value="${emkFactoryArchivesPage.proTypeName }" datatype="*"  type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="proType" name="proType" type="hidden" value="${emkFactoryArchivesPage.proType }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂产品类型</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					工厂名称:
				</label>
			</td>
			<td class="value">
				<input id="companyNameZn" name="companyNameZn" value="${emkFactoryArchivesPage.companyNameZn }" datatype="*" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">Local 中文</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					工厂代码:
				</label>
			</td>
			<td class="value">
				<input id="companyCode" name="companyCode" datatype="*" validType="emk_factory_archives,company_code,id"  value="${emkFactoryArchivesPage.companyCode }" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂代码</label>
			</td>


		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					地址:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="addressZn" name="addressZn" type="text" value="${emkFactoryArchivesPage.addressZn }" style="width: 80%" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">地址</label>
			</td>
		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					主要联系人姓名及头衔:
				</label>
			</td>
			<td class="value" >
				<input id="primaryContact" name="primaryContact" value="${emkFactoryArchivesPage.primaryContact }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">主要联系人</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					第二联系人姓名及头衔:
				</label>
			</td>
			<td class="value" >
				<input id="secondaryContact" name="secondaryContact" value="${emkFactoryArchivesPage.secondaryContact }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">第二联系人</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					邮箱:
				</label>
			</td>
			<td class="value">
				<input id="primaryContactEmail" name="primaryContactEmail" value="${emkFactoryArchivesPage.primaryContactEmail }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">邮箱</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					邮箱:
				</label>
			</td>
			<td class="value" >
				<input id="secondaryContactEmail" name="secondaryContactEmail" value="${emkFactoryArchivesPage.secondaryContactEmail }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">E-mail Address邮箱</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					电话:
				</label>
			</td>
			<td class="value">
				<input id="primaryContactTel" name="primaryContactTel" value="${emkFactoryArchivesPage.primaryContactTel }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					电话:
				</label>
			</td>
			<td class="value" >
				<input id="secondaryContactTel" name="secondaryContactTel" value="${emkFactoryArchivesPage.secondaryContactTel }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					成立年份:
				</label>
			</td>
			<td class="value">
				<input id="yearEstablished" name="yearEstablished" value="${emkFactoryArchivesPage.yearEstablished }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">成立年份</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					营业执照号码:
				</label>
			</td>
			<td class="value" >
				<input id="licenseNumber" name="licenseNumber" value="${emkFactoryArchivesPage.licenseNumber }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">营业执照号码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					开户行:
				</label>
			</td>
			<td class="value">
				<input id="bankName" name="bankName" type="text" value="${emkFactoryArchivesPage.bankName }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					账号:
				</label>
			</td>
			<td class="value" >
				<input id="bankAccount" name="bankAccount" type="text" value="${emkFactoryArchivesPage.bankAccount }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">账号</label>
			</td>
		</tr>


	</table>

</t:formvalid>
</body>
<script src = "webpage/com/emk/storage/factoryarchives/emkFactoryArchives.js"></script>
<script>
	$(document).ready(function() {
		$('#proTypeTree').combotree({
			url : 'emkProductTypeController.do?setPOfficeInfo&selfId=${emkProductTypePage.id}',
			panelHeight: 200,
			width: 157,
			onClick: function(node){
				$("#proType").val(node.id);
				$("#proTypeName").val(node.text);

			}
		});
	});
</script>