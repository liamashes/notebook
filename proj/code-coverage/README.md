# 基于WEBJOIN的代码覆盖率及报告获取
## 1、概述
    Jacoco 是一个开源的覆盖率工具，支持on the fly和offline模式，使用asm修改字节码从而达到监测代码执行率的功能。
    本功能基于Jacoco工具的功能特点，和WEBJOIN进行结合，使用on the fly模式将代码覆盖率的功能集成到WEBJOIN中。

## 2、Jacoco

### 2.1、关于计量维度
	Jacoco 包含了多种尺度的覆盖率计数器,包含指令级（Instructions,C0 coverage），分支（Branches,C1 coverage）、圈复杂度（Cyclomatic Complexity）、行（Lines）、方法（Non-abstract Methods）、类（Classes）。
    ➢ Instructions：Jacoco 计算的最小单位就是字节码指令。指令覆盖率表明了在所有的指令中，哪些被执行过以及哪些没有被执行。这项指数完全独立于源码格式并且在任何情况下有效，不需要类文件的调试信息。
    ➢ Branches：Jacoco 对所有的 if 和 switch 指令计算了分支覆盖率。这项指标会统计所有的分支数量，并同时支出哪些分支被执行，哪些分支没有被执行。这项指标也在任何情况都有效。异常处理不考虑在分支范围内。
        * 在有调试信息的情况下，分支点可以被映射到源码中的每一行，并且被高亮表示。
        * 红色钻石：无覆盖，没有分支被执行。
        * 黄色钻石：部分覆盖，部分分支被执行。
        * 绿色钻石：全覆盖，所有分支被执行。
    ➢ Cyclomatic Complexity：Jacoco 为每个非抽象方法计算圈复杂度，并也会计算每个类、包、组的复杂度。根据 McCabe 1996 的定义，圈复杂度可以理解为覆盖所有的可能情况最少使用的测试用例数。这项参数也在任何情况下有效。
    ➢ Lines：该项指数在有调试信息的情况下计算。
        * 因为每一行代码可能会产生若干条字节码指令，所以我们用三种不同状态表示行覆盖率
        * 红色背景：无覆盖，该行的所有指令均无执行。
        * 黄色背景：部分覆盖，该行部分指令被执行。
        * 绿色背景：全覆盖，该行所有指令被执行。
    ➢ Methods：每一个非抽象方法都至少有一条指令。若一个方法至少被执行了一条指令，就认为它被执行过。因为 Jacoco 直接对字节码进行操作，所以有些方法没有在源码显示（比如某些构造方法和由编译器自动生成的方法）也会被计入在内。
    ➢ Classes：每个类中只要有一个方法被执行，这个类就被认定为被执行。同 5 一样，有些没有在源码声明的方法被执行，也认定该类被执行。

### 2.2、Jacoco 原理

    参考资料：
    
> * [浅谈代码覆盖率](http://tech.youzan.com/code-coverage/)
> * [Jacoco 的原理](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.cnblogs.com%2Fkingzzm%2Fp%2F3321721.html)
> * [Java 代码覆盖率工具 JaCoCo 原理篇](https://links.jianshu.com/go?to=https%3A%2F%2Fcloud.tencent.com%2Fdeveloper%2Farticle%2F1038055)

## 3、功能介绍
### 3.1、参数说明：

> * 【配置项】：用来区分不同webjoin，即一个WEBJOIN环境对应一个【配置项】
> *     例，测试环境分86和89环境，那么对应的【配置项】便是86、89，配置文件为：config-86.sh、config-89.sh
> * 【任务名称】：用于区分每次测试任务，如果使用同一个任务名称，会默认进行dump信息追加，该名称也是生成最后报告的压缩文件名。
> *     例，今日是2021年12月15日，测试版本为营账系统v4.1.1版本，那么任务名称可以命名为：task-miguacct-v4.1.1-20211215

### 3.2、目录说明：
> * bin:
> *     存放项目需要使用的源码及其他功能，当前tomcat.rb的模版文件存放于此
> * conf:
> *     存放功能通用配置及【配置项】对应的配置，其中【配置项】对应的配置文件命名方式为：config-【配置项】.sh
> * jacoco-X.X.X:
> *     jacoco版本文件及jar包
> * report:
> *     存放以【任务名称】命名的任务报告
> * sourcecode:  
> *     存放【编译后的项目文件（class）】及【项目源码】
>   例（均来源于项目本地编译后的目录）：
>   * 	【编译后的项目文件（class）】：target/classes/com
>   * 	【项目源码】：src/main/java
> * zip:
> *     存放以【任务名称】命名的任务报告压缩包
		
### 3.3、功能说明：
    预制条件：
        以下执行命令的前提是基于代码目录放在用户的home目录下

> 1、服务启动（针对开发、运维人员）
> *	    执行命令：
>   * sh ~/codecoverage/restart.sh 【配置项】
> *     注意事项（具体参考配置文件）：
>   * 1、【配置项】中需要指定 WEBJOIN的工作目录（绝对路径）、重启脚本（相对路径）、监测包名、jacoco服务端口、编译后的CLASS路径、源码路径
>   * 2、重启脚本需要支持绝对路径调用，在我们项目中因为需要调用同路径下的其他脚本，因此需要提前加上 cd $(dirname "$0")
>   * 3、jacoco服务端口需要提前规划，同一台机器不同的webjoin端口不能一致
>   * 4、编译后的CLASS路径、源码路径分别到com、java级别，具体参考配置
			
> 2、报告获取（针对开发、测试人员）
> *	    执行命令：
>   * sh ~/codecoverage/dump.sh 【配置项】 【任务名称】
> *     注意事项：
>   * 1、参考【任务名称】的说明
>   * 2、文件在生成后会下载到crt默认的本地目录，查询方式：Options -> Session Options -> Terminal -> X/Y/Zmodem 
>   * 3、报告下载后，打开 html-report 中的 index.html 即可查看（虚拟桌面建议使用edge浏览器）
