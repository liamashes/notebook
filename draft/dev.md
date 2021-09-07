# 
## 接口设计
### 1、接口一个方法是否执行成功，可以通过抛出异常的方式进行反馈
    例：org.springframework.security.access.AccessDecisionManager
        void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        			throws AccessDeniedException, InsufficientAuthenticationException;
### 2、接口设计常用方法
    support：在处理前判断当前接口是否满足需要
