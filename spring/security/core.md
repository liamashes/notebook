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
### 2-2-9、access.intercept.aspectj
#### 2-2-9-1、

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
