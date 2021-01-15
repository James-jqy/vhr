package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.Employee;
import org.javaboy.vhr.bean.RespPageBean;
import org.javaboy.vhr.bean.SysOperLog;
import org.javaboy.vhr.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SysOperLogService
 * @Description TODO
 * @Date 2021/1/14 11:14
 * @Created by Jieqiyue
 */
@Service
public class SysOperLogService {
    @Autowired
    SysOperLogMapper sysOperLogMapper;

    public Integer insertOper(SysOperLog operLog) {
        return sysOperLogMapper.insertOperlog(operLog);
    }

    public RespPageBean getOperByPage(Integer page, Integer size) {
        if (page != null && size != null){
            page = (page - 1) * size;
        }

        List<SysOperLog> data = sysOperLogMapper.getSysOperLogByPage(page,size);
        long total = sysOperLogMapper.getTotal();

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(data);
        respPageBean.setTotal(total);

        return respPageBean;
    }
}
