# Spring 安全架构
本指南是 Spring Security 的入门，提供对框架的设计和基本构建块的深入了解。我们只涵盖应用程序安全的基础知识。但是，通过这样做，我们可以消除
使用 Spring Security 的开发人员所遇到的一些困惑。为此，我们通过使用过滤器，更一般地说，通过使用方法注释来查看在 Web 应用程序中应用安全性
的方式。当您需要高度了解安全应用程序的工作原理、如何对其进行自定义或需要了解如何考虑应用程序安全性时，请使用本指南。

本指南并非旨在作为解决最基本问题以外的其他问题的手册或方法（还有其他来源），但它可能对初学者和专家都有用。 Spring Boot 也经常被引用，因为
它为安全应用程序提供了一些默认行为，并且有助于理解它如何适应整体架构。

> 所有原则同样适用于不使用 Spring Boot 的应用程序。

## 1、身份验证和访问控制
应用程序安全归结为两个或多或少独立的问题：身份验证（你是谁？）和授权（你被允许做什么？）。有时人们会说“访问控制”而不是“授权”，这可能会让人
感到困惑，但这样想是有帮助的，因为“授权”在其他地方超载。 Spring Security 的架构旨在将身份验证与授权分开，并为两者提供策略和扩展点。

### 1-1、验证
认证的主要策略接口是AuthenticationManager，它只有一个方法：
```java
public interface AuthenticationManager {

  Authentication authenticate(Authentication authentication)
    throws AuthenticationException;
}
```

AuthenticationManager 可以在其 authenticate() 方法中执行以下三件事之一：

> * 如果可以验证输入是否代表有效主体，则返回身份验证（通常使用身份验证 = 真）。
> * 如果它认为输入代表无效的主体，则抛出 AuthenticationException。
> * 如果无法决定，则返回 null。

AuthenticationException 是运行时异常。它通常由应用程序以通用方式处理，具体取决于应用程序的样式或目的。换句话说，用户代码通常不会捕获并处
理它。例如，Web UI 可能会呈现一个表明身份验证失败的页面，后端 HTTP 服务可能会发送 401 响应，根据上下文带有或不带有 WWW-Authenticate 标
头。

AuthenticationManager 最常用的实现是 ProviderManager，它委托给 AuthenticationProvider 实例链。 AuthenticationProvider 有点像
 AuthenticationManager，但它有一个额外的方法来允许调用者查询它是否支持给定的 Authentication 类型：

```java
public interface AuthenticationProvider {

	Authentication authenticate(Authentication authentication)
			throws AuthenticationException;

	boolean supports(Class<?> authentication);
}
```

support() 方法中的 Class<?> 参数实际上是 Class<? extends Authentication>（只询问它是否支持传递给 authenticate() 方法的东西）。
 ProviderManager 可以通过委托给 AuthenticationProviders 链在同一应用程序中支持多种不同的身份验证机制。 如果 ProviderManager 无法识
 别特定的 Authentication 实例类型，则会跳过它。

ProviderManager 有一个可选的父级，如果所有提供者都返回 null，它可以咨询它。 如果父级不可用，则空身份验证会导致
 AuthenticationException。

