package com.example.demo.permission.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author alin
 */
@Getter
@Setter
public class Department {


    /**
     * children : [{"id":"300581032","title":"测试2/1","spread":"true"},{"id":"299861143","title":"测试2/2","spread":"true"}]
     * id : 300717035
     * title : 测试2
     * spread : true
     */

    private String id;
    private String title;
    private String spread;
    private List<Department> children;

}
