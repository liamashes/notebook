### jdk 9
> * 更简洁的 try-with-resources 语句

```java
class Test{
    public static String test(){
        // A final resource
        final Resource resource1 = new Resource("resource1");
        // An effectively final resource
        Resource resource2 = new Resource("resource2");
        
        // In Java SE 7 or 8
        try (Resource r1 = resource1;
             Resource r2 = resource2) {
            //...
        }

        // New and improved try-with-resources statement in Java SE 9
        try (resource1;
             resource2) {
            //...
        }
    }
}
```

> * 私有实例方法上允许使用 @SafeVarargs 注释

私有实例方法上允许使用 @SafeVarargs 注释。 它只能应用于不能被覆盖的方法。 其中包括静态方法、最终实例方法，以及 Java SE 9 中的新增私有实例方法。

> * 钻石语法和匿名内部类

您可以将菱形语法与匿名内部类结合使用。 可以在 Java 程序中编写的类型，例如 int 或 String，称为可表示类型。 不能在 Java 程序中编写的编译器内
部类型称为不可表示类型。

由于菱形运算符使用的推理结果，可能会出现不可表示的类型。 因为使用带有匿名类构造函数的 diamond 推断的类型可能超出类文件中签名属性支持的类型集，
因此 Java SE 7 中不允许使用带有匿名类的 diamond。

> * 下划线字符不是合法名称

如果您使用下划线字符（“_”）作为标识符，您的源代码将无法再编译。

> * 支持私有接口方法

支持私有接口方法。 这种支持允许接口的非抽象方法在它们之间共享代码。


### jdk 10
> * Local Variable Type Inference

在 JDK 10 及更高版本中，您可以使用带有 var 标识符的非空初始值设定项来声明局部变量，这可以帮助您编写更易于阅读的代码。

```java
class Test{
    public static String test(){
        // Consider the following example, which seems redundant and is hard to read:
        URL url = new URL("http://www.oracle.com/");
        URLConnection conn = url.openConnection();
        Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        // You can rewrite this example by declaring the local variables with the var identifier. 
        var url = new URL("http://www.oracle.com/");
        var conn = url.openConnection();
        var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        // Local variable declarations with initializers:
        var list = new ArrayList<String>();    // infers ArrayList<String>
        var stream = list.stream();            // infers Stream<String>
        var path = Paths.get(fileName);        // infers Path
        var bytes = Files.readAllBytes(path);  // infers bytes[]

        // Enhanced for-loop indexes:
        List<String> myList = Arrays.asList("a", "b", "c");
        for (var element : myList) {
            //...
        }  // infers String
        
        // Index variables declared in traditional for loops:
        for (var counter = 0; counter < 10; counter++)  {
            //...
        }   // infers int

        // try-with-resources variable:
        try (var input =
                     new FileInputStream("validation.txt")) {
            //...
        }   // infers FileInputStream

        BiFunction<Integer, Integer, Integer> function= (a, b) -> a + b;

        (var a, var b) -> a + b;

        (var x, y) -> x.process(y)      // Cannot mix var and inferred formal parameters
        // in implicitly typed lambda expressions
        (var x, int y) -> x.process(y)  // Cannot mix var and manifest types
        // in explicitly typed lambda expressions
    }
}
```

```java
class Test{
    public static String test(){
        
    }
}
```
### jdk 11

### jdk 12
> * Switch Expressions

Java SE 12 引入了 switch 表达式，它（像所有表达式一样）评估为单个值，并且可以在语句中使用。 Java SE 12 还引入了一种新的 case 标签，它无需
使用 break 语句来防止失败。

case label_1, label_2, ..., label_n -> expression;|throw-statement;|block

