package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.Department;
import org.javaboy.vhr.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Classname DepartmentService
 * @Description TODO
 * @Date 2020/12/21 13:33
 * @Created by Jieqiyue
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;


    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    public void deleteDepById(Department department) {
        departmentMapper.deleteDepById(department);
    }

    public int deleteEmpById(Integer id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
