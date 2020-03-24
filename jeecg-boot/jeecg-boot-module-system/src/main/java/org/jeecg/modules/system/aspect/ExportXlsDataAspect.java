package org.jeecg.modules.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.aspect.annotation.RelativeData;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.oConvertUtils;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 导出Excel数据转换
 * @author linminfeng
 * @Date 2020/03/24
 */
@Aspect
@Component
@Slf4j
public class ExportXlsDataAspect {
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    // 定义切点Pointcut
    @Pointcut("execution(public * org.jeecg.modules..*.*Controller.exportXls(..))")
    public void excuteService() {
    }
    @Around("excuteService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        this.parseExportXlsData(result);
        return result;
    }

    private void parseExportXlsData(Object result) throws IllegalAccessException {
        if (result instanceof ModelAndView){
            List<T> exportList= (List<T>) ((ModelAndView) result).getModelMap().get(NormalExcelConstants.DATA_LIST);
            for(Object record : exportList){
                for (Field field : oConvertUtils.getAllFields(record)){
                    if (field.getAnnotation(RelativeData.class) != null) {
                        translateRelativeData(field,record);
                    }
                }
            }
        }
    }

    private void translateRelativeData(Field field, Object record) throws IllegalAccessException {
        field.setAccessible(true);
        String tableName = field.getAnnotation(RelativeData.class).mainTable();
        String fieldName = field.getAnnotation(RelativeData.class).fieldName();
        String primaryKey = field.getAnnotation(RelativeData.class).primaryKey();
        String relativeKey = String.valueOf(field.get(record));
        //翻译
        String textValue = translateRelativeDataValue(tableName, fieldName, primaryKey, relativeKey);
        field.set(record,textValue);
    }

    private String translateRelativeDataValue(String tableName, String fieldName, String primaryKey, String relativeKey) {
        if (!StringUtils.isEmpty(relativeKey)){
            return sysBaseAPI.getRelativeData(tableName,fieldName,primaryKey,relativeKey);
        }else {
            return null;
        }
    }
}
