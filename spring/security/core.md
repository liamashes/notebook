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
> [authentication.rcp](#2-3-7、authentication.rcp) |允许远程客户端进行身份验证并获取填充的身份验证对象。
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
