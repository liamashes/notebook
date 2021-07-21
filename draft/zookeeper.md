W3C

[zookeeper](https://www.w3cschool.cn/zookeeper/zookeeper_fundamentals.html)

什么是Apache ZooKeeper？

Apache ZooKeeper是由集群（节点组）使用的一种服务，用于在自身之间协调，并通过稳健的同步技术维护共享数据。ZooKeeper本身是一个分布式应用程序，为写入分布式应用程序提供服务。
ZooKeeper提供的常见服务如下 :
* 命名服务 - 按名称标识集群中的节点。它类似于DNS，但仅对于节点。
* 配置管理 - 加入节点的最近的和最新的系统配置信息。
* 集群管理 - 实时地在集群和节点状态中加入/离开节点。
* 选举算法 - 选举一个节点作为协调目的的leader。
* 锁定和同步服务 - 在修改数据的同时锁定数据。此机制可帮助你在连接其他分布式应用程序（如Apache HBase）时进行自动故障恢复。
* 高度可靠的数据注册表 - 即使在一个或几个节点关闭时也可以获得数据。
分布式应用程序提供了很多好处，但它们也抛出了一些复杂和难以解决的挑战。ZooKeeper框架提供了一个完整的机制来克服所有的挑战。竞争条件和死锁使用故障安全同步方法进行处理。另一个主要缺点是数据的不一致性，ZooKeeper使用原子性解析。
ZooKeeper的好处

以下是使用ZooKeeper的好处：
* 简单的分布式协调过程
* 同步 - 服务器进程之间的相互排斥和协作。此过程有助于Apache HBase进行配置管理。
* 有序的消息
* 序列化 - 根据特定规则对数据进行编码。确保应用程序运行一致。这种方法可以在MapReduce中用来协调队列以执行运行的线程。
* 可靠性
* 原子性 - 数据转移完全成功或完全失败，但没有事务是部分的。
层次命名空间

下图描述了用于内存表示的ZooKeeper文件系统的树结构。ZooKeeper节点称为 znode 。每个znode由一个名称标识，并用路径(/)序列分隔。
* 在图中，首先有一个由“/”分隔的znode。在根目录下，你有两个逻辑命名空间 config 和 workers 。
* config 命名空间用于集中式配置管理，workers 命名空间用于命名。
* 在 config 命名空间下，每个znode最多可存储1MB的数据。这与UNIX文件系统相类似，除了父znode也可以存储数据。这种结构的主要目的是存储同步数据并描述znode的元数据。此结构称为 ZooKeeper数据模型。

Znode兼具文件和目录两种特点。既像文件一样维护着数据长度、元信息、ACL、时间戳等数据结构，又像目录一样可以作为路径标识的一部分。每个Znode由三个部分组成：
* stat：此为状态信息，描述该Znode版本、权限等信息。
* data：与该Znode关联的数据
* children：该Znode下的节点
* 版本号 - 每个znode都有版本号，这意味着每当与znode相关联的数据发生变化时，其对应的版本号也会增加。当多个zookeeper客户端尝试在同一znode上执行操作时，版本号的使用就很重要。
* 操作控制列表(ACL) - ACL基本上是访问znode的认证机制。它管理所有znode读取和写入操作。
* 时间戳 - 时间戳表示创建和修改znode所经过的时间。它通常以毫秒为单位。ZooKeeper从“事务ID"(zxid)标识znode的每个更改。Zxid 是唯一的，并且为每个事务保留时间，以便你可以轻松地确定从一个请求到另一个请求所经过的时间。
* 数据长度 - 存储在znode中的数据总量是数据长度。你最多可以存储1MB的数据。
Znode的类型

Znode被分为持久（persistent）节点，顺序（sequential）节点和临时（ephemeral）节点。
* 持久节点  - 即使在创建该特定znode的客户端断开连接后，持久节点仍然存在。默认情况下，除非另有说明，否则所有znode都是持久的。
* 临时节点 - 客户端活跃时，临时节点就是有效的。当客户端与ZooKeeper集合断开连接时，临时节点会自动删除。因此，只有临时节点不允许有子节点。如果临时节点被删除，则下一个合适的节点将填充其位置。临时节点在leader选举中起着重要作用。
* 顺序节点 - 顺序节点可以是持久的或临时的。当一个新的znode被创建为一个顺序节点时，ZooKeeper通过将10位的序列号附加到原始名称来设置znode的路径。例如，如果将具有路径 /myapp 的znode创建为顺序节点，则ZooKeeper会将路径更改为 /myapp0000000001 ，并将下一个序列号设置为0000000002。如果两个顺序节点是同时创建的，那么ZooKeeper不会对每个znode使用相同的数字。顺序节点在锁定和同步中起重要作用。
Sessions（会话）

会话对于ZooKeeper的操作非常重要。会话中的请求按FIFO顺序执行。一旦客户端连接到服务器，将建立会话并向客户端分配会话ID 。
客户端以特定的时间间隔发送心跳以保持会话有效。如果ZooKeeper集合在超过服务器开启时指定的期间（会话超时）都没有从客户端接收到心跳，则它会判定客户端死机。
会话超时通常以毫秒为单位。当会话由于任何原因结束时，在该会话期间创建的临时节点也会被删除。
Watches（监视）
监视是一种简单的机制，使客户端收到关于ZooKeeper集合中的更改的通知。客户端可以在读取特定znode时设置Watches。Watches会向注册的客户端发送任何znode（客户端注册表）更改的通知。
Znode更改是与znode相关的数据的修改或znode的子项中的更改。只触发一次watches。如果客户端想要再次通知，则必须通过另一个读取操作来完成。当连接会话过期时，客户端将与服务器断开连接，相关的watches也将被删除。

Zookeeper 工作流

一旦ZooKeeper集合启动，它将等待客户端连接。客户端将连接到ZooKeeper集合中的一个节点。它可以是领导或跟随者节点。一旦客户端被连接，节点将向特定客户端分配会话ID并向该客户端发送确认。如果客户端没有收到确认，它将尝试连接ZooKeeper集合中的另一个节点。 一旦连接到节点，客户端将以有规律的间隔向节点发送心跳，以确保连接不会丢失。
* 如果客户端想要读取特定的znode，它将会向具有znode路径的节点发送读取请求，并且节点通过从其自己的数据库获取来返回所请求的znode。为此，在ZooKeeper集合中读取速度快。
* 如果客户端想要将数据存储在ZooKeeper集合中，则会将znode路径和数据发送到服务器。连接的服务器将该请求转发给领导者，然后领导者将向所有的跟随着重新发出写入请求。如果只有大部分节点成功响应，而写入请求成功，则成功返回代码将被发送到客户端。 否则，写入请求失败。绝大多数节点被称为 Quorum 。
ZooKeeper集群中的节点

ZooKeeper集合中可以有不同数量的节点。那么，让我们分析一下在ZooKeeper工作流中更改节点的效果：

* 如果我们有单个节点，那么当该节点失败时，ZooKeeper集群就会失效。这就是为什么不建议在生产环境中使用它，因为它会导致"单点故障"。
* 如果我们有两个节点和一个节点故障，我们就没有多数，因为两个节点中有一个不是多数节点。
* 如果我们有三个节点而一个节点故障，那么我们有大多数，因此，这是最低要求。ZooKeeper集群在实际生产环境中必须至少有三个节点。
* 如果我们有四个节点而两个节点故障，它将再次故障。类似于有三个节点，额外节点不用于任何目的，因此，最好添加奇数的节点，例如3，5，7。
我们知道写入过程比ZooKeeper集合中的读取过程昂贵，因为所有节点都需要在数据库中写入相同的数据。因此，对于平衡的环境拥有较少数量（例如3，5，7）的节点比拥有大量的节点要好。
下图描述了ZooKeeper工作流，后面的表说明了它的不同组件。

组件
描述
写入（write） 
写入过程由leader节点处理。leader将写入请求转发到所有znode，并等待znode的回复。如果一半的znode回复，则写入过程完成。
读取（read） 
读取由特定连接的znode在内部执行，因此不需要与集群进行交互。
复制数据库（replicated database）
它用于在zookeeper中存储数据。每个znode都有自己的数据库，每个znode在一致性的帮助下每次都有相同的数据。
领导者（Leader） 
Leader是负责处理写入请求的Znode。
跟随者（Follower） 
follower从客户端接收写入请求，并将它们转发到leader znode。
请求处理器（request processor）
只存在于leader节点。它管理来自follower节点的写入请求。
原子广播（atomic broadcasts）
负责广播从leader节点到follower节点的变化。


Runoob
1.整体介绍
ZooKeeper 是 Apache 软件基金会的一个软件项目，它为大型分布式计算提供开源的分布式配置服务、同步服务和命名注册。
ZooKeeper 的架构通过冗余服务实现高可用性。
Zookeeper 的设计目标是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。
一个典型的分布式数据一致性的解决方案，分布式应用程序可以基于它实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。

2.zookeeper 数据结构
zookkeeper 提供的名称空间非常类似于标准文件系统，key-value 的形式存储。名称 key 由斜线 / 分割的一系列路径元素，zookeeper 名称空间中的每个节点都是由一个路径标识。


简单来说zookeeper=文件系统+监听通知机制。


1、 文件系统
Zookeeper维护一个类似文件系统的数据结构：


每个子目录项如 NameService 都被称作为 znode(目录节点)，和文件系统一样，我们能够自由的增加、删除znode，在一个znode下增加、删除子znode，唯一的不同在于znode是可以存储数据的。
有四种类型的znode：
PERSISTENT-持久化目录节点
客户端与zookeeper断开连接后，该节点依旧存在
PERSISTENT_SEQUENTIAL-持久化顺序编号目录节点
客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号
EPHEMERAL-临时目录节点
客户端与zookeeper断开连接后，该节点被删除
EPHEMERAL_SEQUENTIAL-临时顺序编号目录节点
客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号
2、 监听通知机制
客户端注册监听它关心的目录节点，当目录节点发生变化（数据改变、被删除、子目录节点增加删除）时，zookeeper会通知客户端。
就这么简单，下面我们看看Zookeeper能做点什么呢？
Zookeeper能做什么

zookeeper功能非常强大，可以实现诸如分布式应用配置管理、统一命名服务、状态同步服务、集群管理等功能，我们这里拿比较简单的分布式应用配置管理为例来说明。
假设我们的程序是分布式部署在多台机器上，如果我们要改变程序的配置文件，需要逐台机器去修改，非常麻烦，现在把这些配置全部放到zookeeper上去，保存在 zookeeper 的某个目录节点中，然后所有相关应用程序对这个目录节点进行监听，一旦配置信息发生变化，每个应用程序就会收到 zookeeper 的通知，然后从 zookeeper 获取新的配置信息应用到系统中。


相关 CAP 理论
CAP 理论指出对于一个分布式计算系统来说，不可能同时满足以下三点：
一致性：在分布式环境中，一致性是指数据在多个副本之间是否能够保持一致的特性，等同于所有节点访问同一份最新的数据副本。在一致性的需求下，当一个系统在数据一致的状态下执行更新操作后，应该保证系统的数据仍然处于一致的状态。
可用性：每次请求都能获取到正确的响应，但是不保证获取的数据为最新数据。
分区容错性：分布式系统在遇到任何网络分区故障的时候，仍然需要能够保证对外提供满足一致性和可用性的服务，除非是整个网络环境都发生了故障。
一个分布式系统最多只能同时满足一致性（Consistency）、可用性（Availability）和分区容错性（Partition tolerance）这三项中的两项。

BASE 理论
BASE 是 Basically Available(基本可用)、Soft-state(软状态) 和 Eventually Consistent(最终一致性) 三个短语的缩写。
基本可用：在分布式系统出现故障，允许损失部分可用性（服务降级、页面降级）。
软状态：允许分布式系统出现中间状态。而且中间状态不影响系统的可用性。这里的中间状态是指不同的 data replication（数据备份节点）之间的数据更新可以出现延时的最终一致性。
最终一致性：data replications 经过一段时间达到一致性。
BASE 理论是对 CAP 中的一致性和可用性进行一个权衡的结果，理论的核心思想就是：我们无法做到强一致，但每个应用都可以根据自身的业务特点，采用适当的方式来使系统达到最终一致性。

Linux 安装
zookeeper 下载地址为: https://zookeeper.apache.org/releases.html。

选择一稳定版本，本教程使用的 release 版本为3.4.14，下载并安装。
打开网址 https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz，看到如下界面：

选择一个下载地址，使用 wget 命令下载并安装：
Zookeeper 下载安装
$ wget https://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz
$ tar -zxvf zookeeper-3.4.14.tar.gz
$ cd zookeeper-3.4.14
$ cd conf/
$ cp zoo_sample.cfg zoo.cfg
$ cd ..
$ cd bin/
$ sh zkServer.sh start
执行后，服务端启动成功：

查看服务端状态（启动单机节点）:

启动客户端:
$ sh zkCli.sh
帮助命令:
ZooKeeper -server host:port cmd args
        stat path [watch]
        set path data [version]
        ls path [watch]
        delquota [-n|-b] path
        ls2 path [watch]
        setAcl path acl
        setquota -n|-b val path
        history 
        redo cmdno
        printwatches on|off
        delete path [version]
        sync path
        listquota path
        rmr path
        get path [watch]
        create [-s] [-e] path data acl
        addauth scheme auth
        quit 
        getAcl path
        close 
        connect host:port

zookeeper 服务端集群搭建
本章节将示范三台 zookeeper 服务端集群搭建步骤。
所需准备工作，创建三台虚拟机环境并安装好 java 开发工具包 JDK，可以使用 VM 或者 vagrant+virtualbox 搭建 centos/ubuntu 环境，本案例基于宿主机 windows10 系统同时使用 vagrant+virtualbox 搭建的 centos7 环境，如果直接使用云服务器或者物理机同理。

步骤一：准备三台 zookeeper 环境和并按照上一教程下载 zookeeper 压缩包，三台集群 centos 环境如下：
机器一：192.168.3.33

机器二：192.168.3.35

机器三：192.168.3.37

提示: 查看 ip 地址可以用 ifconfig 命令。
步骤二：别修改 zoo.cfg 配置信息
zookeeper 的三个端口作用
1、2181 : 对 client 端提供服务
2、2888 : 集群内机器通信使用
3、3888 : 选举 leader 使用
按 server.id = ip:port:port 修改集群配置文件:
三台虚拟机 zoo.cfg 文件末尾添加配置:
server.1=192.168.3.33:2888:3888
server.2=192.168.3.35:2888:3888
server.3=192.168.3.37:2888:3888
根据 id 和对应的地址分别配置 myid
vim /tmp/zookeeper/myid
本案例配置完成后查询显示如下：
IP 192.168.3.33 机器配置 myid，因为这台机器上个教程单机启动过，所以出现 version-2，没有也没关系。

IP 192.168.3.35 机器配置 myid

IP192.168.3.37 机器配置 myid

步骤三：启动集群
启动前需要关闭防火墙(生产环境需要打开对应端口)
systemctl stop firewalld
启动 192.168.3.33 并查看日志，此时日志出现报错是正常现象，因为另外两台还没启动，暂时连接不上。

另两台分别启动后，查看三台机器状态：
IP 192.168.3.33

IP 192.168.3.35

IP 192.168.3.37

最后显示集群搭建成功！Mode：leader 代表主节点，follower 代表从节点，一主二从。

Zookeeper伪集群模式安装

https://blog.csdn.net/java_66666/article/details/81015302
