# 最后一个单位（Unit in the last place）
    在计算机科学和数值分析中，最后一位单位或最小精度单位（ULP）是两个连续浮点数之间的间距，即最低有效位（最右边的数字）为 1 时表示的值。 
    在数值计算中被用作衡量准确性的指标。 [1]
## 定义
    一种定义是：在精度为 p 的基数 b 中，如果 be ≤ |x| < be+1，则 ULP(x) = bmax(e,emin)−p+1.[2]
    
    John Harrison 提出的另一个定义略有不同：ULP(x) 是两个最接近的跨浮点数 a 和 b（即 a ≤ x ≤ b 且 a ≠ b 的那些）之间的距离，假设指数
    范围没有上限。[3][4]这些定义仅在基数的有符号幂上有所不同。 [2]
    
    所有现代浮点硬件都遵循 IEEE 754 规范，要求对基本算术运算（自 1985 年以来的加法、减法、乘法、除法和平方根，以及自 2008 年以来的 FMA）
    的结果进行正确舍入，这意味着在四舍五入到最接近时，四舍五入的结果在数学上精确结果的 0.5 ULP 以内，使用 John Harrison 的定义；相反，
    这个属性意味着舍入结果和数学上精确结果之间的距离被最小化（但对于中间情况，它由两个连续的浮点数来满足）。信誉良好的数字库将基本超越函数
    计算到 0.5 到大约 1 ULP 之间。只有少数库在 0.5 ULP 内计算它们，由于 Table-maker 的困境，这个问题很复杂。 [5]

## 示例
### 示例1
让 x 是一个正浮点数，并假设活动舍入模式是舍入到最近的，与偶数相关，表示为 RN。 如果 ULP(x) 小于或等于 1，则 RN(x + 1) > x。 否则，
RN(x + 1) = x 或 RN(x + 1) = x + ULP(x)，取决于最低有效数字的值和 x 的指数。 在交互式提示下键入的以下 Haskell 代码中演示了这一点：
[需要引用]

```text
> until (\x -> x == x+1) (+1) 0 :: Float
1.6777216e7
> it-1
1.6777215e7
> it+1
1.6777216e7
```

这里我们从单精度的0开始，反复加1，直到操作不改变值。 由于单精度数的有效数包含 24 位，因此第一个不能精确表示的整数是 224+1，并且该值四舍五
入到最接近的 224，与偶数相关联。 因此结果等于 224。
### 示例2
Java 中的以下示例通过查找包围 π 的两个双精度值将 π 近似为浮点值：
> p0 < π < p1

```java
// π with 20 decimal digits
BigDecimal π = new BigDecimal("3.14159265358979323846");

// truncate to a double floating point
double p0 = π.doubleValue();
// -> 3.141592653589793  (hex: 0x1.921fb54442d18p1)

// p0 is smaller than π, so find next number representable as double
double p1 = Math.nextUp(p0);
// -> 3.1415926535897936 (hex: 0x1.921fb54442d19p1)
```

然后 ULP(π) 被确定为
> ULP(π) = p1 - p0

```java
// ulp(π) is the difference between p1 and p0
BigDecimal ulp = new BigDecimal(p1).subtract(new BigDecimal(p0));
// -> 4.44089209850062616169452667236328125E-16
// (this is precisely 2**(-51))

// same result when using the standard library function
double ulpMath = Math.ulp(p0);
// -> 4.440892098500626E-16 (hex: 0x1.0p-51)
```
### 示例3
另一个例子，在 Python 中，也是在交互式提示下输入的，是：[需要引用]

```python
>>> x = 1.0
>>> p = 0
>>> while x != x + 1:
...   x = x * 2
...   p = p + 1
... 
>>> x
9007199254740992.0
>>> p
53
>>> x + 2 + 1
9007199254740996.0
```

在这种情况下，我们从 x = 1 开始并重复将其加倍，直到 x = x + 1。与示例 1 类似，结果为 253，因为双精度浮点格式使用 53 位有效数。

## 语言支持
Boost C++ 库提供了函数 boost::math::float_next、boost::math::float_prior、boost::math::nextafter 和 boost::math::float_advance 
来获取附近（和远处）的浮点值， [6] 和 boost::math::float_distance(a, b) 计算两个双精度之间的浮点距离。 [7]

C 语言库提供了计算某个给定方向上的下一个浮点数的函数：nextafterf 和 nexttowardf 用于 float，nextafter 和 nexttoward 用于 double，
nextafterl 和 nexttowardl 用于 long double，在 <math.h> 中声明。它还提供了宏 FLT_EPSILON、DBL_EPSILON、LDBL_EPSILON，它们表示 
1.0 和相应类型中下一个更大的可表示数（即 1 的 ULP）之间的正差。 [8]

Java 标准库提供函数 Math.ulp(double) 和 Math.ulp(float)。它们是在 Java 1.5 中引入的。

Swift 标准库通过实例属性 nextDown 和 nextUp 提供对某个给定方向下一个浮点数的访问。它还为 Swift 的浮点类型提供了实例属性 ulp 和类型属性
 ulpOfOne（对应于 FLT_EPSILON[9] 等 C 宏）。 [10]
