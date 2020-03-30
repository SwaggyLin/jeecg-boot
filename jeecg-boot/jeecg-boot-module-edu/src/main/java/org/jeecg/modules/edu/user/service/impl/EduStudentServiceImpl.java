package org.jeecg.modules.edu.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.edu.user.entity.EduStudent;
import org.jeecg.modules.edu.user.mapper.EduStudentMapper;
import org.jeecg.modules.edu.user.service.IEduStudentService;
import org.springframework.stereotype.Service;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-21
 * @Version: V1.0
 */
@Service
public class EduStudentServiceImpl extends ServiceImpl<EduStudentMapper, EduStudent> implements IEduStudentService {

}
