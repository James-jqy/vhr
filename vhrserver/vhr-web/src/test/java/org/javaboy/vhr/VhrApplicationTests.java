package org.javaboy.vhr;


import org.javaboy.vhr.bean.Employee;
import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.bean.Role;
import org.javaboy.vhr.controller.system.basic.PermissController;
import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.HrService;
import org.javaboy.vhr.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class VhrApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeService employeeService;
    @Test
    public void test(){
        List<Employee> empByPageUseHelper = employeeService.getEmpByPageUseHelper(11, 10);
        empByPageUseHelper.forEach(employee -> System.out.println(employee) );
    }

}
