Oracle 有两种实现 Java Platform Standard Edition (Java SE) 8 的产品：Java SE Development Kit (JDK) 8 和 Java SE Runtime Environment (JRE) 8。

JDK 8 是 JRE 8 的超集，包含 JRE 8 中的所有内容，以及开发小程序和应用程序所需的编译器和调试器等工具。 JRE 8 提供了库、Java 虚拟机 (JVM) 
和其他组件来运行用 Java 编程语言编写的小程序和应用程序。 请注意，JRE 包括 Java SE 规范不需要的组件，包括标准和非标准 Java 组件。

以下概念图说明了 Oracle 的 Java SE 产品的组件：

![conceptual diagram](../images/java%20platform%20standard%20edition%208%20documentation.png)

[official site](https://docs.oracle.com/javase/8/docs/)

包含关系：
JDK <- JRE <- Java SE API <- Compact Profiles

> * [Base Libraries](./Base%20Libraries)
>   * [lang and util](./Base%20Libraries/lang%20and%20util.md)
>   * [Math](./Base%20Libraries/Math.md)
>   * [Collections](./Base%20Libraries/Collections.md)
>   * [Ref Objects](./Base%20Libraries/Ref%20Objects.md)
>   * [Regular Expressions](./Base%20Libraries/Regular%20Expressions.md)
>   * [Logging](./Base%20Libraries/Logging.md)
>   * [Management](./Base%20Libraries/Management.md)
>   * [Instrumentation](./Base%20Libraries/Instrumentation.md)
>   * [Concurrency Utilities](./Base%20Libraries/Concurrency%20Utilies.md)
>   * [Reflection](./Base%20Libraries/Reflection.md)
>   * [Versioning](./Base%20Libraries/Versioning.md)
>   * [Preference API](./Base%20Libraries/Preferences%20API.md)
>   * [JAR](./Base%20Libraries/JAR.md)
>   * [Zip](./Base%20Libraries/Zip.md)

> * [Other Base Libraries](./Other%20Base%20Libraries)
>   * [Beans](./Other%20Base%20Libraries/Beans.md)
>   * [Security](./Other%20Base%20Libraries/Security.md)
>   * [Serialization](./Other%20Base%20Libraries/Serialization.md)
>   * [Extension Mechanism](./Other%20Base%20Libraries/Extension%20Mechanism.md)
>   * [JMX](./Other%20Base%20Libraries/JMX.md)
>   * [XML JAXP](./Other%20Base%20Libraries/XML%20JAXP.md)
>   * [Networking](./Other%20Base%20Libraries/Networking.md)
>   * [Override Mechanism](./Other%20Base%20Libraries/Override%20Mechanism.md)
>   * [JNI](./Other%20Base%20Libraries/JNI.md)
>   * [Date and Time](./Other%20Base%20Libraries/Date%20and%20Time.md)
>   * [Input Output](./Other%20Base%20Libraries/Input%20Output.md)
>   * [Internationalization](./Other%20Base%20Libraries/Internationalization.md)

> * [Integration Libraries](./Integration%20Libraries)
