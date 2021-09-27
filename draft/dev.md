# 
## 接口设计
### 1、接口一个方法是否执行成功，可以通过抛出异常的方式进行反馈
    例：org.springframework.security.access.AccessDecisionManager
        void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        			throws AccessDeniedException, InsufficientAuthenticationException;
### 2、接口设计常用方法
    support：在处理前判断当前接口是否满足需要
### 3、缓存在不主动设置的情况下，考虑无缓存，可以定义一个空缓存来初始化
    例：org.springframework.security.core.userdetails.cache.NullUserCache
### 4、实现某接口的目的，有时是为了包装相应的功能，提供便捷的方式，或【复合优于继承】
    例1：javax.servlet.GenericServlet
    例2：参考 effective java 第18点

## 语法
### 1、org.springframework.security.access.intercept.aspectj.aspect.AnnotationSecurityAspect


## 关键词
### 1、JAAS
    Java Authentication and Authorization Service（JAAS，Java验证和授权API）提供了灵活和可伸缩的机制来保证客户端或服务器端的Java
    程序。Java早期的安全框架强调的是通过验证代码的来源和作者，保护用户避免受到下载下来的代码的攻击。JAAS强调的是通过验证谁在运行代码以及
    他/她的权限来保护系统免受用户的攻击。它让你能够将一些标准的安全机制，例如Solaris NIS（网络信息服务）、Windows NT、LDAP（轻量目录存
    取协议），Kerberos等通过一种通用的，可配置的方式集成到系统当中去。