```java
class Test{

    public enum Day { SUNDAY, MONDAY, TUESDAY,
        WEDNESDAY, THURSDAY, FRIDAY, SATURDAY; }
    
    public static String test(){
        // Consider the following switch statement that prints the number of letters of a day of the week:
        int numLetters = 0;
        Day day = Day.WEDNESDAY;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                numLetters = 6;
                break;
            case TUESDAY:
                numLetters = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                numLetters = 8;
                break;
            case WEDNESDAY:
                numLetters = 9;
                break;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        }
        System.out.println(numLetters);
        
        
        // The following is a switch expression that uses the new kind of case label to print the number of letters of a day of the week:
        Day day = Day.WEDNESDAY;
        System.out.println(
                switch (day) {
                    case MONDAY, FRIDAY, SUNDAY -> 6;
                    case TUESDAY                -> 7;
                    case THURSDAY, SATURDAY     -> 8;
                    case WEDNESDAY              -> 9;
                    default -> throw new IllegalStateException("Invalid day: " + day);
                }
        );

        // The following is like the first example, except it uses "arrow case" labels instead of "colon case" labels:
        int numLetters = 0;
        Day day = Day.WEDNESDAY;
        switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> numLetters = 6;
            case TUESDAY                -> numLetters = 7;
            case THURSDAY, SATURDAY     -> numLetters = 8;
            case WEDNESDAY              -> numLetters = 9;
            default -> throw new IllegalStateException("Invalid day: " + day);
        };
        System.out.println(numLetters);

        // Specify the value that the case label produces with the break statement:
        int numLetters = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> {
                System.out.println(6);
                break 6;
            }
            case TUESDAY -> {
                System.out.println(7);
                break 7;
            }
            case THURSDAY, SATURDAY -> {
                System.out.println(8);
                break 8;
            }
            case WEDNESDAY -> {
                System.out.println(9);
                break 9;
            }
            default -> {
                throw new IllegalStateException("Invalid day: " + day);
            }
        };
        
        
    }
}
```

### jdk 13

### jdk 14
> * Text Blocks

文本块的原则是通过最小化呈现跨越多行的字符串所需的 Java 语法来提供清晰度。

```java
class Test{
    public static String test(){
        // Using a literal string
        String dqName = "Pat Q. Smith";

        // Using a text block
        String tbName = """
                Pat Q. Smith""";

        // Both dqName and tbName are strings of equal value
        dqName.equals(tbName)    // true

        // Both dqName and tbName intern to the same string
        dqName == tbName         // true

        System.out.println("""
                This is the first line
                This is the second line
                This is the third line
                """);

        // ORIGINAL
        String message = "'The time has come,' the Walrus said,\n" +
                "'To talk of many things:\n" +
                "Of shoes -- and ships -- and sealing-wax --\n" +
                "Of cabbages -- and kings --\n" +
                "And why the sea is boiling hot --\n" +
                "And whether pigs have wings.'\n";

        // BETTER
        String message = """
                'The time has come,' the Walrus said,
                'To talk of many things:
                Of shoes -- and ships -- and sealing-wax --
                Of cabbages -- and kings --
                And why the sea is boiling hot --
                And whether pigs have wings.'
                """;
    }
}
```

> * Records

JDK 14 引入了记录，这是一种新的类型声明。 像枚举一样，记录是类的受限形式。 它是“普通数据载体”的理想选择，这些类包含不打算更改的数据，并且只包
含最基本的方法，例如构造函数和访问器。

```java
class Test{
    // Consider the following class definition:
    final class Rectangle implements Shape {
        final double length;
        final double width;

        public Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }

        double length() { return length; }
        double width() { return width; }
    }

    // You can represent this class with a record:
    record Rectangle(float length, float width) { }

    // Custom record constructors still initialize their record's private fields.
    record HelloWorld(String message) {
        public HelloWorld {
            java.util.Objects.requireNonNull(message);
        }
    }
    
    public static String test(){
        
    }
}
```

以下是对记录使用的限制：
> * 记录不能扩展任何类
> * Records 不能声明实例字段（除了对应于记录组件列表的组件的私有 final 字段）； 任何其他声明的字段必须是静态的
> * 记录不能是抽象的； 它们是隐含的最终
> * 记录的组成部分是隐含最终的
> * 除了这些限制之外，记录的行为类似于常规类：

