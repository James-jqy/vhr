package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.bean.Salary;
import org.javaboy.vhr.mapper.EmpSalaryMapper;
import org.javaboy.vhr.mapper.SalaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Classname SalaryService
 * @Description TODO
 * @Date 2021/1/3 15:08
 * @Created by Jieqiyue
 */
@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;
    @Autowired
    EmpSalaryMapper empSalaryMapper;

    public List<Salary> getAllSalarys() {
        return salaryMapper.getAllSalarys();
    }

    public int addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public int deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public int updateSalary(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }

    // 通过员工的id，去删除empsalary表中的记录。
    public int deleteEmpSalaryByEmpId(Integer id){
        return empSalaryMapper.deleteEmpSalaryByEmpId(id);
    }
}
