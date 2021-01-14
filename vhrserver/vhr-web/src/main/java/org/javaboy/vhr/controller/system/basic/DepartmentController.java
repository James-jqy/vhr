package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.bean.Department;
import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname DepartmentController
 * @Description TODO
 * @Date 2020/12/21 13:21
 * @Created by Jieqiyue
 */

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep){
        departmentService.addDep(dep);
        if (dep.getResult() == 1){
            return RespBean.ok("添加成功！",dep);
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id ){
        Department department = new Department();
        department.setId(id);
        departmentService.deleteDepById(department);

        if (department.getResult() == -2){
            return RespBean.error("该部门下有子部门，删除失败！");
        }else if (department.getResult() == -1){
            return RespBean.error("该部门下面有员工，删除失败！");
        }else if (department.getResult() == 1){
            return RespBean.ok("删除成功！");
        }else{
            return RespBean.error("删除失败！");
        }
    }
}
