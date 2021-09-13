1、简介
这篇文章是对 AspectJ 的快速而实用的介绍。

首先，我们将展示如何启用面向方面的编程，然后我们将关注编译时、编译后和加载时编织之间的区别。

让我们先简要介绍面向方面的编程 (AOP) 和 AspectJ 的基础知识。

2. 概述
AOP 是一种编程范式，旨在通过允许分离横切关注点来增加模块化。它通过向现有代码添加额外行为而不修改代码本身来实现。相反，我们单独声明要修改的代码。

AspectJ 使用 Java 编程语言的扩展实现了关注点和横切关注点的编织。

3. Maven 依赖
AspectJ 根据其用途提供不同的库。我们可以在 Maven 中央存储库的 org.aspectj 组下找到 Maven 依赖项。

在本文中，我们重点关注使用编译时、编译后和加载时 Weaver 创建方面和 Weaver 所需的依赖项。

3.1. AspectJ 运行时
运行 AspectJ 程序时，类路径应包含类和方面以及 AspectJ 运行时库 aspectjrt.jar：
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.8.9</version>
</dependency>
```

3.2. AspectJWeaver
除了 AspectJ 运行时依赖之外，我们还需要包含 aspectjweaver.jar 以在加载时向 Java 类引入通知：
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.9</version>
</dependency>
```
该依赖项也可在 Maven Central 上获得。

4. 方面创建
AspectJ 提供了 AOP 的实现，具有三个核心概念：

> * 加入点(Join Point)
> * 切入点(Pointcut)
> * 建议(Advice)
我们将通过创建一个简单的程序来验证用户帐户余额来演示这些概念。

首先，让我们创建一个具有给定余额和提款方法的 Account 类：

```java
public class Account {
    int balance = 20;

    public boolean withdraw(int amount) {
        if (balance < amount) {
            return false;
        } 
        balance = balance - amount;
        return true;
    }
}
```
我们将创建一个 AccountAspect.aj 文件来记录帐户信息并验证帐户余额（请注意，AspectJ 文件以“.aj”文件扩展名结尾）：

```java
public aspect AccountAspect {
    final int MIN_BALANCE = 10;

    pointcut callWithDraw(int amount, Account acc) : 
     call(boolean Account.withdraw(int)) && args(amount) && target(acc);

    before(int amount, Account acc) : callWithDraw(amount, acc) {
    }

    boolean around(int amount, Account acc) : 
      callWithDraw(amount, acc) {
        if (acc.balance < amount) {
            return false;
        }
        return proceed(amount, acc);
    }

    after(int amount, Account balance) : callWithDraw(amount, balance) {
    }
}

```

正如我们所见，我们已经向撤销方法添加了一个切入点，并创建了三个引用定义的切入点的建议。

为了理解以下内容，我们引入以下定义：

方面(Aspect)：跨越多个对象的关注点的模块化。每个方面都专注于特定的横切功能
连接点(Join point)：脚本执行过程中的一个点，例如方法的执行或属性访问
Advice：方面在特定连接点采取的行动
切入点(Pointcut)：匹配连接点的正则表达式。通知与切入点表达式相关联，并在与切入点匹配的任何连接点处运行
有关这些概念及其特定语义的更多详细信息，我们可能需要查看以下链接。

接下来，我们需要将方面编织到我们的代码中。以下部分介绍了三种不同类型的编织：AspectJ 中的编译时编织、编译后编织和加载时编织。

5. 编译时编织
最简单的编织方法是编译时编织。当我们拥有方面的源代码和我们在其中使用方面的代码时，AspectJ 编译器将从源代码编译并生成一个编织类文件作为输出。之后，在执行您的代码时，编织过程输出类将作为普通 Java 类加载到 JVM 中。

我们可以下载 AspectJ 开发工具，因为它包含一个捆绑的 AspectJ 编译器。 AJDT 最重要的功能之一是用于可视化横切关注点的工具，这有助于调试切入点规范。我们甚至可以在部署代码之前将组合效果可视化。

