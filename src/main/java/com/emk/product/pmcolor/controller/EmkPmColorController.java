package com.emk.product.pmcolor.controller;
import com.emk.product.pmcolor.entity.EmkPmColorEntity;
import com.emk.product.pmcolor.service.EmkPmColorServiceI;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emk.product.pocolor.entity.EmkPoColorEntity;
import com.emk.util.Utils;
import com.emk.util.WebFileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.jeecgframework.core.util.ExceptionUtil;

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
 * @Description: 品名色号表
 * @author onlineGenerator
 * @date 2019-08-24 14:19:29
 * @version V1.0   
 *
 */
@Api(value="EmkPmColor",description="品名色号表",tags="emkPmColorController")
@Controller
@RequestMapping("/emkPmColorController")
public class EmkPmColorController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmkPmColorController.class);

	@Autowired
	private EmkPmColorServiceI emkPmColorService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 品名色号表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/emk/product/pmcolor/emkPmColorList");
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
	public void datagrid(EmkPmColorEntity emkPmColor,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(EmkPmColorEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkPmColor, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.emkPmColorService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除品名色号表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(EmkPmColorEntity emkPmColor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		emkPmColor = systemService.getEntity(EmkPmColorEntity.class, emkPmColor.getId());
		message = "品名色号表删除成功";
		try{
			emkPmColorService.delete(emkPmColor);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品名色号表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除品名色号表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "品名色号表删除成功";
		try{
			for(String id:ids.split(",")){
				EmkPmColorEntity emkPmColor = systemService.getEntity(EmkPmColorEntity.class, 
				id
				);
				emkPmColorService.delete(emkPmColor);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "品名色号表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加品名色号表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(EmkPmColorEntity emkPmColor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "品名色号表添加成功";
		try{
			emkPmColorService.save(emkPmColor);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品名色号表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新品名色号表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(EmkPmColorEntity emkPmColor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "品名色号表更新成功";
		EmkPmColorEntity t = emkPmColorService.get(EmkPmColorEntity.class, emkPmColor.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(emkPmColor, t);
			emkPmColorService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品名色号表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 品名色号表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(EmkPmColorEntity emkPmColor, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkPmColor.getId())) {
			emkPmColor = emkPmColorService.getEntity(EmkPmColorEntity.class, emkPmColor.getId());
			req.setAttribute("emkPmColorPage", emkPmColor);
		}
		return new ModelAndView("com/emk/product/pmcolor/emkPmColor-add");
	}
	/**
	 * 品名色号表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(EmkPmColorEntity emkPmColor, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(emkPmColor.getId())) {
			emkPmColor = emkPmColorService.getEntity(EmkPmColorEntity.class, emkPmColor.getId());
			req.setAttribute("emkPmColorPage", emkPmColor);
		}
		return new ModelAndView("com/emk/product/pmcolor/emkPmColor-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","emkPmColorController");
		return new ModelAndView("common/upload/pub_excel_upload2");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(EmkPmColorEntity emkPmColor,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(EmkPmColorEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, emkPmColor, request.getParameterMap());
		List<EmkPmColorEntity> emkPmColors = this.emkPmColorService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"品名色号表");
		modelMap.put(NormalExcelConstants.CLASS,EmkPmColorEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("品名色号表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,emkPmColors);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(EmkPmColorEntity emkPmColor,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"品名色号表");
    	modelMap.put(NormalExcelConstants.CLASS,EmkPmColorEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("品名色号表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(String fileName,String fileNameUrl,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String message = "文件导入成功";
		File newfile = null;
		HSSFWorkbook wb = null;
		String cellValue = "";
		EmkPmColorEntity emkPmColorEntity = null;
		try {
			String savepath = request.getRealPath("/")+"imp/po/";
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
			for (int i = 3; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				itemValue = new ArrayList<String>();
				for(int z = 0; z <= 2 ; z++){
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
				if(Utils.notEmpty(itemValue.get(0))){
					systemService.executeSql("delete from emk_pm_color where pm=? and color_num=?",itemValue.get(0),itemValue.get(1));
					emkPmColorEntity = new EmkPmColorEntity();
					emkPmColorEntity.setPm(itemValue.get(0));
					emkPmColorEntity.setColorNum(itemValue.get(1));
					systemService.save(emkPmColorEntity);
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
	@ApiOperation(value="品名色号表列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<EmkPmColorEntity>> list() {
		List<EmkPmColorEntity> listEmkPmColors=emkPmColorService.getList(EmkPmColorEntity.class);
		return Result.success(listEmkPmColors);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取品名色号表信息",notes="根据ID获取品名色号表信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		EmkPmColorEntity task = emkPmColorService.get(EmkPmColorEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取品名色号表信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建品名色号表")
	public ResponseMessage<?> create(@ApiParam(name="品名色号表对象")@RequestBody EmkPmColorEntity emkPmColor, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkPmColorEntity>> failures = validator.validate(emkPmColor);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkPmColorService.save(emkPmColor);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("品名色号表信息保存失败");
		}
		return Result.success(emkPmColor);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新品名色号表",notes="更新品名色号表")
	public ResponseMessage<?> update(@ApiParam(name="品名色号表对象")@RequestBody EmkPmColorEntity emkPmColor) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<EmkPmColorEntity>> failures = validator.validate(emkPmColor);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			emkPmColorService.saveOrUpdate(emkPmColor);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新品名色号表信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新品名色号表信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除品名色号表")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			emkPmColorService.deleteEntityById(EmkPmColorEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("品名色号表删除失败");
		}

		return Result.success();
	}
}
