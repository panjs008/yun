package com.emk.storage.sampleyin.service.impl;

import com.emk.storage.sampleyin.entity.EmkSampleYinEntity;
import com.emk.storage.sampleyin.service.EmkSampleYinServiceI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("emkSampleYinService")
@Transactional
public class EmkSampleYinServiceImpl
        extends CommonServiceImpl
        implements EmkSampleYinServiceI {
    public void delete(EmkSampleYinEntity entity)
            throws Exception {
        super.delete(entity);

        doDelBus(entity);
    }

    public Serializable save(EmkSampleYinEntity entity)
            throws Exception {
        Serializable t = super.save(entity);

        doAddBus(entity);
        return t;
    }

    public void saveOrUpdate(EmkSampleYinEntity entity)
            throws Exception {
        super.saveOrUpdate(entity);

        doUpdateBus(entity);
    }

    private void doAddBus(EmkSampleYinEntity t)
            throws Exception {
    }

    private void doUpdateBus(EmkSampleYinEntity t)
            throws Exception {
    }

    private void doDelBus(EmkSampleYinEntity t)
            throws Exception {
    }

    private Map<String, Object> populationMap(EmkSampleYinEntity t) {
        Map<String, Object> map = new HashMap();
        map.put("id", t.getId());
        map.put("create_name", t.getCreateName());
        map.put("create_by", t.getCreateBy());
        map.put("create_date", t.getCreateDate());
        map.put("sys_org_code", t.getSysOrgCode());
        map.put("gyzy", t.getGyzy());
        map.put("size", t.getSize());
        map.put("chengben", t.getChengben());
        map.put("sample_id", t.getSampleId());
        return map;
    }

    public String replaceVal(String sql, EmkSampleYinEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{sys_org_code}", String.valueOf(t.getSysOrgCode()));
        sql = sql.replace("#{gyzy}", String.valueOf(t.getGyzy()));
        sql = sql.replace("#{size}", String.valueOf(t.getSize()));
        sql = sql.replace("#{chengben}", String.valueOf(t.getChengben()));
        sql = sql.replace("#{sample_id}", String.valueOf(t.getSampleId()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data)
            throws Exception {
        if (StringUtil.isNotEmpty(cgJavaValue)) {
            Object obj = null;
            try {
                if ("class".equals(cgJavaType)) {
                    obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                } else if ("spring".equals(cgJavaType)) {
                    obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
                }
                if ((obj instanceof CgformEnhanceJavaInter)) {
                    CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
                    javaInter.execute("emk_sample_yin", data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("执行JAVA增强出现异常！");
            }
        }
    }
}