我们使用 Mojo 的 AspectJ Maven 插件通过 AspectJ 编译器将 AspectJ 方面编织到我们的类中。
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.7</version>
    <configuration>
        <complianceLevel>1.8</complianceLevel>
        <source>1.8</source>
        <target>1.8</target>
        <showWeaveInfo>true</showWeaveInfo>
        <verbose>true</verbose>
        <Xlint>ignore</Xlint>
        <encoding>UTF-8 </encoding>
    </configuration>
    <executions>
        <execution>
            <goals>
                <!-- use this goal to weave all your main classes -->
                <goal>compile</goal>
                <!-- use this goal to weave all your test classes -->
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```

让我们为 Account 类添加一些测试用例：

```java
public class AccountTest {
    private Account account;

    @Before
    public void before() {
        account = new Account();
    }

    @Test
    public void given20AndMin10_whenWithdraw5_thenSuccess() {
        assertTrue(account.withdraw(5));
    }

    @Test
    public void given20AndMin10_whenWithdraw100_thenFail() {
        assertFalse(account.withdraw(100));
    }
}
```
当我们运行测试用例时，控制台中显示的以下文本表示我们成功编织了源代码：

```text
[INFO]加入点'方法调用
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20)
来自“com.baeldung.aspectj.AccountAspect”的建议
(AccountAspect.class:18(来自AccountAspect.aj))

[INFO]加入点'方法调用
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20)
来自 'com.baeldung.aspectj.AccountAspect' 之前的建议
(AccountAspect.class:13(来自AccountAspect.aj))

[INFO]加入点'方法调用
(boolean com.baeldung.aspectj.Account.withdraw(int))' in Type
'com.baeldung.aspectj.test.AccountTest' (AccountTest.java:20)
来自'com.baeldung.aspectj.AccountAspect'的建议
(AccountAspect.class:26(来自AccountAspect.aj))

2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提款前余额：20
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提现金额：5
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提款后余额：15
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提款前余额：20
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提现金额：100
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提款被拒绝！
2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.AccountAspect
- 提款后余额：20
```

6. 编译后编织
编译后编织（有时也称为二进制编织）用于编织现有的类文件和 JAR 文件。与编译时编织一样，用于编织的方面可以是源代码或二进制形式，并且它们本身也可以由方面进行编织。

为了使用 Mojo 的 AspectJ Maven 插件做到这一点，我们需要设置我们想要在插件配置中编织的所有 JAR 文件：

```xml
<configuration>
    <weaveDependencies>
        <weaveDependency>  
            <groupId>org.agroup</groupId>
            <artifactId>to-weave</artifactId>
        </weaveDependency>
        <weaveDependency>
            <groupId>org.anothergroup</groupId>
            <artifactId>gen</artifactId>
        </weaveDependency>
    </weaveDependencies>
</configuration>
```
包含要编织的类的 JAR 文件必须在 Maven 项目中列为 <dependencies/>，并在 AspectJ Maven 插件的 <configuration> 中列为 <weaveDependencies/>。

7. 加载时编织
加载时编织只是延迟到类加载器加载类文件并将类定义到 JVM 时的二进制编织。

为了支持这一点，需要一个或多个“编织类加载器”。这些要么由运行时环境明确提供，要么使用“编织代理”启用。

7.1.启用加载时编织
可以使用 AspectJ 代理启用 AspectJ 加载时编织，该代理可以参与类加载过程并在 VM 中定义任何类型之前编织任何类型。我们为 JVM -javaagent:pathto/aspectjweaver.jar 指定 javaagent 选项或使用 Maven 插件来配置 javaagent ：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.10</version>
    <configuration>
        <argLine>
            -javaagent:"${settings.localRepository}"/org/aspectj/
            aspectjweaver/${aspectj.version}/
            aspectjweaver-${aspectj.version}.jar
        </argLine>
        <useSystemClassLoader>true</useSystemClassLoader>
        <forkMode>always</forkMode>
    </configuration>
</plugin>
```

