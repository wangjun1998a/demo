package com.example.demo.permission.service.impl;

import com.example.demo.permission.bean.Navigation;
import com.example.demo.permission.repository.NavigationMapper;
import com.example.demo.permission.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Map<String, Object> data = new HashMap<>();
        //按照pid获取到根目录进行存储对应的子目录
        List<Navigation> navId = navigationMapper.getNavigationByPid();
        for (Navigation nav : navId) {
            List<Navigation> list = navigationMapper.getNavigationListByPid(nav.getId());
            nav.setChildrens(list);
        }
        data.put("menu", navId);
        return data;

    }
}
