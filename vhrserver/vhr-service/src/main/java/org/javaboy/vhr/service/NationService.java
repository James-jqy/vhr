package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.Nation;
import org.javaboy.vhr.mapper.NationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname NationService
 * @Description TODO
 * @Date 2020/12/24 13:53
 * @Created by Jieqiyue
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
