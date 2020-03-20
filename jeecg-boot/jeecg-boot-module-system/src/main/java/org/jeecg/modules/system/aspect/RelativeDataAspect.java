package org.jeecg.modules.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.RelativeData;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

@Aspect
@Component
@Slf4j
@Order(1)
public class RelativeDataAspect {
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    // 定义切点Pointcut
    @Pointcut("execution(public * org.jeecg.modules..*.*Controller.*(..))")
    public void excuteService() {
    }
    @Around("excuteService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1=System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
        long start=System.currentTimeMillis();
        this.parseRelativeData(result);
        long end=System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时"+(end-start)+"ms");
        return result;
    }

    private void parseRelativeData(Object result) {
        if (result instanceof Result) {
            if (((Result) result).getResult() instanceof IPage) {
                List<JSONObject> items = new ArrayList<>();
                for (Object record : ((IPage) ((Result) result).getResult()).getRecords()) {
                    ObjectMapper mapper = new ObjectMapper();
                    String json="{}";
                    try {
                        json = mapper.writeValueAsString(record);
                    } catch (JsonProcessingException e) {
                        log.error("json解析失败"+e.getMessage(),e);
                    }
                    JSONObject item = JSONObject.parseObject(json);
                    for (Field field : oConvertUtils.getAllFields(record)) {
                        if (field.getAnnotation(RelativeData.class) != null) {
                            String tableName = field.getAnnotation(RelativeData.class).mainTable();
                            String fieldName = field.getAnnotation(RelativeData.class).fieldName();
                            String primaryKey = field.getAnnotation(RelativeData.class).primaryKey();
                            String relativeKey = String.valueOf(item.get(field.getName()));

                            //翻译
                            String textValue = translateRelativeData(tableName, fieldName, primaryKey, relativeKey);


                            log.debug(" 结果Val : "+ textValue);
                            log.debug(" __翻译List__ "+field.getName() + CommonConstant.RELATIVE_DATA_SUFFIX+"： "+ textValue);
                            item.put(field.getName() + CommonConstant.RELATIVE_DATA_SUFFIX, textValue);
                        }
                    }
                    items.add(item);
                }
                ((IPage) ((Result) result).getResult()).setRecords(items);
            }

        }
    }

    private String translateRelativeData(String tableName, String fieldName, String primaryKey, String relativeKey) {

        if (!StringUtils.isEmpty(relativeKey)){
            return sysBaseAPI.getRelativeData(tableName,fieldName,primaryKey,relativeKey);
        }else {
            return null;
        }
    }

}
