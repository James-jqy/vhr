package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.Politicsstatus;
import org.javaboy.vhr.mapper.PoliticsstatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname PoliticsstatusService
 * @Description TODO
 * @Date 2020/12/24 13:53
 * @Created by Jieqiyue
 */
@Service
public class PoliticsstatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
