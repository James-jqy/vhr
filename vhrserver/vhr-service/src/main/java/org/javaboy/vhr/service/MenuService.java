package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.Hr;
import org.javaboy.vhr.bean.Menu;
import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname MenuService
 * @Description TODO
 * @Date 2020/12/12 18:50
 * @Created by Jieqiyue
 */
@Service
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId( ((Hr) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId() );
    }

//    @Cacheable  最好将这个方法加上缓存，因为每次请求都要去判断权限是否满足，都需要调用这个方法。
    @Cacheable
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer id) {
        return menuMapper.getMidsByRid(id);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        if (mids.length == 1 && mids[0] == -1){
            menuRoleMapper.deleteByRid(rid);
            return true;
        }
        menuRoleMapper.deleteByRid(rid);
        return (menuRoleMapper.insertRecord(rid,mids)) == mids.length;
    }
}
