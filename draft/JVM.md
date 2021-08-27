实际问题：
> * 1、系统卡死、无法访问、OOM
> * 2、线上GC问题
> * 3、JVM参数设置
> * 4、面试、如何解决问题


推荐书籍：
> * JAVA SE 8
> * 深入理解JAVA虚拟机（周志明）
> * 码出高效
> * 自己动手写Java虚拟机


## 整体结构
> * 1、JVM与Java体系结构
> * 2、类加载子系统（主要结构）
> * 运行时数据区（主要结构）
>   * 3、运行时数据区概述及线程
>   * 4、程序计数器
>   * 5、虚拟机栈
>   * 6、本地方法接口
>   * 7、本地方法栈
>   * 8、堆
>   * 9、方法区
>   * 10、直接内存
> * 11、执行引擎（主要结构）
> * 12、StringTable（面试重点）
> * 垃圾回收
>   * 13、垃圾回收概述
>   * 14、垃圾回收相关算法
>   * 15、垃圾回收相关概念
>   * 16、垃圾回收器

## 内容
###1、JVM与JAVA体系结构
> write once, run anywhere

####1.1 跨语言平台：
语言 -> 编译器 -> 字节码文件 -> java虚拟机  
用户 -> 字节码 -> jvm -> 操作系统 -> 硬件

####1.2 整体结构（针对hotspot vm）：
字节码文件 -> 类装载器子系统 -> 运行时数据区（共享：方法区、堆；私有：java栈、本地方法栈、程序计数器）<-> 执行引擎 <-> 本地方法接口 <- 本地库

####1.3 java执行流程：
java源码 -> java编译器（词法、语法、抽象语法、语义、注解抽象语法树、字节码生成器） -> 字节码 -> java虚拟机（类装载器、字节码校验器、翻译字节码、JIT编译器/热点代码/性能）

####1.4 架构：
基于栈的指令集（JVM）：
> * 设计实现简单，适用于资源受限
> * 避开寄存器的分配难题：使用*零地址*指令方法分配
> * 指令集小
> * 不需要硬件支持，可移植性更好

基于寄存器架构：
> * x86的二进制指令集
> * 依赖硬件，可移植性差
> * 性能有效，执行高效
> * 花费更少的指令完成一次操作
> * 以*一地址*、*二地址*、*三地址*指令为主s

####1.5 生命周期
启动：  
通过引导类加载器（bootstrap class loader)创建一个初始类(initial class)来完成

执行:  
执行java程序时，真正在执行的是jvm虚拟机的进程

结束:
> * 正常结束
> * 执行遇到异常、错误
> * 操作系统错误
> * 代码中主动退出

