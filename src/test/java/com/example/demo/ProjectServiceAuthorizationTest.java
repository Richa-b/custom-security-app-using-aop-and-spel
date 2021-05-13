package com.example.demo;

import com.example.demo.pojo.Project;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.UndeclaredThrowableException;

@SpringBootTest
public class ProjectServiceAuthorizationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Test
    public void testReadProject() {

        // given: current user is User1
        userService.setCurrentUser("1");

        // when: User1 tries to read Project1
        Project project = projectService.readProject("1");
        // then: operation is successful
        Assertions.assertEquals("Project1", project.getName());

        // when: User1 tries to read Project2
        project = projectService.readProject("2");
        // then: operation is successful
        Assertions.assertEquals("Project2", project.getName());

        // given: current user is User2
        userService.setCurrentUser("2");

        // when: User2 tries to read Project1
        project = projectService.readProject("1");
        // then: operation is successful
        Assertions.assertEquals("Project1", project.getName());

        // when: User2 tries to read Project2
        project = projectService.readProject("2");
        // then: operation is successful
        Assertions.assertEquals("Project2", project.getName());
    }

    @Test
    public void testEditProject() {

        // given: current user is User1
        userService.setCurrentUser("1");

        // when: User1 tries to edit Project1
        Executable executable = () -> projectService.editProject("1");
        // then: exception is thrown
        Throwable e = Assertions.assertThrows(Exception.class, executable);
        e = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
        Assertions.assertEquals("User1 is not allowed to perform this operation on project with id:1", e.getMessage());

        // when: User1 tries to edit Project2
        Project project = projectService.editProject("2");
        // then: operation is successful
        Assertions.assertEquals("New Project2", project.getName());


        // given: current user is User2
        userService.setCurrentUser("2");

        // when: User2 tries to edit Project1
        project = projectService.editProject("1");
        // then: operation is successful
        Assertions.assertEquals("New Project1", project.getName());

        // when: User2 tries to edit Project2
        project = projectService.editProject("2");
        // then: operation is successful
        Assertions.assertEquals("New Project2", project.getName());
    }

    // This test case uses read method which takes project as an argument. This allows testing of parsing
    // of nested field in an object by SpEL
    @Test
    public void testReadProject1() {

        // given: current user is User1
        userService.setCurrentUser("1");

        // when: User1 tries to read Project1
        Project project = projectService.readProject(getProjectFromId("1"));
        // then: operation is successful
        Assertions.assertEquals("Project1", project.getName());

        // when: User1 tries to read Project2
        project = projectService.readProject(getProjectFromId("2"));
        // then: operation is successful
        Assertions.assertEquals("Project2", project.getName());

        // given: current user is User2
        userService.setCurrentUser("2");

        // when: User2 tries to read Project1
        project = projectService.readProject(getProjectFromId("1"));
        // then: operation is successful
        Assertions.assertEquals("Project1", project.getName());

        // when: User2 tries to read Project2
        project = projectService.readProject(getProjectFromId("2"));
        // then: operation is successful
        Assertions.assertEquals("Project2", project.getName());
    }

    // This test case uses edit method which takes project as an argument. This allows testing of parsing
    // of nested field in an object by SpEL
    @Test
    public void testEditProject1() {

        // given: current user is User1
        userService.setCurrentUser("1");

        // when: User1 tries to edit Project1
        Executable executable = () -> projectService.editProject(getProjectFromId("1"));
        // then: exception is thrown
        Throwable e = Assertions.assertThrows(Exception.class, executable);
        e = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
        Assertions.assertEquals("User1 is not allowed to perform this operation on project with id:1", e.getMessage());

        // when: User1 tries to edit Project2
        Project project = projectService.editProject(getProjectFromId("2"));
        // then: operation is successful
        Assertions.assertEquals("New Project2", project.getName());


        // given: current user is User2
        userService.setCurrentUser("2");

        // when: User2 tries to edit Project1
        project = projectService.editProject(getProjectFromId("1"));
        // then: operation is successful
        Assertions.assertEquals("New Project1", project.getName());

        // when: User2 tries to edit Project2
        project = projectService.editProject(getProjectFromId("2"));
        // then: operation is successful
        Assertions.assertEquals("New Project2", project.getName());
    }

    private Project getProjectFromId(String projectId) {
        return new Project("Project"+projectId, projectId);
    }

    @Configuration
    @ComponentScan(basePackages = "com.example.demo")
    static class SpringConfig {
    }
}
