package com.emk.product.pmcolor.service;
import com.emk.product.pmcolor.entity.EmkPmColorEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkPmColorServiceI extends CommonService{
	
 	public void delete(EmkPmColorEntity entity) throws Exception;
 	
 	public Serializable save(EmkPmColorEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkPmColorEntity entity) throws Exception;
 	
}
