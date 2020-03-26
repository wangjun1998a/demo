package com.example.demo.permission.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 实体类
 *
 * @author alin
 */
@Getter
@Setter
public class UserInfo {

    private long uid;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        admin,
        normal
    }

    private String picture;


}