您可以在类中声明它们； 嵌套记录是隐式静态的
> * 您可以创建通用记录
> * 记录可以实现接口
> * 您使用新关键字实例化记录
> * 您可以在记录的主体中声明静态方法、静态字段、静态初始化程序、构造函数、实例方法和嵌套类型
> * 您可以注释记录和记录的各个组成部分


> * Pattern Matching for the instanceof Operator

模式匹配涉及测试对象是否具有特定结构，然后从该对象中提取数据（如果存在匹配）。 您已经可以使用 Java 做到这一点； 但是，模式匹配引入了新的语言增
强功能，使您能够有条件地从具有更简洁和健壮代码的对象中提取数据。

更具体地说，JDK 14 扩展了 instanceof 运算符：您可以指定绑定变量； 如果 instanceof 运算符的结果为真，则将被测试的对象分配给绑定变量。

```java
class Test{
    public static String test(){
        
    }

    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle s) {
            return 2 * s.length() + 2 * s.width();
        } else if (shape instanceof Circle s) {
            return 2 * s.radius() * Math.PI;
        } else {
            throw new IllegalArgumentException("Unrecognized shape");
        }
    }

    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle s) {
            // You can use the binding variable s (of type Rectangle) here.
        } else if (shape instanceof Circle s) {
            // You can use the binding variable s of type Circle here
            // but not the binding variable s of type Rectangle.
        } else {
            // You cannot use either binding variable here.
        }
    }

    public static boolean bigEnoughRect(Shape s) {
        if (!(s instanceof Rectangle r)) {
            // You cannot use the binding variable r here.
            return false;
        }
        // You can use r here.
        return r.length() > 5;
    }

    public static void bigEnoughRect2(Shape shape) {
        if (shape instanceof Rectangle s && s.length() > 5) {
            // ...
        }

        if (shape instanceof Rectangle s || s.length() > 0) { // error
            // ...
        }
    }
    
}
```

### jdk 15
> * Sealed Classes

密封的类和接口限制了哪些其他类或接口可以扩展或实现它们。

继承的主要目的之一是代码重用：当您想要创建一个新类并且已经有一个包含一些您想要的代码的类时，您可以从现有类派生您的新类。在这样做时，您可以重用现
有类的字段和方法，而无需自己编写（和调试）它们。

但是，如果您想通过定义其实体并确定这些实体应如何相互关联来对域中存在的各种可能性进行建模，该怎么办？例如，您正在处理图形库。您想确定您的库应如何
处理常见的几何图元，如圆形和正方形。您已经创建了这些几何基元可以扩展的 Shape 类。但是，您对允许任意类扩展 Shape 不感兴趣；您不希望您的图书馆
的客户声明任何进一步的原语。通过密封一个类，您可以指定允许哪些类扩展它并防止任何其他任意类这样做。

```java
public sealed class Shape
    permits Circle, Square, Rectangle {
}

public final class Circle extends Shape {
    public float radius;
}

public non-sealed class Square extends Shape {
    public double side;
}

public sealed class Rectangle extends Shape permits FilledRectangle {
    public double length, width;
}

public final class FilledRectangle extends Rectangle {
    public int red, green, blue;
}
```

或者，您可以在与密封类相同的文件中定义允许的子类。 如果这样做，则可以省略 permit 子句：
```java
package com.example.geometry;

public sealed class Figure
    // The permits clause has been omitted
    // as its permitted classes have been
    // defined in the same file.
{ }

final class Circle extends Figure {
    float radius;
}
non-sealed class Square extends Figure {
    float side;
}
sealed class Rectangle extends Figure {
    float length, width;
}
final class FilledRectangle extends Rectangle {
    int red, green, blue;
}

```

> #*允许的子类的约束*

