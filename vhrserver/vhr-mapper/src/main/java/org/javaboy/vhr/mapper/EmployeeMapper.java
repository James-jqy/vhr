package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.bean.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("page") Integer page,
                                     @Param("size") Integer size,
                                     @Param("emp") Employee emp,
                                     @Param("beginDateScope") Date[] beginDateScope);

    long getTotal( @Param("emp") Employee emp,
                   @Param("beginDateScope") Date[] beginDateScope);

    Integer getMaxWordID();

    Integer addEmps(@Param("list") List<Employee> list);

    // 通过员工id，获取到员工信息。
    Employee getEmployeeById(Integer id);


    List<Employee> getAllEmpByPageWithSalary(@Param("page") Integer page,@Param("size") Integer size);

    int updateEmployeeSalaryById(Integer eid, Integer sid);

    // 这个方法就是去玩了一下pageHelper，实际项目中没有用这个插件
    List<Employee> getEmpByPageUseHelper(@Param("page") Integer page,@Param("size") Integer size);
}