package com.emk.product.pocolor.service;
import com.emk.product.pocolor.entity.EmkPoColorEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface EmkPoColorServiceI extends CommonService{
	
 	public void delete(EmkPoColorEntity entity) throws Exception;
 	
 	public Serializable save(EmkPoColorEntity entity) throws Exception;
 	
 	public void saveOrUpdate(EmkPoColorEntity entity) throws Exception;
 	
}
