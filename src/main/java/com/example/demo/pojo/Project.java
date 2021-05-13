package com.example.demo.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {

    private String name;
    private final String projectId;

    public Project(String name, String projectId) {
        this.name = name;
        this.projectId = projectId;
    }
}
