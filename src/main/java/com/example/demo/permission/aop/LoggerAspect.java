package com.example.demo.permission.aop;

import com.example.demo.permission.annotation.LoggerOperator;
import com.example.demo.permission.repository.NavigationRepository;
import com.example.demo.permission.service.LoggerService;
import com.example.demo.permission.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AOP切点类
 *
 * @author alin
 */

@Aspect
@Component
public class LoggerAspect {
    @Autowired
    private LoggerService loggerService;

    @Autowired
    private NavigationRepository navigationRepository;
    // 定义切点 @Pointcut。是面前注解类的地址。

    @Pointcut("@annotation(com.example.demo.permission.annotation.LoggerOperator)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
//            获取用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            String userRole = navigationRepository.getUserRole(userName);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = IpUtil.getIpAddress(request);
            String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
//            String role = employeeService.get(employeeId).getPosition();
            String desc = getControllerMethodDescription(joinPoint);
            loggerService.insertLogger(ip, userRole, method, "role", desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getControllerMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();// 目标方法名
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(LoggerOperator.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