7.2.配置编织器
AspectJ 的加载时编织代理是通过使用 aop.xml 文件来配置的。它会在 META-INF 目录中的类路径上查找一个或多个 aop.xml 文件并聚合内容以确定织机配置。

aop.xml 文件包含两个关键部分：

> * 方面(Aspects)：定义织造者的一个或多个方面，并控制在织造过程中使用哪些方面。方面元素可以选择包含一个或多个包含和排除元素（默认情况下，
所有定义的方面都用于编织）
> * Weaver：定义编织器的编织器选项并指定应该编织的类型集。如果未指定包含元素，则编织者可见的所有类型都将被编织

让我们为 weaver 配置一个方面：

```xml
<aspectj>
    <aspects>
        <aspect name="com.baeldung.aspectj.AccountAspect"/>
        <weaver options="-verbose -showWeaveInfo">
            <include within="com.baeldung.aspectj.*"/>
        </weaver>
    </aspects>
</aspectj>
```
可以看到，我们已经配置了一个指向AccountAspect的aspect，只有com.baeldung.aspectj包中的源码才会被AspectJ编织。

8. 注释方面
除了熟悉的 AspectJ 基于代码的方面声明风格之外，AspectJ 5 还支持基于注解的方面声明风格。我们非正式地将支持这种开发风格的注释集称为“@AspectJ”注释。

让我们创建一个注释：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
    public boolean isLocked() default false; 
}

```

我们使用@Secured 注解来启用或禁用一个方法：

```java
public class SecuredMethod {

    @Secured(isLocked = true)
    public void lockedMethod() {
    }

    @Secured(isLocked = false)
    public void unlockedMethod() {
    }
}

```
接下来，我们使用 AspectJ 注解样式添加一个方面，并根据@Secured 注解的属性检查权限：
```java
@Aspect
public class SecuredMethodAspect {
    @Pointcut("@annotation(secured)")
    public void callAt(Secured secured) {
    }

    @Around("callAt(secured)")
    public Object around(ProceedingJoinPoint pjp, 
      Secured secured) throws Throwable {
        return secured.isLocked() ? null : pjp.proceed();
    }
}

```
有关 AspectJ 注释样式的更多详细信息，我们可以查看以下链接。

接下来，我们使用加载时编织器编织我们的类和方面，并将 aop.xml 放在 META-INF 文件夹下：

```xml
<aspectj>
    <aspects>
        <aspect name="com.baeldung.aspectj.SecuredMethodAspect"/>
        <weaver options="-verbose -showWeaveInfo">
            <include within="com.baeldung.aspectj.*"/>
        </weaver>
    </aspects>
</aspectj>
```
最后，我们添加单元测试并检查结果：

```java
@Test
public void testMethod() throws Exception {
	SecuredMethod service = new SecuredMethod();
	service.unlockedMethod();
	service.lockedMethod();
}
```
当我们运行测试用例时，我们可能会检查控制台输出以验证我们是否成功地在源代码中编织了我们的方面和类：

```text
[INFO]加入点'方法调用
(void com.baeldung.aspectj.SecuredMethod.unlockedMethod())'
在类型'com.baeldung.aspectj.test.SecuredMethodTest'
(SecuredMethodTest.java:11)
来自“com.baeldung.aspectj.SecuredMethodAspect”的建议
（SecuredMethodAspect.class（来自 SecuredMethodAspect.java））

2016-11-15 22:53:51 [主要] INFO com.baeldung.aspectj.SecuredMethod
- 解锁方法
2016-11-15 22:53:51 [主要] 信息 c.b.aspectj.SecuredMethodAspect -
public void com.baeldung.aspectj.SecuredMethod.lockedMethod() 被锁定
```

9. 结论
在本文中，我们介绍了有关 AspectJ 的介绍性概念。有关详细信息，您可以查看 AspectJ 主页。

您可以在 GitHub 上找到本文的[源代码](https://github.com/eugenp/tutorials/tree/master/spring-aop)。
