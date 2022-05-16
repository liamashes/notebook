> * 背景
> * 关系型数据库
> * NoSQL
> * 快速应用开发
> * 大数据
> * 数据网格

> * 文档数据库
> * 图数据库
> * 键/值存储
> * hadoop
> * GemFire

阅读顺序

介绍项目 -> 阐述SpringSource及团队背后驱动力 -> 示例工程的领域模型 

SpringDataRepository的基本理念 -> Querydsl

介绍传统的持久化技术: JPS、JDBC -> 基础上提供的新特性

NoSQL存储: 以MongoDB为例介绍文档数据库 -> 以Neo4j讲解图数据库 -> 以Redis为例讲解键/值存储 -> 列族数据库HBase
【领域类 -> 特定存储的数据结构 -> 通过API与存储交互 -> 对Repository抽象的使用】

Spring Data REST导出器、Spring Roo集成 -> 基于Repository抽象，将Spring Data管理的实体导出到Web之中（表述性状态转移）

大数据：Hadoop和Spring for Apache Hadoop -> Spring Data简化Hadoop使用 -> 使用Spring Batch和Spring Integration构建复杂的大数据管道

Spring Data对GemFire的支持：分布式的数据网格解决方案
