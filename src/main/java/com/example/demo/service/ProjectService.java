package com.example.demo.service;

import com.example.demo.conf.ProjectSecured;
import com.example.demo.pojo.Project;
import com.example.demo.pojo.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectService {

    private static List<Project> PROJECT_LIST = new ArrayList<>();

    @ProjectSecured(roles = {Role.READ}, projectIdField = "#project.projectId")
    public Project readProject(Project project) {
        return PROJECT_LIST.stream()
                .filter(p -> p.getProjectId().equals(project.getProjectId()))
                .findFirst()
                .orElse(null);
    }

    @ProjectSecured(roles = {Role.READ}, projectIdField = "#id")
    public Project readProject(String id) {
        return PROJECT_LIST.stream()
                .filter(project -> project.getProjectId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @ProjectSecured(roles = {Role.WRITE}, projectIdField = "#project.projectId")
    public Project editProject(Project project) {
        project.setName("New Project" + project.getProjectId());
        return project;
    }

    @ProjectSecured(roles = {Role.WRITE}, projectIdField = "#id")
    public Project editProject(String id) {
        Project project = PROJECT_LIST.stream()
                .filter(p -> p.getProjectId().equals(id))
                .findFirst()
                .orElse(null);
        project.setName("New Project" + id);
        return project;
    }

    public void createProject(String projectId) {
        PROJECT_LIST.add(new Project("Project" + projectId, projectId));
    }
}
