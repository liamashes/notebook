# spring相关特性
## Aware:
    说明：
        标记超接口，指示 bean 有资格通过回调样式方法由特定框架对象的 Spring 容器通知。 实际的方法签名由各个子接口确定，但通常应仅包含一个接
    受单个参数的返回 void 的方法。
        请注意，仅实现 Aware 不会提供默认功能。 相反，必须明确地进行处理，例如在 BeanPostProcessor 中。 处理 *Aware 接口回调的示例
    参考 
        org.springframework.context.support.ApplicationContextAwareProcessor 
        org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory。

## BeanFactoryAware
    继承 Aware
    说明：
        由希望知道自己拥有的 BeanFactory 的 bean 实现的接口。
        例如，bean 可以通过工厂（依赖查找）查找协作 bean。 请注意，大多数 bean 将选择通过相应的 bean 属性或构造函数参数（依赖注入）接收对协作 bean 的引用。
        有关所有 bean 生命周期方法的列表，请参阅 BeanFactory javadocs。

## BeanFactory
    说明：
        用于访问 Spring bean 容器的根接口。这是 bean 容器的基本客户端视图；其他接口，如 ListableBeanFactory 和 
    org.springframework.beans.factory.config.ConfigurableBeanFactory 可用于特定目的。
        该接口由包含许多 bean 定义的对象实现，每个定义由一个字符串名称唯一标识。根据 bean 定义，工厂将返回包含对象的独立实例（原型设计模式）
    或单个共享实例（单例设计模式的更好替代方案，其中实例是范围内的单例）工厂）。将返回哪种类型的实例取决于 bean 工厂配置：API 相同。从
    Spring 2.0 开始，根据具体的应用程序上下文（例如 Web 环境中的“请求”和“会话”范围），可以使用更多范围。
        这种方法的要点是 BeanFactory 是应用程序组件的中央注册表，并集中了应用程序组件的配置（例如，单个对象不再需要读取属性文件）。有关这种
    方法的好处的讨论，请参阅“专家一对一 J2EE 设计和开发”的第 4 章和第 11 章。
        请注意，通常最好依靠依赖注入（“推”配置）通过设置器或构造器来配置应用程序对象，而不是使用任何形式的“拉”配置，如 BeanFactory 查找。 
    Spring 的依赖注入功能是使用这个 BeanFactory 接口及其子接口实现的。
        通常，BeanFactory 会加载存储在配置源（例如 XML 文档）中的 bean 定义，并使用 org.springframework.beans 包来配置 bean。但是，
    实现可以简单地返回它根据需要直接在 Java 代码中创建的 Java 对象。对如何存储定义没有限制：LDAP、RDBMS、XML、属性文件等。鼓励实现支持 
    bean 之间的引用（依赖注入）。
        与 ListableBeanFactory 中的方法相比，该接口中的所有操作也会检查父工厂是否为 HierarchicalBeanFactory。如果在该工厂实例中找不到
    bean，则会询问直接父工厂。这个工厂实例中的 bean 应该覆盖任何父工厂中的同名 bean。
        Bean 工厂实现应尽可能支持标准的 bean 生命周期接口。全套初始化方法及其标准顺序为：
            BeanNameAware 的 setBeanName
            BeanClassLoaderAware 的 setBeanClassLoader
            BeanFactoryAware 的 setBeanFactory
            EnvironmentAware 的 setEnvironment
            EmbeddedValueResolverAware 的 setEmbeddedValueResolver
            ResourceLoaderAware 的 setResourceLoader（仅在应用程序上下文中运行时适用）
            ApplicationEventPublisherAware 的 setApplicationEventPublisher（仅在应用程序上下文中运行时适用）
            MessageSourceAware 的 setMessageSource（仅在应用程序上下文中运行时适用）
            ApplicationContextAware 的 setApplicationContext（仅在应用程序上下文中运行时适用）
            ServletContextAware 的 setServletContext（仅适用于在 Web 应用程序上下文中运行时）
            BeanPostProcessors 的 postProcessBeforeInitialization 方法
            InitializingBean 的 afterPropertiesSet
            自定义初始化方法定义
            BeanPostProcessors 的 postProcessAfterInitialization 方法
        在关闭 bean 工厂时，将应用以下生命周期方法：
            DestructionAwareBeanPostProcessors 的 postProcessBeforeDestruction 方法
            DisposableBean 的销毁
            自定义销毁方法定义

# Proxy
    Spring: instanceof SpringProxy
    JDK: java.lang.reflect.Proxy.isProxyClass()
    Cglib: ClassUtils.isCglibProxyClass()

# NestedConfigurationProperty
    说明：
        指示应将 ConfigurationProperties 对象中的字段视为嵌套类型。 此注解与实际绑定过程无关，但它被 spring-boot-configuration-processor 用作字段未绑定为单个值的提示。

# DataSourceLookup
    说明：
        按名称查找数据源的策略接口。例如，用于解析 JPA persistence.xml 文件中的数据源名称。