####1.6 发展历程
> * sun classic vm：
>   * 第一款商用的java虚拟机，仅提供解释器；
>   * jdk1.4被淘汰；
>   * 可以外挂JIT，但是不能共用
> * Exact VM：
>   * jdk1.2时提供；
>   * 准确式内存管理：
>   * 可以知道内存中某个位置的数据具体是什么类型；
>   * 具备：热点探测、编译器和解释器的混合工作模式；
>   * 在Solaris平台短暂使用
> * HotSpot VM（三大虚拟机）：
>   * JDK1.3默认虚拟机；
>   * LongView Tech -> Sun -> Oracle；
>   * 相比以上两个：提出*方法区*概念；
>   * 热点探测：JIT、栈上替换；
>   * 编译器与解释器协同工作，平衡响应时间和执行时间
> * BEA的JRockit（三大虚拟机）： 
>   * 专注服务器端，不关注启动速度，不包含解析器实现，全部由*即时编译器*编译后执行；
>   * 速度最快的JVM
>   * 优势：全面的运行时解决方案组合
>       * 面向延迟敏感型应用，以ms或us的JVM响应时间，适合财务、军事指挥、电信网络
>       * MissionControl服务套件，以极低的开销监控、管理、分析生产环境
>   * 08年被Oracle收购
> * IBM的J9（三大虚拟机）：
>   * IBM Technology for java Virtual Machine，简称IT4J，内部代号：J9
>   * 定位接近HotSpot，面向服务器、桌面应用、嵌入式
>   * 三大商用虚拟机，号称最快
> * KVM和CDC/CLDC Hotspot
>   * Oracle在Java ME产品线的虚拟机
>   * KVM简单、轻量、高度可移植性，用于老年机、传感器等
>   * 从移动市场被淘汰
> * Azul VM
>   * 前面三大虚拟机可以通用
>   * 和BEA Liquid VM与特定硬件平台绑定、软硬件配合的专有虚拟机：性能更好
>   * 管理至少数十个CPU和数百GB内存的硬件资源，提供巨大内存范围内实现可控的GC时间的垃圾收集器、专有硬件优化的线程调度等
>   * 10年发布Zing JVM，可以在通用x86平台上提供接近于Vega
> * liquid VM
>   * 高性能
>   * BEA的，直接运行在Hyperviosor上
>   * 即现在的JRockit VE，不需要操作系统的支持，自己本身实现了一个专用操作系统的必要功能，如线程调度、文件系统、网络支持
>   * 随着JRockit暂停，也暂停了研发
> * Apache Harmony
>   * 与jdk 1.5、1.6兼容
>   * IBM、Intel联合开发的开源，受开源OpenJDK的压制，Sun坚决不让Harmony或得JCP认证，于2011年退役
>   * 类库在Android SDK中得以使用
> * Microsoft JVM
>   * 为在IE3浏览器中支持Java Applets
>   * 只能在window平台下运行
>   * Sun指控微软侵犯商标，后抹掉该VM
> * TaobaoJVM
>   * AliJVM开发，覆盖云计算、金融、物流、电商
>   * 基于OpenJDK开发，深度定制且开源
>       * 创新的GCIH（GC invisible heap），将生命周期较长的java对象从heap中移到heap之外，GC不能管理GCIH内部的java对象，从而降低GC的回收频率和提升GC的回收效率
>       * GCIH中的对象可以在多个Java虚拟机进程中实现共享
>       * 使用crc32指令实现JVM intrinsic降低JNI的调用开销
>       * PMU hardware的Java profiling tool和诊断协助
>       * 针对大数据场景的ZenGC
>   * 验证依赖intel的cpu，损失兼容性，提高性能
> * Dalvik VM
>   * 谷歌开发，应用于Android，在2.2中提供了JIT，发展迅速
>   * 只是虚拟机，不是"Java 虚拟机"，没有遵循Java虚拟机规范
>   * 不能直接执行Java的Class文件
>   * 基于寄存器架构，不是jvm的栈架构
>   * 执行的是编译后的dex（Dalvik Executable）文件，效率很高，该文件可以通过Class文件转化而来
>   * Android 5.0 使用 支持提前编译（Ahead Of Time Compilation， AOT）的ART VM替换Dalvik VM
> * Graal VM
>   * 2018年4年，Oracle Labs公开了Graal VM，号称*Run Programs Faster Anywhere*
>   * 在HotSpot VM基础上增强而成的*跨语言全栈虚拟机*
>   * 支持**不同语言中混用对方的接口和对象**，支持使用已经编写好的本地库文件
>   * **将这些语言中的源代码或源代码编译后的中间格式，通过解释器转换为能被Graal VM接受的中间表示。**
>   * **提供Truffle工具集，快速构建面向一种新语言的解释器，运行时进行即时编译优化，获取比原生编译器更优秀的执行效率**
>   * 最可能取代HotSpot
> * 其他JVM
>   * Java Card VM、Squawk VM、JavaInJava、Maxine VM、Jikes RVM、IKVM.NET、Jam VM、Cacao VM、Sable VM
>   * Kaffle、Jelatine JVM、Nano VM、MRP、Moxie JVM


###2、类加载子系统
> 自己实现JVM必须要实现的：类加载子系统、执行引擎

