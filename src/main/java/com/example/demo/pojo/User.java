package com.example.demo.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class User {

    private final String userName;
    private final String userId;
    private Map<String, Role[]> projectRoleMap;

    public User(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

}
