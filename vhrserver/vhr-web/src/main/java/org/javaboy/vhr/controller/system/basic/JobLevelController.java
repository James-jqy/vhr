package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.bean.JobLevel;
import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname JobLevelController
 * @Description TODO
 * @Date 2020/12/19 15:21
 * @Created by Jieqiyue
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return  jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if (jobLevelService.addJobLevel(jobLevel) == 1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody JobLevel jobLevel){
        if (jobLevelService.updateJobLevelById(jobLevel) == 1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    // 前端 http://localhost:8080/system/basic/joblevel/28 这个28会被自动封装到id上
    public RespBean deleteJobLevelById(@PathVariable Integer id ){
        if (jobLevelService.deleteJobLevelById(id) == 1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    //此处前端传来的请求是 http://localhost:8080/system/basic/joblevel/?ids=26&ids=27&
    //这样的参数也会被自动封装为一个数组
    public RespBean deleteJobLevelByIds(Integer []ids){
        if (jobLevelService.deleteJobLevelByIds(ids) == ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败");
    }
}
