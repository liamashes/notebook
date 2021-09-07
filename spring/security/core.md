# core
## 2-1、模块关系
![core](./images/core/core.png)
> 包 | 描述
> --- | ---
> access |核心访问控制相关代码，包括安全元数据相关类、拦截代码、访问控制注释、EL 支持和中央 AccessDecisionManager 接口的基于投票者的实现。
> access.annotation |支持 JSR-250 和 Spring Security @Secured 注释。
> access.event |授权事件和侦听器类。
> access.expression |表达式处理代码支持在 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释中使用基于 Spring-EL 的表达式。
> access.expression.method |基于表达式的方法安全的实现。
> access.hierarchicalroles |角色层次结构实现。
> access.intercept |抽象级安全拦截类，负责为安全对象强制执行配置的安全约束。
> access.intercept.aopalliance |为 AOP Alliance MethodInvocation 强制实施安全性，例如通过 Spring AOP。
> access.intercept.aspectj |强制 AspectJ JointPoints 的安全性，将安全对象回调委托给调用方面。
> access.method |提供 SecurityMetadataSource 实现以通过不同的 AOP 库保护 Java 方法调用。
> access.prepost |包含用于处理 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释的基础结构类。
> access.vote |实施基于投票的授权决策方法。
> authentication |与用户身份验证相关的核心类和接口，在整个 Spring Security 中使用。
> authentication.dao |一个依赖于数据访问对象的 AuthenticationProvider。
> authentication.event |可以发布到 Spring 应用程序上下文的身份验证成功和失败事件。
> authentication.jaas | JAAS 的身份验证提供程序。
> authentication.jaas.event | JAAS 身份验证事件可以由 JAAS 身份验证提供程序发布到 Spring 应用程序上下文。
> authentication.jaas.memory |内存中的 JAAS 实现。
> authentication.rcp |允许远程客户端进行身份验证并获取填充的身份验证对象。
> authorization |
> authorization.method |
> concurrent |
> context |
> converter |
> core |与用户认证和授权相关的核心类和接口，以及安全上下文的维护。
> core.annotation |
> core.authority | GrantedAuthority 接口的默认实现。
> core.authority.mapping |将属性列表（例如角色或 LDAP 组）映射到 GrantedAuthority 列表的策略。
> core.context |与在请求期间（例如 HTTP 或 RMI 调用）建立安全上下文相关的类。
> core.parameters |
> core.session |由 org.springframework.security.core.session.SessionInformation SessionInformation 类提供的会话抽象。
> core.token |用于构建安全随机令牌的服务。
> core.userdetails |用于实现用户数据 DAO 的标准接口。
> core.userdetails.cache | UserCache 的实现。
> core.userdetails.jdbc |公开基于 JDBC 的身份验证存储库，实现 org.springframework.security.core.userdetails.UserDetailsS​​ervice UserDetailsS​​ervice。
> core.userdetails.memory |公开内存中的身份验证存储库。
> jackson2 |混合类以添加 Jackson 序列化支持。
> provisioning |包含简单的用户和权限组帐户供应接口以及基于 JDBC 的实现。
> scheduling |
> task |
> util |在整个 Spring Security 框架中使用的通用实用程序类。
## 2-2、访问（access）
### 2-2-1、access
### 2-2-2、access.annotation
### 2-2-3、access.event
### 2-2-4、access.expression
### 2-2-5、access.expression.method
### 2-2-6、access.hierarchicalroles
### 2-2-7、access.intercept
### 2-2-8、access.intercept.aopalliance
### 2-2-9、access.intercept.aspectj
### 2-2-10、access.method
### 2-2-11、access.prepost
### 2-2-12、access.vote
## 2-3、认证（authentication）
### 2-3-1、authentication
### 2-3-2、authentication.dao
### 2-3-3、authentication.event
### 2-3-4、authentication.jaas
### 2-3-5、authentication.jaas.event
### 2-3-6、authentication.jaas.memory
### 2-3-7、authentication.rcp
### 2-3-8、authorization
### 2-3-9、authorization.method
## 2-4、并发（concurrent）
### 2-4-1、concurrent
## 2-5、会话（context）
### 2-5-1、context
## 2-6、转化器（converter）
### 2-6-1、converter
## 2-7、内核（core）
### 2-7-1、core
### 2-7-2、core.annotation
### 2-7-3、core.authority
### 2-7-4、core.authority.mapping
### 2-7-5、core.context
### 2-7-6、core.parameters
### 2-7-7、core.session
### 2-7-8、core.token
### 2-7-9、core.userdetails
### 2-7-10、core.userdetails.cache
### 2-7-11、core.userdetails.jdbc
### 2-7-12、core.userdetails.memory
### 2-7-13、jackson2
### 2-7-14、provisioning
### 2-7-15、scheduling
### 2-7-16、task
### 2-7-17、util
