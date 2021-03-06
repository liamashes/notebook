# 1、java.util
## 1-1、Collection<E>
    extends Iterable<E>

集合层次结构中的根接口。一个集合代表一组对象，称为它的元素。一些集合允许重复元素，而另一些则不允许。有些是有序的，有些是无序的。 JDK 不提供
此接口的任何直接实现：它提供了更具体的子接口（如 Set 和 List）的实现。此接口通常用于传递集合并在需要最大通用性的地方操作它们。
Bags 或 multisets（可能包含重复元素的无序集合）应该直接实现这个接口。

所有通用 Collection 实现类（通常通过其子接口之一间接实现 Collection）应该提供两个“标准”构造函数：一个 void（无参数）构造函数，它创建一
个空集合，以及一个具有单个参数类型的构造函数集合，它创建一个具有与其参数相同的元素的新集合。实际上，后一个构造函数允许用户复制任何集合，生成
所需实现类型的等效集合。没有办法强制执行此约定（因为接口不能包含构造函数），但 Java 平台库中的所有通用 Collection 实现都符合。

此接口中包含的“破坏性”方法，即修改它们操作的集合的方法，如果此集合不支持该操作，则指定为抛出 UnsupportedOperationException。在这种情况
下，如果调用对集合没有影响，则这些方法可能（但不是必需）抛出 UnsupportedOperationException。例如，如果要添加的集合为空，则在不可修改的
集合上调用 addAll(Collection) 方法可能（但不是必需）抛出异常。

一些集合实现对它们可能包含的元素有限制。例如，有些实现禁止空元素，有些实现对其元素的类型有限制。尝试添加不合格的元素会引发未经检查的异常，
通常为 NullPointerException 或 ClassCastException。尝试查询不合格元素的存在可能会引发异常，或者可能只是返回 false；一些实现会表现出
前一种行为，而另一些会表现出后者。更一般地，尝试对不合格元素执行操作，其完成不会导致将不合格元素插入到集合中，这可能会引发异常，或者可能会
成功，具体取决于实现的选择。在此接口的规范中，此类异常被标记为“可选”。

由每个集合来确定自己的同步策略。在没有更强大的实现保证的情况下，未定义的行为可能是由于在另一个线程正在改变的集合上调用任何方法；这包括直接
调用、将集合传递给可能执行调用的方法以及使用现有迭代器检查集合。

Collections Framework 接口中的许多方法都是根据 equals 方法定义的。例如， contains(Object o) 方法的规范说：“当且仅当此集合包含至少一
个元素 e 使得 (o==null ? e==null : o.equals(e)) 时才返回 true .”本规范不应被解释为暗示使用非空参数 o 调用 Collection.contains 将
导致为任何元素 e 调用 o.equals(e)。实现可以自由地实现优化，从而避免 equals 调用，例如，通过首先比较两个元素的哈希码。 
（Object.hashCode() 规范保证哈希码不相等的两个对象不能相等。）更一般地说，各种集合框架接口的实现可以自由地利用底层对象方法的指定行为，只
要实现者认为合适.

一些对集合执行递归遍历的集合操作可能会失败，但对于自引用实例（其中集合直接或间接包含自身）除外。这包括 clone()、equals()、hashCode() 和 
toString() 方法。实现可以选择性地处理自引用场景，但是大多数当前的实现都没有这样做。

此接口是 Java 集合框架的成员。

### 实施要求：
默认方法实现（继承或以其他方式）不应用任何同步协议。如果 Collection 实现具有特定的同步协议，则它必须覆盖默认实现以应用该协议。
### 方法
#### size() int
    返回此集合中的元素数。 如果此集合包含多个 Integer.MAX_VALUE 元素，则返回 Integer.MAX_VALUE。
> * 返回：
>   * 此集合中的元素数

#### isEmpty() boolean
#### contains(Object o) boolean
    如果此集合包含指定的元素，则返回 true。 更正式地说，当且仅当此集合包含至少一个元素 e 使得 (o==null ? e==null : o.equals(e)) 时才
    返回 true。
> * 参数：
>   * o - 要测试其在此集合中是否存在的元素
> * 返回：
>   * 如果此集合包含指定的元素，则为 true
> * 抛出：
>   * ClassCastException - 如果指定元素的类型与此集合不兼容（可选）
>   * NullPointerException - 如果指定的元素为 null 并且此集合不允许 null 元素（可选）
#### iterator() Iterator<E>
    返回此集合中元素的迭代器。 没有关于元素返回顺序的保证（除非这个集合是提供保证的某个类的实例）。

> * 指定者：
>   * 接口 Iterable<E> 中的迭代器
> * 返回：
>   * 此集合中元素的迭代器
#### toArray() Object[]
    返回一个包含此集合中所有元素的数组。 如果此集合对其迭代器返回其元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。
    返回的数组将是“安全的”，因为此集合不维护对它的引用。 （换句话说，即使此集合由数组支持，此方法也必须分配一个新数组）。 因此调用者可以自
    由地修改返回的数组。
    
    此方法充当基于数组和基于集合的 API 之间的桥梁。
    
> * 返回：
>   * 包含此集合中所有元素的数组
#### toArray(T[] a) <T> T[]
    返回一个包含此集合中所有元素的数组；返回数组的运行时类型是指定数组的类型。如果集合适合指定的数组，则在其中返回。否则，将使用指定数组的
    运行时类型和此集合的大小分配一个新数组。
    如果此集合适合指定的数组并有剩余空间（即，该数组的元素比此集合多），则紧跟该集合末尾的数组中的元素将设置为 null。 （仅当调用者知道此集
    合不包含任何空元素时，这才可用于确定此集合的长度。）
    
    如果此集合对其迭代器返回其元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。
    
    与 toArray() 方法一样，该方法充当基于数组和基于集合的 API 之间的桥梁。此外，该方法允许精确控制输出数组的运行时类型，并且在某些情况下
    可用于节省分配成本。
    
    假设 x 是一个已知只包含字符串的集合。以下代码可用于将集合转储到新分配的 String 数组中：
    
         String[] y = x.toArray(new String[0]);
    请注意 toArray(new Object[0]) 在功能上与 toArray() 相同。
> * 类型参数：
>   * T - 包含集合的数组的运行时类型
> * 参数：
>   * a - 要存储此集合的元素的数组，如果它足够大；否则，将为此分配一个相同运行时类型的新数组。
> * 返回：
>   * 包含此集合中所有元素的数组
> * 抛出：
>   * ArrayStoreException - 如果指定数组的运行时类型不是此集合中每个元素的运行时类型的超类型
>   * NullPointerException - 如果指定的数组为 null
