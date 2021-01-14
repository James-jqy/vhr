package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.bean.Salary;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname EmpSalary
 * @Description TODO
 * @Date 2021/1/3 15:07
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/salary/sob")
public class EmpSalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalarys(){
        return salaryService.getAllSalarys();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if (salaryService.addSalary(salary) == 1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id ){
        if (salaryService.deleteSalaryById(id) == 1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.ok("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if (salaryService.updateSalary(salary) == 1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
