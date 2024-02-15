package io.security.springsecuritymaster.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@EnableMethodSecurity(prePostEnabled = false)
@Configuration
public class MethodSecurityConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor protectServicePointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* io.security.springsecuritymaster.DataService.getUser(..))");
        AuthorityAuthorizationManager<MethodInvocation> manager = hasRole("USER");
        return new AuthorizationManagerBeforeMethodInterceptor(pointcut, manager);
    }

    /*@Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor protectServicePointcut() {
        return new AuthorizationManagerBeforeMethodInterceptor(createCompositePointcut(), hasRole("USER"));
    }

    public Pointcut createCompositePointcut() {
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
        pointcut1.setExpression("execution(* io.security.MyService.user(..))");

        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("execution(* io.security.MyService.display(..))");

        // 두 포인트컷을 조합
        ComposablePointcut compositePointcut = new ComposablePointcut(pointcut1);
        compositePointcut.union(pointcut2);

        return compositePointcut;
    }*/
}