允许的子类具有以下约束：
> * 它们必须在编译时可由密封类访问。
>   * 例如，要编译 Shape.java，编译器必须能够访问所有允许的 Shape 类：Circle.java、Square.java 和 Rectangle.java。此外，由于 Rectangle 是一个密封类，编译器还需要访问 FilledRectangle.java。
> * 他们必须直接扩展密封类。
> * 它们必须恰好具有以下修饰符之一来描述它如何继续由其超类发起的密封：
>   * final：无法进一步扩展
>   * sealed：只能由其允许的子类扩展
>   * non-sealed：可以被未知的子类扩展；密封类不能阻止其允许的子类这样做
>   * 例如，Shape 的允许子类演示了这三个修饰符中的每一个：Circle 是 final 而 Rectangle 是密封的，Square 是非密封的。
> * 它们必须与密封类在同一个模块中（如果密封类在命名模块中）或在同一个包中（如果密封类在未命名模块中，如 Shape.java 示例中）。
>   * 例如，在下面的 com.example.graphics.Shape 声明中，其允许的子类都在不同的包中。仅当 Shape 及其所有允许的子类位于同一个命名模块中时，此示例才会编译。

```java
package com.example.graphics;

public sealed class Shape 
    permits com.example.polar.Circle,
            com.example.quad.Rectangle,
            com.example.quad.simple.Square { }
```

> #*定义密封接口*

与密封类一样，要密封接口，请将密封修饰符添加到其声明中。 然后，在任何extends子句之后，添加 permit 子句，指定可以实现密封接口的类以及可以扩展密封接口的接口。

下面的示例声明了一个名为 Expr 的密封接口。 只有 ConstantExpr、PlusExpr、TimesExpr 和 NegExpr 类可以实现它：
```java
package com.example.expressions;

public class TestExpressions {
  public static void main(String[] args) {
    // (6 + 7) * -8
    System.out.println(
      new TimesExpr(
        new PlusExpr(new ConstantExpr(6), new ConstantExpr(7)),
        new NegExpr(new ConstantExpr(8))
      ).eval());
   }
}

sealed interface Expr
    permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {
    public int eval();
}

final class ConstantExpr implements Expr {
    int i;
    ConstantExpr(int i) { this.i = i; }
    public int eval() { return i; }
}

final class PlusExpr implements Expr {
    Expr a, b;
    PlusExpr(Expr a, Expr b) { this.a = a; this.b = b; }
    public int eval() { return a.eval() + b.eval(); }
}

final class TimesExpr implements Expr {
    Expr a, b;
    TimesExpr(Expr a, Expr b) { this.a = a; this.b = b; }
    public int eval() { return a.eval() * b.eval(); }
}

final class NegExpr implements Expr {
    Expr e;
    NegExpr(Expr e) { this.e = e; }
    public int eval() { return -e.eval(); }
}
```

> #*将类记录为允许的子类*

您可以在密封类或接口的许可子句中命名记录类。 有关详细信息，请参阅记录类。

记录类是隐式最终的，因此您可以使用记录类而不是普通类来实现前面的示例：
```java
package com.example.records.expressions;

public class TestExpressions {
  public static void main(String[] args) {
    // (6 + 7) * -8
    System.out.println(
      new TimesExpr(
        new PlusExpr(new ConstantExpr(6), new ConstantExpr(7)),
        new NegExpr(new ConstantExpr(8))
      ).eval());
   }
}

sealed interface Expr
    permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {
    public int eval();
}

record ConstantExpr(int i) implements Expr {
    public int eval() { return i(); }
}

record PlusExpr(Expr a, Expr b) implements Expr {
    public int eval() { return a.eval() + b.eval(); }
}

record TimesExpr(Expr a, Expr b) implements Expr {
    public int eval() { return a.eval() * b.eval(); }
}

record NegExpr(Expr e) implements Expr {
    public int eval() { return -e.eval(); }
}
```

> #*与密封类和接口相关的 API*

java.lang.Class 类有两个与密封类和接口相关的新方法：

