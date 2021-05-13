package com.example.demo.conf;

import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class SecureProjectAspect {

    @Autowired
    UserService userService;

    @Around("methodsAnnotatedWithProjectSecuredAnnotation()")
    public Object processMethodsAnnotatedWithProjectSecuredAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ProjectSecured projectSecuredAnnotation = method.getAnnotation(ProjectSecured.class);
        String projectId = (String) CustomSpringExpressionLanguageParser.
                getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), projectSecuredAnnotation.projectIdField());
        Role[] roles = projectSecuredAnnotation.roles();
        // If user is authorized, then proceed else throw an exception
        boolean isUserAuthorized = userService.isUserAuthorized(projectId, Arrays.asList(roles));
        if (isUserAuthorized) {
            return joinPoint.proceed();
        } else {
            User currentUser = userService.getCurrentUser();
            throw new Exception(currentUser.getUserName() + " is not allowed to perform this operation on project with id:" + projectId);
        }
    }

    @Pointcut("@annotation(com.example.demo.conf.ProjectSecured)")
    private void methodsAnnotatedWithProjectSecuredAnnotation() {

    }
}
