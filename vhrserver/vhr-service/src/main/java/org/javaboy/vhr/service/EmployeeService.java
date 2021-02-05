package org.javaboy.vhr.service;


import com.github.pagehelper.PageHelper;
import org.apache.ibatis.jdbc.SQL;
import org.javaboy.vhr.bean.*;
import org.javaboy.vhr.mapper.EmpSalaryMapper;
import org.javaboy.vhr.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Classname EmployeeService
 * @Description TODO
 * @Date 2020/12/23 18:54
 * @Created by Jieqiyue
 */
@Service
public class EmployeeService {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeMapper employeeMapper;

    // 当用户在删除员工的时候，也需要去把用户的那条薪资套账的记录一起删除。
    @Autowired
    EmpSalaryMapper empSalaryMapper;

    @Autowired
    MailSendLogService mailSendLogService;

    // 此处的rabbittemplate是自定义的。修改了发送失败的时候的回调。
    @Autowired
    RabbitTemplate rabbitTemplate;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat mouthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public List<Employee> getEmpByPageUseHelper(Integer page , Integer size){
        PageHelper.startPage(page, size);
        List<Employee> data = employeeMapper.getEmpByPageUseHelper(page,size);
        return data;
    }
    // 获取employee，并且进行分页操作。
    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee,Date[] beginDateScope) {
        if (page != null && size != null){
            page = (page - 1) * size;
        }

        List<Employee> data = employeeMapper.getEmployeeByPage(page,size,employee,beginDateScope);
        long total = employeeMapper.getTotal(employee,beginDateScope);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(data);
        respPageBean.setTotal(total);

        return respPageBean;
    }

    public int addEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();

        double v = Double.parseDouble(yearFormat.format(endContract));
        double v1 = Double.parseDouble(yearFormat.format(beginContract));
        double v2 = Double.parseDouble(mouthFormat.format(endContract));
        double v3 = Double.parseDouble(mouthFormat.format(beginContract));

        employee.setContractTerm(Double.parseDouble(decimalFormat.format(((v - v1)*12 + (v2 - v3))/12)));
        int i = employeeMapper.insertSelective(employee);

        // i如果是1，就代表插入员工记录成功。
        if (i == 1){
            // 通过主键回填找到被插入的那条员工记录。
            Employee emp = employeeMapper.getEmployeeById(employee.getId());
            // 实例化一个MailSendLog对象。
            String msgId = UUID.randomUUID().toString();
            MailSendLog msdLog = new MailSendLog();
            msdLog.setMsgId(msgId);
            msdLog.setCreateTime(new Date());
            msdLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            msdLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            msdLog.setEmpId(emp.getId());
            msdLog.setTryTime(new Date(System.currentTimeMillis() + 1000*60*MailConstants.MSG_TIMEOUT) );
            // 将刚刚配置好的MailSendLog对象注入到数据库中。
            mailSendLogService.insert(msdLog);
            // 在这里做邮件发送的工作。
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
        }

        return i;
    }


    public Integer getMaxWordID() {
        return employeeMapper.getMaxWordID();
    }

    @Transactional()
    public int deleteEmpById(Integer id)  {
        int i = empSalaryMapper.deleteEmpSalaryByEmpId(id);
        int i1 = employeeMapper.deleteByPrimaryKey(id);

        return ((i == i1)&&i==1)?1:0;
    }

    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }


    public int addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }
    // 获取到所有的员工，并且还要带上薪资，用于员工套账管理。
    public RespPageBean getAllEmpByPageWithSalary(Integer page, Integer size) {
        if (page!=null && size != null){
            page = (page - 1)*size;
        }
        List<Employee> list = employeeMapper.getAllEmpByPageWithSalary(page,size);
        RespPageBean res = new RespPageBean();
        res.setData(list);
        res.setTotal(employeeMapper.getTotal(null,null));
        return res;
    }

    public int updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }

    public Employee getEmployeeById(Integer empId) {
        return employeeMapper.getEmployeeById(empId);
    }
}
