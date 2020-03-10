package com.example.demo.permission.repository;

import com.example.demo.permission.bean.MenuTable;
import com.example.demo.permission.bean.Navigation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author alin
 */
@Mapper
public interface NavigationMapper {

    /**
     * 获取数据表
     *
     * @return 菜单表
     */
    @Select("select * from `spring-security`.menu m " +
            "where m.del_flag = '0';")
    List<MenuTable> getMenuTable();

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
            "and r.role_key in (#{userRole})" +
            "group by m.id;")
    List<Navigation> getNavigationListByPid(@Param("pid") Integer pid, @Param("userRole") String userRole);

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
            "and r.role_key in (#{userRole})" +
            "group by m.id;")
    List<Navigation> getNavigationByPid(@Param("userRole") String userRole);

    /**
     * 获取用户角色
     *
     * @param userName userName
     * @return userRole
     */
    @Select("select r.role_key\n" +
            "from `spring-security`.user_info ui\n" +
            "         left join `spring-security`.user_role ur on ur.user_id = ui.uid\n" +
            "         left join `spring-security`.role r on r.role_id = ur.role_id\n" +
            "where ui.username = #{userName};")
    String getUserRole(@Param("userName") String userName);

    /**
     * 数据插入方法
     *
     * @param name   name
     * @param pid    pid
     * @param descpt descpt
     * @param url    url
     */
    @Insert(value = "insert into `spring-security`.menu (name, pid, descpt, url, create_time, del_flag)\n" +
            "values (#{name}, #{pid}, #{descpt}, #{url}, now(), '0');")
    void insertData(String name, Integer pid, String descpt, String url);

    /**
     * 创建菜单和权限的关联关系
     * 默认绑定admin权限
     *
     * @param name   name
     * @param pid    pid
     * @param descpt descpt
     * @param url    url
     */
    @Insert("insert into `spring-security`.menu_role (menu_id, role_id)\n" +
            "values ((select menu.id\n" +
            "         from `spring-security`.menu\n" +
            "         where name = #{name}\n" +
            "           " +
            "and pid = #{pid}\n" +
            "           and descpt = #{descpt}\n" +
            "           and url = #{url}" +
            "), 1);")
    void menuCorrelationWithRole(String name, Integer pid, String descpt, String url);
}
