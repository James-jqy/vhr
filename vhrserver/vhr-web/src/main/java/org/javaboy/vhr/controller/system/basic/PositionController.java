package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.bean.Position;
import org.javaboy.vhr.bean.RespBean;
import org.javaboy.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname PositionController
 * @Description TODO
 * @Date 2020/12/18 11:06
 * @Created by Jieqiyue
 */

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

    /**
     * 也可以返回responsebean
     * @return
     */
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if (positionService.addPosition(position) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position){
        if (positionService.updatePositions(position) == 1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if (positionService.deletePositionById(id) == 1){
            return RespBean.ok("删除成功！");
        }

        return RespBean.error("删除失败,该职位下可能还有员工！");
    }

    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer []ids){
        if (positionService.deletePositionByIds(ids) == ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败");
    }

}
