Java.util.function
功能与使用（参考Java 15的手册）

Java.util.stream
功能与使用（参考Java 15的手册）

Java的访问级别

对于成员（域、方法、嵌 类和嵌套接口）有四种可能的访问级别，下面按照可访问性
的递增顺序罗列出来：
> * 私有的 private 一－只有在声明该成员的顶层类内部才可以访问这个成员
> * 包级私有的 package-private 一一声明该成员的包内部的任何类都可以访问这个
成员 从技术上讲，它被称为“缺省”（ default ）访问级别，如果没有为成员指定访
问修饰符，就采用这个访问级别（当然，接口成员除外，它们默认的访问级别是公
有的）
> * 受保护的 protected 一一声明该成员的类的子类可以访问这个成员（但有一些限制
[ JLS. 6.6 ］），并且声明该成员的包内部的任何类也可以访问这个成员
> * 公有的 public －一在任何地方都可以访问该成员
![java access](./images/java/java-access.png)