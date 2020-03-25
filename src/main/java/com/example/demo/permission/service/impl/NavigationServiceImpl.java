package com.example.demo.permission.service.impl;

import com.example.demo.permission.bean.MenuTable;
import com.example.demo.permission.bean.Navigation;
import com.example.demo.permission.repository.NavigationMapper;
import com.example.demo.permission.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alin
 */
@Service
public class NavigationServiceImpl implements NavigationService {
    @Autowired
    private NavigationMapper navigationMapper;

    @Override
    public Map<String, Object> findMenu() {
//        SqlSession sqlSession = null;
//            NavigationMapper mapper = sqlSession.getMapper(navigationMapper.getClass());
        // 获取用户角色
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = navigationMapper.getUserRole(userName);
        Map<String, Object> data = new HashMap<>();
        //按照pid获取到根目录进行存储对应的子目录
        List<Navigation> navId = navigationMapper.getNavigationByPid(userRole);
        for (Navigation nav : navId) {
            List<Navigation> list = navigationMapper.getNavigationListByPid(nav.getId(), userRole);
            nav.setChildrens(list);
        }
        data.put("menu", navId);
        return data;
    }

    @Override
    public Map<String, Object> findMenuTable() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = navigationMapper.getUserRole(userName);
        Map<String, Object> data = new HashMap<>();
        //按照pid获取到根目录进行存储对应的子目录
        List<MenuTable> menuTable = navigationMapper.getMenuTable();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", menuTable.size());
        data.put("data", menuTable);
        return data;
    }

    /**
     * 插入一个新的界面
     * 1。创建一个新的菜单
     * 2。创建菜单和权限的关联关系(目前只使用admin)
     */
    @Override
    public String insertMenu(String name, Integer pid, String descpt, String url) {
        navigationMapper.insertMenu(name, pid, descpt, url);
        navigationMapper.menuCorrelationWithRole(name, pid, descpt, url);
        return "200";
    }

    /**
     * 删除菜单 实际上就是吧del_flag变成1
     *
     * @param id 菜单ID
     * @return 200
     */
    @Override
    public String deleteMenu(String id) {
        navigationMapper.deleteMenu(id);
        return "200";
    }
}