#### 作用
> * 从文件系统或者网络中加载Class文件，在文件开头有特定的标识
> * **只负责加载**，是否可以运行由执行引擎决定
> * 加载的信息存放在*方法区*的内存空间，该区域同时存放**运行时常量池**信息，可能还包含**字符串字面量**和**数字常量**（是Class文件中常量池部分的内存映射）

#### 角色
> * Class file是一个模版，执行时加载到JVM实例化n个一模一样的实例
> * Class file加载到JVM中称为DNA元数据模版，放在**方法区**
> * Class file -> JVM -> 元数据模版，类加载器是一个运输工具

#### 加载过程
加载（loading） -> 链接（Linking）【验证（Verification） -> 准备（Preparation） -> 解析（Resolution）】 -> 初始化（Initialization）

加载
> * 通过**类的全限定名**获取定义此类的二进制字节流
> * 将该字节流所代表的**静态存储结构**转化为*方法区*的**运行时数据结构**
> * 在内存中生成一个代表这个类的对象，作为方法区这个类的各种数据访问入口
> * 加载方式
>   * 本地系统
>   * 网络，典型：Web Applet
>   * zip包，是日后jar、war的基础
>   * 运行时计算生成，最多：动态代理技术
>   * 由其他文件生成，典型：JSP
>   * 专有数据库提取.class
>   * 加密文件获取，防Class文件被反编译

链接
> * 验证
>   * 目的：字节流符合要求、加载类正确、不会危害虚拟机自身安全
>   * 四种验证：文件格式、元数据、字节码、符号引用
> * 准备
>   * 为类变量分配内存、设置该类变量的默认初始值
>   * 不包含final修饰的static，因为final在编译时就分配类，准备阶段会显示初始化
>   * 不会为实例变量分配初始化，类变量会分配在方法区中，实例变量会随着对象一起分配到Java堆中
> * 解析
>   * 将常量池内的符号引用转换为直接引用
>   * 往往伴随着JVM在执行完初始化之后再执行
>   * 符号引用：一组符号描述所引用的目标，字面量形式明确定义在《JVM规范》的Class文件格式中
>   * 直接引用：指向目标的指针、相对偏移量或一个间接定位到目标的句柄
>   * 解析动作：针对类、接口、字段、类方法、接口方法、方法类型，对应常量池中的CONSTANT_Class_info、CONSTANT_Fieldref_info、CONSTANT_Methodref_info

初始化
>   * 初始化阶段：执行类构造器方法<clinit>()过程
>   * 不需要定义，是javac编译器自动收集类中的所有*静态类变量*的赋值动作，和*静态代码块*中的语句合并而来，若没有，则无<clinit>()
>   * 按照语句在源文件中出现的顺序执行
>   * <clinit>()不同于类的构造器（关联：构造器是虚拟机视角下的<init>())
>   * 若该类具有父类，JVM会保证子类的<clinit>()执行前，父类的<clinit>()已经执行完毕
>   * 虚拟机必须保证一个类的<clinit>()方法在多线程下被同步加锁

#### 类加载器的分类

> * 引导类加载器：C实现的
> * 自定义类加载器：java实现的，将所有派生于抽象类加载器（AbstractClassLoader）的都划分为自定义类加载器

虚拟机自带的加载器
> * 启动类加载器（引导类加载器，BootstrapClassLoader）
>   * 使用C/C++语言实现，嵌套在JVM内部
>   * 用来加载Java的核心库（**JAVA_HOME/jre/lib**/rt.jar、resources.jar或**sun.boot.class.path**路径下的内容），用于提供JVM自身需要的类
>   * 并不继承自java.lang.ClassLoader，没有父加载器
>   * 加载扩展类和应用程序类加载器，并指定为他们的父类加载器
>   * 处于安全考虑，Bootstrap只加载包名为java、javax、sun等开头的类
> * 扩展类加载器（Extension ClassLoader）
>   * Java语言编写，由sun.misc.Launcher$ExtClassLoader实现
>   * 派生于ClassLoader
>   * 父类加载器为启动类加载器
>   * 负责从**java.ext.dirs**所指定的目录、JDK安装目录的jre/lib/ext子目录（用户创建的jar包在该目录下也会自动由扩展类加载器加载）中加载类库
> * 应用程序类加载器（系统类加载器，AppClassLoader）
>   * Java语言编写，由sun.misc.Launcher$AppClassLoader实现
>   * 派生于ClassLoader
>   * 父类加载器为扩展类加载器
>   * 负责从环境变量或java.class.path指定的露肩下加载类库
>   * 程序中默认的类加载器，通过ClassLoader#getSystemClassLoader可以获取到该类加载器

