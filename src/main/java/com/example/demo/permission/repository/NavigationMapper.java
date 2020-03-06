package com.example.demo.permission.repository;

import com.example.demo.permission.bean.Navigation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author alin
 */
@Mapper
public interface NavigationMapper {
    /**
     * 根据父PID获取子级数据
     *
     * @param pid 父pid
     * @return Navigation
     */
    @Select("select m.id                     as id,\n" +
            "       m.name                   as name,\n" +
            "       m.pid                    as pid,\n" +
            "       m.descpt                 as descpt,\n" +
            "       m.url                    as url,\n" +
            "       m.create_time            as create_time,\n" +
            "       m.update_time            as update_time,\n" +
            "       m.del_flag               as del_flag,\n" +
            "       group_concat(r.role_key) as roles\n" +
            "from `spring-security`.menu m\n" +
            "         left join `spring-security`.menu_role mr on m.id = mr.menu_id\n" +
            "         left join `spring-security`.role r on r.role_id = mr.role_id\n" +
            "where pid = #{pid}\n" +
            "group by m.id;")
    List<Navigation> getNavigationListByPid(Integer pid);

    /**
     * 获取pid为0的头标识
     *
     * @return Navigation
     */
    @Select("select m.id                     as id,\n" +
            "       m.name                   as name,\n" +
            "       m.pid                    as pid,\n" +
            "       m.descpt                 as descpt,\n" +
            "       m.url                    as url,\n" +
            "       m.create_time            as create_time,\n" +
            "       m.update_time            as update_time,\n" +
            "       m.del_flag               as del_flag,\n" +
            "       group_concat(r.role_key) as roles\n" +
            "from `spring-security`.menu m\n" +
            "         left join `spring-security`.menu_role mr on m.id = mr.menu_id\n" +
            "         left join `spring-security`.role r on r.role_id = mr.role_id\n" +
            "where pid = 0\n" +
            "group by m.id;")
    List<Navigation> getNavigationByPid();

}
