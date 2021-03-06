package org.javaboy.vhr.mapper;

import org.javaboy.vhr.bean.EmpSalary;

public interface EmpSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpSalary record);

    int insertSelective(EmpSalary record);

    EmpSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpSalary record);

    int updateByPrimaryKey(EmpSalary record);

    int deleteEmpSalaryByEmpId(Integer id);
}