java.lang.constant.ClassDesc[] allowedSubclasses()：返回一个数组，其中包含 java.lang.constant.ClassDesc 对象，如果它是密封的，则表示该类的所有允许子类； 如果类未密封，则返回一个空数组
boolean isSealed()：如果给定的类或接口是密封的，则返回 true
### jdk 16

### jdk 17

### jdk 18
> * switch 表达式和语句的模式匹配

switch 语句将控制转移到几个语句或表达式之一，具体取决于其选择器表达式的值。 在早期版本中，选择器表达式的计算结果必须为数字、字符串或枚举
常量，并且大小写标签必须是常量。 但是，在此版本中，选择器表达式可以是任何类型，并且 case 标签可以有模式。 因此，switch 语句或表达式可以测试其
选择器表达式是否匹配模式，与测试其选择器表达式是否完全等于常量相比，这提供了更大的灵活性和表现力。

考虑以下代码，该代码从模式匹配部分计算某些形状的周长，例如 instanceof：
```java
interface Shape { }
record Rectangle(double length, double width) implements Shape { }
record Circle(double radius) implements Shape { }

class Test{
    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle r) {
            return 2 * r.length() + 2 * r.width();
        } else if (shape instanceof Circle c) {
            return 2 * c.radius() * Math.PI;
        } else {
            throw new IllegalArgumentException("Unrecognized shape");
        }
    }
    
    // You can rewrite this code to use a pattern switch expression as follows:
    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        return switch (shape) {
            case Rectangle r -> 2 * r.length() + 2 * r.width();
            case Circle c    -> 2 * c.radius() * Math.PI;
            default          -> throw new IllegalArgumentException("Unrecognized shape");
        };
    }
    
    // The following example uses a switch statement instead of a switch expression:
    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        switch (shape) {
            case Rectangle r: return 2 * r.length() + 2 * r.width();
            case Circle c:    return 2 * c.radius() * Math.PI;
            default:          throw new IllegalArgumentException("Unrecognized shape");
        }
    }
}
```

> #*选择器表达式类型*

选择器表达式的类型可以是整数原始类型或任何引用类型（例如前面的示例）。 以下 switch 表达式将选择器表达式 obj 与涉及类类型、枚举类型、记录类型
和数组类型的类型模式匹配：
```java
record Point(int x, int y) { }
enum Color { RED, GREEN, BLUE; }
class Test{
    static void typeTester(Object obj) {
        switch (obj) {
            case null     -> System.out.println("null");
            case String s -> System.out.println("String");
            case Color c  -> System.out.println("Color with " + c.values().length + " values");
            case Point p  -> System.out.println("Record class: " + p.toString());
            case int[] ia -> System.out.println("Array of int values of length" + ia.length);
            default       -> System.out.println("Something else");
        }
    }
}
```

> #*模式标签优势*

许多模式标签可能与选择器表达式的值匹配。 为了帮助可预测性，标签按照它们在开关块中出现的顺序进行测试。 此外，如果模式标签永远不会匹配，编译器会
引发错误，因为前面的标签总是首先匹配。 以下示例导致编译时错误：
```java
class Test{
    static void error(Object obj) {
        switch(obj) {
            case CharSequence cs ->
                    System.out.println("A sequence of length " + cs.length());
            case String s -> // error: this case label is dominated by a preceding case label
                    System.out.println("A string: " + s);
            default ->
                    throw new IllegalStateException("Invalid argument");
        }
    }
}
```

第一个模式标签案例 CharSequence cs 支配第二个模式标签案例 String s，因为与模式 String s 匹配的每个值也匹配模式 CharSequence cs 但不是
相反。 这是因为 String 是 CharSequence 的子类型。

一个模式标签可以支配一个常量标签。 这些示例会导致编译时错误：
```java
class Test{
    static void error2(Integer value) {
        switch(value) {
            case Integer i ->
                    System.out.println("Integer: " + i);
            case -1, 1 -> // Compile-time errors for both cases -1 and 1:
                // this case label is dominated by a preceding case label       
                    System.out.println("The number 42");
            default ->
                    throw new IllegalStateException("Invalid argument");
        }
    }

    enum Color { RED, GREEN, BLUE; }

    static void error3(Color value) {
        switch(value) {
            case Color c ->
                    System.out.println("Color: " + c);
            case RED -> // error: this case label is dominated by a preceding case label
                    System.out.println("The color red");
        }
    }
}
```

