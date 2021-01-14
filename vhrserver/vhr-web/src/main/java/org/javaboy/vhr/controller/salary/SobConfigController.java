package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.bean.RespPageBean;
import org.javaboy.vhr.bean.Salary;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname SobConfigController
 * @Description TODO
 * @Date 2021/1/3 19:25
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getAllEmpByPageWithSalary(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getAllEmpByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalarys(){
        return salaryService.getAllSalarys();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid, Integer sid){
        int i = employeeService.updateEmployeeSalaryById(eid, sid);
        if(i == 1 || i == 2){
            return RespBean.ok("更新成功！");
        }
        return RespBean.ok("更新失败！");
    }
}