用户自定义类加载器

为什么要定义？
> * 隔离加载类（解决中间件jar包冲突，隔离中间件）
> * 修改类加载的方式（在需要的时候再加载）
> * 扩展加载源（数据库、机顶盒）
> * 防止源码泄漏（加密）

实现步骤
> * 继承java.lang.ClassLoader
> * 建议把类加载逻辑写在findClass方法（避免重写loadClass方法）
> * 没有复杂的需求时，直接继承URLClassLoader类，避免重写findClass和获取字节码流的方式

#### 关于ClassLoader

> * 是一个抽象类，除了启动类加载器，均继承该类
> * 方法：
>   * getParent(): 返回超类加载器
>   * loadClass(String name)：加载名称为name的类，返回Class类的实例
>   * findClass(String name)：查找名称为name的类，返回Class类的实例
>   * findLoadedClass(String name)：查找名称为name的已经被加载过的类，返回Class类的实例
>   * defineClass(String name, byte[] b, int off, int len)：把字节数组b中的内容转换为一个Java类，返回Class类的实例
>   * resolveClass(Class<?> c)：连接指定的一个Java类

获取ClassLoader的方式
> * 获取当前类的ClassLoader：clazz.getClassLoader()
> * 获取当前线程上下文的ClassLoader：Thread.currentThread().getContextClassLoader()
> * 获取系统的CL：ClassLoader.getSystemClassLoader()
> * 获取调用者的CL：DriverManager.getCallerClassLoader()

#### 双亲委派机制
按需加载 -> 双亲委派模式（将由父类处理，任务委派的模式）

工作原理：
> * 一个类加载器收到了类加载请求，它并不会自己去加载，而是委托给父类的加载器去执行
> * 如果父类还存在父类，那进一步委托，依次递归，最终到顶层的启动类加载器
> * 如果父类加载器完成类加载，则成功返回，否则子类尝试自己去加载

优势：
> * 避免类的重复加载
> * 保护程序安全，防止核心API被随意篡改
>   * java.lang.String
>   * java.lang.example (Prohibited)

#### 沙箱安全机制
对java核心源代码的保护机制，该机制称为沙箱安全机制（参考双亲委派机制）

#### 其他
判断两个Class对象是否为同一个类
> * 类的完整类名必须一致，包括包名
> * 加载这个类的ClassLoader（实例对象）必须相同

对类加载器的引用
> * JVM必须知道一个类型是启动加载器加载的还是用户类加载器加载的
> * 如果是用户类加载器
>   * 将该类加载器的一个引用作为类型信息的一部分保存在**方法区**
>   * 解析一个类型到另一个类型的引用时，需要保证这两个类型类加载器是相同的

类的主动使用和被动使用  
主动使用
> * 创建类的实例
> * 访问某个类或接口的静态变量，或者对该静态变量赋值
> * 调用类的静态方法
> * 反射（比如Class.forName("")）
> * 初始化一个类的子类
> * JVM启动时被标明为启动类的类
> * JDK7开始提供的动态语言支持：
>   * java.lang.invoke.MethodHandle实例的解析结果
>   * REF_getStatic、REF_putStatic、REF_invokeStatic句柄对应的类没有初始化，则初始化

被动使用
> 与主动使用区别：不会导致类的初始化


### 3、运行时数据区（Runtime Data Area）