受保护的模式标签（请参阅受保护的模式）也可以支配常量标签：
```java
class Test{
    static void error4(Integer value) {
        switch(value) {
            case Integer i && i > 0 ->
                    System.out.println("Positive integer");
            case -1, 1 -> // Compile-time errors for both cases -1 and 1:
                // this case label is dominated by a preceding case label
                    System.out.println("Value is 1 or -1");
            default ->
                    throw new IllegalStateException("Invalid argument");
        }
    }
}
```

要解决这些与支配性相关的编译器错误，请确保常量标签出现在受保护的模式标签之前，它必须出现在非受保护的类型模式标签之前：
```java
class Test{
    static void checkIntegers(Integer value) {
        switch(value) {
            case -1, 1 -> // Constant labels
                    System.out.println("Value is 1 or -1");
            case Integer i && i > 0 -> // Guarded pattern label
                    System.out.println("Positive integer");
            case Integer i -> // Non-guarded type pattern label
                    System.out.println("Neither positive, 1, nor -1");
        }
    }
}
```

有两个标签可以匹配所有值：默认标签和总类型模式（请参阅 Null-Matching case Labels）。 在 switch 块中不能有超过这两个标签中的一个。

> #*switch 表达式和语句中的类型覆盖*

如 Switch Expressions 中所述，switch 表达式和 switch 语句（使用模式或空标签）的 switch 块必须是详尽的，这意味着对于所有可能的值，必须有一个匹配的 switch 标签。以下 switch 表达式并不详尽，并且会生成编译时错误。它的类型覆盖由 String 或 Integer 的子类型组成，不包括选择器表达式的类型 Object：
```java
class Text{
    static int coverage(Object obj) {
        return switch (obj) {         // Error - not exhaustive
            case String s  -> s.length();
            case Integer i -> i;
        };
    }
}
```
但是case标签default的类型覆盖是所有类型，所以下面的例子编译：
```java
class Text{
    static int coverage(Object obj) {
        return switch (obj) {
            case String s  -> s.length();
            case Integer i -> i;
            default        -> 0;
        };
    }
}
```

编译器会考虑选择器表达式的类型是否为密封类。下面的 switch 表达式编译。它不需要默认 case 标签，因为它的类型覆盖是类 A、B 和 C，它们是 S 的唯一允许的子类，选择器表达式的类型：
```java
sealed interface S permits A, B, C { }
final class A implements S { }
final class B implements S { }
record C(int i) implements S { }  // Implicitly final
class Test{
    static int testSealedCoverage(S s) {
        return switch (s) {
            case A a -> 1;
            case B b -> 2;
            case C c -> 3;
        };
    }
}
```

如果其选择器表达式的类型是通用密封类，编译器还可以确定 switch 表达式或语句的类型覆盖。以下示例编译。接口 I 唯一允许的子类是类 A 和 B。但是，因为选择器表达式的类型是 I<Integer>，所以 switch 块只要求其类型覆盖范围内的类 B 是详尽的：

```java
    sealed interface I<T> permits A, B {}
    final class A<X> implements I<String> {}
    final class B<Y> implements I<Y> {}

    static int testGenericSealedExhaustive(I<Integer> i) {
        return switch (i) {
        // Exhaustive as no A case possible!  
            case B<Integer> bi -> 42;
        };
    }
```

> #*模式变量声明的范围*

