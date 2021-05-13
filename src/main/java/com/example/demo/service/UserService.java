package com.example.demo.service;

import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {

    public static final List<User> USER_LIST = new ArrayList<>();

    public static User CURRENT_USER;

    public User getCurrentUser() {
        return CURRENT_USER;
    }

    /**
     * dummy method to set current user
     */
    public void setCurrentUser(String userId) {
        CURRENT_USER = USER_LIST.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<Role> getRoles(String projectId) {
        User user = getCurrentUser();
        Map<String, Role[]> projectRoleMap = user.getProjectRoleMap();
        return Arrays.asList(projectRoleMap.get(projectId));
    }

    public boolean isUserAuthorized(String projectId, List<Role> allowedRoles) {
        List<Role> userRoles = getRoles(projectId);
        return !Collections.disjoint(allowedRoles, userRoles);
    }

    public void createUser(User user) {
        USER_LIST.add(user);
    }

}
