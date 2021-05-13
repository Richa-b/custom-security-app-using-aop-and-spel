package com.example.demo.service;

import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.example.demo.pojo.Role.READ;
import static com.example.demo.pojo.Role.WRITE;

/**
 * This class is to create dummy projects and users at application startup
 */
@Component
public class BootstrapService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDummyProject(2);
        createDummyUser();
    }

    private void createDummyUser() {
        User user = new User("User1", "1");
        user.setProjectRoleMap(ImmutableMap.of("1", new Role[]{READ}, "2", new Role[]{READ, WRITE}));
        userService.createUser(user);

        user = new User("User2", "2");
        user.setProjectRoleMap(ImmutableMap.of("1", new Role[]{READ, WRITE}, "2", new Role[]{READ, WRITE}));
        userService.createUser(user);
    }

    private void createDummyProject(int count) {
        for (int i = 1; i <= count; i++) {
            projectService.createProject(String.valueOf(i));
        }
    }
}