有时，应用程序具有受保护资源的逻辑组（例如，匹配路径模式的所有 Web 资源，例如 /api/**），并且每个组都可以有自己专用的
 AuthenticationManager。 通常，它们中的每一个都是一个 ProviderManager，并且它们共享一个父级。 父级是一种“全局”资源，充当所有提供者的
后备。

<p align="center"><img src="images/authentication.png" height="40%" width="40%" alt="authentication" /></p>

图 1. 使用 ProviderManager 的 AuthenticationManager 层次结构

### 1-2、自定义身份验证管理器

Spring Security 提供了一些配置助手来快速获取在您的应用程序中设置的常见身份验证管理器功能。 最常用的帮助器是
 AuthenticationManagerBuilder，它非常适合设置内存、JDBC 或 LDAP 用户详细信息或添加自定义 UserDetailsService。 以下示例显示了一个配
 置全局（父）AuthenticationManager 的应用程序：
```java
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

   ... // web stuff here

  @Autowired
  public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) {
    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
      .password("secret").roles("USER");
  }

}
```
此示例与 Web 应用程序有关，但 AuthenticationManagerBuilder 的用法适用范围更广（有关如何实现 Web 应用程序安全性的更多详细信息，请参阅
 Web Security）。 请注意，AuthenticationManagerBuilder 是@Autowired 到@Bean 中的一个方法 — 这就是它构建全局（父）
 AuthenticationManager 的原因。 相反，请考虑以下示例：

```java
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

   ... // web stuff here

  @Override
  public void configure(AuthenticationManagerBuilder builder) {
    builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
      .password("secret").roles("USER");
  }

}
```

如果我们在配置器中使用了@Override 方法，则 AuthenticationManagerBuilder 将仅用于构建“本地” AuthenticationManager，它将是全局
 AuthenticationManager 的子项。在 Spring Boot 应用程序中，您可以 @Autowired 将全局 bean 放入另一个 bean 中，但除非您自己显式公开它
，否则不能对本地 bean 执行此操作。

Spring Boot 提供了一个默认的全局 AuthenticationManager（只有一个用户），除非您通过提供自己的 AuthenticationManager 类型的 bean 来
抢占它。默认值本身就足够安全，您不必担心太多，除非您主动需要自定义全局 AuthenticationManager。如果您进行任何构建
 AuthenticationManager 的配置，您通常可以在本地对您正在保护的资源进行配置，而不必担心全局默认值。

### 1-3、授权或访问控制
一旦认证成功，我们就可以进行授权，这里的核心策略是 AccessDecisionManager。框架提供了三个实现，所有三个实现都委托给一个
 AccessDecisionVoter 实例链，有点像 ProviderManager 委托给 AuthenticationProviders。

一个 AccessDecisionVoter 考虑一个 Authentication（代表一个主体）和一个安全的 Object，它已经用 ConfigAttributes 修饰了：

```java
boolean supports(ConfigAttribute attribute);

boolean supports(Class<?> clazz);

int vote(Authentication authentication, S object,
        Collection<ConfigAttribute> attributes);

```

该对象在 AccessDecisionManager 和 AccessDecisionVoter 的签名中是完全通用的。它代表用户可能想要访问的任何内容（Web 资源或 Java 类中
的方法是两种最常见的情况）。 ConfigAttributes 也是相当通用的，代表安全对象的装饰，带有一些决定访问它所需的权限级别的元数据。
 ConfigAttribute 是一个接口。它只有一个方法（非常通用并返回一个字符串），因此这些字符串以某种方式编码资源所有者的意图，表达允许谁访问它的
规则。典型的 ConfigAttribute 是用户角色的名称（如 ROLE_ADMIN 或 ROLE_AUDIT），它们通常具有特殊格式（如 ROLE_ 前缀）或表示需要评估的
表达式。

大多数人使用默认的 AccessDecisionManager，它是 AffirmativeBased（如果任何投票者肯定返回，则授予访问权限）。通过添加新的或修改现有的工
作方式，任何定制都倾向于在选民中发生。

使用 Spring 表达式语言 (SpEL) 表达式的 ConfigAttributes 是很常见的 — 例如，isFullyAuthenticated() && hasRole('user')。这是由
 AccessDecisionVoter 支持的，它可以处理表达式并为它们创建上下文。要扩展可以处理的表达式范围，需要 SecurityExpressionRoot 的自定义实
现，有时还需要 SecurityExpressionHandler。

## 2、网络安全
Web 层（用于 UI 和 HTTP 后端）中的 Spring Security 是基于 Servlet 过滤器的，因此先了解一下过滤器的一般作用是有帮助的。下图显示了单个
 HTTP 请求的处理程序的典型分层。

<p align="center"><img src="images/filters.png" alt="filters" width="40%" height="40%" /></p>

客户端向应用程序发送请求，容器根据请求 URI 的路径决定对其应用哪些过滤器和哪个 servlet。最多一个 servlet 可以处理单个请求，但过滤器形成一
个链，因此它们是有序的。事实上，如果过滤器想要自己处理请求，它可以否决链的其余部分。过滤器还可以修改下游过滤器和 servlet 中使用的请求或响
应。过滤器链的顺序非常重要，Spring Boot 通过两种机制来管理它：Filter 类型的@Beans 可以有一个@Order 或实现Ordered，它们可以是一个
FilterRegistrationBean 的一部分，而FilterRegistrationBean 本身有一个顺序作为其一部分应用程序接口。一些现成的过滤器定义了它们自己的常
量，以帮助表明它们相对于彼此的顺序（例如，Spring Session 中的 SessionRepositoryFilter 的 DEFAULT_ORDER 为
 Integer.MIN_VALUE + 50，这告诉我们它喜欢在链的早期，但不排除其他过滤器在它之前）。

Spring Security 作为链中的单个过滤器安装，其具体类型是 FilterChainProxy，原因我们很快就会介绍。在 Spring Boot 应用程序中，安全过滤器
是 ApplicationContext 中的一个 @Bean，它是默认安装的，以便应用于每个请求。它安装在由 SecurityProperties.DEFAULT_FILTER_ORDER 定义
的位置，而该位置又由 FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER 锚定（Spring Boot 应用程序期望过滤器在包装请求时具有
的最大顺序，修改其行为）。但是，还有更多：从容器的角度来看，Spring Security 是一个单一的过滤器，但是，在它内部，还有额外的过滤器，每个过滤
器都扮演着特殊的角色。下图显示了这种关系：

<p align="center"><img src="images/security-filters.png" alt="security filters" width="40%" height="40%" /></p>

图 2. Spring Security 是单个物理过滤器，但将处理委托给一系列内部过滤器

事实上，安全过滤器中甚至还有一层间接：它通常作为 DelegatingFilterProxy 安装在容器中，它不一定是 Spring @Bean。代理委托给一个
 FilterChainProxy，它总是一个@Bean，通常有一个固定的名字 springSecurityFilterChain。 FilterChainProxy 包含内部排列为一个（或多
个）过滤器链的所有安全逻辑。所有过滤器都具有相同的 API（它们都实现了 Servlet 规范中的 Filter 接口），并且它们都有机会否决链的其余部分。

在同一个顶级 FilterChainProxy 中可以有多个过滤器链，它们都由 Spring Security 管理，并且容器都不知道所有过滤器链。 Spring Security 
过滤器包含一个过滤器链列表，并将请求分派到匹配它的第一个链。下图显示了基于匹配请求路径（/foo/** 匹配 /** 之前）发生的调度。这是很常见的，
但不是匹配请求的唯一方法。这个调度过程最重要的特点是只有一个链处理一个请求。

<p align="center"><img src="images/security-filters-dispatch.png" alt="security filters dispatch" width="40%" height="40%" /></p>

图 3. Spring Security FilterChainProxy 将请求分派到第一个匹配的链。

没有自定义安全配置的 vanilla Spring Boot 应用程序有多个（称为 n 个）过滤器链，其中通常 n=6。第一个 (n-1) 链只是为了忽略静态资源模式，
如 /css/** 和 /images/**，以及错误视图：/error。 （这些路径可以由用户通过 SecurityProperties 配置 bean 中的 security.ignored 来
控制。）最后一个链匹配捕获所有路径 (/**) 并且更活跃，包含用于身份验证、授权、异常处理、会话的逻辑处理、标题写入等。默认情况下，该链中共有
 11 个过滤器，但通常用户无需关心使用哪些过滤器以及何时使用。


> **Spring Security 内部的所有过滤器对于容器来说都是未知的**这一事实很重要，尤其是在 Spring Boot 应用程序中，默认情况下，所有类型为
> Filter 的 @Beans 都会自动注册到容器中。因此，**如果您想向安全链添加自定义过滤器，则需要不使其成为 @Bean 或将其包装在显式禁用容器注册的
> FilterRegistrationBean 中**。

### 2-1、创建和自定义过滤器链
Spring Boot 应用程序中的默认回退过滤器链（带有 /** 请求匹配器的应用程序）具有 SecurityProperties.BASIC_AUTH_ORDER 的预定义顺序。您
可以通过设置 security.basic.enabled=false 将其完全关闭，或者您可以将其用作后备并以较低的顺序定义其他规则。要执行后者，请添加类型为
 WebSecurityConfigurerAdapter（或 WebSecurityConfigurer）的 @Bean 并使用 @Order 装饰该类，如下所示：

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/match1/**")
     ...;
  }
}
```

这个 bean 导致 Spring Security 添加一个新的过滤器链并在回退之前对其进行排序。

与另一组资源相比，许多应用程序对一组资源具有完全不同的访问规则。例如，托管 UI 和后备 API 的应用程序可能支持基于 cookie 的身份验证，重定向
到 UI 部分的登录页面，以及基于令牌的身份验证，对 API 部分的未经身份验证的请求发出 401 响应。每组资源都有自己的
 WebSecurityConfigurerAdapter，具有唯一的顺序和自己的请求匹配器。如果匹配规则重叠，则最早排序的过滤器链获胜。

### 2-2、请求匹配调度和授权
安全过滤器链（或等效的 WebSecurityConfigurerAdapter）有一个请求匹配器，用于决定是否将其应用于 HTTP 请求。一旦决定应用特定的过滤器链，
就不会再应用其他过滤器链。但是，在过滤器链中，您可以通过在 HttpSecurity 配置器中设置其他匹配器来对授权进行更细粒度的控制，如下所示：

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/match1/**")
      .authorizeRequests()
        .antMatchers("/match1/user").hasRole("USER")
        .antMatchers("/match1/spam").hasRole("SPAM")
        .anyRequest().isAuthenticated();
  }
}
```

配置 Spring Security 时最容易犯的错误之一是**忘记了这些匹配器适用于不同的进程**。一个是整个过滤器链的请求匹配器，另一个是只选择要应用的
访问规则。

### 2-3、将应用程序安全规则与执行器规则相结合
如果您将 Spring Boot Actuator 用于管理端点，您可能希望它们是安全的，并且默认情况下，它们是安全的。事实上，只要您将 Actuator 添加到安全
应用程序中，您就会获得一个仅适用于执行器端点的附加过滤器链。它使用仅匹配执行器端点的请求匹配器定义，并且它的顺序为
 ManagementServerProperties.BASIC_AUTH_ORDER，比默认的 SecurityProperties 回退过滤器少 5，因此在回退之前进行咨询。

如果您希望您的应用程序安全规则应用于执行器端点，您可以添加一个过滤器链，该过滤器链的顺序早于执行器，并且具有包含所有执行器端点的请求匹配器。
如果您更喜欢执行器端点的默认安全设置，最简单的方法是在执行器之后添加您自己的过滤器，但早于回退（例如，
ManagementServerProperties.BASIC_AUTH_ORDER + 1），如下所示：

```java
@Configuration
@Order(ManagementServerProperties.BASIC_AUTH_ORDER + 1)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/foo/**")
     ...;
  }
}
```

Web 层中的 Spring Security 当前绑定到 Servlet API，因此它仅在 servlet 容器中运行应用程序时才真正适用，无论是嵌入式还是其他方式。但
是，它不绑定到 Spring MVC 或 Spring Web 堆栈的其余部分，因此它可以在任何 servlet 应用程序中使用 — 例如，使用 JAX-RS 的应用程序。

## 3、方法安全
除了支持保护 Web 应用程序，Spring Security 还支持将访问规则应用于 Java 方法执行。对于 Spring Security，这只是一种不同类型的“受保护资
源”。对于用户来说，这意味着使用相同格式的 ConfigAttribute 字符串（例如，角色或表达式）声明访问规则，但在代码中的不同位置。第一步是启用方
法安全性 — 例如，在我们应用程序的顶级配置中：

```java
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SampleSecureApplication {
}
```
然后我们可以直接装饰方法资源：

```java
@Service
public class MyService {

  @Secured("ROLE_USER")
  public String secure() {
    return "Hello Security";
  }

}
```
此示例是具有安全方法的服务。如果 Spring 创建了这种类型的 @Bean，它会被代理并且调用者必须在该方法实际执行之前通过一个安全拦截器。如果访问
被拒绝，调用者会得到一个 AccessDeniedException 而不是实际的方法结果。

您可以在方法上使用其他注释来强制实施安全约束，特别是 @PreAuthorize 和 @PostAuthorize，它们分别允许您编写包含对方法参数和返回值的引用的
表达式。

> 将 Web 安全性和方法安全性结合起来的情况并不少见。过滤器链提供用户体验功能，例如身份验证和重定向到登录页面等，方法安全提供更细粒度的保护。

## 4、使用线程
Spring Security 基本上是线程绑定的，因为它需要使当前经过身份验证的主体可用于各种下游消费者。基本构建块是 SecurityContext，它可能包含一
个身份验证（当用户登录时，它是一个经过显式身份验证的身份验证）。您始终可以通过 SecurityContextHolder 中的静态便捷方法访问和操作
 SecurityContext，而后者又操作 ThreadLocal。以下示例显示了这种安排：

```java
@RequestMapping("/foo")
public String foo(@AuthenticationPrincipal User user) {
  ... // do stuff with user
}
```

此注释从 SecurityContext 中提取当前 Authentication 并对其调用 getPrincipal() 方法以生成方法参数。 身份验证中的主体类型取决于用于验
证身份验证的 AuthenticationManager，因此这可能是一个有用的小技巧，可以获取对用户数据的类型安全引用。

如果使用 Spring Security，则 HttpServletRequest 中的 Principal 是 Authentication 类型，因此您也可以直接使用它：

```java
@RequestMapping("/foo")
public String foo(Principal principal) {
  Authentication authentication = (Authentication) principal;
  User = (User) authentication.getPrincipal();
  ... // do stuff with user
}

```

如果您需要编写在不使用 Spring Security 时工作的代码，这有时会很有用（您需要对加载 Authentication 类更加防御）。

### 4-1、异步处理安全方法
由于 SecurityContext 是线程绑定的，如果您想要执行任何调用安全方法的后台处理（例如，使用 @Async），您需要确保传播上下文。 这归结为使用在
后台执行的任务（Runnable、Callable 等）包装 SecurityContext。 Spring Security 提供了一些帮助器来使这更容易，例如 Runnable 和
 Callable 的包装器。 要将 SecurityContext 传播到 @Async 方法，您需要提供一个 AsyncConfigurer 并确保 Executor 是正确的类型：
```java
@Configuration
public class ApplicationConfiguration extends AsyncConfigurerSupport {

  @Override
  public Executor getAsyncExecutor() {
    return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(5));
  }

}
```
