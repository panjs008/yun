package com.emk.storage.cancelorder.controller;
import com.emk.approval.approval.entity.EmkApprovalEntity;
import com.emk.approval.approvaldetail.entity.EmkApprovalDetailEntity;
import com.emk.check.sizecheck.entity.EmkSizeEntity;
import com.emk.check.sizecheck.entity.EmkSizeTotalEntity;
import com.emk.storage.cancelorder.entity.EmkCancelOrderEntity;
import com.emk.storage.cancelorder.service.EmkCancelOrderServiceI;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.storage.cancelqa.entity.EmkCancelQaEntity;
import com.emk.storage.enquiry.entity.EmkEnquiryEntity;
import com.emk.storage.enquirydetail.entity.EmkEnquiryDetailEntity;
import com.emk.storage.factoryarchives.entity.EmkFactoryArchivesEntity;
import com.emk.storage.outorder.entity.EmkOutOrderEntity;
import com.emk.util.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 退货表
 * @author onlineGenerator
 * @date 2019-09-02 11:50:23
 * @version V1.0   
 *
 */
@Api(value="EmkCancelOrder",description="退货表",tags="emkCancelOrderController")
@Controller
@RequestMapping("/emkCancelOrderController")
public class EmkCancelOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkCancelOrderController.class);

	@Autowired
	private EmkCancelOrderServiceI emkCancelOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 退货表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrderList");
	}

	@RequestMapping(params = "orderMxList")
	public ModelAndView orderMxList(HttpServletRequest request) {
		List<Map<String, Object>> list = systemService.findForJdbc("select CONCAT(po_number,'-',color,'-',nc_size) typecode from emk_po_color ");
		String color = JSONHelper.collection2json(list);
		request.setAttribute("color", "'"+color+ "'");
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("proOrderId"))) {
			List<EmkEnquiryDetailEntity> emkEnquiryDetailEntityList = systemService.findHql("from EmkEnquiryDetailEntity where enquiryId = ? order by colorValue asc",map.get("proOrderId"));
			request.setAttribute("emkProOrderDetailEntities", emkEnquiryDetailEntityList);
			EmkSizeEntity emkSizeEntity = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",map.get("proOrderId"));
			request.setAttribute("emkSizePage", emkSizeEntity);
		}
		return new ModelAndView("com/emk/storage/cancelorder/orderMxList");
	}


	@RequestMapping(params = "qaMxList")
	public ModelAndView qaMxList(HttpServletRequest request) {
		Map map = ParameterUtil.getParamMaps(request.getParameterMap());
		if (Utils.notEmpty(map.get("cancelId"))) {
			List<EmkCancelQaEntity> emkCancelQaEntities = systemService.findHql("from EmkCancelQaEntity where cancelId = ?",map.get("cancelId"));
			request.setAttribute("emkCancelQaEntityList", emkCancelQaEntities);
		}
		return new ModelAndView("com/emk/storage/cancelorder/qaMxList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkCancelOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCancelOrder, request.getParameterMap());
		try{
			//自定义追加查询条件
			TSUser user = (TSUser) request.getSession().getAttribute(ResourceUtil.LOCAL_CLINET_USER);
			if(user.getUserKey().equals("工厂")) {
				EmkFactoryArchivesEntity factoryArchivesEntity = systemService.findUniqueByProperty(EmkFactoryArchivesEntity.class,"companyNameZn",user.getCurrentDepart().getDepartname());
				cq.eq("gysCode",factoryArchivesEntity.getCompanyCode());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkCancelOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除退货表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkCancelOrder = systemService.getEntity(EmkCancelOrderEntity.class, emkCancelOrder.getId());
		message = "退货表删除成功";
		try{
			emkCancelOrderService.delete(emkCancelOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退货表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除退货表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkCancelOrderEntity emkCancelOrder = systemService.getEntity(EmkCancelOrderEntity.class, 
				id
				);
				if (!emkCancelOrder.getState().equals("0")) {
					message = "存在已提交的退货，请重新选择！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
				if("0".equals(emkCancelOrder.getState())){
					systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))",emkCancelOrder.getId());
					systemService.executeSql("delete from emk_cancel_qa where cancel_id=?",emkCancelOrder.getOrderId());
					systemService.executeSql("delete from emk_size where form_id=?", emkCancelOrder.getId());
					emkCancelOrderService.delete(emkCancelOrder);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			message = "退货表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加退货表
	 * 
	 * @param ids
	 * @return
	 */
		@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkCancelOrderEntity emkCancelOrder,EmkSizeEntity emkSize, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货表添加成功";
		try{
			emkCancelOrder.setState("0");
			String orderNo = emkCancelOrder.getOrderNo();

			/*List<EmkCancelOrderEntity> list = systemService.findHql("from EmkCancelOrderEntity where state=0");
			if(list.size()>0){
				j.setMsg("存在未提交的退货，无法录入新的数据");
				j.setSuccess(false);
				return j;
			}*/
			EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkCancelOrder.getOrderNo());
			if(Integer.valueOf(emkEnquiryEntity.getState().toString())<11){
				j.setMsg("存在未验货的订单，无法录入新的数据");
				j.setSuccess(false);
				return j;
			}
			//Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no, 2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+emkCancelOrder.getOrderNo()+"-退货-"+"%'");
			String eNo = "";
			if(emkCancelOrder.getOrderNo().contains("退货")){
				eNo = emkCancelOrder.getOrderNo().substring(0,emkCancelOrder.getOrderNo().indexOf("-退货"));
			}else{
				eNo = emkCancelOrder.getOrderNo();
			}
			Map orderNum = systemService.findOneForJdbc("select CAST(ifnull(max(right(enquiry_no,2)),0)+1 AS signed) orderNum from emk_enquiry where enquiry_no like '%"+eNo+"-退货%'");
			EmkCancelOrderEntity cancelOrderEntity = systemService.findUniqueByProperty(EmkCancelOrderEntity.class,"orderNo",eNo);

			Map vint = systemService.findOneForJdbc("select ifnull(t.TEXT_,0) isBack from act_hi_varinst t where t.PROC_INST_ID_ in (select h.PROC_INST_ID_ from act_hi_varinst h where h.TEXT_=?) AND t.NAME_='isBack' limit 0,1",emkEnquiryEntity.getId());
			if("1".equals(vint.get("isBack"))){
				emkCancelOrder.setOrderNo(eNo+"-退货"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
			}else{
				if(orderNum.get("orderNum").toString().equals("1") && Utils.isEmpty(cancelOrderEntity)){
					emkCancelOrder.setOrderId(emkEnquiryEntity.getId());
				}else{
					emkCancelOrder.setOrderNo(eNo+"-退货"+String.format("%02d", Integer.valueOf(orderNum.get("orderNum").toString())));
				}
			}

			emkCancelOrderService.save(emkCancelOrder);

			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
			emkSize.setFormId(emkCancelOrder.getId());

			systemService.save(emkSize);
			Class c = Class.forName(EmkSizeTotalEntity.class.getName());
			String dataRows = (String) map.get("dataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkEnquiryDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(emkCancelOrder.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setColorValue(colorArr[0]);
							orderMxEntity.setColor(colorArr[1]);
							orderMxEntity.setSize(colorArr[2]);
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}
						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",eNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());
						if(Utils.notEmpty(price)){
							orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
							orderMxEntity.setMoney(orderMxEntity.getTotal()*orderMxEntity.getPrice());
						}else{
							if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
								orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
								orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
							}
						}

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						systemService.save(orderMxEntity);

						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}

			dataRows = (String) map.get("qadataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				int rows = Integer.parseInt(dataRows);
				EmkCancelQaEntity orderMxEntity = null;
				for (int i = 0; i < rows; i++) {
					if(Utils.notEmpty(map.get("orderMxList["+i+"].question"))){
						orderMxEntity = new EmkCancelQaEntity();
						orderMxEntity.setCancelId(emkCancelOrder.getId());
						orderMxEntity.setQuestion(map.get("orderMxList["+i+"].question"));
						orderMxEntity.setFileName(map.get("orderMxList["+i+"].fileName"));
						orderMxEntity.setFileNameUrl(map.get("orderMxList["+i+"].fileNameUrl"));
						orderMxEntity.setSaveFileName(map.get("orderMxList["+i+"].saveFileName"));
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].totalQa"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].totalQa")));
						}
						systemService.save(orderMxEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退货表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新退货表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkCancelOrderEntity emkCancelOrder,EmkSizeEntity emkSize,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货表更新成功";
		Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
		EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkCancelOrder.getOrderNo());
		emkCancelOrder.setOrderId(emkEnquiryEntity.getId());
		EmkCancelOrderEntity t = emkCancelOrderService.get(EmkCancelOrderEntity.class, emkCancelOrder.getId());
		EmkSizeEntity t2 = systemService.findUniqueByProperty(EmkSizeEntity.class,"formId",t.getId());

		try {
			if(!t.getState().equals("0") && !t.getState().equals("3")){
				j.setMsg("退货已提交，无法进行修改");
				j.setSuccess(false);
				return j;
			}
			MyBeanUtils.copyBeanNotNull2Bean(emkCancelOrder, t);
			emkCancelOrderService.saveOrUpdate(t);

			emkSize.setId(null);
			if(Utils.notEmpty(t2)){
				MyBeanUtils.copyBeanNotNull2Bean(emkSize, t2);
				systemService.saveOrUpdate(t2);
			}else{
				t2 = new EmkSizeEntity();
				MyBeanUtils.copyBeanNotNull2Bean(emkSize, t2);
				t2.setFormId(t.getId());
				systemService.save(t2);
			}
			String dataRows = (String) map.get("dataRowsVal");
			String eNo = "";
			if(emkCancelOrder.getOrderNo().contains("退货")){
				eNo = emkCancelOrder.getOrderNo().substring(0,emkCancelOrder.getOrderNo().indexOf("-退货"));
			}else{
				eNo = emkCancelOrder.getOrderNo();
			}
			if (Utils.notEmpty(dataRows)) {
				Class c = Class.forName(EmkSizeTotalEntity.class.getName());
				systemService.executeSql("delete from emk_size_total where FIND_IN_SET(p_id,(SELECT GROUP_CONCAT(id) FROM emk_enquiry_detail where enquiry_id=?))", t.getId());
				systemService.executeSql("delete from emk_enquiry_detail where enquiry_id=?",t.getId());

				int rows = Integer.parseInt(dataRows);
				EmkEnquiryDetailEntity orderMxEntity = null;
				EmkSizeTotalEntity emkSizeTotalEntity = null;
				String m0 = "";
				Method show = null;
				for (int i = 0; i < rows; i++) {
					if (Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(t.getId());
						if(Utils.notEmpty(map.get("orderMxList["+i+"].color"))){
							String[] colorArr = map.get("orderMxList["+i+"].color").split("-");
							orderMxEntity.setColorValue(colorArr[0]);
							orderMxEntity.setColor(colorArr[1]);
							orderMxEntity.setSize(colorArr[2]);
						}
						if(Utils.notEmpty(map.get("orderMxList["+i+"].total"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].total")));
						}

						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",eNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());
						if(Utils.notEmpty(price)){
							orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
							orderMxEntity.setMoney(orderMxEntity.getTotal()*orderMxEntity.getPrice());
						}else{
							if(Utils.notEmpty(map.get("orderMxList["+i+"].price"))) {
								orderMxEntity.setPrice(Double.valueOf(map.get("orderMxList["+i+"].price")));
							}
							if(Utils.notEmpty(map.get("orderMxList["+i+"].money"))) {
								orderMxEntity.setMoney(Double.valueOf(map.get("orderMxList["+i+"].money")));
							}
						}

						orderMxEntity.setSortDesc(String.valueOf(i+1));

						systemService.save(orderMxEntity);
						emkSizeTotalEntity = new EmkSizeTotalEntity();
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(map.get("orderMxList["+i+"].total"+(char)(z+64))) ? map.get("orderMxList["+i+"].total"+(char)(z+64)):"");
						}
						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}
			}
			dataRows = (String) map.get("qadataRowsVal");
			if (Utils.notEmpty(dataRows)) {
				systemService.executeSql("delete from emk_cancel_qa where cancel_id=?",t.getId());
				int rows = Integer.parseInt(dataRows);
				EmkCancelQaEntity orderMxEntity = null;
				for (int i = 0; i < rows; i++) {
					if(Utils.notEmpty(map.get("orderMxList["+i+"].question"))){
						orderMxEntity = new EmkCancelQaEntity();
						orderMxEntity.setCancelId(t.getId());
						orderMxEntity.setQuestion(map.get("orderMxList["+i+"].question"));
						orderMxEntity.setFileName(map.get("orderMxList["+i+"].fileName"));
						orderMxEntity.setFileNameUrl(map.get("orderMxList["+i+"].fileNameUrl"));
						orderMxEntity.setSaveFileName(map.get("orderMxList["+i+"].saveFileName"));
						orderMxEntity.setSortDesc(String.valueOf(i+1));
						if(Utils.notEmpty(map.get("orderMxList["+i+"].totalQa"))) {
							orderMxEntity.setTotal(Integer.parseInt(map.get("orderMxList["+i+"].totalQa")));
						}
						systemService.save(orderMxEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工厂退货表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params="doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "退货提交成功";
		try {
			int flag = 0;
			TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
			Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());

			if ((Utils.isEmpty(emkCancelOrder.getId()))) {
				for (String id : map.get("ids").split(",")) {
					EmkCancelOrderEntity cancelOrderEntity = systemService.getEntity(EmkCancelOrderEntity.class, id);
					if(!cancelOrderEntity.getCreateBy().equals(user.getUserName())){
						message = "只能由创建人提交退货！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
					if (!cancelOrderEntity.getState().equals("0") && !cancelOrderEntity.getState().equals("3")) {
						message = "存在已提交的退货，请重新选择在提交！";
						j.setSuccess(false);
						flag = 1;
						break;
					}
				}
			}else{
				map.put("ids", emkCancelOrder.getId());
			}
			Map<String, Object> variables = new HashMap();
			if (flag == 0) {
				for (String id : map.get("ids").split(",")) {
					emkCancelOrder = systemService.get(EmkCancelOrderEntity.class,id);
					String eNo = "";
					if(emkCancelOrder.getOrderNo().contains("退货")){
						eNo = emkCancelOrder.getOrderNo().substring(0,emkCancelOrder.getOrderNo().indexOf("-退货"));
					}else{
						eNo = emkCancelOrder.getOrderNo();
					}
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",eNo);
					EmkEnquiryEntity emkEnquiryEntity2 = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkCancelOrder.getOrderNo());
					EmkEnquiryEntity t = null;
					//Map vint = systemService.findOneForJdbc("select ifnull(t.TEXT_,0) isBack from act_hi_varinst t where t.PROC_INST_ID_ in (select h.PROC_INST_ID_ from act_hi_varinst h where h.TEXT_=?) AND t.NAME_='isBack' limit 0,1",emkEnquiryEntity.getId());
					if(Utils.notEmpty(emkEnquiryEntity2)) {
						if (emkEnquiryEntity2.getState().equals("13")) {
							t = systemService.get(EmkEnquiryEntity.class, emkEnquiryEntity2.getId());
						} else {
							if(eNo.equals(emkCancelOrder.getOrderNo())){
								t = systemService.get(EmkEnquiryEntity.class,emkCancelOrder.getOrderId());
							}else{
								t = new EmkEnquiryEntity();
								MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
								t.setEnquiryNo(emkCancelOrder.getOrderNo());
								t.setState("11");
								t.setId(null);
								systemService.save(t);
								emkCancelOrder.setOrderId(t.getId());

								EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
								EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
								ApprovalUtil.saveApproval(approvalEntity,0,emkCancelOrder.getOrderNo(),t.getId(),user);
								systemService.save(approvalEntity);
								ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
								systemService.save(approvalDetailEntity);

								variables.put("optUser", t.getId());
								ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
								List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								Task task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								taskService.complete(task1.getId(), variables);

								task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
								task1 = task.get(task.size() - 1);
								variables.put("isBack","0");
								taskService.complete(task1.getId(), variables);
							}
						}
					}else{
						if(eNo.equals(emkCancelOrder.getOrderNo())){
							t = systemService.get(EmkEnquiryEntity.class,emkCancelOrder.getOrderId());
						}else{
							t = new EmkEnquiryEntity();
							MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
							t.setEnquiryNo(emkCancelOrder.getOrderNo());
							t.setState("11");
							t.setId(null);
							systemService.save(t);
							emkCancelOrder.setOrderId(t.getId());

							EmkApprovalEntity approvalEntity = new EmkApprovalEntity();
							EmkApprovalDetailEntity approvalDetailEntity = new EmkApprovalDetailEntity();
							ApprovalUtil.saveApproval(approvalEntity,0,emkCancelOrder.getOrderNo(),t.getId(),user);
							systemService.save(approvalEntity);
							ApprovalUtil.saveApprovalDetail(approvalDetailEntity,approvalEntity.getId(),"报单","billTask","提交",user);
							systemService.save(approvalDetailEntity);

							variables.put("optUser", t.getId());
							ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("femk", "emkEnquiryEntity", variables);
							List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							Task task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							taskService.complete(task1.getId(), variables);

							task = taskService.createTaskQuery().taskAssignee(t.getId()).list();
							task1 = task.get(task.size() - 1);
							variables.put("isBack","0");
							taskService.complete(task1.getId(), variables);
						}
					}
					//if("1".equals(vint.get("isBack")) || (!emkEnquiryEntity.getState().equals("11") && !emkEnquiryEntity.getState().equals("13"))){
					/*if("1".equals(vint.get("isBack"))){

					}else{
						t = systemService.get(EmkEnquiryEntity.class,emkCancelOrder.getOrderId());
					}*/

					variables.put("optUser", t.getId());
					EmkApprovalEntity b = systemService.findUniqueByProperty(EmkApprovalEntity.class,"formId",t.getId());
					List<Task> task = taskService.createTaskQuery().taskAssignee(t.getId()).list();

					if (task.size() > 0) {
						//保存审批意见
						EmkApprovalDetailEntity approvalDetail = ApprovalUtil.saveApprovalDetail(b.getId(),user,b,map.get("advice"));
						TSUser makerUser = systemService.findUniqueByProperty(TSUser.class,"userName",t.getCreateBy());
						TSUser bpmUser = null;
						if (task.size() > 0) {
							bpmUser = systemService.get(TSUser.class,b.getBpmSherId());
						}else{
							bpmUser = systemService.get(TSUser.class,b.getCommitId());
						}
						Task task1 = (Task)task.get(task.size() - 1);
						if (task1.getTaskDefinitionKey().equals("tuihuoTask")) {
							taskService.complete(task1.getId(), variables);
							t.setState("12");
							b.setStatus(12);
							String title = "";
							if(emkCancelOrder.getState().equals("10")){
								title = "重新提交退货";
							}else{
								title = "提交退货";
							}
							saveApprvoalDetail(approvalDetail,"退货","tuihuoTask",0,title);

							String userKey = "";
							Map userM = null;
							/*userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t where t.userkey='工厂'");
							if(Utils.notEmpty(userM)){
								saveSmsAndEmailForMany("工厂","叫布","您有【"+b.getCreateName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。",user.getUserName());
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}*/
							userM = systemService.findOneForJdbc("select GROUP_CONCAT(t.realname) realNames,GROUP_CONCAT(t.username) userNames from t_s_base_user t left join t_s_depart d on d.id=t.departid where t.userkey='工厂' and d.departname=?",t.getGys());
							if(Utils.notEmpty(userM)){
								saveSmsAndEmailForGc(user,t.getGys(),"退货","您有【"+user.getRealName()+"】"+title+"，单号："+b.getWorkNum()+"，请及时审核。");
								b.setNextBpmSherId(userM.get("userNames").toString());
								b.setNextBpmSher(userM.get("realNames").toString());
							}
						}
						systemService.save(approvalDetail);
						emkCancelOrder.setState("1");
						systemService.saveOrUpdate(t);
						systemService.saveOrUpdate(b);
					}else{
						message = "退货提交异常";
						j.setMsg(message);
						j.setSuccess(false);
						return j;
					}

				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "退货提交失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 退货表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		if (StringUtil.isNotEmpty(emkCancelOrder.getId())) {
			emkCancelOrder = emkCancelOrderService.getEntity(EmkCancelOrderEntity.class, emkCancelOrder.getId());
			req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		}
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-add");
	}

	/**
	 * 退货表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkCancelOrder.getId())) {
			emkCancelOrder = emkCancelOrderService.getEntity(EmkCancelOrderEntity.class, emkCancelOrder.getId());
			req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		}
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-update");
	}
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(EmkCancelOrderEntity emkCancelOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkCancelOrder.getId())) {
			emkCancelOrder = emkCancelOrderService.findUniqueByProperty(EmkCancelOrderEntity.class,"orderId",emkCancelOrder.getId());
			req.setAttribute("emkCancelOrderPage", emkCancelOrder);
		}
		return new ModelAndView("com/emk/storage/cancelorder/emkCancelOrder-update2");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("kdDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
		return new ModelAndView("com/emk/storage/cancelorder/uploadView");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkCancelOrderEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkCancelOrder, request.getParameterMap());
		List<EmkCancelOrderEntity> emkCancelOrders = this.emkCancelOrderService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"退货表");
		modelMap.put(NormalExcelConstants.CLASS,EmkCancelOrderEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退货表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkCancelOrders);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkCancelOrderEntity emkCancelOrder,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"退货表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkCancelOrderEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退货表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(EmkCancelOrderEntity emkCancelOrder,String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";
		EmkEnquiryDetailEntity orderMxEntity = null;
		EmkSizeTotalEntity emkSizeTotalEntity = null;
		String orderNo = emkCancelOrder.getOrderNo();

		try {
			String savepath = request.getRealPath("/")+"imp/order/";
			newfile =  new File(savepath+fileName);
			wb = WebFileUtils.createHSSFWorkBook(newfile);
			HSSFSheet sheet = wb.getSheetAt(0);
			DecimalFormat df = new DecimalFormat("0");
			HSSFCell cell = null;
			int counter = 0;
			HSSFRow row = null;
			logger.info("执行导入："+newfile.getName());
			List<String> itemValue = null;
			Map orderNum = null;
			TSDepart tsDepart = null;
			String m0 = "";
			Method show = null;

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				itemValue = new ArrayList<String>();
				for(int z = 0; z <= 28 ; z++){
					cell = row.getCell(z);
					if(null == cell){
						itemValue.add(cellValue);
						continue;
					}
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							cellValue =cell.getRichStringCellValue().getString().trim();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
								SimpleDateFormat sdf = null;
								if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
										.getBuiltinFormat("h:mm")) {
									sdf = new SimpleDateFormat("HH:mm");
								} else {// 日期
									sdf = new SimpleDateFormat("yyyy-MM-dd");
								}
								Date date = cell.getDateCellValue();
								cellValue = sdf.format(date);
							} else if (cell.getCellStyle().getDataFormat() == 58) {
								// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil
										.getJavaDate(value);
								cellValue = sdf.format(date);
							} else {
								double value = cell.getNumericCellValue();
								CellStyle style = cell.getCellStyle();
								DecimalFormat format = new DecimalFormat();
								String temp = style.getDataFormatString();
								// 单元格设置成常规
								if (temp.equals("General")) {
									format.applyPattern("#");
								}
								cellValue = format.format(value);
							}
							//cellValue = df.format(cell.getNumericCellValue()).toString();
							break;

						default:
							cellValue = "";
					}
					itemValue.add(cellValue);
					cellValue = "";
				}
				if(i == 0 ){
					EmkEnquiryEntity emkEnquiryEntity = systemService.findUniqueByProperty(EmkEnquiryEntity.class,"enquiryNo",emkCancelOrder.getOrderNo());
					if(Integer.valueOf(emkEnquiryEntity.getState().toString())<11){
						j.setMsg("存在未验货的订单，无法录入新的数据");
						j.setSuccess(false);
						return j;
					}
					EmkEnquiryEntity t = new EmkEnquiryEntity();
					t.setState("11");
					MyBeanUtils.copyBeanNotNull2Bean(emkEnquiryEntity,t);
					t.setId(null);
					systemService.save(t);

					emkCancelOrder.setState("0");
					emkCancelOrder.setOrderId(t.getId());

					TSUser user = (TSUser)request.getSession().getAttribute("LOCAL_CLINET_USER");
					Map<String, String> map = ParameterUtil.getParamMaps(request.getParameterMap());
					emkCancelOrderService.save(emkCancelOrder);

					EmkSizeEntity emkSize = new EmkSizeEntity();
					Class c = Class.forName(EmkSizeEntity.class.getName());
					emkSize.setFormId(emkCancelOrder.getId());
					for(int z = 1 ; z < 23 ; z++){
						m0 = "setSize"+(char)(z+64);
						show = c.getMethod(m0,String.class);
						show.invoke(emkSize,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
					}
					systemService.save(emkSize);


				}else{
					if(Utils.notEmpty(itemValue.get(0))){
						orderMxEntity = new EmkEnquiryDetailEntity();
						orderMxEntity.setEnquiryId(emkCancelOrder.getId());
						orderMxEntity.setColorValue(itemValue.get(0));
						orderMxEntity.setColor(itemValue.get(1));
						orderMxEntity.setSize(itemValue.get(2));

						orderMxEntity.setSortDesc(String.valueOf(i+1));
						emkSizeTotalEntity = new EmkSizeTotalEntity();
						Class c = Class.forName(EmkSizeTotalEntity.class.getName());
						int total = 0;
						for(int z = 1 ; z < 23 ; z++){
							m0 = "setTotal"+(char)(z+64);
							show = c.getMethod(m0,String.class);
							total += Integer.valueOf(Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"0");
							show.invoke(emkSizeTotalEntity,Utils.notEmpty(itemValue.get(z+2)) ? itemValue.get(z+2):"");
						}
						orderMxEntity.setTotal(total);

						Map price = systemService.findOneForJdbc("select ifnull(t1.price,0) price from emk_enquiry t LEFT JOIN emk_enquiry_detail t1 on t1.enquiry_id=t.id " +
								"where t.enquiry_no=? and t1.color_value=? and t1.color=? and t1.size=?",orderNo,orderMxEntity.getColorValue(),orderMxEntity.getColor(),orderMxEntity.getSize());
						orderMxEntity.setPrice(Double.valueOf(price.get("price").toString()));
						orderMxEntity.setMoney(total*orderMxEntity.getPrice());

						systemService.save(orderMxEntity);

						emkSizeTotalEntity.setpId(orderMxEntity.getId());
						systemService.save(emkSizeTotalEntity);
					}
				}

				j.setSuccess(true);
			}
		} catch (Exception e) {
			message = "文件导入失败";
			j.setSuccess(false);
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}finally{
			newfile.delete();
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="退货表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkCancelOrderEntity>> list() {
		List<EmkCancelOrderEntity> listEmkCancelOrders=emkCancelOrderService.getList(EmkCancelOrderEntity.class);
		return Result.success(listEmkCancelOrders);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取退货表信息",notes="根据ID获取退货表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkCancelOrderEntity task = emkCancelOrderService.get(EmkCancelOrderEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取退货表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建退货表")
	public ResponseMessage<?> create(@ApiParam(name="退货表对象")@RequestBody EmkCancelOrderEntity emkCancelOrder, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCancelOrderEntity>> failures = validator.validate(emkCancelOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCancelOrderService.save(emkCancelOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("退货表信息保存失败");
		}
		return Result.success(emkCancelOrder);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新退货表",notes="更新退货表")
	public ResponseMessage<?> update(@ApiParam(name="退货表对象")@RequestBody EmkCancelOrderEntity emkCancelOrder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkCancelOrderEntity>> failures = validator.validate(emkCancelOrder);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkCancelOrderService.saveOrUpdate(emkCancelOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新退货表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新退货表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除退货表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkCancelOrderService.deleteEntityById(EmkCancelOrderEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("退货表删除失败");
		}

		return Result.success();
	}
}
