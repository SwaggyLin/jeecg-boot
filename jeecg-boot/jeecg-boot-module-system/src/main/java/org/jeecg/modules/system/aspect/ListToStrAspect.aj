package org.jeecg.modules.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.aspect.annotation.ListToStr;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public aspect ListToStrAspect {
    // 定义切点Pointcut
    @Pointcut("execution(public * org.jeecg.modules..*.*Controller.*(..))")
    public void excudeService() {
    }
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1=System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
        long start=System.currentTimeMillis();
        this.parseListToStr(result);
        long end=System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时"+(end-start)+"ms");
        return result;
    }

    private void parseListToStr(Object result) {
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
                        if (field.getAnnotation(ListToStr.class) != null) {
                            String fieldName = field.getAnnotation(ListToStr.class).fieldName();
                            List<Object> list= Collections.singletonList(item.get(field.getName()));

                            //翻译List对应的txt
                            String listStr = getListStr(list, fieldName);

                            log.debug(" 结果Val : "+ listStr);
                            log.debug(" __翻译List__ "+field.getName() + CommonConstant.LIST_TO_STR_SUFFIX+"： "+ listStr);
                            item.put(field.getName() + CommonConstant.LIST_TO_STR_SUFFIX, listStr);
                        }
                    }
                    items.add(item);
                }
                ((IPage) ((Result) result).getResult()).setRecords(items);
            }

        }
    }

    private String getListStr(List<Object> list, String fieldName) {
        List<String> strList=new ArrayList<>();

        try {
            for (Object record:list){
                for (Field field :oConvertUtils.getAllFields(record)){
                    if (fieldName.equals(field.getName())){
                        String str=String.valueOf(field.get(fieldName));
                        strList.add(str);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return strList.stream().collect(Collectors.joining(", "));
    }
}