如 instanceof 的模式匹配部分所述，模式变量的范围是程序只有在 instanceof 运算符为真时才能到达的地方：
```java
class Test{
    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle s) {
            // You can use the pattern variable s of type Rectangle here.
        } else if (shape instanceof Circle s) {
            // You can use the pattern variable s of type Circle here
            // but not the pattern variable s of type Rectangle.
        } else {
            // You cannot use either pattern variable here.
        }
    }
}
```
在 switch 表达式中，您可以在出现在箭头右侧的表达式、块或 throw 语句中使用模式变量。例如：
```java
class Test{
    static void test(Object obj) {
        switch (obj) {
            case Character c -> {
                if (c.charValue() == 7) {
                    System.out.println("Ding!");
                }
                System.out.println("Character, value " + c.charValue());
            }
            case Integer i ->
                    System.out.println("Integer: " + i);
            default ->
                    throw new IllegalStateException("Invalid argument");
        }
    }
}
```
模式变量 c 的作用域是 case Character c -> 右边的块。模式变量 i 的范围是 case Integer I -> 右侧的 println 语句。

在 switch 语句中，您可以在其带有 switch 标签的语句组中使用 case 标签的模式变量。但是，您不能在任何其他带有开关标签的语句组中使用它，即使程序流可能会通过默认语句组。例如：
```java
class Test{
    static void test(Object obj) {
        switch (obj) {
            case Character c:
                if (c.charValue() == 7) {
                    System.out.print("Ding ");
                }
                if (c.charValue() == 9) {
                    System.out.print("Tab ");
                }
                System.out.println("character, value " + c.charValue());
            default:
                // You cannot use the pattern variable c here:
                throw new IllegalStateException("Invalid argument");
        }
    }
}
```
模式变量 c 的范围由 case Character c 语句组组成：两个 if 语句和它们后面的 println 语句。范围不包括默认语句组，即使 switch 语句可以执行 case 字符 c 语句组，通过默认 case 标签，然后执行默认语句组。

如果有可能通过声明模式变量的 case 标签，您将收到编译时错误。以下示例无法编译：
```java
class Test{
    static void test(Object obj) {
        switch (obj) {
            case Character c:
                if (c.charValue() == 7) {
                    System.out.print("Ding ");
                }
                if (c.charValue() == 9) {
                    System.out.print("Tab ");
                }
                System.out.println("character");
            case Integer i:                 // Compile-time error
                System.out.println("An integer " + i);
            default:
                System.out.println("Neither character nor integer");
        }
    }
}
```
如果允许这段代码，并且选择器表达式 obj 的值是一个字符，那么 switch 语句可以执行 case Character c 语句组，然后通过 case Integer i case 标签，其中模式变量 i 将具有没有被初始化。

同样，您不能在一个案例标签中声明多个模式变量。以下是不允许的； c 或 i 都会被初始化（取决于 obj 的值）。
```
    case Character c, Integer i: ...
    case Character c, Integer i -> ...
```

> #*空匹配大小写标签*

在此预览功能之前，如果选择器表达式的值为 null，则 switch 表达式和 switch 语句将引发 NullPointerException。 然而，模式标签提供了更大的灵活性——现在有两个新的空匹配大小写标签。 首先，可以使用 null case 标签：
```java
class Test{
    static void test(Object obj) {
        switch (obj) {
            case null     -> System.out.println("null!");
            case String s -> System.out.println("String");
            default       -> System.out.println("Something else");
        }
    }
}
```
这个例子打印空！ 当 obj 为 null 而不是抛出 NullPointerException。

其次，如果选择器表达式的值为 null，则模式为总类型模式的模式标签匹配 null。 如果 S 的类型擦除是 T 的类型擦除的子类型，则类型模式 T t 是类型 S 的总和。例如，类型模式 Object obj 是类型 String 的总和。 考虑以下 switch 语句：
```java
class Test{
    void test(){
        String s = "...";
        switch (s) {
            case Object obj -> "..." // total type pattern, so it matches null!
        }
    }
}
```
如果 s 评估为 null，则应用模式标签 case Object obj。

如果选择器表达式的计算结果为 null 并且 switch 块没有与 null 匹配的模式标签，则正常抛出 NullPointerException。
