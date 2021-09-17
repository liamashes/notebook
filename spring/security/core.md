# core
## 2-1、模块关系
![core](./images/core/core.png)
> 包 | 描述
> --- | ---
> [access](#2-2-1access) |核心访问控制相关代码，包括安全元数据相关类、拦截代码、访问控制注释、EL 支持和中央 AccessDecisionManager 接口的基于投票者的实现。
> [access.annotation](#2-2-2accessannotation) |支持 JSR-250 和 Spring Security @Secured 注释。
> [access.event](#2-2-3accessevent) |授权事件和侦听器类。
> [access.expression](#2-2-4accessexpression) |表达式处理代码支持在 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释中使用基于 Spring-EL 的表达式。
> [access.expression.method](#2-2-5accessexpressionmethod) |基于表达式的方法安全的实现。
> [access.hierarchicalroles](#2-2-6accesshierarchicalroles) |角色层次结构实现。
> [access.intercept](#2-2-7accessintercept) |抽象级安全拦截类，负责为安全对象强制执行配置的安全约束。
> [access.intercept.aopalliance](#2-2-8accessinterceptaopalliance) |为 AOP Alliance MethodInvocation 强制实施安全性，例如通过 Spring AOP。
> [access.intercept.aspectj](#2-2-9accessinterceptaspectj) |强制 AspectJ JointPoints 的安全性，将安全对象回调委托给调用方面。
> [access.method](#2-2-10accessmethod) |提供 SecurityMetadataSource 实现以通过不同的 AOP 库保护 Java 方法调用。
> [access.prepost](#2-2-11accessprepost) |包含用于处理 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释的基础结构类。
> [access.vote](#2-2-12accessvote) |实施基于投票的授权决策方法。
> [authentication](#2-3-1authentication) |与用户身份验证相关的核心类和接口，在整个 Spring Security 中使用。
> [authentication.dao](#2-3-2authenticationdao) |一个依赖于数据访问对象的 AuthenticationProvider。
> [authentication.event](#2-3-3authenticationevent) |可以发布到 Spring 应用程序上下文的身份验证成功和失败事件。
> [authentication.jaas](#2-3-4authenticationjaas) | JAAS 的身份验证提供程序。
> [authentication.jaas.event](#2-3-5authenticationjaasevent) | JAAS 身份验证事件可以由 JAAS 身份验证提供程序发布到 Spring 应用程序上下文。
> [authentication.jaas.memory](#2-3-6authenticationjaasmemory) |内存中的 JAAS 实现。
> [authentication.rcp](#2-3-7authenticationrcp) |允许远程客户端进行身份验证并获取填充的身份验证对象。
> [authorization](#2-3-8authorization) |
> [authorization.method](#2-3-9authorizationmethod) |
> [concurrent](#2-4-1concurrent) |
> [context](#2-5-1context) |
> [converter](#2-6-1converter) |
> [core](#2-7-1core) |与用户认证和授权相关的核心类和接口，以及安全上下文的维护。
> [core.annotation](#2-7-2coreannotation) |
> [core.authority](#2-7-3coreauthority) | GrantedAuthority 接口的默认实现。
> [core.authority.mapping](#2-7-4coreauthoritymapping) |将属性列表（例如角色或 LDAP 组）映射到 GrantedAuthority 列表的策略。
> [core.context](#2-7-5corecontext) |与在请求期间（例如 HTTP 或 RMI 调用）建立安全上下文相关的类。
> [core.parameters](#2-7-6coreparameters) |
> [core.session](#2-7-7coresession) |由 org.springframework.security.core.session.SessionInformation SessionInformation 类提供的会话抽象。
> [core.token](#2-7-8coretoken) |用于构建安全随机令牌的服务。
> [core.userdetails](#2-7-9coreuserdetails) |用于实现用户数据 DAO 的标准接口。
> [core.userdetails.cache](#2-7-10coreuserdetailscache) | UserCache 的实现。
> [core.userdetails.jdbc](#2-7-11coreuserdetailsjdbc) |公开基于 JDBC 的身份验证存储库，实现 org.springframework.security.core.userdetails.UserDetailsS​​ervice UserDetailsS​​ervice。
> [core.userdetails.memory](#2-7-12coreuserdetailsmemory) |公开内存中的身份验证存储库。
> [jackson2](#2-8-1jackson2) |混合类以添加 Jackson 序列化支持。
> [provisioning](#2-9-1provisioning) |包含简单的用户和权限组帐户供应接口以及基于 JDBC 的实现。
> [scheduling](#2-10-1scheduling) |
> [task](#2-11-1task) |
> [util](#2-12-1util) |在整个 Spring Security 框架中使用的通用实用程序类。
## 2-2、访问（access）
![access-model](./images/core/security-core-access-model.png)

### 2-2-1、access
    核心访问控制相关代码，包括安全元数据相关类、拦截代码、访问控制注释、EL 支持和中央 AccessDecisionManager 接口的基于投票者的实现。

![access](images/core/security-core-access.png)

#### 2-2-1-1、AccessDecisionManager
    做出最终的访问控制（授权）决定。

##### decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) void
    为传递的参数解析访问控制决策。
    通过抛出异常的方式判断该方法是否验证通过

> * 参数：
>   * 身份验证 - 调用方法的调用者（非空）
>   * object - 被调用的安全对象
>   * configAttributes - 与被调用的安全对象关联的配置属性
> * 抛出：
>   * AccessDeniedException - 如果访问被拒绝，因为身份验证没有所需的权限或 ACL 特权
>   * InsufficientAuthenticationException - 如果访问被拒绝，因为身份验证没有提供足够的信任级别

##### supports(java.lang.Class<?> clazz) boolean
    指示此 AccessDecisionManager 是否能够处理通过传递的 ConfigAttribute 呈现的授权请求。

    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AfterInvocationManager 可以使用
    的每个配置属性。

> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 AccessDecisionManager 可以支持传递的配置属性，则为 true
##### supports(ConfigAttribute attribute) boolean
    指示 AccessDecisionManager 实现是否能够为指示的安全对象类型提供访问控制决策。

> * 参数：
>   * clazz - 被查询的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true

#### 2-2-1-2、AccessDecisionVoter
    表示一个类负责对授权决定进行投票。
    投票的协调（即轮询 AccessDecisionVoters，统计他们的响应，并做出最终的授权决定）由 AccessDecisionManager 执行。
    
> * 域：
>   * ACCESS_ABSTAIN
>   * ACCESS_DENIED
>   * ACCESS_GRANTED

##### supports(Class<?> clazz) boolean
    指示此 AccessDecisionVoter 是否能够对传递的 ConfigAttribute 进行投票。
    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AfterInvocationManager 可以
    使用的每个配置属性。

> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 AccessDecisionVoter 可以支持传递的配置属性，则为 true

##### supports(ConfigAttribute attribute) boolean
    指示 AccessDecisionVoter 实现是否能够为指示的安全对象类型提供访问控制投票。

> * 参数：
>   * clazz - 被查询的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true
##### vote(Authentication authentication, S object, Collection<ConfigAttribute> attributes) int
    指示是否授予访问权限。
    决定必须是肯定 (ACCESS_GRANTED)、否定 (ACCESS_DENIED) 或 AccessDecisionVoter 可以弃权 (ACCESS_ABSTAIN) 投票。在任何情况下，
    实现类都不应返回任何其他值。如果需要对结果进行加权，则应改为在自定义 AccessDecisionManager 中处理。

    除非由于传递的方法调用或配置属性参数，AccessDecisionVoter 专门用于对访问控制决策进行投票，否则它必须返回 ACCESS_ABSTAIN。这可以防
    止协调 AccessDecisionManager 计算那些对访问控制决策没有合法利益的 AccessDecisionVoter 的选票。

    虽然安全对象（例如 MethodInvocation）作为参数传递以最大限度地提高访问控制决策的灵活性，但实现类不应修改它或导致所表示的调用发生（例
    如，通过调用 MethodInvocation.proceed()） .

> * 参数：
>   * 身份验证 - 进行调用的调用者
>   * object - 被调用的安全对象
>   * 属性 - 与受保护对象关联的配置属性
> * 返回：
>   * ACCESS_GRANTED、ACCESS_ABSTAIN 或 ACCESS_DENIED
#### 2-2-1-3、AfterInvocationProvider
    表示一个类负责参与 AfterInvocationProviderManager 决策。
    
##### decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes, Object returnedObject)  Object

> * 抛出:
>   * AccessDeniedException

##### supports(ConfigAttribute attribute) boolean
    指示此 AfterInvocationProvider 是否能够参与涉及传递的 ConfigAttribute 的决策。
    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AccessDecisionManager 可以
    使用的每个配置属性。

> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 AfterInvocationProvider 可以支持传递的配置属性，则为 true

##### supports(java.lang.Class<?> clazz) boolean
    指示 AfterInvocationProvider 是否能够为指示的安全对象类型提供“调用后”处理。

> * 参数：
>   * clazz - 被查询的安全对象的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true

#### 2-2-1-4、ConfigAttribute
    存储与安全系统相关的配置属性。
    设置 AbstractSecurityInterceptor 时，会为安全对象模式定义配置属性列表。 这些配置属性对于 RunAsManager、AccessDecisionManager 
    或 AccessDecisionManager 委托具有特殊意义。
    在运行时与同一安全对象目标的其他 ConfigAttributes 一起存储。

##### getAttribute() String
    如果 ConfigAttribute 可以表示为字符串，并且该字符串的精度足以被 RunAsManager、AccessDecisionManager 或 AccessDecisionManager 
    委托作为配置参数所依赖，则此方法应返回这样的字符串。
    如果 ConfigAttribute 不能以足够的精度表示为字符串，则应返回 null。 返回 null 将需要任何依赖类专门支持 ConfigAttribute 实现，因此
    除非实际需要，否则应避免返回 null。

> * 返回：
> * 配置属性的表示（如果配置属性不能表示为具有足够精度的字符串，则为 null）。

#### 2-2-1-5、PermissionCacheOptimizer
    允许在对表达式使用前置或后置过滤时预先缓存权限
    
##### cachePermissionsFor(Authentication a, Collection<?> objects) void
    优化权限缓存以对提供的对象集合进行预期操作。 通常这需要批量加载集合中对象的权限。

> * 参数：
>   * a - 应为其获取权限的用户。
>   * 对象 - 应为其检索权限的域对象的（非空）集合。

#### 2-2-1-6、PermissionEvaluator
    表达式评估中使用的策略，用于确定用户是否具有给定域对象的一个或多个权限。
    
##### hasPermission(Authentication authentication, Object targetDomainObject, Object permission)  boolean
> * 参数：
>   * 身份验证 - 代表有问题的用户。 不应为空。
>   * targetDomainObject - 应检查其权限的域对象。 可能为 null，在这种情况下，实现应返回 false，因为可以在表达式中明确检查 null 条件。
>   * 权限 - 表达系统提供的权限对象的表示。 不为空。
> * 返回：
>   * 如果授予权限，则为 true，否则为 false

##### hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) boolean
    评估权限的替代方法，其中只有目标对象的标识符可用，而不是目标实例本身。

> * 参数：
>   * 身份验证 - 代表有问题的用户。 不应为空。
>   * targetId - 对象实例的标识符（通常是 Long）
>   * targetType - 表示目标类型的字符串（通常是 Java 类名）。 不为空。
>   * 权限 - 表达系统提供的权限对象的表示。 不为空。
> * 返回：
>   * 如果授予权限，则为 true，否则为 false

#### 2-2-1-7、SecurityMetadataSource
    由存储并可以识别适用于给定安全对象调用的 ConfigAttributes 的类实现。

##### getAllConfigAttributes() Collection<ConfigAttribute>
    如果可用，则返回实现类定义的所有 ConfigAttributes。
    AbstractSecurityInterceptor 使用它来执行针对它配置的每个 ConfigAttribute 的启动时间验证。

> * 返回：
>   * 如果不支持，则为 ConfigAttributes 或 null

##### getAttributes(Object object) Collection<ConfigAttribute>
    访问适用于给定安全对象的 ConfigAttributes。

> * 参数：
>   * object - 被保护的对象
> * 返回：
>   * 适用于传入的安全对象的属性。 如果没有适用的属性，则应返回一个空集合。
> * 抛出：
>   * java.lang.IllegalArgumentException - 如果传递的对象不是 SecurityMetadataSource 实现支持的类型

##### supports(java.lang.Class<?> clazz) boolean
    指示 SecurityMetadataSource 实现是否能够为指示的安全对象类型提供 ConfigAttributes。

> * 参数：
>   * clazz - 被查询的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true

### 2-2-2、access.annotation
支持 JSR-250 和 Spring Security @Secured 注释。
![model](./images/core/access-annotation-model.png)

#### 2-2-2-1、AnnotationMetadataExtractor<A extends Annotation>
    处理自定义安全注释以提取相关 ConfigAttributes 以保护方法的策略。
    由 SecuredAnnotationSecurityMetadataSource 使用。

##### extractAttributes(A securityAnnotation) Collection<? extends ConfigAttribute>

#### 2-2-2-2、Secured
    用于描述服务层安全属性的 Java 5 注释。
    Secured 注释用于定义业务方法的安全配置属性列表。 此注释可用作 XML 配置的 Java 5 替代方案。

    例如：
     @Secured({ "ROLE_USER" })
     public void create(Contact contact);
    
     @Secured({ "ROLE_USER", "ROLE_ADMIN" })
     public void update(Contact contact);
    
     @Secured({ "ROLE_ADMIN" })
     public void delete(Contact contact);

##### value String[]
    返回安全配置属性列表（例如 ROLE_USER、ROLE_ADMIN）。

> * 返回：
>   * String[] 安全方法属性
### 2-2-3、access.event
    授权事件和侦听器类。
![model](./images/core/access-event-model.png)

#### 2-2-3-1、AbstractAuthorizationEvent
    所有安全拦截相关事件的抽象超类。

##### AbstractAuthorizationEvent(Object secureObject)  AbstractAuthorizationEvent 
    构造事件，传入被拦截的安全对象。

> * 参数：
>   * secureObject - 安全对象
### 2-2-4、access.expression
    表达式处理代码支持在 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释中使用基于 Spring-EL 的表达式。

![model](./images/core/access-expression-model.png)

#### 2-2-4-1、SecurityExpressionHandler
    Facade 将 Spring Security 对评估安全表达式的要求与底层表达式对象的实现隔离开来

##### createEvaluationContext(Authentication authentication, T invocation) EvaluationContext
    提供评估上下文，在其中评估调用类型的安全表达式。

##### getExpressionParser() ExpressionParser

> * 返回：
> * 实现使用的表达式的表达式解析器。

#### 2-2-4-2、SecurityExpressionOperations
    与基于表达式的安全性一起使用的表达式根对象的标准接口。
    
##### denyAll() boolean
    始终拒绝访问。
> * 返回：
>   * 假
##### getAuthentication() Authentication
    获取用于评估表达式的身份验证
    
> * 返回：
>   * 用于评估表达式的身份验证
##### hasAnyAuthority(String... authorities) boolean
    确定 getAuthentication() 是否在 Authentication.getAuthorities() 中具有任何指定的权限。
    
> * 参数：
>   * authority - 要测试的权限（即“ROLE_USER”、“ROLE_ADMIN”）
> * 返回：
>   * 如果找到任何权威，则为真，否则为假
##### hasAnyRole(String... roles) boolean
    确定 getAuthentication() 是否在 Authentication.getAuthorities() 中具有任何指定的权限。

    这与 hasAnyAuthority 类似，但此方法暗示传入的 String 是一个角色。 例如，如果在实现中传递了“USER”，则可能会将其转换为使用
    “ROLE_USER”。 角色转换的方式可能取决于实现设置。

> * 参数：
>   * 角色 - 要测试的权限（即“用户”、“管理员”）
> * 返回：
>   * 如果找到任何权威，则为真，否则为假
##### hasAuthority(String authority) boolean
    确定 getAuthentication() 在 Authentication.getAuthorities() 中是否具有特定权限。
    
> * 参数：
>   * authority - 测试权限（即“ROLE_USER”）
> * 返回：
>   * 如果找到权威则为真，否则为假
##### hasPermission(Object target, Object permission) boolean
    确定 getAuthentication() 是否有权访问给定权限的目标

> * 参数：
>   * target - 要检查权限的目标域对象
>   * 权限 - 检查域对象的权限（即“读取”、“写入”等）。
> * 返回：
>   * 如果授予 getAuthentication() 权限，则为 true，否则为 false
##### hasPermission(Object targetId, String targetType, Object permission) boolean
    确定 getAuthentication() 是否有权访问具有给定 id、类型和权限的域对象。
    
> * 参数：
>   * targetId - 确定访问权限的域对象的标识符
>   * targetType - 类型（即 com.example.domain.Message）
>   * 权限 - 检查域对象的权限（即“读取”、“写入”等）
> * 返回：
>   * 如果授予 getAuthentication() 权限，则为 true，否则为 false
##### hasRole(String role) boolean
    确定 getAuthentication() 在 Authentication.getAuthorities() 中是否具有特定权限。

    这与 hasAuthority(String) 类似，除了此方法暗示传入的 String 是一个角色。 例如，如果在实现中传递了“USER”，则可能会将其转换为使用
    “ROLE_USER”。 角色转换的方式可能取决于实现设置。

> * 参数：
>   * 角色 - 测试权限（即“用户”）
> * 返回：
>   * 如果找到权威则为真，否则为假
##### isAnonymous() boolean
    确定 getAuthentication() 是否是匿名的
    
> * 返回：
>   * 如果用户是匿名的，则为 true，否则为 false
##### isAuthenticated() boolean
    确定 getAuthentication() 是否经过身份验证
    
> * 返回：
>   * 如果 getAuthentication() 已通过身份验证，则为 true，否则为 false
##### isFullyAuthenticated boolean
    确定 getAuthentication() 是否在不使用“记住我”的情况下进行身份验证
    
> * 返回：
>   * 如果 getAuthentication() 在没有使用记住我的情况下进行身份验证，则为 true，否则为 false
##### isRememberMe() boolean
    确定是否使用记住我对 getAuthentication() 进行了身份验证
    
> * 返回：
>   * 如果 getAuthentication() 使用记住我进行身份验证，则为 true，否则为 false
##### permitAll() boolean
    始终授予访问权限。
> * 返回：
>   * 真的


### 2-2-5、access.expression.method
    基于表达式的方法安全的实现。

![model](./images/core/access-expression-method-model.png)    

#### 2-2-5-1、MethodSecurityExpressionHandler
    扩展的表达式处理程序外观，它添加了特定于保护方法调用的方法。

##### filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) Object
    过滤目标集合或数组。 仅适用于方法调用。

> * 参数：
>   * filterTarget - 要过滤的数组或集合。
>   * filterExpression - 应用作过滤条件的表达式。 如果它在评估时返回 false，则该对象将从返回的集合中删除
>   * ctx - 当前评估上下文（通过调用 SecurityExpressionHandler.createEvaluationContext(org.springframework.security.core.Authentication, Object) 创建）
> * 返回：
>   * 过滤后的集合或数组
##### setReturnObject(Object returnObject, EvaluationContext ctx) void
    用于通知给定评估上下文的返回对象的表达式系统。 仅适用于方法调用。

> * 参数：
>   * returnObject - 返回对象值
>   * ctx - 应在其中设置对象的上下文（通过调用 SecurityExpressionHandler.createEvaluationContext(org.springframework.security.core.Authentication, Object) 创建
#### 2-2-5-2、MethodSecurityExpressionOperations
    如果要在方法安全表达式中使用过滤，则必须实现该接口。

##### getFilterObject() Object
##### getReturnObject() Object
##### getThis() Object
##### setFilterObject() void
##### setReturnObject() void


### 2-2-6、access.hierarchicalroles
    角色层次结构实现

#### 2-2-6-1、RoleHierarchy
    角色层次结构的简单接口。

##### getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities)
    返回所有可访问权限的数组。
    可访问权限是直接分配的权限加上角色层次结构中可从它们（可传递地）访问的所有权限。

    例子：
    角色层次结构：ROLE_A > ROLE_B > ROLE_C。
    直接分配的权限：ROLE_A。
    可访问权限：ROLE_A、ROLE_B、ROLE_C。

> * 参数：
>   * 权限 - - 直接分配的权限列表。
> * 返回：
>   * 给定指定权限的所有可访问权限列表。

### 2-2-7、access.intercept
    抽象级安全拦截类，负责为安全对象强制执行配置的安全约束。

![model](./images/core/access-intercept.png)
#### 2-2-7-1、AbstractSecurityInterceptor
    为安全对象实现安全拦截的抽象类。
    AbstractSecurityInterceptor 将确保安全拦截器的正确启动配置。它还将实现对安全对象调用的正确处理，即：

    1.从 SecurityContextHolder 获取 Authentication 对象。
    2.通过针对 SecurityMetadataSource 查找安全对象请求，确定请求是与安全调用还是公共调用相关。
    3.对于受保护的调用（有一个用于安全对象调用的 ConfigAttributes 列表）：
        3.1.如果 Authentication.isAuthenticated() 返回 false，或者 alwaysReauthenticate 为 true，则根据配置的
         AuthenticationManager 对请求进行身份验证。通过身份验证后，将 SecurityContextHolder 上的 Authentication 对象替换为返回值。
        3.2.针对配置的 AccessDecisionManager 授权请求。
        3.3.通过配置的 RunAsManager 执行任何运行方式替换。
        3.4.将控制权传递回具体的子类，它实际上将继续执行对象。返回一个 InterceptorStatusToken 以便在子类完成对象的执行后，其 finally 
        子句可以确保 AbstractSecurityInterceptor 被调用并使用 finallyInvocation(InterceptorStatusToken) 正确整理。
        3.5.具体的子类将通过 afterInvocation(InterceptorStatusToken, Object) 方法重新调用 AbstractSecurityInterceptor。
        3.6.如果 RunAsManager 替换了 Authentication 对象，则将 SecurityContextHolder 返回到调用 AuthenticationManager 后存在
        的对象。
        3.7.如果定义了 AfterInvocationManager，则调用调用管理器并允许它替换因返回给调用者的对象。
    4.对于公开的调用（安全对象调用没有 ConfigAttributes）：
        4.1.如上所述，具体子类将返回一个 InterceptorStatusToken，在执行完安全对象后，该 InterceptorStatusToken 随后被重新呈现给
         AbstractSecurityInterceptor。 AbstractSecurityInterceptor 在它的 afterInvocation(InterceptorStatusToken, Object) 
         被调用时不会采取进一步的行动。
    5.控制再次返回到具体的子类，以及应该返回给调用者的对象。然后子类会将该结果或异常返回给原始调用者。

##### finallyInvocation(InterceptorStatusToken token) void
    在安全对象调用完成后清理 AbstractSecurityInterceptor 的工作。 无论安全对象调用是否成功返回（即它应该在 finally 块中完成），都应该
    在安全对象调用之后和 afterInvocation 之前调用此方法。

> * 参数：
>   * 令牌 - 由 beforeInvocation(Object) 方法返回

##### afterInvocation(InterceptorStatusToken token, Object returnedObject) Object
    在安全对象调用完成后完成 AbstractSecurityInterceptor 的工作。

> * 参数：
>   * 令牌 - 由 beforeInvocation(Object) 方法返回
>   * ReturnedObject - 从安全对象调用返回的任何对象（可能为 null）
> * 返回：
>   * 安全对象调用最终应返回给其调用者的对象（可能为空）

##### authenticateIfRequired() Authentication
    如果 Authentication.isAuthenticated() 返回 false 或属性 alwaysReauthenticate 已设置为 true，则检查当前的身份验证令牌并将其传
    递给 AuthenticationManager。
    
> * 返回：
>   * 经过身份验证的 Authentication 对象。

##### credentialsNotFound(String reason, Object secureObject, Collection<ConfigAttribute> configAttribs) void
    Helper 方法生成包含传递原因的异常，并将事件发布到应用程序上下文。
    总是抛出异常。

> * 参数：
>   * 原因 - 在异常细节中提供
>   * secureObject - 被调用的对象
>   * configAttribs - 为 secureObject 定义的

##### getSecureObjectClass() Class<?>
    指示子类将呈现给抽象父类以进行处理的安全对象的类型。 这用于确保连接到 AbstractSecurityInterceptor 的协作者都支持指定的安全对象类。

> * 返回：
>   * 子类为其提供服务的安全对象的类型

##### setAlwaysReauthenticate(boolean alwaysReauthenticate) void
    指示 AbstractSecurityInterceptor 是否应忽略 Authentication.isAuthenticated() 属性。 默认为 false，这意味着默认情况下
     Authentication.isAuthenticated() 属性是受信任的，如果主体已经过身份验证，则不会发生重新身份验证。

> * 参数：
>   * alwaysReauthenticate - true 强制 AbstractSecurityInterceptor 忽略 Authentication.isAuthenticated() 的值并始终重新验证
请求（默认为 false）。

##### setPublishAuthorizationSuccess(boolean publishAuthorizationSuccess) void
    只会发布 AuthorizationFailureEvent。 如果将此属性设置为 true，则还将发布 AuthorizedEvents。
    
> * 参数：
>   * publishAuthorizationSuccess - 默认值为 false
##### setRejectPublicInvocations(boolean rejectPublicInvocations) void
    通过拒绝公共调用（并将此属性设置为 true），本质上您可以确保 AbstractSecurityInterceptor 建议的每个安全对象调用都定义了配置属性。 
    这对于确保“故障安全”模式非常有用，在这种模式下，未声明的安全对象将被拒绝并及早检测到配置遗漏。 如果将此属性设置为 true 并尝试调用没有
    配置属性的安全对象，则 AbstractSecurityInterceptor 将抛出 IllegalArgumentException。

> * 参数：
>   * rejectPublicInvocations - 设置为 true 以拒绝没有配置属性的安全对象的调用（默认情况下它是 false，它将未声明的安全对象视为“公共”
或未经授权的对象）。

#### 2-2-7-2、AfterInvocationManager
    查看从安全对象调用返回的对象，能够修改对象或抛出 AccessDeniedException。
    通常用于确保允许主体访问由服务层 bean 返回的域对象实例。 也可用于改变域对象实例，因此主体只能访问授权的 bean 属性或集合元素。
    
    应该特别考虑在修改数据库的 bean 方法上使用 AfterInvocationManager。 通常 AfterInvocationManager 与只读方法一起使用，例如
     public DomainObject getById(id)。 如果与修改数据库的方法一起使用，则应使用事务管理器来确保任何 AccessDeniedException 都会导致
    事务所做更改的回滚。

##### decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes, Object returnedObject) Object
    给定安全对象调用的详细信息，包括其返回的对象，做出访问控制决定或可选地修改返回的对象。
    
> * 参数：
>   * 身份验证 - 调用该方法的调用者
>   * object - 被调用的安全对象
>   * 属性 - 与被调用的安全对象关联的配置属性
>   * ReturnedObject - 从安全对象调用返回的对象
> * 返回：
>   * 最终将返回给调用者的对象（如果一个实现不希望修改要返回给调用者的对象，则实现应该简单地返回它由返回对象方法参数传递的相同对象）
> * 抛出：
>   * AccessDeniedException - 如果访问被拒绝
##### supports(ConfigAttribute attribute) boolean
    指示此 AfterInvocationManager 是否能够处理通过传递的 ConfigAttribute 呈现的“调用后”请求。
    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AfterInvocationManager 可
    以使用的每个配置属性。

> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 AfterInvocationManager 可以支持传递的配置属性，则为 true

##### supports(java.lang.Class<?> clazz) boolean
    指示 AfterInvocationManager 实现是否能够为指示的安全对象类型提供访问控制决策。
    
> * 参数：
>   * clazz - 被查询的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true

#### 2-2-7-3、RunAsManager
    仅为当前安全对象调用创建一个新的临时身份验证对象。
    此接口允许实现替换仅适用于当前安全对象调用的 Authentication 对象。 AbstractSecurityInterceptor 将仅在安全对象回调期间替换
     SecurityContext 中保存的 Authentication 对象，并在回调结束时将其返回到原始 Authentication 对象。
    
    提供此功能是为了可以建立具有两层对象的系统。一层面向公众，具有正常的安全方法，授予的权限预计由外部调用者持有。另一层是私有的，只能由面
    向公众的层内的对象调用。此私有层中的对象仍然需要安全性（否则它们将是公共方法）并且它们还需要以防止它们被外部调用者直接调用的方式的安全
    性。私有层中的对象将被配置为需要从未授予外部调用者的授予权限。 RunAsManager 接口提供了一种以这种方式提升安全性的机制。
    
    预期实现将提供相应的具体 Authentication 和 AuthenticationProvider 以便可以对替换的 Authentication 对象进行身份验证。需要实现
    某种形式的安全性以确保 AuthenticationProvider 只接受由 RunAsManager 的授权具体实现创建的 Authentication 对象。

##### buildRunAs(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) Authentication
    返回当前安全对象调用的替换 Authentication 对象，如果不需要替换，则返回 null。

> * 参数：
>   * 身份验证 - 调用安全对象的调用者
>   * object - 被调用的安全对象
>   * 属性 - 与被调用的安全对象关联的配置属性
> * 返回：
>   * 在安全对象调用期间使用的替换对象，如果身份验证应保持原样，则为 null

##### supports(java.lang.Class<?> clazz) boolean
    指示 RunAsManager 实现是否能够为指示的安全对象类型提供 run-as 替换。

> * 参数：
>   * clazz - 被查询的类
> * 返回：
>   * 如果实现可以处理指定的类，则为 true

##### supports(ConfigAttribute attribute) boolean
    指示此 RunAsManager 是否能够处理传递的 ConfigAttribute。
    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AfterInvocationManager 可以
    使用的每个配置属性。

> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 RunAsManager 可以支持传递的配置属性，则为 true

### 2-2-8、access.intercept.aopalliance
    为 AOP Alliance MethodInvocation 强制实施安全性，例如通过 Spring AOP。
![aopalliance](./images/core/access-intercept-aopalliance.png)


### 2-2-9、access.intercept.aspectj
    强制 AspectJ JointPoints 的安全性，将安全对象回调委托给调用方面。
![aspectj](./images/core/access-intercept-aspectj.png)

#### 2-2-9-1、AspectJCallback
    当 AspectJMethodSecurityInterceptor 希望 AspectJ 处理继续时由它调用。 通常在 around() 通知中实现为一个简单的返回proceed()； 陈述。

##### proceedWithObject() Object

### 2-2-10、access.method
    提供 SecurityMetadataSource 实现以通过不同的 AOP 库保护 Java 方法调用。

![method](./images/core/access-method.png)

#### 2-2-10-1、MethodSecurityMetadataSource
    SecurityMetadataSource 实现的接口，旨在执行以方法为键的查找。

##### getAttributes(java.lang.reflect.Method method, java.lang.Class<?> targetClass)  Collection<ConfigAttribute>

### 2-2-11、access.prepost
    包含用于处理 @PreAuthorize、@PreFilter、@PostAuthorize 和 @PostFilter 注释的基础结构类。

![prepost](./images/core/access-prepost.png)

#### 2-2-11-1、PostAuthorize
    用于指定方法访问控制表达式的注释，该表达式将在调用方法后进行评估。

> * 参数：
>   * value - 值，调用受保护方法后要评估的 Spring-EL 表达式

#### 2-2-11-2、PostFilter
    用于指定方法过滤表达式的注释，该表达式将在调用方法后进行评估。

> * 参数：
>   * value - 值，调用受保护方法后要评估的 Spring-EL 表达式

#### 2-2-11-3、PreAuthorize
    用于指定方法访问控制表达式的注释，该表达式将被评估以决定是否允许方法调用。
    
> * 参数：
>   * value - 值，在调用受保护方法之前要评估的 Spring-EL 表达式
  
#### 2-2-11-4、PreFilter
    用于指定方法过滤表达式的注释，该表达式将在调用方法之前进行评估。 要过滤的参数的名称是使用 filterTarget 属性指定的。 这必须是支持
     remove 方法的 Java 集合实现。 数组类型不支持预过滤，如果命名过滤器目标参数的值在运行时为空，则预过滤将失败。
    对于具有集合类型的单个参数的方法，此参数将用作过滤器目标。

    注释值包含将为集合中的每个元素计算的表达式。 如果表达式的计算结果为 false，则元素将被删除。 保留名称“filterObject”可用于表达式中以
    引用正在评估的当前对象。

> * 参数：
>   * value - 值，在调用受保护方法之前要评估的 Spring-EL 表达式
>   * filterTarget - 过滤器目标，应过滤的参数名称（必须是非空集合实例） 如果方法包含单个集合参数，则可以省略此属性。

#### 2-2-11-5、PostInvocationAttribute
    从组合@PostFilter 和@PostAuthorize 注释创建的属性的标记接口。
    由 PostInvocationAuthorizationAdvice 使用。
    扩展自ConfigAttribute，自带获取属性的方法

#### 2-2-11-6、PostInvocationAuthorizationAdvice
    在调用方法后执行过滤和授权逻辑。

##### after(Authentication authentication, org.aopalliance.intercept.MethodInvocation mi, PostInvocationAttribute pia, Object returnedObject) Object

> * 抛出
>   * AccessDeniedException

#### 2-2-11-7、PreInvocationAttribute
    从组合@PreFilter 和@PreAuthorize 注释创建的属性的标记接口。
    由 PreInvocationAuthorizationAdvice 使用。
    扩展自ConfigAttribute，自带获取属性的方法

#### 2-2-11-8、PreInvocationAuthorizationAdvice
    在调用方法之前执行参数过滤和授权逻辑。
    
##### before(Authentication authentication, org.aopalliance.intercept.MethodInvocation mi, PreInvocationAttribute preInvocationAttribute) boolean
    应该执行的“before”建议以执行任何必要的过滤并决定方法调用是否被授权。

> * 参数：
>   * 身份验证 - 有关应在其帐户上做出决定的委托人的信息
>   * mi - 正在尝试的方法调用
>   * preInvocationAttribute - 从 @PreFilter 和 @PostFilter 注释构建的属性。
> * 返回：
>   * 如果授权为真，否则为假

#### 2-2-11-9、PrePostInvocationAttributeFactory

##### createPostInvocationAttribute(java.lang.String postFilterAttribute, String postAuthorizeAttribute)  PostInvocationAttribute
##### createPreInvocationAttribute(java.lang.String preFilterAttribute, String filterObject, String preAuthorizeAttribute) PreInvocationAttribute

### 2-2-12、access.vote
    实施基于投票的授权决策方法。

![vote](./images/core/access-vote.png)

#### 2-2-12-1、AbstractAccessDecisionManager
    AccessDecisionManager 的抽象实现。
    如果所有选民都放弃投票（默认为拒绝访问），则处理 bean 上下文定义的 AccessDecisionVoters 列表和访问控制行为的配置。

域：
> 域名 | 域类型
> ---- | -----
> allowIfAllAbstainDecisions | boolean
> decisionVoters | List<AccessDecisionVoter<?>>
> logger | org.apache.commons.logging.Log
> messages | org.springframework.context.support.MessageSourceAccessor

##### afterPropertiesSet() void
##### checkAllowIfAllAbstainDecisions() void
##### getDecisionVoters() List<AccessDecisionVoter<?>>
##### isAllowIfAllAbstainDecisions() boolean
##### setAllowIfAllAbstainDecisions(boolean allowIfAllAbstainDecisions) void
##### setMessageSource(org.springframework.context.MessageSource messageSource) void
> * 指定者：
>   * 接口 org.springframework.context.MessageSourceAware 中的 setMessageSource
##### supports(Class<?> clazz) boolean
    遍历所有 AccessDecisionVoters 并确保每个都可以支持所呈现的类。
    如果一个或多个投票者不能支持所呈现的类，则返回 false。

> * 指定者：
>   * 在接口 AccessDecisionManager 中支持
> * 参数：
>   * clazz - 所呈现的受保护对象的类型
> * 返回：
>   * 如果支持此类型，则为 true
##### supports(ConfigAttribute attribute) boolean
    从接口复制的描述：AccessDecisionManager
    指示此 AccessDecisionManager 是否能够处理通过传递的 ConfigAttribute 呈现的授权请求。
    这允许 AbstractSecurityInterceptor 检查配置的 AccessDecisionManager 和/或 RunAsManager 和/或 AfterInvocationManager 可以
    使用的每个配置属性。

> * 指定者：
>   * 在接口 AccessDecisionManager 中支持
> * 参数：
>   * attribute - 针对 AbstractSecurityInterceptor 配置的配置属性
> * 返回：
>   * 如果此 AccessDecisionManager 可以支持传递的配置属性，则为 true
##### toString() String


#### 2-2-12-2、AbstractAclVoter
    提供用于编写域对象 ACL 投票者的辅助方法。 不受任何特定 ACL 系统的约束。

> 域名 | 域类型
> ---- | -----
> processDomainObjectClass | Class<?>

##### getDomainObjectInstance(org.aopalliance.intercept.MethodInvocation invocation) Object
##### getProcessDomainObjectClass() Class<?>
##### setProcessDomainObjectClass(Class<?> processDomainObjectClass) void
##### supports(java.lang.Class<?> clazz) boolean
    此实现仅支持 MethodSecurityInterceptor，因为它查询呈现的 MethodInvocation。

> * 指定者：
>   * 在接口 AccessDecisionVoter<org.aopalliance.intercept.MethodInvocation> 中支持
> * 参数：
>   * clazz - 安全对象
> * 返回：
>   * 如果安全对象是 MethodInvocation，则为 true，否则为 false

## 2-3、认证（authentication）
### 2-3-1、authentication
    与用户身份验证相关的核心类和接口，在整个 Spring Security 中使用。
    最重要的是 AuthenticationManager 及其默认实现 ProviderManager，它维护一个 AuthenticationProviders 列表，它将身份验证请求委托
    给它。
    
![authentication](./images/core/authentication.png)

#### 2-3-1-1、AuthenticationDetailsSource<C,T>
    为给定的 Web 请求提供 Authentication.getDetails() 对象。

##### buildDetail(C context) T
    当类希望创建新的身份验证详细信息实例时由类调用。

> * 参数：
>   * context - 请求对象，可以被身份验证详细信息对象使用
> * 返回：
>   * 完全配置的身份验证详细信息实例
#### 2-3-1-2、AuthenticationEventPublisher
##### publishAuthenticationFailure(AuthenticationException exception, Authentication authentication)  void
##### publishAuthenticationSuccess(Authentication authentication) void

#### 2-3-1-3、AuthenticationManager
    处理身份验证请求。
##### authenticate(Authentication authentication) Authentication
    尝试对传递的 Authentication 对象进行身份验证，如果成功则返回完全填充的 Authentication 对象（包括授予的权限）。
    AuthenticationManager 必须遵守以下有关异常的合同：

        1、如果帐户被禁用并且 AuthenticationManager 可以测试此状态，则必须抛出 DisabledException。
        2、如果帐户被锁定并且 AuthenticationManager 可以测试帐户锁定，则必须抛出 LockedException。
        3、如果提供了不正确的凭据，则必须抛出 BadCredentialsException。虽然上述例外是可选的，但 AuthenticationManager 必须始终测试
        凭据。
    应测试异常，如果适用，则按上述顺序抛出异常（即，如果帐户被禁用或锁定，则立即拒绝身份验证请求并且不执行凭据测试过程）。这可以防止针对禁
    用或锁定的帐户测试凭据。

> * 参数：
>   * authentication - 身份验证请求对象
> * 返回：
>   * 一个完全经过身份验证的对象，包括凭据
> * 抛出：
>   * AuthenticationException - 如果身份验证失败

#### 2-3-1-4、AuthenticationManagerResolver<C>
    用于根据提供的上下文解析 AuthenticationManager 的接口

##### resolve(C context) AuthenticationManager
    从提供的上下文中解析 AuthenticationManager

> * 参数：
>   * 语境 -
> * 返回：
>   * 要使用的 AuthenticationManager

#### 2-3-1-5、AuthenticationProvider
    表示一个类可以处理特定的身份验证实现。

##### authenticate(Authentication authentication) Authentication
    使用与 AuthenticationManager.authenticate(Authentication) 相同的合同执行身份验证。
    
> * 参数：
>   * 身份验证 - 身份验证请求对象。
> * 返回：
>   * 一个完全经过身份验证的对象，包括凭据。 如果 AuthenticationProvider 无法支持对传递的 Authentication 对象进行身份验证，则可能返
>回 null。 在这种情况下，将尝试支持呈现的 Authentication 类的下一个 AuthenticationProvider。
> * 抛出：
>   * AuthenticationException - 如果身份验证失败。

##### supports(java.lang.Class<?> authentication) boolean
    如果此 AuthenticationProvider 支持指定的 Authentication 对象，则返回 true。
    返回 true 并不能保证 AuthenticationProvider 将能够对 Authentication 类的呈现实例进行身份验证。 它只是表明它可以支持对其进行更深
    入的评估。 AuthenticationProvider 仍然可以从 authenticate(Authentication) 方法返回 null 以指示应该尝试另一个 AuthenticationProvider。

    能够执行身份验证的 AuthenticationProvider 的选择是在 ProviderManager 运行时进行的。

> * 参数：
>   * 验证 -
> * 返回：
>   * 如果实现可以更仔细地评估呈现的 Authentication 类，则为 true

#### 2-3-1-6、AuthenticationTrustResolver
    评估身份验证令牌

##### isAnonymous(Authentication authentication) boolean
    指示传递的身份验证令牌是否代表匿名用户。 通常，如果框架试图决定 AccessDeniedException 是否应该导致最终拒绝（即如果主体是非匿名/完全
    验证的情况）或指示主体尝试实际验证（即 如果身份验证只是匿名的，情况也会如此）。

> * 参数：
>   * 身份验证 - 测试（可能为 null，在这种情况下，该方法将始终返回 false）
> * 返回：
>   * true 传递的身份验证令牌表示匿名主体，否则为 false
##### isRememberMe(Authentication authentication) boolean
    指示传递的身份验证令牌是否代表已被记住的用户（即不是已完全身份验证的用户）。
    提供该方法是为了协助您可能开发的自定义 AccessDecisionVoters 等。 当然，您也不需要使用此方法，而是可以开发自己的“信任级别”层次结构。

> * 参数：
>   * 身份验证 - 测试（可能为 null，在这种情况下，该方法将始终返回 false）
> * 返回：
>   * true 传递的身份验证令牌表示使用记住我的令牌进行身份验证的主体，否则为 false
#### 2-3-1-7、ReactiveAuthenticationManager
    功能接口：
    这是一个函数式接口，因此可以用作 lambda 表达式或方法引用的赋值目标。
    
    确定提供的身份验证是否可以通过身份验证。

##### authenticate(Authentication authentication) reactor.core.publisher.Mono<Authentication>
    尝试对提供的身份验证进行身份验证

> * 参数：
>   * authentication - 要测试的身份验证
> * 返回：
>   * 如果身份验证成功，则返回身份验证。 如果无法确定身份验证，则返回空 Mono。 如果身份验证失败，则返回 Mono 错误。

#### 2-3-1-8、ReactiveAuthenticationManagerResolver<C>
    功能接口：
    这是一个函数式接口，因此可以用作 lambda 表达式或方法引用的赋值目标。
    
    基于提供的上下文解析 ReactiveAuthenticationManager 的接口

##### resolve(C context) reactor.core.publisher.Mono<Authentication>

### 2-3-2、authentication.dao
    一个依赖于数据访问对象的 AuthenticationProvider。

![authentication dao](./images/core/authentication-dao.png)

#### 2-3-2-1、AbstractUserDetailsAuthenticationProvider
    一个基本的 AuthenticationProvider，它允许子类覆盖和使用 UserDetails 对象。该类旨在响应 UsernamePasswordAuthenticationToken 
    身份验证请求。
    验证成功后，将创建 UsernamePasswordAuthenticationToken 并将其返回给调用者。令牌将包括用户名的字符串表示或从身份验证存储库返回的
     UserDetails 作为其主体。如果正在使用容器适配器，则使用 String 是合适的，因为它需要用户名的 String 表示。如果您需要访问经过身份验
    证的用户的其他属性，例如电子邮件地址、人性化名称等，则使用 UserDetails 是合适的。由于不建议使用容器适配器，并且 UserDetails 实现提
    供了额外的灵活性，默认情况下会返回 UserDetails .要覆盖此默认值，请将 setForcePrincipalAsString(boolean) 设置为 true。

    缓存是通过存储放置在 UserCache 中的 UserDetails 对象来处理的。这确保可以验证具有相同用户名的后续请求，而无需查询 UserDetailsS​​ervice。
    应该注意的是，如果用户似乎提供了错误的密码，将查询 UserDetailsS​​ervice 以确认使用了最新的密码进行比较。只有无状态应用程序可能需要缓存。
    例如，在普通的 Web 应用程序中，SecurityContext 存储在用户的会话中，并且不会在每次请求时对用户重新进行身份验证。因此默认缓存实现是
     NullUserCache。

    嵌套类
> 修饰符和类型 | 类和描述
> ---------- | -------
> private class | DefaultPostAuthenticationChecks 
> private class | DefaultPreAuthenticationChecks 

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private GrantedAuthoritiesMapper | 	authoritiesMapper 
> private boolean | 	forcePrincipalAsString 
> protected boolean | 	hideUserNotFoundExceptions 
> protected org.apache.commons.logging.Log | 	logger 
> protected org.springframework.context.support.MessageSourceAccessor | 	messages 
> private UserDetailsChecker | 	postAuthenticationChecks 
> private UserDetailsChecker | 	preAuthenticationChecks 
> private UserCache | 	userCache 

##### additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) void
    允许子类对给定的身份验证请求返回（或缓存）的 UserDetails 执行任何其他检查。 通常子类至少会比较 Authentication.getCredentials() 
    和 UserDetails.getPassword()。 如果需要自定义逻辑来比较 UserDetails 和/或 UsernamePasswordAuthenticationToken 的其他属性，
    这些也应出现在此方法中。

> * 参数：
>   * userDetails - 从retrieveUser(String, UsernamePasswordAuthenticationToken) 或UserCache 中检索
>   * authentication - 当前需要认证的请求
> * 抛出：
>   * AuthenticationException - AuthenticationException 如果无法验证凭据（通常是 BadCredentialsException、AuthenticationServiceException）

##### afterPropertiesSet() void
    指定者：
    接口 org.springframework.beans.factory.InitializingBean 中的 afterPropertiesSet
    
> * 抛出：
>   * java.lang.异常

##### authenticate(Authentication authentication) Authentication
    从接口复制的描述：AuthenticationProvider
    使用与 AuthenticationManager.authenticate(Authentication) 相同的合同执行身份验证。
    指定者：
    在 AuthenticationProvider 接口中进行身份验证

> * 参数：
>   * 身份验证 - 身份验证请求对象。
> * 返回：
>   * 一个完全经过身份验证的对象，包括凭据。 如果 AuthenticationProvider 无法支持对传递的 Authentication 对象进行身份验证，则可能返回 null。 在这种情况下，将尝试支持呈现的 Authentication 类的下一个 AuthenticationProvider。
> * 抛出：
>   * AuthenticationException - 如果身份验证失败。

##### createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) Authentication
    创建一个成功的 Authentication 对象。
    受保护以便子类可以覆盖。

    子类通常将用户提供的原始凭证（不是加盐或编码的密码）存储在返回的 Authentication 对象中。

> * 参数：
>   * principal - 这应该是返回对象中的主体（由 isForcePrincipalAsString() 方法定义）
>   * 身份验证 - 提交给提供者进行验证
>   * 用户 - 由实现加载
> * 返回：
>   * 成功的认证令牌

##### retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) UserDetails
    允许子类从特定于实现的位置实际检索 UserDetails，如果提供的凭据不正确，则可以选择立即抛出 AuthenticationException（如果需要以用户
    身份绑定到资源以获得或生成用户详细信息）。
    子类不需要执行任何缓存，因为 AbstractUserDetailsAuthenticationProvider 将默认缓存 UserDetails。 UserDetails 的缓存确实带来了
    额外的复杂性，因为这意味着依赖缓存的后续请求仍需要验证其凭据，即使在此方法中采用基于绑定的策略的子类确保了凭据的正确性。因此，重要的是子
    类要么禁用缓存（如果他们想确保此方法是唯一能够验证请求的方法，因为不会缓存任何 UserDetails）或确保子类实现
     additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken) 以进行比较带有后续身份验证请求的缓存
     UserDetails 的凭据。

    大多数情况下，子类不会在此方法中执行凭据检查，而是在 additionalAuthenticationChecks(UserDetails, UsernamePasswordAuthenticationToken) 
    中执行它，以便与凭据验证相关的代码不需要在两个方法中重复。

> * 参数：
>   * username - 要检索的用户名
>   * authentication - 身份验证请求，子类可能需要执行基于绑定的 UserDetails 检索
> * 返回：
>   * 用户信息（从不为空 - 应该抛出异常）
> * 抛出：
>   * AuthenticationException - 如果无法验证凭据（通常是 BadCredentialsException、AuthenticationServiceException 或 UsernameNotFoundException）

##### setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) void
    默认情况下，如果未找到用户名或密码不正确， AbstractUserDetailsAuthenticationProvider 将抛出 BadCredentialsException。 将此属
    性设置为 false 将导致为前者抛出 UsernameNotFoundExceptions。 请注意，这被认为比对这两种异常都抛出 BadCredentialsException 更不
    安全。

> * 参数：
>   * hideUserNotFoundExceptions - 如果您希望抛出 UsernameNotFoundExceptions 而不是非特定的 BadCredentialsException，则设置为
> false（默认为 true）

##### setMessageSource(org.springframework.context.MessageSource messageSource) void
    指定者：
    接口 org.springframework.context.MessageSourceAware 中的 setMessageSource

##### supports(java.lang.Class<?> authentication) boolean
    从接口复制的描述：AuthenticationProvider
    如果此 AuthenticationProvider 支持指定的 Authentication 对象，则返回 true。
    返回 true 并不能保证 AuthenticationProvider 将能够对 Authentication 类的呈现实例进行身份验证。 它只是表明它可以支持对其进行更深
    入的评估。 AuthenticationProvider 仍然可以从 AuthenticationProvider.authenticate(Authentication) 方法返回 null 以指示应该
    尝试另一个 AuthenticationProvider。
    
    能够执行身份验证的 AuthenticationProvider 的选择是在 ProviderManager 运行时进行的。

> * 指定者：
>   * 在接口 AuthenticationProvider 中支持
> * 返回：
>   * 如果实现可以更仔细地评估呈现的 Authentication 类，则为 true

##### setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) void
    设置策略将用于在验证凭据之前验证加载的 UserDetails 的状态。

> * 参数：
>   * preAuthenticationChecks - 在身份验证之前调用的策略。

### 2-3-3、authentication.event
    可以发布到 Spring 应用程序上下文的身份验证成功和失败事件。

![authentication event](./images/core/authentication-event.png)

#### 2-3-3-1、AbstractAuthenticationEvent
    表示应用程序身份验证事件。
    ApplicationEvent 的来源将是 Authentication 对象。

    域：
    source

##### getAuthentication() Authentication
    导致事件的身份验证请求的 Getter。 也可以从 super.getSource() 获得。

> * 返回：
>   * 认证请求

#### 2-3-3-2、AbstractAuthenticationFailureEvent
    抽象应用程序事件，表明由于某种原因认证失败。

    域：
        exception

##### getException() AuthenticationException

### 2-3-4、authentication.jaas
    JAAS 的身份验证提供程序。

![authentication-jaas](./images/core/authentication-jaas.png)

#### 2-3-4-1、AuthorityGranter
    AuthorityGranter 接口用于将给定的主体映射到角色名称。
    例如，如果要从 JAAS 使用 Windows NT 登录模块，则可以创建 AuthrityGranter 实现以将 NT 组主体映射到 ROLE_USER 角色。
    
##### grant(java.security.Principal principal) Set<String>
    为从 LoginContext 主题返回的每个主体调用 grant 方法。 如果 AuthorityGranter 希望授予任何权限，它应该返回一个包含它希望授予的角色
    名称的 java.util.Set，例如 ROLE_USER。 如果 AuthrityGranter 不想授予任何权限，它应该返回 null。
    该集合可以包含任何对象，因为返回集合中的所有对象都将使用 toString() 传递给 JaasGrantedAuthority 构造函数。

> * 参数：
>   * principal - LoginContext.getSubect().getPrincipals() 方法中的主体之一。
> * 返回：
>   * 要授予的角色名称，或者为 null，这意味着不应向主体授予任何角色。

#### 2-3-4-2、JaasAuthenticationCallbackHandler
    JaasAuthenticationCallbackHandler 类似于 javax.security.auth.callback.CallbackHandler 接口，因为它定义了一个句柄方法。
     JaasAuthenticationCallbackHandler 只被要求一次处理一个 Callback 实例，而不是所有回调的数组，正如 javax... CallbackHandler 
    定义的那样。
    在 JaasAuthenticationCallbackHandler 被要求“处理”任何回调之前，首先传递登录尝试所针对的 Authentication 对象。 注意：身份验证对
    象尚未“验证”。

##### handle(javax.security.auth.callback.Callback callback, Authentication auth) void
    处理回调。 将为从 LoginContext 发送的每个回调实例调用 handle 方法。 这意味着可以为给定的 JaasAuthenticationCallbackHandler 多
    次调用 handle 方法。

> * 参数：
>   * 回调 -
>   * auth - 当前正在验证的 Authentication 对象。
> * 抛出：
>   * java.io.IO异常
>   * javax.security.auth.callback.UnsupportedCallbackException

#### 2-3-4-3、LoginExceptionResolver
    JaasAuthenticationProvider 使用 LoginExceptionResolver 的实例来解决 LoginModule 特定于 Spring Security AuthenticationExceptions 
    的异常。 例如，配置的登录模块可能会抛出扩展 LoginException 的 ScrewedUpPasswordException，在这种情况下，LoginExceptionResolver 
    实现将返回 BadCredentialsException。
    
##### resolveException(javax.security.auth.login.LoginException ex) AuthenticationException
    将 Jaas LoginException 转换为 SpringSecurityException。

> * 参数：
>   * ex - 配置的 LoginModule 抛出的 LoginException。
> * 返回：
>   * JaasAuthenticationProvider 应该抛出的 AuthenticationException 。
#### 2-3-4-4、AbstractJaasAuthenticationProvider
    从 JAAS 登录配置中检索用户详细信息的 AuthenticationProvider 实现。
    此 AuthenticationProvider 能够验证包含正确用户名和密码的 UsernamePasswordAuthenticationToken 请求。
    
    此实现由子类的 createLoginContext(CallbackHandler) 实现提供的 JAAS 配置支持。
    
    当使用 JAAS 登录模块作为身份验证源时，有时 LoginContext 将需要 CallbackHandlers。 AbstractJaasAuthenticationProvider 使用内
    部 CallbackHandler 来包装 ApplicationContext 中配置的 JaasAuthenticationCallbackHandler。当 LoginContext 调用内部的
     CallbackHandler 时，控制权被传递给每个 JaasAuthenticationCallbackHandler 传递的每个 Callback。
    
    JaasAuthenticationCallbackHandlers 通过 callbackHandlers 属性传递给 AbstractJaasAuthenticationProvider。

```xml
 <property name="callbackHandlers">
   <list>
     <bean class="org.springframework.security.authentication.jaas.TestCallbackHandler"/>
     <bean class="org.springframework.security.authentication.jaas.JaasNameCallbackHandler"/>
     <bean class="org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler"/>
  </list>
 </property>
```
 
    在调用 LoginContext.login() 之后，AbstractJaasAuthenticationProvider 将从主题 (LoginContext.getSubject().getPrincipals) 
    中检索返回的主体。然后将每个返回的主体传递给配置的 AuthorityGranters。 AuthorityGranter 是返回的 Principal 和角色名称之间的映射。
    如果 AuthorityGranter 希望授予 Authorization 一个角色，它会从它的 AuthorityGranter.grant(java.security.Principal) 方法返回
    该角色名称。返回的角色将作为 GrantedAuthority 应用于 Authorization 对象。
    
    AuthorityGranters在spring xml中配置如下...

```xml
 <property name="authorityGranters">
   <list>
     <bean class="org.springframework.security.authentication.jaas.TestAuthorityGranter"/>
   </list>
 </property>
```

    嵌套类
> 修饰符和类型 | 类和描述
> ---------- | -------
> private class | InternalCallbackHandler （JAASAuthenticationCallbackHandlers的包装类）

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private org.springframework.context.ApplicationEventPublisher |	applicationEventPublisher 
> private AuthorityGranter[] |	authorityGranters 
> private JaasAuthenticationCallbackHandler[] |	callbackHandlers 
> protected org.apache.commons.logging.Log |	log 
> private java.lang.String |	loginContextName 
> private LoginExceptionResolver |	loginExceptionResolver 

##### afterPropertiesSet() void
    验证是否设置了所需的属性。 此外，如果尚未使用有效处理程序调用 setCallbackHandlers(JaasAuthenticationCallbackHandler[])，则初始
    化为使用 JaasNameCallbackHandler 和 JaasPasswordCallbackHandler。

> * 指定者：
>   * 接口 org.springframework.beans.factory.InitializingBean 中的 afterPropertiesSet
> * 抛出：
>   * java.lang.异常
##### authenticate(Authentication auth) Authentication
    尝试根据身份验证对象主体和凭据登录用户

> * 指定者：
>   * 在 AuthenticationProvider 接口中进行身份验证
> * 参数：
>   * auth - 要进行身份验证的身份验证对象。
> * 返回：
>   * 经过身份验证的 Authentication 对象，设置了 grantAuthorities。
> * 抛出：
>   * AuthenticationException - 此实现不处理“锁定”或“禁用”帐户。 如果 loginContext.login() 方法失败，此方法只会抛出一个 AuthenticationServiceException 
>   * 以及将抛出的 LoginException 消息。
##### createLoginContext(javax.security.auth.callback.CallbackHandler handler) javax.security.auth.login.LoginContext
    创建用于身份验证的 LoginContext。

> * 参数：
>   * handler - 应该用于 LoginContext 的 CallbackHandler（从不为 null）。
> * 返回：
>   * 用于身份验证的 LoginContext。
> * 抛出：
>   * javax.security.auth.login.LoginException
##### getApplicationEventPublisher() ApplicationEventPublisher
##### getAuthorities(java.util.Set<java.security.Principal> principals) Set<GrantedAuthority>
##### getAuthorityGranters() AuthorityGranter[]
    返回传递给 setAuthorityGranters(AuthorityGranter[]) 方法的 AuthorityGranter 数组，如果从未设置过，则返回 null。

> * 返回：
>   * AuthorityGranter 数组，或 null
> * 也可以看看：
>   * setAuthorityGranters(AuthorityGranter[])
##### getCallbackHandlers() JaasAuthenticationCallbackHandler[]
##### getLoginContextName() String
##### getLoginExceptionResolver() LoginExceptionResolver
##### handleLogout(SessionDestroyedEvent event) void
    通过获取已销毁会话的安全上下文并为包含 JaasAuthenticationToken 的任何会话调用 LoginContext.logout() 来处理注销。

> * 参数：
>   * event - 包含当前会话的会话事件
##### logout(JaasAuthenticationToken token, javax.security.auth.login.LoginContext loginContext)  void
> * 抛出：
>   * javax.security.auth.login.LoginException
##### onApplicationEvent(SessionDestroyedEvent event)  void
> * 指定者：
>   * 接口 org.springframework.context.ApplicationListener<SessionDestroyedEvent> 中的 onApplicationEvent
##### publishFailureEvent(UsernamePasswordAuthenticationToken token, AuthenticationException ase) void
    发布 JaasAuthenticationFailedEvent。 可以被不同功能的子类覆盖

> * 参数：
>   * token - 正在处理的身份验证令牌
>   * ase - 导致身份验证失败的异常
##### publishSuccessEvent(UsernamePasswordAuthenticationToken token) void
    发布 JaasAuthenticationSuccessEvent。 可以被不同功能的子类覆盖。

> * 参数：
>   * 令牌 - 正在处理的令牌
##### setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher applicationEventPublisher) void
##### setAuthorityGranters(AuthorityGranter[] authorityGranters) void
##### setCallbackHandlers(JaasAuthenticationCallbackHandler[] callbackHandlers) void
##### setLoginContextName(java.lang.String loginContextName) void
##### setLoginExceptionResolver(LoginExceptionResolver loginExceptionResolver)  void
##### supports(java.lang.Class<?> aClass) boolean
    从接口复制的描述：AuthenticationProvider
    如果此 AuthenticationProvider 支持指定的 Authentication 对象，则返回 true。
    返回 true 并不能保证 AuthenticationProvider 将能够对 Authentication 类的呈现实例进行身份验证。 它只是表明它可以支持对其进行更深
    入的评估。 AuthenticationProvider 仍然可以从 AuthenticationProvider.authenticate(Authentication) 方法返回 null 以指示应该
    尝试另一个 AuthenticationProvider。

    能够执行身份验证的 AuthenticationProvider 的选择是在 ProviderManager 运行时进行的。

> * 指定者：
>   * 在接口 AuthenticationProvider 中支持
> * 返回：
>   * 如果实现可以更仔细地评估呈现的 Authentication 类，则为 true

#### 2-3-4-5、JaasGrantedAuthority
    GrantedAuthority，除了分配的角色之外，还持有 AuthorizationGranter 用作授予此权限的理由的主体。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.security.Principal |	principal 
> private java.lang.String |	role 
> private static long |	serialVersionUID 

##### getAuthority() String
    从接口复制的描述：GrantedAuthority
    如果 GrantedAuthority 可以表示为一个字符串，并且该字符串的精度足以依赖于 AccessDecisionManager（或委托）的访问控制决策，则此方法
    应返回这样一个字符串。
    如果 GrantedAuthority 不能以足够的精度表示为 String，则应返回 null。 返回 null 将需要 AccessDecisionManager（或委托）来专门支
    持 GrantedAuthority 实现，因此除非实际需要，否则应避免返回 null。

> * 指定者：
>   * 接口 GrantedAuthority 中的 getAuthority
> * 返回：
>   * 授予权限的表示（如果授予的权限不能表示为具有足够精度的字符串，则为 null）。

### 2-3-5、authentication.jaas.event
    JAAS 身份验证事件可以由 JAAS 身份验证提供程序发布到 Spring 应用程序上下文。

![jaas event](./images/core/authentication-jaas-event.png)

#### 2-3-5-1、JaasAuthenticationEvent
    JaasAuthenticationProvider 触发的事件的父类。
    
    域：
    source
    
##### 	getAuthentication() Authentication
    返回事件“源”的预制方法。

> * 返回：
>   * 认证
### 2-3-6、authentication.jaas.memory
    内存中的 JAAS 实现。

![jaas memory](./images/core/authentication-jaas-memory.png)

#### 2-3-6-1、InMemoryConfiguration
    JAAS 配置的内存表示。 构造函数接受一个 Map，其中键表示登录上下文名称的名称，值是该登录上下文名称的 AppConfigurationEntry 数组。 
    可以指定默认的 AppConfigurationEntry 数组，如果指定了未定义的登录上下文，则将返回该数组。
    
    域：

> 修饰符和类型 | 类和描述
> ---------- | -------
> private javax.security.auth.login.AppConfigurationEntry[] |	defaultConfiguration 
> private java.util.Map<java.lang.String,javax.security.auth.login.AppConfigurationEntry[]> |	mappedConfigurations 

    构造方法：

##### InMemoryConfiguration(javax.security.auth.login.AppConfigurationEntry[] defaultConfiguration)
    创建一个只有 defaultConfiguration 的新实例。 任何配置名称都将导致返回 defaultConfiguration。

> * 参数：
>   * defaultConfiguration - 对 getAppConfigurationEntry(String) 的任何调用的结果。 可以为空。
##### InMemoryConfiguration(Map<String,AppConfigurationEntry[]> mappedConfigurations)
    创建一个新实例，将登录上下文名称映射到 AppConfigurationEntrys 数组。

> * 参数：
>   * mappingConfigurations - 每个键代表一个登录上下文名称，每个值是一个应该使用的 AppConfigurationEntrys 数组。
##### InMemoryConfiguration(Map<String,AppConfigurationEntry[]> mappedConfigurations,AppConfigurationEntry[] defaultConfiguration)
    创建一个新实例，将登录上下文名称映射到 AppConfigurationEntrys 数组，以及在未找到给定登录上下文名称的映射时使用的默认配置。

> * 参数：
>   * mappingConfigurations - 每个键代表一个登录上下文名称，每个值是一个应该使用的 AppConfigurationEntrys 数组。
>   * defaultConfiguration - 对 getAppConfigurationEntry(String) 的任何调用的结果。 可以为空。

##### getAppConfigurationEntry(java.lang.String name) javax.security.auth.login.AppConfigurationEntry[]
> * 指定者：
>   * 类 javax.security.auth.login.Configuration 中的 getAppConfigurationEntry
##### refresh() void
    什么都不做，但需要 JDK5

> * 覆盖：
>   * 在类 javax.security.auth.login.Configuration 中刷新

### 2-3-7、authentication.rcp
    允许远程客户端进行身份验证并获取填充的身份验证对象。

![authentication rcp](./images/core/authentication-rcp.png)

#### 2-3-7-1、RemoteAuthenticationManager
    允许远程客户端尝试身份验证。

##### attemptAuthentication(String username, String password) Collection<? extends GrantedAuthority>
    尝试使用提供的用户名和密码对远程客户端进行身份验证。 如果身份验证成功，将返回 GrantedAuthority 对象的集合。
    为了最大限度地提高远程协议的兼容性，设计决定以最少的参数进行操作，并仅返回远程客户端启用/禁用相关用户界面命令等所需的最少量信息。没有什
    么可以阻止用户实现他们自己的等效项 适用于更复杂对象类型的包。

> * 参数：
>   * username - 远程客户端希望使用的用户名。
>   * 密码 - 远程客户端希望使用的密码。
> * 返回：
>   * 指定的用户名和密码有权访问的所有授予的权限。
> * 抛出：
>   * RemoteAuthenticationException - 如果身份验证失败。
### 2-3-8、authorization

![authorization](./images/core/authorization.png)
#### 2-3-8-1、AuthorizationManager<T>
    功能接口：
    这是一个函数式接口，因此可以用作 lambda 表达式或方法引用的赋值目标。
    
    一个授权管理器，可以确定身份验证是否可以访问特定对象。

##### verify(java.util.function.Supplier<Authentication> authentication, T object) void
    确定是否应为特定身份验证和对象授予访问权限。

> * 参数：
>   * authentication - 要检查的身份验证的供应商
>   * object - 要检查的 T 对象
> * 抛出：
>   * AccessDeniedException - 如果未授予访问权限
##### check(java.util.function.Supplier<Authentication> authentication, T object) AuthorizationDecision
    确定是否为特定身份验证和对象授予访问权限。

> * 参数：
>   * authentication - 要检查的身份验证的供应商
>   * object - 要检查的 T 对象
> * 返回：
>   * AuthorizationDecision 或 null 如果无法做出决定

#### 2-3-8-2、ReactiveAuthorizationManager<T>
    一个反应式授权管理器，可以确定身份验证是否可以访问特定对象。

##### check(reactor.core.publisher.Mono<Authentication> authentication, T object) Mono<AuthorizationDecision>
    确定是否为特定身份验证和对象授予访问权限。

> * 参数：
>   * authentication - 要检查的身份验证
>   * object - 要检查的对象
> * 返回：
>   * 如果无法做出决定，则为决定或空 Mono。
##### verify(reactor.core.publisher.Mono<Authentication> authentication, T object) Mono<Void>
    确定是否应授予特定身份验证和对象的访问权限

> * 参数：
>   * authentication - 要检查的身份验证
>   * object - 要检查的对象
> * 返回：
>   * 如果授权被授予，则为空 Mono；如果访问被拒绝，则为 Mono 错误

### 2-3-9、authorization.method
![method](./images/core/authorization-method.png)

#### 2-3-9-1、AbstractAuthorizationManagerRegistry
    仅供内部使用，因为此合同可能会更改
    
    域：
    
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.util.Map<org.springframework.core.MethodClassKey,AuthorizationManager<MethodInvocation>> |	cachedManagers 
> (package private) static AuthorizationManager<org.aopalliance.intercept.MethodInvocation> |	NULL_MANAGER 

##### getManager(MethodInvocation methodInvocation) AuthorizationManager<MethodInvocation>
    返回 MethodInvocation 的 AuthorizationManager。

> * 参数：
>   * methodInvocation - 要使用的 MethodInvocation
> * 返回：
>   * 要使用的 AuthorizationManager
##### resolveManager(java.lang.reflect.Method method, java.lang.Class<?> targetClass)
    子类应该实现这个方法来为方法和目标类提供非空的 AuthorizationManager。

> * 参数：
>   * 方法 - 方法
>   * targetClass - 目标类
> * 返回：
>   * 非空 AuthorizationManager

#### 2-3-9-2、AbstractExpressionAttributeRegistry<T extends ExpressionAttribute>
    仅供内部使用，因为此合同可能会更改

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.util.Map<org.springframework.core.MethodClassKey,T> |	cachedAttributes 

##### getAttribute(java.lang.reflect.Method method, java.lang.Class<?> targetClass) T
    返回方法和目标类的 ExpressionAttribute。

> * 参数：
>   * 方法 - 方法
>   * targetClass - 目标类
> * 返回：
>   * 要使用的 ExpressionAttribute

##### getAttribute(MethodInvocation mi) T
    返回 MethodInvocation 的 ExpressionAttribute。

> * 参数：
>   * mi - 要使用的 MethodInvocation
> * 返回：
>   * 要使用的 ExpressionAttribute

##### resolveAttribute(java.lang.reflect.Method method, java.lang.Class<?> targetClass) T
    子类应该实现这个方法来为方法和目标类提供非空的 ExpressionAttribute。

> * 参数：
>   * 方法 - 方法
>   * targetClass - 目标类
> * 返回：
>   * 非空的 ExpressionAttribute

## 2-4、并发（concurrent）
### 2-4-1、concurrent
![concurrent](./images/core/concurrent.png)

#### 2-4-1-1、AbstractDelegatingSecurityContextSupport
    使用 DelegatingSecurityContextCallable 包装 Callable 和使用 DelegatingSecurityContextRunnable 包装 Runnable 的内部支持类

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private SecurityContext |	securityContext 

    构造方法：

##### AbstractDelegatingSecurityContextSupport(SecurityContext securityContext)
    创建一个使用指定 SecurityContext 的新 AbstractDelegatingSecurityContextSupport。

> * 参数：
>   * securityContext - 用于每个 DelegatingSecurityContextRunnable 和每个 DelegatingSecurityContextCallable 的 SecurityContext 
>或 null 以默认为当前 SecurityContext。

##### wrap(java.util.concurrent.Callable<T> delegate) <T> java.util.concurrent.Callable<T>
##### wrap(java.lang.Runnable delegate)  java.lang.Runnable

## 2-5、会话（context）
### 2-5-1、context
![context](./images/core/context.png)

#### 2-5-1-1、DelegatingApplicationListener
    用于委托给多个 SmartApplicationListener 实例。 当需要以编程方式向 ApplicationContext 注册 SmartApplicationListener 时，这很
    有用。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private List<org.springframework.context.event.SmartApplicationListener> |	listeners 

##### addListener(org.springframework.context.event.SmartApplicationListener smartApplicationListener) void
    添加要使用的新 SmartApplicationListener。

> * 参数：
>   * smartApplicationListener - 要使用的 SmartApplicationListener。 不能为空。
##### onApplicationEvent(org.springframework.context.ApplicationEvent event) void
> * 指定者：
>   * 接口 org.springframework.context.ApplicationListener<org.springframework.context.ApplicationEvent> 中的 onApplicationEvent

## 2-6、转化器（converter）
### 2-6-1、converter
#### 2-6-1-1、RsaKeyConverters
    用于创建 Key 转换器实例

    嵌套类：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static class | 	RsaKeyConverters.X509CertificateDecoder 
> private static class | 	RsaKeyConverters.X509PemDecoder 
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static String |	DASHES 
> private static String |	PKCS8_PEM_FOOTER 
> private static String |	PKCS8_PEM_HEADER 
> private static String |	X509_CERT_FOOTER 
> private static String |	X509_CERT_HEADER 
> private static String |	X509_PEM_FOOTER 
> private static String |	X509_PEM_HEADER 

##### isNotPkcs8Wrapper(java.lang.String line) boolean
##### pkcs8() org.springframework.core.convert.converter.Converter<java.io.InputStream,java.security.interfaces.RSAPrivateKey>	
    构造一个转换器，用于将 PEM 编码的 PKCS#8 RSA 私钥转换为 RSAPrivateKey。 请注意，密钥通常在 PKCS#1 中格式化，这可以通过标头轻松识
    别。 如果密钥文件以“-----BEGIN RSA PRIVATE KEY-----”开头，则它是PKCS#1。 如果它是 PKCS#8 格式，那么它以“-----BEGIN PRIVATE KEY-----”
    开头。 该转换器不会关闭 InputStream 以避免对流的来源和进一步使用做出不可移植的假设。

> * 返回：
>   * 可以读取 PEM 编码的 PKCS#8 RSA 私钥并返回 RSAPrivateKey 的转换器。
##### readAllLines(java.io.InputStream source) List<String>
##### rsaFactory()  java.security.KeyFactory
##### x509() org.springframework.core.convert.converter.Converter<java.io.InputStream,java.security.interfaces.RSAPublicKey>
    构造一个转换器，用于将 PEM 编码的 X.509 RSA 公钥或 X.509 证书转换为 RSAPublicKey。 该转换器不会关闭 InputStream 以避免对流的来
    源和进一步使用做出不可移植的假设。

> * 返回：
>   * 可以读取 PEM 编码的 X.509 RSA 公钥并返回 RSAPublicKey 的转换器。
##### x509CertificateFactory() java.security.cert.CertificateFactory

## 2-7、内核（core）
### 2-7-1、core
    与用户认证和授权相关的核心类和接口，以及安全上下文的维护。
    
![core](./images/core/core.png)

#### 2-7-1-1、AuthenticatedPrincipal
    一旦 AuthenticationManager.authenticate(Authentication) 方法成功验证了身份验证请求，则表示经过身份验证的主体。 实现者通常提供
    他们自己的 Principal 表示，其中通常包含描述 Principal 实体的信息，例如名字/中间名/姓氏、地址、电子邮件、电话、ID 等。此接口允许实
    现者公开其自定义的特定属性 以通用方式表示 Principal。

##### getName() String
    返回经过身份验证的 Principal 的名称。 从不为空。

> * 返回：
>   * 经过身份验证的 Principal 的名称
#### 2-7-1-2、Authentication
    一旦请求已被 AuthenticationManager.authenticate(Authentication) 方法处理，表示身份验证请求或经过身份验证的主体的令牌。
    一旦请求通过身份验证，身份验证通常将存储在由正在使用的身份验证机制的 SecurityContextHolder 管理的线程本地 SecurityContext 中。通
    过创建 Authentication 实例并使用以下代码，可以在不使用 Spring Security 的身份验证机制之一的情况下实现显式身份验证：

```xml
     SecurityContext context = SecurityContextHolder.createEmptyContext();
     context.setAuthentication(anAuthentication);
     SecurityContextHolder.setContext(context);
```
     
    请注意，除非 Authentication 将身份验证属性设置为 true，否则任何遇到它的安全拦截器（用于方法或 Web 调用）仍将对其进行身份验证。
    在大多数情况下，框架会透明地为您管理安全上下文和身份验证对象。

##### getAuthorities() Collection<? extends GrantedAuthority>
    由 AuthenticationManager 设置以指示已授予主体的权限。 请注意，类不应依赖此值作为有效值，除非它已由受信任的 AuthenticationManager 
    设置。
    实现应确保对返回的集合数组的修改不会影响 Authentication 对象的状态，或使用不可修改的实例。

> * 返回：
>   * 授予主体的权限，或者如果令牌尚未经过身份验证，则为空集合。 从不为空。
##### getCredentials() Object
    证明主体正确的凭据。 这通常是一个密码，但可以是与 AuthenticationManager 相关的任何内容。 呼叫者应填充凭据。

> * 返回：
>   * 证明委托人身份的凭证
##### getDetails() Object
    存储有关身份验证请求的其他详细信息。 这些可能是 IP 地址、证书序列号等。

> * 返回：
>   * 有关身份验证请求的其他详细信息，如果未使用，则为 null
##### getPrincipal() Object
    被验证主体的身份。 在带有用户名和密码的身份验证请求的情况下，这将是用户名。 调用者应为身份验证请求填充主体。
    AuthenticationManager 实现通常会返回一个包含更丰富信息的身份验证作为应用程序使用的主体。 许多身份验证提供程序将创建一个 UserDetails 
    对象作为主体。

> * 返回：
>   * 被认证的主体或认证后的被认证主体。
##### isAuthenticated() boolean
    用于指示 AbstractSecurityInterceptor 是否应向 AuthenticationManager 提供身份验证令牌。 通常，AuthenticationManager（或更常
    见的是，它的 AuthenticationProviders 之一）将在成功认证后返回一个不可变的认证令牌，在这种情况下，该令牌可以安全地返回 true 给此方
    法。 返回 true 将提高性能，因为不再需要为每个请求调用 AuthenticationManager。
    出于安全原因，这个接口的实现应该非常小心地从这个方法返回 true，除非它们是不可变的，或者有某种方法来确保自最初创建以来属性没有被更改。

> * 返回：
>   * 如果令牌已通过身份验证并且 AbstractSecurityInterceptor 不需要再次将令牌呈现给 AuthenticationManager 以进行重新身份验证，则为
> true。
##### setAuthenticated(boolean isAuthenticated) void
    有关完整说明，请参阅 isAuthenticated()。
    实现应始终允许使用 false 参数调用此方法，因为各种类使用它来指定不应信任的身份验证令牌。 如果实现希望拒绝带有 true 参数的调用（这表明
    身份验证令牌是可信的 - 潜在的安全风险），则实现应该抛出 IllegalArgumentException。

> * 参数：
>   * isAuthenticated - 如果应该信任令牌（这可能会导致异常），则为 true，如果不应信任令牌，则为 false
> * 抛出：
>   * java.lang.IllegalArgumentException - 如果由于实现是不可变的或实现了它自己的 isAuthenticated() 替代方法，尝试使身份验证令牌
>受信任（通过将 true 作为参数传递）被拒绝
#### 2-7-1-3、ComparableVersion.Item
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> static int |	BIGINTEGER_ITEM 
> static int |	INT_ITEM 
> static int |	LIST_ITEM 
> static int |	LONG_ITEM 
> static int |	STRING_ITEM 
#### 2-7-1-4、CredentialsContainer
    表示实现对象包含敏感数据，可以使用eraseCredentials方法擦除。 实现应该调用任何内部对象上的方法，这些对象也可以实现这个接口。
    仅供内部框架使用。 正在编写自己的 AuthenticationProvider 实现的用户应该在那里创建并返回一个适当的 Authentication 对象，减去任何
    敏感数据，而不是使用这个接口。

##### eraseCredentials() void
#### 2-7-1-5、GrantedAuthority
    表示授予身份验证对象的权限。
    GrantedAuthority 必须将其自身表示为 String 或由 AccessDecisionManager 特别支持。
##### getAuthority() String
    如果 GrantedAuthority 可以表示为一个字符串，并且该字符串的精度足以依赖于 AccessDecisionManager（或委托）的访问控制决策，则此方法
    应返回这样一个字符串。
    如果 GrantedAuthority 不能以足够的精度表示为 String，则应返回 null。 返回 null 将需要 AccessDecisionManager（或委托）来专门支
    持 GrantedAuthority 实现，因此除非实际需要，否则应避免返回 null。

> * 返回：
>   * 授予权限的表示（如果授予的权限不能表示为具有足够精度的字符串，则为 null）。
#### 2-7-1-6、Transient
    永远不应跨请求存储的身份验证标记，例如不记名令牌身份验证
#### 2-7-1-7、ComparableVersion
    implements java.lang.Comparable<ComparableVersion>
    
    版本比较的通用实现。
    
    注意：这是来自 https://github.com/apache/maven/blob/maven-3.6.3/maven-artifact/src/main/java/org/apache/maven/artifact/versioning/ComparableVersion.java 的副本 特征：
    '-'（连字符）和 '.' 的混合 （点）分隔符，
    字符和数字之间的过渡也构成了分隔符：1.0alpha1 => [1, 0, alpha, 1]
    无限数量的版本组件，
    文本中的版本组件可以是数字或字符串，
    检查字符串是否有众所周知的限定符，并且限定符排序用于版本排序。 众所周知的限定符（不区分大小写）是：
    阿尔法或
    β 或 b
    里程碑或 m
    rc 或 cr
    快照
    （空字符串）或 ga 或 final
    sp
    在已知限定符之后考虑未知限定符，具有词汇顺序（始终不区分大小写），
    连字符通常位于限定符之前，并且总是不如以点开头的东西重要。
#### 2-7-1-8、SpringSecurityMessageSource
    All Implemented Interfaces:
    org.springframework.beans.factory.Aware, org.springframework.beans.factory.BeanClassLoaderAware, 
    org.springframework.context.HierarchicalMessageSource, org.springframework.context.MessageSource
    
    Spring Security 使用的默认 MessageSource。
    默认情况下，所有需要消息本地化的 Spring Security 类都将使用此类。 但是，所有此类类还将实现 MessageSourceAware，以便应用程序上下文
    可以注入替代消息源。 因此，此类仅在部署环境未指定替代消息源时使用。

![relation](./images/core/core-ssms.png)
### 2-7-2、core.annotation
![core annotation](./images/core/core-annotation.png)
#### 2-7-2-1、AuthenticationPrincipal
    用于将 Authentication.getPrincipal() 解析为方法参数的注释。
    
##### errorOnInvalidType boolean
    如果当前 Authentication.getPrincipal() 的类型不正确时应抛出 ClassCastException，则为 True。 默认为假。
> * 返回：
> * 默认：
>   * 错误的
##### expression String
    如果指定，将使用提供的 SpEL 表达式来解析主体。 如果用户需要转换结果，这很方便。
    例如，也许用户想要解析一个最终的并利用 UserDetailsService 的 CustomUser 对象。 这可以通过返回一个如下所示的对象来处理：
    
      public class CustomUserUserDetails extends User {
          // ...
          public CustomUser getCustomUser() {
              return customUser；
          }
      }
     
    然后用户可以指定一个注释，如下所示：
      @AuthenticationPrincipal(expression = "customUser")
 
返回：
要使用的表达式。
默认：
“”
#### 2-7-2-2、CurrentSecurityContext
    用于将 SecurityContext 解析为方法参数的注释。

##### errorOnInvalidType boolean
    如果当前 SecurityContext 的类型不正确时应抛出 ClassCastException，则为 True。 默认为假。
> * 返回：
>   * 是否在无效类型上出错
> * 默认：
>   * 错误的
##### expression String
    如果指定，将使用提供的 SpEL 表达式来解析安全上下文。 如果应用程序需要转换结果，这很方便。 例如，如果应用程序需要提取其自定义身份验证实现，则它可以指定适当的 SpEL，如下所示：
    @CurrentSecurityContext(expression = "authentication") CustomAuthentication authentication
 
> * 返回：
>   * 要使用的表达式
> * 默认：
>   * “”
    
### 2-7-3、core.authority
    GrantedAuthority 接口的默认实现。
![authority](./images/core/core-authority.png)

#### 2-7-3-1、GrantedAuthoritiesContainer
    extends Serializable
    
    指示对象存储 GrantedAuthority 对象。
    通常用于预认证场景，当 AuthenticationDetails 实例也可用于获取用户权限时。
    
##### getGrantedAuthorities() Collection<? extends GrantedAuthority>	
#### 2-7-3-2、AuthorityUtils
    用于操作 GrantedAuthority 集合等的实用方法。
    主要供内部使用。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> static List<GrantedAuthority> | NO_AUTHORITIES 

##### commaSeparatedStringToAuthorityList(String authorityString) List<GrantedAuthority>
    从逗号分隔的字符串表示形式（例如“ROLE_A、ROLE_B、ROLE_C”）创建一个 GrantedAuthority 对象数组。
> * 参数：
>   * authorityString - 逗号分隔的字符串
> * 返回：
>   * 通过标记字符串创建的权限
##### authorityListToSet(Collection<? extends GrantedAuthority> userAuthorities) Set<String> 
    将 GrantedAuthority 对象数组转换为 Set。
> * 返回：
>   * 从每次调用 GrantedAuthority.getAuthority() 获得的一组字符串
##### createAuthorityList(String... authorities) List<GrantedAuthority>
    将权限转换为 GrantedAuthority 对象列表。
> * 参数：
>   * 权威 - 转换的权威
> * 返回：
>   * GrantedAuthority 对象列表
#### 2-7-3-3、SimpleGrantedAuthority
    implements GrantedAuthority
    
    GrantedAuthority 的基本具体实现。
    存储授予身份验证对象的权限的字符串表示形式。
##### getAuthority() String
    从接口复制的描述：GrantedAuthority
    如果 GrantedAuthority 可以表示为一个字符串，并且该字符串的精度足以依赖于 AccessDecisionManager（或委托）的访问控制决策，则此方法
    应返回这样一个字符串。
    如果 GrantedAuthority 不能以足够的精度表示为 String，则应返回 null。 返回 null 将需要 AccessDecisionManager（或委托）来专门支
    持 GrantedAuthority 实现，因此除非实际需要，否则应避免返回 null。
    
> * 指定者：
>   * 接口 GrantedAuthority 中的 getAuthority
> * 返回：
>   * 被授予的权限的表示（如果被授予的权限不能表示为具有足够精度的字符串，则为 null）。
### 2-7-4、core.authority.mapping
    将属性列表（例如角色或 LDAP 组）映射到 GrantedAuthority 列表的策略。
    在安全数据存储库和应用程序内所需的逻辑权限之间提供一个间接层。

![core authority mapping](./images/core/core-authority-mapping.png)

#### 2-7-4-1、Attributes2GrantedAuthoritiesMapper
    由可以将安全属性列表（例如角色或组名称）映射到 Spring Security GrantedAuthority 集合的类实现的接口。
    
##### getGrantedAuthorities(Collection<String> attributes) Collection<? extends GrantedAuthority>	
    此方法的实现应该将给定的属性集合映射到 Spring Security GrantedAuthorities 的集合。 映射过程没有限制； 单个属性可以映射到多个 
    Spring Security GrantedAuthority，所有属性都可以映射到单个 Spring Security GrantedAuthority，某些属性可能无法映射等。

> * 参数：
>   * 属性 - 要映射的属性
> * 返回：
>   * 从属性创建的权限集合
#### 2-7-4-2、GrantedAuthoritiesMapper
    映射接口，可以注入认证层，将存储中加载的权限转换为将在 Authentication 对象中使用的权限。
    
##### mapAuthorities(Collection<? extends GrantedAuthority> authorities) Collection<? extends GrantedAuthority>

#### 2-7-4-3、MappableAttributesRetriever
    由可以检索可映射安全属性字符串列表的类实现的接口（例如，Web 或 EJB 应用程序中所有可用 J2EE 角色的列表）。
    
##### getMappableAttributes() Set<String>
    此方法的实现应返回一组可以映射到 GrantedAuthoritys 的所有字符串属性。
> * 返回：
>   * 一组所有可映射的角色

### 2-7-5、core.context
    与在请求期间（例如 HTTP 或 RMI 调用）建立安全上下文相关的类。
    在请求期间，安全上下文通常与当前执行线程相关联，从而使其包含的身份验证信息在应用程序的所有层中均可用。
    
    可以通过调用 SecurityContextHolder 随时访问 SecurityContext。
    
![core context](./images/core/core-context.png)

#### 2-7-5-1、SecurityContext
    extends Serializable
    
    定义与当前执行线程关联的最低安全信息的接口。
    安全上下文存储在 SecurityContextHolder 中。

##### getAuthentication() Authentication
    获取当前经过身份验证的主体或身份验证请求令牌。
> * 返回：
>   * 如果没有可用的身份验证信息，则为 Authentication 或 null
##### setAuthentication(Authentication authentication) void
    更改当前经过身份验证的主体，或删除身份验证信息。
> * 参数：
>   * 身份验证 - 新的身份验证令牌，如果不应存储进一步的身份验证信息，则为 null
#### 2-7-5-2、SecurityContextChangedListener
    Functional Interface
    
    SecurityContextChangedEvents 的监听器 
    
##### securityContextChanged(SecurityContextChangedEvent event)  void
#### 2-7-5-3、SecurityContextHolderStrategy
    针对线程存储安全上下文信息的策略。
    首选策略由 SecurityContextHolder 加载。

##### clearContext() void
##### createEmptyContext() SecurityContext
    创建一个新的空上下文实现，供 SecurityContextRepository 实现在第一次创建新上下文时使用。
> * 返回：
>   * 空的上下文。
##### getContext() SecurityContext
##### setContext(SecurityContext context) void

#### 2-7-5-4、SecurityContextHolder
    将给定的 SecurityContext 与当前执行线程相关联。
    此类提供了一系列委托给 SecurityContextHolderStrategy 实例的静态方法。该类的目的是提供一种方便的方法来指定应该用于给定 JVM 的策略。
    这是 JVM 范围的设置，因为此类中的所有内容都是静态的，以方便调用代码时的使用。
    
    要指定应使用哪种策略，您必须提供模式设置。模式设置是定义为静态最终字段的三个有效 MODE_ 设置之一，或者是提供公共无参数构造函数的 
    SecurityContextHolderStrategy 的具体实现的完全限定类名。
    
    有两种方法可以指定所需的策略模式字符串。第一种是通过以 SYSTEM_PROPERTY 为键的系统属性来指定它。第二种是在使用类之前调用​​ 
    setStrategyName(String) 。如果这两种方法都没有使用，该类将默认使用 MODE_THREADLOCAL，它向后兼容，具有较少的 JVM 不兼容性并且适
    用于服务器（而 MODE_GLOBAL 绝对不适合服务器使用）。

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static int |	initializeCount 
> static String |	MODE_GLOBAL 
> static String |	MODE_INHERITABLETHREADLOCAL 
> static String |	MODE_THREADLOCAL 
> private static SecurityContextHolderStrategy |	strategy 
> private static String |	strategyName 
> static String |	SYSTEM_PROPERTY 

##### initialize() void
##### clearContext() void
##### getContext() SecurityContext
##### getInitializeCount() int
    主要用于故障排除目的，此方法显示类已重新初始化其 SecurityContextHolderStrategy 的次数。
> * 返回：
>   * 计数（应该是 1，除非您已调用 setStrategyName(String) 切换到备用策略。
##### setContext(SecurityContext context) void
    将新的 SecurityContext 与当前执行线程相关联。
> * 参数：
>   * context - 新的 SecurityContext（可能不为空）
##### setStrategyName(String strategyName) void
    更改首选策略。 对于给定的 JVM，不要多次调用此方法，因为它会重新初始化策略并对使用旧策略的任何现有线程产生不利影响。
> * 参数：
>   * strategyName - 应使用的策略的完全限定类名。
##### getContextHolderStrategy() SecurityContextHolderStrategy
    允许检索上下文策略。 参见 SEC-1188。
> * 返回：
>   * 用于存储安全上下文的配置策略。
##### createEmptyContext() SecurityContext
    将创建新的空上下文委托给配置的策略。
##### addListener(SecurityContextChangedListener listener) void
    注册一个侦听器，以便在 SecurityContext 更改时收到通知。 请注意，这不会通知基础身份验证何时更改。 要获得有关身份验证更改的通知，请确
    保在更改身份验证时使用 setContext(org.springframework.security.core.context.SecurityContext) ，如下所示：
             SecurityContext context = SecurityContextHolder.createEmptyContext();
             context.setAuthentication(authentication);
             SecurityContextHolder.setContext(context);
     
    要将其与 Spring 的 ApplicationEvent 支持集成，您可以像这样添加一个侦听器：
             SecurityContextHolder.addListener(this.applicationContext::publishEvent);
 
> * 参数：
>   * listener - 当 SecurityContext 更改时要通知的侦听器

#### 2-7-5-5、ReactiveSecurityContextHolder
    允许将 Spring SecurityContext 获取和设置为 Context。

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> Class<?> | SECURITY_CONTEXT_KEY

> 以下的Context均为reactor.util.context.Context
##### getContext() Mono<SecurityContext>
##### hasSecurityContext(Context context) boolean
##### getSecurityContext(Context context) Mono<SecurityContext>
##### clearContext() Function<Context,Context>
    从 Reactor 上下文中清除 Mono<SecurityContext>
> * 返回：
>   * 返回一个 Mono<Void> ，它只从清除上下文中重放完整和错误信号。
##### withSecurityContext(Mono<? extends SecurityContext> securityContext) Context
    创建一个 Reactor Context，其中包含可以合并到另一个 Context 的 Mono<SecurityContext>
> * 参数：
>   * securityContext - 要在返回的 Reactor 上下文中设置的 Mono<SecurityContext>
> * 返回：
>   * 包含 Mono<SecurityContext> 的 Reactor Context
##### withAuthentication(Authentication authentication) Context
    withSecurityContext(Mono) 的快捷方式
> * 参数：
>   * 身份验证 - 要使用的身份验证
> * 返回：
>   * 包含 Mono<SecurityContext> 的 Reactor Context
### 2-7-6、core.parameters
![core parameters](./images/core/core-parameters.png)

#### 2-7-6-1、AnnotationParameterNameDiscoverer.ParameterNameFactory<T extends AccessibleObject>
    Functional Interface
    
    用于查找参数名称的策略接口。
##### findParameterAnnotations(T t) Annotation[][]
    获取指定索引处的注解
> * 参数：
>   * t -
>   * 指数 -
> * 返回：
#### 2-7-6-2、AnnotationParameterNameDiscoverer
    implements org.springframework.core.ParameterNameDiscoverer
    
    允许使用任意数量的 Annotation 实例的 value 属性查找参数名称。当需要使用 Spring Security 的方法级别安全性发现接口的参数名称时，这
    很有用。例如，请考虑以下情况：
     导入 org.springframework.security.access.method.P;
    
      @PostAuthorize("#to == returnObject.to")
     public Message findMessageByTo(@P("to") String to);
     
    我们可以使用以下 AnnotationParameterNameDiscoverer 实现这一点：
     ParameterAnnotationsNameDiscoverer 发现者 = 新的 ParameterAnnotationsNameDiscoverer(
                    "org.springframework.security.access.method.P");
     
    用户通常将 AnnotationParameterNameDiscoverer 与 PrioritizedParameterNameDiscoverer 结合使用。实际上，Spring Security 的 
    DefaultSecurityParameterNameDiscoverer（默认情况下与方法级安全性一起使用）扩展了PrioritizedParameterNameDiscoverer，如果在
    类路径中找到它，它将自动支持 P 和 Spring Data 的 Param 注释。
    
    需要注意的是，如果单个参数名称上有受支持的注释，则所有方法都使用 AnnotationParameterNameDiscoverer 进行解析。例如，请考虑以下情况：
    
     导入 org.springframework.security.access.method.P;
    
      @PostAuthorize("#to == returnObject.to")
     public Message findMessageByToAndFrom(@P("to") User to, User from);
     
    在上一个示例中查找参数的结果将是 new String[] { "to", null}，因为只有一个参数包含注释。这主要是因为PrioritizedParameterNameDiscoverer 
    的回退是全有或全无操作。

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> Set<String> | annotationClassesToUse 
> AnnotationParameterNameDiscoverer.ParameterNameFactory<Constructor<?>> |	CONSTRUCTOR_METHODPARAM_FACTORY 
> AnnotationParameterNameDiscoverer.ParameterNameFactory<Method> |	METHOD_METHODPARAM_FACTORY 

##### getParameterNames(Method method) String[]
##### getParameterNames(Constructor<?> constructor) String[] 
##### lookupParameterNames(AnnotationParameterNameDiscoverer.ParameterNameFactory<T> parameterNameFactory, T t) <T extends AccessibleObject> String[]
    如果未找到，则获取参数名称或 null。
> * 参数：
>   * parameterNameFactory - 要使用的 AnnotationParameterNameDiscoverer.ParameterNameFactory
>   * t - 用于查找参数名称的 AccessibleObject（即方法或构造函数）
> * 返回：
>   * 参数名称或 null
##### findParameterName(java.lang.annotation.Annotation[] parameterAnnotations) String
    从提供的 Annotations 中查找参数名称，如果找不到，则为 null。 搜索是通过查看 annotationClassesToUse 的 value 属性来完成的。
> * 参数：
>   * parameterAnnotations - 要搜索的注释。
> * 返回：
#### 2-7-6-3、DefaultSecurityParameterNameDiscoverer
    extends org.springframework.core.PrioritizedParameterNameDiscoverer

    Spring Security 的默认 ParameterNameDiscoverer 会根据在类路径上找到的内容尝试多个 ParameterNameDiscoverer。
    将使用 AnnotationParameterNameDiscoverer 的实例和 P 作为有效注释。 如果，Spring Data 在类路径上也会添加 Param 注解。
    如果 Spring 4 在类路径上，则添加 DefaultParameterNameDiscoverer。 这首先尝试使用 JDK 8 信息，然后回退到 LocalVariableTableParameterNameDiscoverer。
    如果 Spring 4 不在类路径上，则直接添加 LocalVariableTableParameterNameDiscoverer。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> String |	DATA_PARAM_CLASSNAME 
> boolean |	DATA_PARAM_PRESENT 
#### 2-7-6-4、P
    可与 AnnotationParameterNameDiscoverer 一起使用以指定参数名称的注释。 这对于不能包含参数名称的 JDK 8 之前的接口很有用。
    
##### value String

### 2-7-7、core.session
    由 org.springframework.security.core.session.SessionInformation SessionInformation 类提供的会话抽象。 SessionRegistry 
    是基于 Web 的并发会话控制的核心部分，但代码不依赖于任何 servlet API。

![core session](./images/core/core-session.png)

#### 2-7-7-1、SessionRegistry
    维护 SessionInformation 实例的注册表。

##### getAllPrincipals() List<Object>
    获取 SessionRegistry 中的所有已知主体。
> * 返回：
>   * 每个唯一的主体，然后可以将其呈现给 getAllSessions(Object, boolean)。
##### getAllSessions(Object principal, boolean includeExpiredSessions) List<SessionInformation>
    获取指定主体的所有已知会话。 已销毁的会话不会返回。 根据传递的参数，可能会返回已过期的会话。
> * 参数：
>   * 主体 - 定位会话（不应为空）
>   * includeExpiredSessions - 如果为 true，则返回的会话还将包括主体已过期的会话
> * 返回：
>   * 此主体的匹配会话（不应返回 null）。
##### getSessionInformation(String sessionId) SessionInformation
    获取指定 sessionId 的会话信息。 甚至会返回过期的会话（尽管永远不会返回已销毁的会话）。
> * 参数：
>   * sessionId - 查找（不应为空）
> * 返回：
>   * 会话信息，如果未找到，则为 null
##### refreshLastRequest(String sessionId) void
    更新给定的 sessionId，使其上次请求时间等于当前日期和时间。 如果找不到给定的 sessionId 或会话被标记为过期，则静默返回。
> * 参数：
>   * sessionId - 更新上次请求的日期和时间（不应为空）
##### registerNewSession(String sessionId, Object principal) void
    为指定的主体注册新会话。 新注册的会话不会被标记为过期。
> * 参数：
>   * sessionId - 与主体关联（不应为 null ）
>   * 主体 - 与会话相关联（不应为空）
##### removeSessionInformation(java.lang.String sessionId) void
    删除为指定 sessionId 维护的所有会话信息。 如果找不到 sessionId，该方法会正常返回。
> * 参数：
>   * sessionId - 删除信息（不应为空）
#### 2-7-7-2、AbstractSessionEvent
    extends org.springframework.context.ApplicationEvent
    implements Serializable
    
    所有会话相关事件的抽象超类。
    
#### 2-7-7-3、SessionCreationEvent
    extends AbstractSessionEvent
    
    指示会话（可能由安全上下文表示）已开始的通用会话创建事件。

#### 2-7-7-4、SessionDestroyedEvent
    extends AbstractSessionEvent
    
    指示会话（可能由安全上下文表示）已结束的通用“会话终止”事件。

##### getId() String
> * 返回：
>   * 与销毁会话关联的标识符。
##### getSecurityContexts() List<SecurityContext>
    提供与销毁会话关联的 SecurityContext 实例。 通常每个会话只有一个安全上下文。
> * 返回：
>   * 存储在当前会话中的 SecurityContext 实例（如果没有，则为空列表）。
#### 2-7-7-5、SessionIdChangedEvent
    extends AbstractSessionEvent

    指示会话标识符（可能由安全上下文表示）已更改的通用“会话 ID 更改”事件。

##### getNewSessionId() String
##### getOldSessionId() String
#### 2-7-7-6、SessionInformation
    implements java.io.Serializable
    
    表示 Spring Security 框架内的会话记录。
    这主要用于并发会话支持。
    
    会话具有三种状态：活动、过期和销毁。 通过 session.invalidate() 或通过 Servlet 容器管理无效的会话可以被视为“已销毁”。 另一方面，
    “过期”会话是 Spring Security 想要结束的会话，因为它出于某种原因被选择删除（通常是因为它是最近最少使用的会话并且达到了用户的最大会话
    数）。 过滤器会尽快删除“过期”会话。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private boolean |	expired 
> private java.util.Date |	lastRequest 
> private java.lang.Object |	principal 
> private static long |	serialVersionUID 
> private java.lang.String |	sessionId 
##### expireNow() void
##### getLastRequest() Date
##### getPrincipal() Object
##### getSessionId() String
##### isExpired boolean
##### refreshLastRequest() void
    将内部 lastRequest 刷新为当前日期和时间。
#### 2-7-7-7、SessionRegistryImpl
    implements SessionRegistry, org.springframework.context.ApplicationListener<AbstractSessionEvent>

    SessionRegistry 的默认实现，它侦听在 Spring 应用程序上下文中发布的 SessionDestroyedEvents。
    要使此类在 Web 应用程序中正常运行，请务必在 web.xml 文件中注册 HttpSessionEventPublisher，以便在会话过期时通知此类。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> protected org.apache.commons.logging.Log |	logger 
> private java.util.concurrent.ConcurrentMap<java.lang.Object,java.util.Set<java.lang.String>> |	principals 
> private java.util.Map<java.lang.String,SessionInformation> |	sessionIds 

### 2-7-8、core.token
    用于构建安全随机令牌的服务。

![core token](./images/core/core-token.png)

#### 2-7-8-1、Token
    TokenService 发行的令牌。
    分配给令牌的密钥必须充分随机且安全，以便它们可以用作识别唯一的用户会话，这一点很重要。 TokenService 的实现可以自由使用他们选择的加密
    或编码策略。 强烈建议密钥足够长，以平衡安全性和持久性成本。 关于持久性成本，强烈建议返回的密钥足够小以在 cookie 中进行编码。
    
##### getKey() String
    获取分配给此令牌的随机安全密钥。 将此令牌呈现给 TokenService 将始终返回一个令牌，该令牌等于为该密钥颁发的原始令牌。
> * 返回：
>   * 具有适当随机性和安全性的密钥。
##### getKeyCreationTime() long
    最初创建令牌密钥的时间可从此方法获得。 请注意，给定的令牌绝不能更改此创建时间。 如有必要，可以从 TokenService 请求新令牌来替换原始令牌。
> * 返回：
>   * 创建此令牌密钥的时间，格式与 Date.getTime() 指定的格式相同。
##### getExtendedInformation() String
    获取令牌内关联的扩展信息，该信息在首次创建令牌时显示。
> * 返回：
>   * 用户指定的扩展信息，如果有的话
#### 2-7-8-2、TokenService
    提供一种机制来分配和重建安全的随机令牌。
    实现只关心按需发行新令牌。 发行的令牌可能包含用户指定的扩展信息。 该令牌还包含一个加密强的、基于字节数组的密钥。 如果需要，这允许使用
    令牌来识别用户会话。 随后可以将密钥重新提交给 TokenService 以验证和重建与原始 Token 相等的 Token。
    
    鉴于此接口提供的密切关注的行为，它可以作为更复杂的基于令牌的解决方案的构建块。 例如，依赖于无状态会话密钥的身份验证系统。 例如，这些可
    以将用户名放在与密钥关联的用户指定的扩展信息中）。 重要的是要认识到我们不打算扩展此接口以直接提供此类功能。
##### allocateToken(String extendedInformation) Token
    强制分配新令牌。
> * 参数：
>   * extendedInformation - 令牌中所需的扩展信息（不能为null，但可以为empty）
> * 返回：
>   * 以前未发行过的新令牌，并且保证在未来的任何时间都能被此实现的 verifyToken(String) 识别。
##### verifyToken(String key) Token
    允许验证此 TokenService 发出的 Token.getKey() 并重建相应的 Token。
> * 参数：
>   * 密钥 - 从 Token.getKey() 获得并由此实现创建
> * 返回：
>   * 令牌，如果令牌不是由此 TokenService 发行的，则为 null
#### 2-7-8-3、DefaultToken
    Token的默认实现
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private String |	extendedInformation 
> private String |	key 
> private long |	keyCreationTime 

#### 2-7-8-4、KeyBasedPersistenceTokenService
    implements TokenService, org.springframework.beans.factory.InitializingBean
    
    TokenService 的基本实现，兼容集群，跨机器重启，不需要数据库持久化。
    密钥以以下格式生成：
    
    Base64(creationTime + ":" + hex(pseudoRandomNumber) + ":" + extendedInformation + ":" + Sha512Hex(creationTime + ":" 
    + hex(pseudoRandomNumber) + ":" + extendedInformation + ":" + serverSecret) )
    
    上面的creationTime、tokenKey和extendedInformation与Token中存储的相同。 Sha512Hex 包含相同的有效负载，以及一个 serverSecret。
    
    serverSecret 每毫秒都在变化。它依赖于两个静态服务器端机密。第一个是密码，第二个是服务器整数。对于随后被识别的任何已发布密钥，这两者都
    必须保持不变。任何毫秒内适用的 serverSecret 由密码 + ":" + (creationTime % serverInteger) 计算。这种方法进一步混淆了实际的服务
    器秘密，并使计算服务器秘密的尝试在实用性上更加有限（因为任何虚假令牌都将被迫具有等于计算出的哈希值的创建时间）。回想一下，依赖于令牌服
    务的框架功能在任何情况下都应该拒绝相对较旧的令牌。
    
    对此类的进一步考虑是对加密强伪随机数的要求。为此，推荐使用 SecureRandomFactoryBean 注入属性。
    
    此实现在内部使用 UTF-8 编码进行字符串操作。
#### 2-7-8-5、SecureRandomFactoryBean
    implements org.springframework.beans.factory.FactoryBean<java.security.SecureRandom>
    
    创建一个 SecureRandom 实例。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.lang.String |	algorithm 
> private org.springframework.core.io.Resource |	seed 
##### getObject() java.security.SecureRandom
> * 指定者：
>   * 接口 org.springframework.beans.factory.FactoryBean<java.security.SecureRandom> 中的 getObject
> * 抛出：
>   * java.lang.Exception
##### getObjectType() Class<java.security.SecureRandom>
##### isSingleton() boolean
##### setAlgorithm(String algorithm) void
    允许指定伪随机数生成器 (PRNG) 算法。 默认为“SHA1PRNG”。
> * 参数：
>   * 算法 - 使用（强制）
##### setSeed(org.springframework.core.io.Resource seed) void
    允许用户指定将充当 SecureRandom 实例的种子的资源。 具体来说，资源将被读入 InputStream 并将这些字节呈现给 SecureRandom.setSeed(byte[]) 
    方法。 请注意，这将只是补充而不是替换现有种子。 因此，使用这种方法设置种子总是安全的（它永远不会降低随机性）。
> * 参数：
>   * 种子 - 使用，如果不需要额外的种子，则为 null

#### 2-7-8-6、Sha512DigestUtils
    提供 SHA512 摘要方法。
    基于 Commons Codec，目前不提供 SHA512 支持。
##### getSha512Digest() java.security.MessageDigest
    返回一个 SHA 摘要。
> * 返回：
>   * 一个 SHA 摘要实例。
> * 抛出：
>   * java.lang.RuntimeException - 捕获到 NoSuchAlgorithmException 时。
##### sha(byte[] data) byte[]
    计算 SHA 摘要并以 byte[] 形式返回值。
> * 参数：
>   * data - 要消化的数据
> * 返回：
>   * SHA摘要
##### sha(String data) byte[]
##### shaHex(byte[] data) String
##### shaHex(String data) String

### 2-7-9、core.userdetails
    用于实现用户数据 DAO 的标准接口。
    可以是传统的 UserDetailsService，它使用唯一的用户名来标识用户，或者，对于更复杂的要求，AuthenticationUserDetailsService。

![core userdetails](./images/core/core-userdetails.png)
#### 2-7-9-1、AuthenticationUserDetailsService
    允许基于 Authentication 对象检索 UserDetails 对象的接口。

##### loadUserDetails(T token) UserDetails
> * 参数：
>   * token - 预先认证的认证令牌
> * 返回：
>   * 给定身份验证令牌的 UserDetails，永远不会为空。
> * 抛出：
>   * UsernameNotFoundException - 如果无法找到给定身份验证令牌的用户详细信息   
    
#### 2-7-9-2、ReactiveUserDetailsPasswordService
    用于更改 UserDetails 密码的 API。
##### updatePassword(UserDetails user, String newPassword) Mono<UserDetails>
    修改指定用户的密码。 这应该会更改持久用户存储库（数据库、LDAP 等）中的用户密码。
> * 参数：
>   * user - 修改密码的用户
>   * newPassword - 要更改的密码
> * 返回：
>   * 使用新密码更新的 UserDetails
#### 2-7-9-3、ReactiveUserDetailsService
    用于按用户名查找 UserDetails 的 API。
##### findByUsername(java.lang.String username) Mono<UserDetails>
    按用户名查找 UserDetails。
> * 参数：
>   * username - 要查找的用户名
> * 返回：
>   * 用户详细信息。 不能为空
#### 2-7-9-4、UserCache
    提供 UserDetails 对象的缓存。
    实现应提供适当的方法来设置其缓存参数（例如生存时间）和/或在实体正常到期之前强制删除实体。 这些不是 UserCache 接口合同的一部分，因为它
    们因使用的缓存系统类型（内存、磁盘、集群、混合等）而异。
    
    缓存通常只在不维护服务器端状态的应用程序中需要，例如远程客户端或 Web 服务。 然后在每次调用时都会提供身份验证凭据，访问数据库或其他持久
    存储机制进行验证的开销会很大。 在这种情况下，您将配置一个缓存来存储 UserDetails 信息，而不是每次都加载它。
    
    也可以看看：
    AbstractUserDetailsAuthenticationProvider
##### getUserFromCache(String username) UserDetails
    从缓存中获取 UserDetails。
> * 参数：
>   * username - 用于将用户放入缓存的 User.getUsername()
> * 返回：
>   * 如果找不到用户或缓存条目已过期，则填充的 UserDetails 或 null
##### putUserInCache(UserDetails user) void
    在缓存中放置一个 UserDetails。 用户名是用于随后检索 UserDetails 的密钥。
> * 参数：
>   * user - 要放置在缓存中的完全填充的 UserDetails
##### removeUserFromCache(String username) void
    从缓存中删除指定的用户。 用户名是用于删除用户的密钥。 如果没有找到用户，该方法应该简单地返回（不抛出异常）。
    某些缓存实现可能不支持从缓存中逐出，在这种情况下，它们应该提供适当的行为以在其文档中、通过异常或通过日志消息更改用户。
    
> * 参数：
>   * 用户名 - 从缓存中驱逐
#### 2-7-9-5、UserDetails
    extends java.io.Serializable
    
    提供核心用户信息。
    出于安全目的，Spring Security 不直接使用实现。 它们只是存储用户信息，然后将这些信息封装到 Authentication 对象中。 这允许将非安全
    相关的用户信息（例如电子邮件地址、电话号码等）存储在方便的位置。
    
    具体的实现必须特别小心，以确保为每个方法详细说明的非空契约得到执行。 请参阅 User 以获取参考实现（您可能希望在代码中扩展或使用它）。
    
##### getAuthorities() Collection<? extends GrantedAuthority>
    返回授予用户的权限。 不能返回空值。
> * 返回：
>   * 权限，按自然键排序（从不为空）
##### getPassword() String
    返回用于验证用户的密码。
> * 返回：
>   * 密码
##### getUsername() String
    返回用于验证用户的用户名。 不能返回空值。
> * 返回：
>   * 用户名（从不为空）
##### isAccountNonExpired() boolean
    指示用户的帐户是否已过期。 无法验证过期帐户。
> * 返回：
>   * 如果用户的帐户有效（即未过期），则为 true，如果不再有效（即已过期），则为 false
##### isAccountNonLocked() boolean
    指示用户是被锁定还是未锁定。 无法对锁定的用户进行身份验证。
> * 返回：
>   * 如果用户没有被锁定，则为 true，否则为 false
##### isCredentialsNonExpired() boolean
    指示用户的凭据（密码）是否已过期。 过期的凭据会阻止身份验证。
> * 返回：
>   * 如果用户的凭据有效（即未过期），则为 true，如果不再有效（即已过期），则为 false
##### isEnabled() boolean
    指示用户是启用还是禁用。 无法对禁用的用户进行身份验证。
> * 返回：
>   * 如果用户已启用，则为 true，否则为 false

#### 2-7-9-6、UserDetailsChecker
    由使用 UserDetailsService 检查加载的 UserDetails 对象状态的类调用。 通常，这将涉及检查与帐户关联的各种标志，并在无法使用信息时引
    发异常（例如，如果用户帐户被锁定或禁用），但自定义实现可以执行它希望的任何检查。
    目的是该接口应该仅用于检查与用户关联的持久数据。 它不应参与根据提交的身份验证请求做出任何身份验证决定。
##### check(UserDetails toCheck) void
    检查用户
> * 参数：
>   * toCheck - 应检查其状态的 UserDetails 实例。
#### 2-7-9-7、UserDetailPasswordService
    用于更改 UserDetails 密码的 API。
##### updatePassword(UserDetails user, java.lang.String newPassword) UserDetails
    修改指定用户的密码。 这应该会更改持久用户存储库（数据库、LDAP 等）中的用户密码。
> * 参数：
>   * user - 修改密码的用户
>   * newPassword - 要更改的密码，由配置的 PasswordEncoder 编码
> * 返回：
>   * 使用新密码更新的 UserDetails
#### 2-7-9-8、UserDetailService
    加载用户特定数据的核心接口。
    它在整个框架中用作用户 DAO，并且是 DaoAuthenticationProvider 使用的策略。
    
    该接口只需要一种只读方法，这简化了对新数据访问策略的支持。
##### loadUserByUsername(java.lang.String username) UserDetails
    根据用户名定位用户。 在实际实现中，搜索可能区分大小写，也可能不区分大小写，具体取决于实现实例的配置方式。 在这种情况下，返回的 
    UserDetails 对象的用户名可能与实际请求的用户名不同。
> * 参数：
>   * username - 标识需要其数据的用户的用户名。
> * 返回：
>   * 完全填充的用户记录（从不为空）
> * 抛出：
>   * UsernameNotFoundException - 如果找不到用户或用户没有 GrantedAuthority
#### 2-7-9-9、MapReactiveUserDetailsService
    implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService
    
    基于 Map 的 ReactiveUserDetailsService 实现
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> Map<java.lang.String,UserDetails> |	users 

##### findByUsername(String username) Mono<UserDetails>
##### updatePassword(UserDetails user, java.lang.String newPassword) Mono<UserDetails>
##### withNewPassword(UserDetails userDetails, java.lang.String newPassword) UserDetails
##### getKey(java.lang.String username) String
#### 2-7-9-10、User
    implements UserDetails, CredentialsContainer
    
    对 UserDetailsService 检索的核心用户信息进行建模。
    开发人员可以直接使用这个类、子类化它，或者从头开始编写他们自己的 UserDetails 实现。
    
    equals 和 hashcode 实现仅基于 username 属性，因为目的是查找同一用户主体对象（例如，在用户注册表中）将匹配对象代表同一用户的位置，
    而不仅仅是所有属性（ 权限，例如密码）相同。
    
    请注意，此实现不是一成不变的。 它实现了 CredentialsContainer 接口，以允许在身份验证后删除密码。 如果您将实例存储在内存中并重用它们，
    这可能会导致副作用。 如果是这样，请确保每次调用时都从 UserDetailsService 返回一个副本。
    
    使用建造者模式

    嵌套类
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static class | 	User.AuthorityComparator 
> static class | 	User.UserBuilder (Builds the user to be added.)

    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private boolean |	accountNonExpired 
> private boolean |	accountNonLocked 
> private java.util.Set<GrantedAuthority> |	authorities 
> private boolean |	credentialsNonExpired 
> private boolean |	enabled 
> private static org.apache.commons.logging.Log |	logger 
> private java.lang.String |	password 
> private static long |	serialVersionUID 
> private java.lang.String |	username 


### 2-7-10、core.userdetails.cache
    UserCache的实现

![core user details cache](./images/core/core-userdetails-cache.png)

#### 2-7-10-1、EhCacheBasedUserCache
    implements UserCache, org.springframework.beans.factory.InitializingBean
    
    使用 Spring IoC 定义的 EHCACHE 缓存用户对象。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private net.sf.ehcache.Ehcache |	cache 
> private static org.apache.commons.logging.Log |	logger 
#### 2-7-10-2、NullUserCache
    implements UserCache
    
    不执行任何缓存。
#### 2-7-10-3、SpringCacheBasedUserCache
    implements UserCache
    
    在 Spring 定义的缓存中缓存 UserDetails 实例。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private org.springframework.cache.Cache |	cache 
> private static org.apache.commons.logging.Log |	logger 

### 2-7-11、core.userdetails.jdbc
    公开基于 JDBC 的身份验证存储库，实现 org.springframework.security.core.userdetails.UserDetailsService UserDetailsService。
    
![core user details jdbc](./images/core/core-userdetails-jdbc.png)

#### 2-7-11-1、JdbcDaoImpl
    extends org.springframework.jdbc.core.support.JdbcDaoSupport
    implements UserDetailsService, org.springframework.context.MessageSourceAware

    UserDetailsS​​ervice 实现，它使用 JDBC 查询从数据库中检索用户详细信息（用户名、密码、启用标志和权限）。
    **默认架构**
    假设有一个默认的数据库模式，有两个表“用户”和“权限”。
        **用户表**
        该表包含用户的登录名、密码和启用状态。
            **列**
            用户名
            密码
            启用
        **权限表**
            **列**
            用户名
            权威
    如果您使用现有架构，则必须设置查询 usersByUsernameQuery 和 authorityByUsernameQuery 以匹配您的数据库设置（请参阅 
    DEF_USERS_BY_USERNAME_QUERY 和 DEF_AUTHORITIES_BY_USERNAME_QUERY）。
    为了最大限度地减少向后兼容性问题，此实现不识别用户帐户的到期或用户凭据的到期。但是，它确实识别并尊重用户启用/禁用的列。这应该映射到结果
    集中的布尔类型（SQL 类型将取决于您使用的数据库）。所有其他列都映射到字符串。
    
    **团体支持**
    可以通过将 enableGroups 属性设置为 true 来启用对基于组的权限的支持（您可能还希望将 enableAuthorities 设置为 false 以直接禁用权
    限加载）。使用这种方法，权限被分配给组，并且用户的权限基于他们所属的组来确定。最终结果是相同的（加载了包含一组 GrantedAuthority 的 
    UserDetails），但不同的持久性策略可能更适合某些应用程序的管理。
    当使用组时，使用表“groups”、“group_members”和“group_authorities”。有关用于加载组权限的默认查询，请参阅 
    DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY。同样，您可以通过设置 groupAuthoritiesByUsernameQuery 属性来自定义它，但返回的行的
    格式应与默认值匹配。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.lang.String |	authoritiesByUsernameQuery 
> static java.lang.String |	DEF_AUTHORITIES_BY_USERNAME_QUERY 
> static java.lang.String |	DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY 
> static java.lang.String |	DEF_USERS_BY_USERNAME_QUERY 
> private boolean |	enableAuthorities 
> private boolean |	enableGroups 
> private java.lang.String |	groupAuthoritiesByUsernameQuery 
> protected org.springframework.context.support.MessageSourceAccessor |	messages 
> private java.lang.String |	rolePrefix 
> private boolean |	usernameBasedPrimaryKey 
> private java.lang.String |	usersByUsernameQuery 
### 2-7-12、core.userdetails.memory
    公开内存中的身份验证存储库。
![core user details memory](./images/core/core-userdetails-memory.png)

#### 2-7-12-1、UserAttribute
    InMemoryUserDetailsManager 使用它来临时存储与用户关联的属性。 
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.util.List<GrantedAuthority> |	authorities 
> private boolean |	enabled 
> private java.lang.String |	password 
##### addAuthority(GrantedAuthority newAuthority) void
##### getAuthorities() java.util.List<GrantedAuthority>	
##### getPassword() java.lang.String	
##### isEnabled() boolean	
##### isValid() boolean	
##### setAuthorities(java.util.List<GrantedAuthority> authorities) void	
##### setAuthoritiesAsString(java.util.List<java.lang.String> authoritiesAsStrings) void
    从字符串值设置此用户的所有权限。 它将创建必要的 GrantedAuthority 对象。
> * 参数：
>   * authorityAsStrings - 列表 <String>
##### setEnabled(boolean enabled)  void	
##### setPassword(java.lang.String password) void	

#### 2-7-12-2、UserAttributeEditor
    extends java.beans.PropertyEditorSupport
    
    从逗号分隔的值列表创建 UserAttribute 的属性编辑器。
    
##### setAsText(java.lang.String s) void
> * 指定者：
>   * 接口 java.beans.PropertyEditor 中的 setAsText
> * 覆盖：
>   * 类 java.beans.PropertyEditorSupport 中的 setAsText
> * 抛出：
>   * java.lang.IllegalArgumentException
## 2-8、jackson2
### 2-8-1、jackson2
    混合类以添加 Jackson 序列化支持。

![core jackson2](./images/core/core-jackson2.png)

    JsonDeserializer
#### 2-8-1-1、UnmodifiableSetDeserializer
    extends com.fasterxml.jackson.databind.JsonDeserializer<java.util.Set>
    
    UnmodifiableSetDeserializer 的自定义解串器。
##### deserialize(com.fasterxml.jackson.core.JsonParser jp, com.fasterxml.jackson.databind.DeserializationContext ctxt)  Set
#### 2-8-1-2、UnmodifiableListDeserializer
    extends com.fasterxml.jackson.databind.JsonDeserializer<java.util.List>
    
    UnmodifiableListDeserializer 的自定义解串器。
##### deserialize(com.fasterxml.jackson.core.JsonParser jp, com.fasterxml.jackson.databind.DeserializationContext ctxt) List
#### 2-8-1-3、UserDeserializer
    extends com.fasterxml.jackson.databind.JsonDeserializer<User>
    
    用户类的自定义反序列化器。 这已经在 UserMixin 注册了。 你也可以直接在你的 mixin 类中使用它。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static com.fasterxml.jackson.core.type.TypeReference<java.util.Set<SimpleGrantedAuthority>> |	SIMPLE_GRANTED_AUTHORITY_SET 
##### deserialize(com.fasterxml.jackson.core.JsonParser jp, com.fasterxml.jackson.databind.DeserializationContext ctxt) User
    此方法将创建用户对象。 即使序列化 json 中的密码密钥为空，它也将确保成功创建对象，因为可以通过调用 User.eraseCredentials() 从用户中
    删除凭据。 在这种情况下，序列化的 json 中不会有任何密码密钥。
> * 指定者：
>   * 在 com.fasterxml.jackson.databind.JsonDeserializer<User> 类中反序列化
> * 参数：
>   * jp - JsonParser
>   * ctxt - 反序列化上下文
> * 返回：
>   * 用户
> * 抛出：
>   * java.io.IOException - 如果 IO 期间发生异常
>   * com.fasterxml.jackson.core.JsonProcessingException - 如果在 JSON 处理过程中发生错误
#### 2-8-1-4、UsernamePasswordAuthenticationTokenDeserializer
    extends com.fasterxml.jackson.databind.JsonDeserializer<UsernamePasswordAuthenticationToken>
    
    UsernamePasswordAuthenticationToken 的自定义解串器。 在反序列化时，它将根据已验证属性的值调用合适的构造函数。 它将确保令牌的状态不得更改。
    这个反序列化器已经用 UsernamePasswordAuthenticationTokenMixin 注册，但你也可以用你自己的 mixin 类注册它。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static com.fasterxml.jackson.core.type.TypeReference<java.util.List<GrantedAuthority>> |	GRANTED_AUTHORITY_LIST 
> private static com.fasterxml.jackson.core.type.TypeReference<java.lang.Object> |	OBJECT 

##### deserialize(com.fasterxml.jackson.core.JsonParser jp, com.fasterxml.jackson.databind.DeserializationContext ctxt) UsernamePasswordAuthenticationToken
    此方法从序列化的 json 构造 UsernamePasswordAuthenticationToken 对象。
> * 指定者：
>   * 在 com.fasterxml.jackson.databind.JsonDeserializer<UsernamePasswordAuthenticationToken> 类中反序列化
> * 参数：
>   * jp - JsonParser
>   * ctxt - 反序列化上下文
> * 返回：
>   * 用户
> * 抛出：
>   * java.io.IOException - 如果 IO 期间发生异常
>   * com.fasterxml.jackson.core.JsonProcessingException - 如果在 JSON 处理过程中发生错误
##### getCredentials(com.fasterxml.jackson.databind.JsonNode credentialsNode) Object
##### getPrincipal(com.fasterxml.jackson.databind.ObjectMapper mapper, com.fasterxml.jackson.databind.JsonNode principalNode) Object
> * 抛出:
>   * java.io.IOException
>   * com.fasterxml.jackson.core.JsonParseException
>   * com.fasterxml.jackson.databind.JsonMappingException
##### readJsonNode(com.fasterxml.jackson.databind.JsonNode jsonNode, java.lang.String field) JsonNode


    JsonDeserialize、JsonTypeInfo、JsonAutoDetect、JsonIgnoreProperties
#### 2-8-1-5、UnmodifiableSetMixin
    这个 mixin 类用于反序列化 java.util.Collections$UnmodifiableSet 并与各种 AuthenticationToken 实现的 mixin 类一起使用。
#### 2-8-1-6、UnmodifiableListMixin
    这个 mixin 类用于反序列化 java.util.Collections$UnmodifiableRandomAccessList 并与各种 AuthenticationToken 实现的 mixin 
    类一起使用。
#### 2-8-1-7、UsernamePasswordAuthenticationTokenMixin
    这个 mixin 类用于序列化/反序列化 UsernamePasswordAuthenticationToken。 此类注册自定义反序列化器 
    UsernamePasswordAuthenticationTokenDeserializer。 为了使用这个 mixin，你需要再添加 3 个 mixin 类。
        1.UnmodifiableSetMixin
        2.SimpleGrantedAuthorityMixin
        3.UserMixin
#### 2-8-1-8、UserMixin
    这个 mixin 类有助于序列化/反序列化用户。 该类还注册了一个自定义反序列化器 UserDeserializer 以成功反序列化 User 对象。 为了使用这个 
    mixin，您需要在 ObjectMapper 配置中注册另外两个 mixin 类。
        1.SimpleGrantedAuthorityMixin
        2.UnmodifiableSetMixin
#### 2-8-1-9、AnonymousAuthenticationTokenMixin
    这是一个 Jackson mixin 类，有助于序列化/反序列化 AnonymousAuthenticationToken 类。 要使用此类，您需要使用 ObjectMapper 和 
    SimpleGrantedAuthorityMixin 注册它，因为 AnonymousAuthenticationToken 包含 SimpleGrantedAuthority。
#### 2-8-1-10、SimpleGrantedAuthorityMixin
    Jackson Mixin 类有助于序列化/反序列化 SimpleGrantedAuthority。
#### 2-8-1-11、RememberMeAuthenticationTokenMixin
    这个 mixin 类有助于序列化/反序列化 RememberMeAuthenticationToken 类。 要使用这个类，你需要使用 ObjectMapper 和另外 2 个 mixin 
    类来注册它。
        1.SimpleGrantedAuthorityMixin
        2.UserMixin
        3.UnmodifiableSetMixin
#### 2-8-1-12、BadCredentialExceptionMixin
    这个 mixin 类有助于序列化/反序列化 BadCredentialsException 类。 要使用这个类，你需要用 ObjectMapper 注册它。
    
> 注意：这个类会将 TypeInfo（类全名）保存到一个名为 @class 的属性中，在序列化中忽略了 cause 和 stackTrace。
    
    Module
#### 2-8-1-13、CoreJackson2Module
    extends com.fasterxml.jackson.databind.module.SimpleModule
    
    spring-security-core 的 Jackson 模块。 该模块注册了 AnonymousAuthenticationTokenMixin、RememberMeAuthenticationTokenMixin、
    SimpleGrantedAuthorityMixin、UnmodifiableSetMixin、UserMixin 和 UsernamePasswordAuthenticationTokenMixin。 如果默认情
    况下没有启用默认输入，那么它将启用它，因为需要输入信息来正确序列化/反序列化对象。 为了使用这个模块，只需将这个模块添加到你的 
    ObjectMapper 配置中。
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CoreJackson2Module());
    
    注意：使用 SecurityJackson2Modules.getModules(ClassLoader) 获取所有安全模块的列表。
##### setupModule(com.fasterxml.jackson.databind.Module.SetupContext context) void

#### 2-8-1-14、SecurityJackson2Modules
    这个实用程序类将在类路径中找到所有的 SecurityModules。
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules());
    上面的代码相当于
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.registerModule(new CoreJackson2Module());
        mapper.registerModule(new CasJackson2Module());
        mapper.registerModule(new WebJackson2Module());
        mapper.registerModule(new WebServletJackson2Module());
        mapper.registerModule(new WebServerJackson2Module());
        mapper.registerModule(new OAuth2ClientJackson2Module());
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static java.lang.String |	javaTimeJackson2ModuleClass 
> private static org.apache.commons.logging.Log |	logger 
> private static java.lang.String |	oauth2ClientJackson2ModuleClass 
> private static java.util.List<java.lang.String> |	securityJackson2ModuleClasses 
> private static java.lang.String |	webServletJackson2ModuleClass 

##### enableDefaultTyping(com.fasterxml.jackson.databind.ObjectMapper mapper) void
##### loadAndGetInstance(String className, java.lang.ClassLoader loader) com.fasterxml.jackson.databind.Module
##### getModules(java.lang.ClassLoader loader) List<com.fasterxml.jackson.databind.Module>
##### addToModulesList(java.lang.ClassLoader loader,List<Module> modules, String className) void
##### createAllowlistedDefaultTyping() TypeResolverBuilder<? extends TypeResolverBuilder>
    创建一个限制允许类型的 TypeResolverBuilder。
> * 返回：
>   * 限制允许类型的 TypeResolverBuilder。

    嵌套类：
##### AllowlistTypeIdResolver
    implements com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    
    如果正在查找的类不在许可名单中，则委托给现有实现并抛出 IllegalStateException 的 TypeIdResolver，不提供显式混合，并且未使用 
    Jackson 映射进行注释。 见 https://github.com/spring-projects/spring-security/issues/4370
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private static java.util.Set<java.lang.String> |	ALLOWLIST_CLASS_NAMES 
> private com.fasterxml.jackson.databind.jsontype.TypeIdResolver |	delegate 

    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> java.lang.String |	getDescForKnownTypeIds() 
> com.fasterxml.jackson.annotation.JsonTypeInfo.Id |	getMechanism() 
> java.lang.String |	idFromBaseType() 
> java.lang.String |	idFromValue(java.lang.Object value) 
> java.lang.String |	idFromValueAndType(java.lang.Object value, java.lang.Class<?> suggestedType) 
> void |	init(com.fasterxml.jackson.databind.JavaType baseType) 
> private boolean |	isInAllowlist(java.lang.String id) 
> com.fasterxml.jackson.databind.JavaType |	typeFromId(com.fasterxml.jackson.databind.DatabindContext context, java.lang.String id) 
##### AllowlistTypeResolverBuilder
    extends com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder
    
    ObjectMapper.DefaultTypeResolverBuilder 的一个实现，它插入一个允许所有的 PolymorphicTypeValidator 并覆盖 TypeIdResolver
    
    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> protected com.fasterxml.jackson.databind.jsontype.TypeIdResolver | idResolver(com.fasterxml.jackson.databind.cfg.MapperConfig<?> config, com.fasterxml.jackson.databind.JavaType baseType, com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator subtypeValidator, java.util.Collection<com.fasterxml.jackson.databind.jsontype.NamedType> subtypes, boolean forSer, boolean forDeser) 


## 2-9、provisioning
### 2-9-1、provisioning
    包含简单的用户和权限组帐户供应接口以及基于 JDBC 的实现。

![core provisioning](./images/core/core-provisioning.png)

#### 2-9-1-1、GroupManager
    允许管理权限组及其成员。
    通常，这将用于在以下情况下补充 UserDetailsManager 的功能：将应用程序授予的权限组织到组中，而不是直接将用户映射到角色。
    
    在这种情况下，用户被分配到组并获得分配给组的权限列表，从而提供更灵活的管理选项。

##### addGroupAuthority(java.lang.String groupName, GrantedAuthority authority) void
    为组分配新权限。
##### addUserToGroup(java.lang.String username, java.lang.String group) void
    使用户成为特定组的成员。
> * 参数：
>   * 用户名 - 要授予会员资格的用户。
>   * group - 用户将被添加到的组的名称。
##### createGroup(java.lang.String groupName, java.util.List<GrantedAuthority> authorities) void
    创建具有指定权限列表的新组。
> * 参数：
>   * groupName - 新组的名称
>   * 权限 - 分配给该组的权限。
##### deleteGroup(java.lang.String groupName) void
    删除一个组，包括所有成员和权限。
> * 参数：
>   * groupName - 要删除的组。
##### findAllGroups() List<String>
    返回此组管理员控制的所有组的名称。
##### findGroupAuthorities(java.lang.String groupName) List<GrantedAuthority>
    获取分配给组的权限列表。
##### findUsersInGroup(java.lang.String groupName) List<String>
    定位属于组成员的用户
> * 参数：
>   * groupName - 需要其成员的组
> * 返回：
>   * 群组成员的用户名
##### removeGroupAuthority(java.lang.String groupName, GrantedAuthority authority) void
    从分配给组的权限中删除权限
##### removeUserFromGroup(java.lang.String username, java.lang.String groupName) void
    删除用户的组成员资格。
> * 参数：
>   * 用户名 - 用户
>   * groupName - 要从中删除它们的组
##### renameGroup(java.lang.String oldName, java.lang.String newName) void
    在不改变分配的权限或成员的情况下更改组的名称。

#### 2-9-1-2、MutableUserDetails
    extends UserDetails
    
##### setPassword(java.lang.String password)  void

#### 2-9-1-3、UserDetailsManager
    extends UserDetailsService
    
    UserDetailsService 的扩展，提供创建新用户和更新现有用户的能力。
    
##### createUser(UserDetails user) void
##### updateUser(UserDetails user) void
##### deleteUser(java.lang.String username) void
##### changePassword(java.lang.String oldPassword, java.lang.String newPassword) void
    修改当前用户的密码。 这应该会更改持久用户存储库（数据库、LDAP 等）中的用户密码。
> * 参数：
>   * oldPassword - 当前密码（如果需要，用于重新验证）
>   * newPassword - 要更改的密码
##### userExists(java.lang.String username) boolean

#### 2-9-1-4、InMemoryUserDetailsManager
    implements UserDetailsManager, UserDetailsPasswordService
    
    由内存映射支持的 UserDetailsManager 的非持久实现。
    主要用于测试和演示目的，不需要完整的持久系统。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private AuthenticationManager |	authenticationManager 
> protected org.apache.commons.logging.Log |	logger 
> private java.util.Map<java.lang.String,MutableUserDetails> |	users 
#### 2-9-1-5、JdbcUserDetailsManager
    extends JdbcDaoImpl
    implements UserDetailsManager, GroupManager
    
    Jdbc 用户管理服务，基于与其父类 JdbcDaoImpl 相同的表结构。
    为用户和组提供 CRUD 操作。 请注意，如果 enableAuthorities 属性设置为 false，则对 createUser、updateUser 和 deleteUser 的调用
    将不会存储 UserDetails 中的权限或删除用户的权限。 由于此类无法区分为个人加载的权限还是为个人所属的组加载的权限，因此在使用此实现管理
    用户时考虑到这一点很重要。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private AuthenticationManager	| authenticationManager 
> private java.lang.String	| changePasswordSql 
> private java.lang.String	| createAuthoritySql 
> private java.lang.String	| createUserSql 
> static java.lang.String	| DEF_CHANGE_PASSWORD_SQL 
> static java.lang.String	| DEF_CREATE_USER_SQL 
> static java.lang.String	| DEF_DELETE_GROUP_AUTHORITIES_SQL 
> static java.lang.String	| DEF_DELETE_GROUP_AUTHORITY_SQL 
> static java.lang.String	| DEF_DELETE_GROUP_MEMBER_SQL 
> static java.lang.String	| DEF_DELETE_GROUP_MEMBERS_SQL 
> static java.lang.String	| DEF_DELETE_GROUP_SQL 
> static java.lang.String	| DEF_DELETE_USER_AUTHORITIES_SQL 
> static java.lang.String	| DEF_DELETE_USER_SQL 
> static java.lang.String	| DEF_FIND_GROUP_ID_SQL 
> static java.lang.String	| DEF_FIND_GROUPS_SQL 
> static java.lang.String	| DEF_FIND_USERS_IN_GROUP_SQL 
> static java.lang.String	| DEF_GROUP_AUTHORITIES_QUERY_SQL 
> static java.lang.String	| DEF_INSERT_AUTHORITY_SQL 
> static java.lang.String	| DEF_INSERT_GROUP_AUTHORITY_SQL 
> static java.lang.String	| DEF_INSERT_GROUP_MEMBER_SQL 
> static java.lang.String	| DEF_INSERT_GROUP_SQL 
> static java.lang.String	| DEF_RENAME_GROUP_SQL 
> static java.lang.String	| DEF_UPDATE_USER_SQL 
> static java.lang.String	| DEF_USER_EXISTS_SQL 
> private java.lang.String	| deleteGroupAuthoritiesSql 
> private java.lang.String	| deleteGroupAuthoritySql 
> private java.lang.String	| deleteGroupMemberSql 
> private java.lang.String	| deleteGroupMembersSql 
> private java.lang.String	| deleteGroupSql 
> private java.lang.String	| deleteUserAuthoritiesSql 
> private java.lang.String	| deleteUserSql 
> private java.lang.String	| findAllGroupsSql 
> private java.lang.String	| findGroupIdSql 
> private java.lang.String	| findUsersInGroupSql 
> private java.lang.String	| groupAuthoritiesSql 
> private java.lang.String	| insertGroupAuthoritySql 
> private java.lang.String	| insertGroupMemberSql 
> private java.lang.String	| insertGroupSql 
> protected org.apache.commons.logging.Log	| logger 
> private java.lang.String	| renameGroupSql 
> private java.lang.String	| updateUserSql 
> private UserCache	| userCache 
> private java.lang.String	| userExistsSql 
#### 2-9-1-6、MutableUser
    implements MutableUserDetails
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private UserDetails |	delegate 
> private java.lang.String |	password 
> private static long |	serialVersionUID 

## 2-10、scheduling
### 2-10-1、scheduling
![scheduling](./images/core/scheduling.png)

#### 2-10-1-1、DelegatingSecurityContextSchedulingTaskExecutor
    extends DelegatingSecurityContextAsyncTaskExecutor
    implements org.springframework.scheduling.SchedulingTaskExecutor
    
    一个 SchedulingTaskExecutor，它将每个 Runnable 包装在 DelegatingSecurityContextRunnable 中，并将每个 Callable 包装在 
    DelegatingSecurityContextCallable 中。
    
#### 2-10-1-2、DelegatingSecurityContextTaskScheduler
    implements org.springframework.scheduling.TaskScheduler

    每当触发器指示下一次执行时间时调用它的 TaskScheduler 的实现。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private org.springframework.scheduling.TaskScheduler |	delegate 
> private SecurityContext |	securityContext 

## 2-11、task
### 2-11-1、task

![task](./images/core/task.png)

#### 2-11-1-1、DelegatingSecurityContextAsyncTaskExecutor
    extends DelegatingSecurityContextTaskExecutor
    implements org.springframework.core.task.AsyncTaskExecutor
    
    AsyncTaskExecutor 将每个 Runnable 包装在 DelegatingSecurityContextRunnable 中，并将每个 Callable 包装在 
    DelegatingSecurityContextCallable 中。

    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> void |	execute(java.lang.Runnable task, long startTimeout) 
> private org.springframework.core.task.AsyncTaskExecutor |	getDelegate() 
> <T> java.util.concurrent.Future<T> |	submit(java.util.concurrent.Callable<T> task) 
> java.util.concurrent.Future<?> |	submit(java.lang.Runnable task) 

## 2-12、util
### 2-12-1、util
    在整个 Spring Security 框架中使用的通用实用程序类。 供内部使用。
    这个包应该是独立的——它不应该依赖于框架的其他部分，只依赖于外部库和 JDK。
    
![util](./images/core/util.png)

#### 2-12-1-1、FieldUtils
    提供用于直接操作字段的静态方法。
    
    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> static java.lang.reflect.Field |	getField(java.lang.Class<?> clazz, java.lang.String fieldName)
> static java.lang.Object |	getFieldValue(java.lang.Object bean, java.lang.String fieldName)
> static java.lang.Object |	getProtectedFieldValue(java.lang.String protectedField, java.lang.Object object) 
> static void |	setProtectedFieldValue(java.lang.String protectedField, java.lang.Object object, java.lang.Object newValue) 
#### 2-12-1-2、InMemoryResource
    extends org.springframework.core.io.AbstractResource
    
    Spring Resource 接口的内存实现。
    用于从 XML 字符串而不是文件创建 bean 工厂。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.lang.String |	description 
> private byte[] |	source 

    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> boolean |	equals(java.lang.Object res) 
> java.lang.String |	getDescription() 
> java.io.InputStream |	getInputStream() 
> int |	hashCode() 
#### 2-12-1-3、MethodInvocationUtils
    用于创建可在 Spring Security 中使用的 MethodInvocation 的静态实用方法。
    此类的所有方法都返回一个 SimpleMethodInvocation。
    
##### create(java.lang.Object object, java.lang.String methodName, java.lang.Object... args) org.aopalliance.intercept.MethodInvocation
    为传递的对象上的指定 methodName 生成 MethodInvocation，使用 args 来定位方法。
> * 参数：
>   * object - 将用于查找相关方法的对象
>   * methodName - 要查找的方法的名称
>   * args - 作为方法签名一部分所需的参数（可以为空）
> * 返回：
>   * MethodInvocation ，如果有问题，则为 null
##### createFromClass(java.lang.Class<?> clazz, java.lang.String methodName) MethodInvocation
    为传递的类上的指定 methodName 生成 MethodInvocation。 如果不存在具有此名称的不带参数的方法，它将检查类上声明的方法，直到找到与提供
    的名称匹配的方法。 如果有多个方法名称匹配，则会引发 IllegalArgumentException。
> * 参数：
>   * clazz - 将用于查找相关方法的对象类
>   * methodName - 要查找的方法的名称
> * 返回：
>   * MethodInvocation ，如果有问题，则为 null
##### createFromClass(Object targetObject, Class<?> clazz, String methodName, Class<?>[] classArgs, Object[] args) MethodInvocation
    为传递的类上的指定 methodName 生成 MethodInvocation，使用 args 来定位方法。
> * 参数：
>   * targetObject - 被调用的对象
>   * clazz - 将用于查找相关方法的对象类
>   * methodName - 要查找的方法的名称
>   * classArgs - 定位相关方法签名所需的参数
>   * args - 应该传递给 SimpleMethodInvocation 的实际参数
> * 返回：
>   * MethodInvocation ，如果有问题，则为 null
#### 2-12-1-4、SimpleMethodInvocation
    implements org.aopalliance.intercept.MethodInvocation

    表示 AOP 联盟方法调用。
    
    域：
> 修饰符和类型 | 类和描述
> ---------- | -------
> private java.lang.Object[] |	arguments 
> private java.lang.reflect.Method |	method 
> private java.lang.Object |	targetObject 

    方法：
> 修饰符和类型 | 方法和描述
> ---------- | -------
> java.lang.Object[] |	getArguments() 
> java.lang.reflect.Method |	getMethod() 
> java.lang.reflect.AccessibleObject |	getStaticPart() 
> java.lang.Object |	getThis() 
> java.lang.Object |	proceed() 
> java.lang.String |	toString() 

