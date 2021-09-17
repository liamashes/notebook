# The Collections Framework
    集合框架是用于表示和操作集合的统一架构，使它们能够独立于其表示的细节进行操作。 它减少了编程工作，同时提高了性能。 它支持无关 API 之间
    的互操作性，减少设计和学习新 API 的工作量，并促进软件重用。 该框架基于十几个集合接口。 它包括这些接口的实现和操作它们的算法。

## 概述
> [概述](#集合概述) - 集合框架的概述。

## API规范
> [API 参考](#集合API参考) - 包含集合框架的类和接口的带注释的大纲，以及指向 JavaDoc 的链接。
## 教程和程序员指南
> [Tutorial](#集合教程) - 包含许多编程示例的集合框架的教程介绍。
## API 增强功能
> * [Java SE 8 中的 API 增强](#集合API增强) - 版本 7 和 8 之间 API 更改的带注释列表。
> * Java SE 7 中的 API 增强 - 版本 6 和 7 之间 API 更改的带注释列表。
> * Java SE 6 中的 API 增强 - 版本 5.0 和 6 之间 API 更改的带注释列表。
> * J2SE 5.0 中的 API 增强 - 版本 1.4 和 5.0 之间 API 更改的带注释列表。
> * J2SDK 1.4 中的 API 增强 - 版本 1.3 和 1.4 之间 API 更改的带注释列表。
## 更多信息
> * [设计常见问题解答](#集合常见问题) - 有关集合框架设计的常见问题解答 (FAQ)。


# 详细
## 集合概述
### 介绍
Java 平台包括一个集合框架。集合是代表一组对象的对象（例如经典的 Vector 类）。集合框架是用于表示和操作集合的统一架构，使集合能够独立于实现
细节进行操作。
集合框架的主要优点是：
> * 通过提供数据结构和算法来减少编程工作，因此您不必自己编写它们。
> * 通过提供数据结构和算法的高性能实现来提高性能。因为每个接口的各种实现是可以互换的，所以可以通过切换实现来调整程序。
> * 通过建立一种公共语言来来回传递集合，在不相关的 API 之间提供互操作性。
> * 通过要求您学习多个临时集合 API 来减少学习 API 所需的工作量。
> * 无需您生成临时集合 API，从而减少设计和实现 API 所需的工作量。
> * 通过为集合和操作它们的算法提供标准接口来促进软件重用。

集合框架包括：
> * 采集接口。表示不同类型的集合，例如集合、列表和映射。这些接口构成了框架的基础。
> * 通用实现。集合接口的主要实现。
> * 遗留实现。早期版本中的集合类 Vector 和 Hashtable 进行了改造以实现集合接口。
> * 特殊用途的实现。设计用于特殊情况的实现。这些实现显示了非标准的性能特征、使用限制或行为。
> * 并发实现。为高度并发使用而设计的实现。
> * 包装器实现。向其他实现添加功能，例如同步。
> * 方便的实现。集合接口的高性能“迷你实现”。
> * 抽象实现。集合接口的部分实现，以方便自定义实现。
> * 算法。对集合执行有用功能的静态方法，例如对列表进行排序。
> * 基础设施。为集合接口提供基本支持的接口。
> * 阵列实用程序。基本类型和引用对象数组的实用函数。严格来说，不是集合框架的一部分，这个特性是与集合框架同时添加到 Java 平台的，并且依赖于
>一些相同的基础设施。

### Collection接口
采集接口分为两组。最基本的接口 java.util.Collection 具有以下后代：
> * java.util.Set
> * java.util.SortedSet
> * java.util.NavigableSet
> * java.util.Queue
> * java.util.concurrent.BlockingQueue
> * java.util.concurrent.TransferQueue
> * java.util.Deque
> * java.util.concurrent.BlockingDeque

其他集合接口基于 java.util.Map 并且不是真正的集合。但是，这些接口包含集合视图操作，这使它们能够作为集合进行操作。 Map有以下后代：
> * java.util.SortedMap
> * java.util.NavigableMap
> * java.util.concurrent.ConcurrentMap
> * java.util.concurrent.ConcurrentNavigableMap

集合接口中的许多修改方法都被标记为可选的。允许实现不执行这些操作中的一个或多个，如果尝试将抛出运行时异常 (UnsupportedOperationException)。
每个实现的文档必须指定支持哪些可选操作。引入了几个术语来帮助本规范：
> * 不支持修改操作（如添加、删除和清除）的集合被称为不可修改的。不可修改的集合是可修改的。
> * 额外保证 Collection 对象中的任何更改都不可见的集合称为不可变的。不可变的集合是可变的。
> * 即使元素可以更改，也能保证其大小保持不变的列表称为固定大小。非固定大小的列表称为可变大小。
> * 支持快速（通常是恒定时间）索引元素访问的列表称为随机访问列表。不支持快速索引元素访问的列表称为顺序访问列表。 RandomAccess 标记接口使
>列表能够宣传它们支持随机访问的事实。这使得通用算法能够改变它们的行为，以在应用于随机或顺序访问列表时提供良好的性能。

一些实现限制了可以存储哪些元素（或者在 Maps 的情况下，键和值）。可能的限制包括要求元素：
> * 属于特定类型。
> * 不为空。
> * 服从一些任意的谓词。

尝试添加违反实现限制的元素会导致运行时异常，通常是 ClassCastException、IllegalArgumentException 或 NullPointerException。尝试删除
或测试违反实现限制的元素的存在可能会导致异常。一些受限制的集合允许这种用法。

### 集合实现
实现集合接口的类通常具有 <Implementation-style><Interface> 形式的名称。 下表总结了通用实现：
> interface | Hash Table | Resizable Array | Balanced Tree | Linked List | Hash Table + Linked List
> --------- | ---------- | --------------- | ------------- | ----------- | ------------------------
> Set | HashSet | | TreeSet | | LinkedHashSet
> List | | ArrayList | | LinkedList |
> Deque | | ArrayDeque | | LinkedList |
> Map | HashMap | | TreeMap | | LinkedHashMap

通用实现支持集合接口中的所有可选操作，并且对它们可能包含的元素没有限制。它们是未同步的，但 Collections 类包含称为同步包装器的静态工厂，可
用于向许多未同步的集合添加同步。所有新实现都有快速失败迭代器，它检测无效的并发修改，并快速干净地失败（而不是行为不规律）。

AbstractCollection、AbstractSet、AbstractList、AbstractSequentialList 和 AbstractMap 类提供核心集合接口的基本实现，以最大限度地
减少实现它们所需的工作。这些类的 API 文档精确地描述了每个方法是如何实现的，因此实现者知道哪些方法必须被覆盖，给定特定实现的基本操作的性能。

### 并发集合
使用来自多个线程的集合的应用程序必须仔细编程。通常，这称为并发编程。 Java 平台包括对并发编程的广泛支持。有关详细信息，请参阅 Java 并发实用
程序。

集合的使用非常频繁，以至于 API 中包含了各种并发友好的接口和集合的实现。这些类型超越了前面讨论的同步包装器，提供了并发编程中经常需要的特性。

这些并发感知接口可用：

> * 阻塞队列(BlockingQueue)
> * 传输队列(TransferQueue)
> * 阻塞队列(BlockingDeque)
> * 并发映射(ConcurrentMap)
> * ConcurrentNavigableMap

以下并发感知实现类可用。有关这些实现的正确用法，请参阅 API 文档。
> * 链接阻塞队列
> * 数组阻塞队列
> * 优先阻塞队列
> * 延迟队列
> * 同步队列
> * LinkedBlockingDeque
> * 链接传输队列
> * 复制写入数组列表
> * 复制写入数组集
> * 并发跳过列表集
> * 并发哈希映射
> * 并发跳过列表映射

### 设计目标
主要设计目标是生产体积小，更重要的是“概念重量”的 API。至关重要的是，新功能与当前的 Java 程序员没有太大区别；它必须增加现有设施，而不是更换
它们。同时，新的 API 必须足够强大以提供之前描述的所有优势。

为了保持核心接口的数量较少，接口不会尝试捕捉诸如可变性、可修改性和可调整大小之类的细微区别。相反，核心接口中的某些调用是可选的，使实现能够抛
出 UnsupportedOperationException 以指示它们不支持指定的可选操作。集合实现者必须清楚地记录实现支持哪些可选操作。

为了保持每个核心接口中方法的数量较少，只有在以下任一情况下，接口才包含一个方法：
> * 这是一个真正的基本操作：一个可以合理定义其他操作的基本操作，
> * 重要的实现想要覆盖它有一个令人信服的性能原因。

集合的所有合理表示都能很好地互操作是至关重要的。这包括数组，不能在不改变语言的情况下直接实现 Collection 接口。因此，该框架包括使集合能够移
动到数组中、将数组视为集合以及将映射视为集合的方法。

## 集合API参考
集合框架包括：
> * *Collection interfaces（集合接口）* - 操作集合的主要方式。
>   * Collection - 一组对象。不假设集合的顺序（如果有）或它是否可以包含重复元素。
>   * Set - 熟悉的集合抽象。不允许重复元素。可能会或可能不会被订购。扩展集合接口。
>   * List - 有序集合，也称为序列。通常允许重复。允许位置访问。扩展集合接口。
>   * Queue - 设计用于在处理之前保存元素的集合。除了基本的 Collection 操作，队列还提供额外的插入、提取和检查操作。
>   * Deque - 双端队列，支持两端元素的插入和移除。扩展队列接口。
>   * Map - 从键到值的映射。每个键可以映射到一个值。
>   * SortedSet - 一个集合，其元素自动排序，或者按照它们的自然顺序（参见 Comparable 接口）或者按照创建 SortedSet 实例时提供的 
>Comparator 对象进行排序。扩展 Set 接口。
>   * SortedMap - 映射自动按键排序的映射，使用键的自然顺序或创建 SortedMap 实例时提供的比较器。扩展地图界面。
>   * NavigableSet - SortedSet 扩展了导航方法，报告给定搜索目标的最接近匹配。 NavigableSet 可以按升序或降序访问和遍历。
>   * NavigableMap - SortedMap 扩展了导航方法，返回给定搜索目标的最接近的匹配项。可以按升序或降序键顺序访问和遍历 NavigableMap。
>   * BlockingQueue - 具有在检索元素时等待队列变为非空以及在存储元素时等待队列中可用空间的操作的队列。 （这个接口是 java.util.concurrent 
>包的一部分。）
>   * TransferQueue - 一个 BlockingQueue，生产者可以在其中等待消费者接收元素。 （这个接口是 java.util.concurrent 包的一部分。）
>   * BlockingDeque - 一个 Deque，其操作在检索元素时等待双端队列变为非空，并在存储元素时等待双端队列中的空间变为可用。扩展 Deque 和 
>BlockingQueue 接口。 （这个接口是 java.util.concurrent 包的一部分。）
>   * ConcurrentMap - 具有原子 putIfAbsent、remove 和 replace 方法的 Map。 （这个接口是 java.util.concurrent 包的一部分。）
>   * ConcurrentNavigableMap - 一个 ConcurrentMap，也是一个 NavigableMap。
> * *General-purpose implementations（通用实现）* - 集合接口的主要实现。
>   * HashSet - Set 接口的哈希表实现。 Set 接口的最佳全面实现。
>   * TreeSet - NavigableSet 接口的红黑树实现。
>   * LinkedHashSet - Set 接口的哈希表和链表实现。一个插入顺序的 Set 实现，其运行速度几乎与 HashSet 一样快。
>   * ArrayList - List 接口的可调整大小的数组实现（非同步向量）。 List 接口的最佳全面实现。
>   * ArrayDeque - Deque 接口的高效、可调整大小的数组实现。
>   * LinkedList - List 接口的双向链表实现。如果元素在列表中频繁插入或删除，则提供比 ArrayList 实现更好的性能。还实现了 Deque 接口。
>通过 Queue 接口访问时，LinkedList 充当 FIFO 队列。
>   * PriorityQueue - 无界优先级队列的堆实现。
>   * HashMap - Map 接口的哈希表实现（支持空键和值的非同步哈希表）。 Map 接口的最佳全面实现。
>   * TreeMap NavigableMap 接口的红黑树实现。
>   * LinkedHashMap - Map 接口的哈希表和链表实现。一个插入排序的 Map 实现，其运行速度几乎与 HashMap 一样快。也可用于构建缓存（请参阅 
>removeEldestEntry(Map.Entry) ）。
> * *Wrapper implementations（包装器实现）* - 与其他实现一起使用的功能增强实现。仅通过静态工厂方法访问。
>   * Collections.unmodifiableInterface - 返回指定集合的​​不可修改视图，如果用户尝试修改它，则会抛出 UnsupportedOperationException。
>   * Collections.synchronizedInterface - 返回由指定（通常是非同步）集合支持的同步集合。只要对后备集合的所有访问都是通过返回的集合，就
>可以保证线程安全。
>   * Collections.checkedInterface - 返回指定集合的​​动态类型安全视图，如果客户端尝试添加错误类型的元素，则会抛出 ClassCastException。
>该语言中的泛型机制提供了编译时（静态）类型检查，但可以绕过此机制。动态类型安全视图消除了这种可能性。
> * *Adapter implementations（适配器实现）* - 使一个集合接口适应另一个集合的实现：
>   * newSetFromMap(Map) - 从通用 Map 实现创建通用 Set 实现。
>   * asLifoQueue(Deque) - 将 Deque 的视图作为后进先出 (LIFO) 队列返回。
> * *Convenience implementations（便利实现）* - 集合接口的高性能“迷你实现”。
>   * Arrays.asList - 允许将数组视为列表。
>   * emptySet、emptyList 和 emptyMap - 返回一个不可变的空集、列表或映射。
>   * singleton、singletonList 和 singletonMap - 返回一个不可变的单例集合、列表或映射，仅包含指定的对象（或键值映射）。
>   * nCopies - 返回由指定对象的 n 个副本组成的不可变列表。
> * *Legacy implementations（遗留实现）* - 旧的集合类被改造以实现集合接口。
>   * Vector - List 接口的同步可调整大小的数组实现与其他遗留方法。
>   * Hashtable - Map 接口的同步哈希表实现，不允许空键或值，以及其他遗留方法。
> * *Special-purpose implementations（特殊用途的实现）*
>   * WeakHashMap - Map 接口的实现，仅存储对其键的弱引用。仅存储弱引用可以在 WeakHashMap 之外不再引用键时对键值对进行垃圾回收。这个类
>是使用弱引用的最简单的方法。它对于实现类似注册表的数据结构很有用，当任何线程不再可以访问其键时，条目的效用就会消失。
>   * IdentityHashMap - 基于哈希表的基于身份的 Map 实现。此类对于保留拓扑的对象图转换（例如序列化或深度复制）很有用。要执行这些转换，您
>必须维护一个基于身份的“节点表”，以跟踪已经看到哪些对象。基于身份的映射还用于在动态调试器和类似系统中维护对象到元信息的映射。最后，基于身份
>的映射可用于防止由故意反常的 equals 方法导致的“欺骗攻击”。 （IdentityHashMap 从不调用它的键上的 equals 方法。）这种实现的另一个好处是
>它很快。
>   * CopyOnWriteArrayList - 由写时复制数组支持的 List 实现。所有可变操作（例如添加、设置和删除）都是通过创建数组的新副本来实现的。即
>使在迭代期间也不需要同步，并且保证迭代器永远不会抛出 ConcurrentModificationException。此实现非常适合维护事件处理程序列表（其中更改不
>频繁，遍历频繁且可能耗时）。
>   * CopyOnWriteArraySet - 由写时复制数组支持的 Set 实现。此实现类似于 CopyOnWriteArrayList。与大多数 Set 实现不同，add、remove 
>和 contains 方法需要的时间与 set 的大小成正比。此实现非常适合维护必须防止重复的事件处理程序列表。
>   * EnumSet - 由位向量支持的高性能 Set 实现。每个 EnumSet 实例的所有元素都必须是单个枚举类型的元素。
>   * EnumMap - 由数组支持的高性能 Map 实现。每个 EnumMap 实例中的所有键都必须是单个枚举类型的元素。
> * *Concurrent implementations（并发实现）* - 这些实现是 java.util.concurrent 的一部分。
>   * ConcurrentLinkedQueue - 基于链接节点的无界先进先出 (FIFO) 队列。
>   * LinkedBlockingQueue - 由链接节点支持的可选有界 FIFO 阻塞队列。
>   * ArrayBlockingQueue - 由数组支持的有界 FIFO 阻塞队列。
>   * PriorityBlockingQueue - 由优先级堆支持的无界阻塞优先级队列。
>   * DelayQueue - 由优先级堆支持的基于时间的调度队列。
>   * SynchronousQueue - 一种使用 BlockingQueue 接口的简单集合机制。
>   * LinkedBlockingDeque - 由链接节点支持的可选有界 FIFO 阻塞双端队列。
>   * LinkedTransferQueue - 由链接节点支持的无界 TransferQueue。
>   * ConcurrentHashMap - 基于哈希表的高并发、高性能 ConcurrentMap 实现。此实现在执行检索时从不阻塞，并使客户端能够选择更新的并发级别。
>它旨在替代 Hashtable。除了实现 ConcurrentMap 之外，它还支持 Hashtable 的所有遗留方法。
>   * ConcurrentSkipListSet - 跳过 NavigableSet 接口的列表实现。
>   * ConcurrentSkipListMap - 跳过 ConcurrentNavigableMap 接口的列表实现。
> * *Abstract implementations（抽象实现）* - 集合接口的骨架实现，以促进自定义实现。
>   * AbstractCollection - 既不是集合也不是列表（例如“包”或多重集）的骨架集合实现。
>   * AbstractSet - 骨架集实现。
>   * AbstractList - 由随机访问数据存储（例如数组）支持的骨架列表实现。
>   * AbstractSequentialList - 由顺序访问数据存储（例如链表）支持的骨架列表实现。
>   * AbstractQueue - 骨架队列实现。
>   * AbstractMap - 骨架图实现。
> * *Algorithms（算法）* - Collections 类包含这些有用的静态方法。
>   * sort(List) - 使用合并排序算法对列表进行排序，该算法提供与高质量快速排序相当的平均情况性能，保证 O(n*log n) 性能（与快速排序不同）
>和稳定性（与快速排序不同）。稳定排序是不重新排序相等元素的排序。
>   * binarySearch(List, Object) - 使用二分搜索算法在有序列表中搜索元素。
>   * reverse(List) - 反转列表中元素的顺序。
>   * shuffle(List) - 随机改变列表中元素的顺序。
>   * fill(List, Object) - 用指定的值覆盖列表中的每个元素。
>   * copy(List dest, List src) - 将源列表复制到目标列表中。
>   * min(Collection) - 返回集合中的最小元素。
>   * max(Collection) - 返回集合中的最大元素。
>   * rotate(List list, int distance) - 按指定距离旋转列表中的所有元素。
>   * replaceAll(List list, Object oldVal, Object newVal) - 用另一个替换所有出现的指定值。
>   * indexOfSubList(List source, List target) - 返回等于目标的源的第一个子列表的索引。
>   * lastIndexOfSubList(List source, List target) - 返回等于目标的最后一个源子列表的索引。
>   * swap(List, int, int) - 交换指定列表中指定位置的元素。
>   * frequency(Collection, Object) - 计算指定元素在指定集合中出现的次数。
>   * disjoint(Collection, Collection) - 确定两个集合是否不相交，换句话说，它们是否不包含共同的元素。
>   * addAll(Collection<? super T>, T...) - 将指定数组中的所有元素添加到指定集合中。
> * *Infrastructure（基础设施）*
>   * *Iterators（迭代器）* - 类似于熟悉的 Enumeration 接口，但功能更强大，并具有改进的方法名称。
>       * Iterator - 除了 Enumeration 接口的功能之外，还使用户能够从具有明确定义的有用语义的后备集合中删除元素。
>       * ListIterator - 用于列表的迭代器。除了 Iterator 接口的功能外，还支持双向迭代、元素替换、元素插入和索引检索。
>   * *Ordering（排序）*
>       * Comparable - 为实现它的类赋予自然顺序。自然排序可用于对列表进行排序或维护已排序集合或映射中的顺序。许多类都经过改造以实现此接口。
>       * Comparator - 表示顺序关系，可用于对列表进行排序或维护已排序集合或映射中的顺序。可以覆盖类型的自然排序或未实现 Comparable 接
>口的类型的排序对象。
>   * *Runtime Exceptions（运行时异常）*
>       * UnsupportedOperationException - 如果调用了不受支持的可选操作，则由集合抛出。
>       * ConcurrentModificationException - 如果在迭代过程中后备集合意外更改，则由迭代器和列表迭代器抛出。如果后备列表意外更改，也会
>由列表的子列表视图抛出。
>   * *Performance（性能）*
>       * RandomAccess - 标记接口，让 List 实现表明它们支持快速（通常是恒定时间）随机访问。这让通用算法在应用于随机或顺序访问列表时可以
>改变它们的行为以提供良好的性能。
> * *Array Utilities（数组实用程序）*
>   * Arrays - 包含用于排序、搜索、比较、散列、复制、调整大小、转换为字符串以及填充基元和对象数组的静态方法。

## 集合教程

## 集合API增强

## 集合常见问题
