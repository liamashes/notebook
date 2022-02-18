[原视频链接](https://www.bilibili.com/video/BV1S4411E7LY?p=6)

## 整体
1、字符串中支持 $变量名 来获取对应的变量的值

## 6、集合类型

需要注意的知识点：
> * 集合类型：List Set Map
> * Set：不能有重复元素
> * List去重方式：将元素放入到Set中，再转为List
> * List操作：add addAll remove removeAt reverse join（输出字符串） isEmpty isNotEmpty contains
> * Map：keys和values都可以toList 
> * 遍历方式：for foreach 
> * 遍历方式2（   ）：.map .where .any(有一个满足的即为true) .every(全部满足返回true)

## 7、函数
方法支持 
> * 可选参数（放在[]内）
> * 默认参数（签名中给到对应的值）
> * 命名参数（放在{}中，传参方式：参数名:参数值）
> * 方法参数（函数作为参数，不用制定参数类型）

## 8、函数
> * 箭头函数： .method((参数1，参数2...) => {方法体})
> * ？？函数： .method((参数1，参数2...){方法体；返回值})
> * 匿名函数：var 方法名 = (参数1，参数2){}
> * 自执行方法：((参数1，参数2...){})(变量1，变量2...)
> * 方法的递归：方法可以调用自己，注意增加返回点

### 闭包

> * 1、全局变量特点： 全局变量常驻内存、全局变量污染全局
> * 2、局部变量特点： 不常驻内存会被垃圾机制回收，不会污染全局

想要实现的功能：
> * 1、常驻内存
> * 2、不污染全局

定义：
> 函数嵌套函数，内部函数会调用外部函数的变量或参数，变量或参数不会被系统

写法：
> 函数嵌套函数，并return里面的函数

> * fn(){
> *  var a=123;
> *  return (){
> *    a++;
> *    print(a);
> *  }
> * }
> * 
> * var b=fn()
> * b();
> * b();
> * b();


## 9、对象 类
三个基本特征：
> * 封装
> * 继承：只能单继承，mixins类似于多继承
> * 多态：子类型的指针赋值给父类类型，同一个函数调用会有不同的执行效果

> * 默认构造函数的简写：构造器（this.变量名1，this.变量名2）
> * 命名构造函数：构造器.方法名（参数1，参数2）
> * 类模块化：把结构类放在单独的目录，通过import引用
> * 私有化：属性或方法名前加_
> * getter： 
>   * 定义：class example{ get 属性名{方法体，返回值} }
>   * 调用：example.属性名
> * setter： 
>   * 定义：class example{ set 属性名(参数1){方法体} }
>   * 调用：example.属性名=变量
> * 构造函数运行前初始化：
>   * 定义：class example{ int a; int b; example():a=1,b=2{print "$a $b"} }

## 10、类
> * static和java中的使用方式相同
> * is：判断变量是否是某种类型
> * as：强制转换（变量 as 类型）
> * 连缀操作： 变量 ..属性=a ..方法();
> * 继承：和java类似
> * 抽象类：与java类似，区别在于：
>   * 方法不可以用abstract声明，没有方法体的称为抽象方法
> * extends与implements区别：
>   * extends用于复用方法
>   * implements用于接口规范


## 11、抽象
abstract声明抽象类或接口类，可以extends或implement

## 12、一个类实现多个接口

mixins特性：
> * 阐述：中文意思是混入，就是在类中混入其他功能；使用该功能实现类似多继承的功能
> * 2.x中使用mixins的条件：
>   * 1、作为mixins的类只能继承自Object，不能继承其他类
>   * 2、作为mixins的类不能有构造函数
>   * 3、一个类可以mixins多个mixins类
>   * 4、mixins绝不是继承，也不是接口，而是一种全新的特性
> * 特点：
>   * 一个类mixins多个类的时候时，相同签名的方法按照顺序进行覆盖
>   * 一个mixins其他类的类是其mixins类的子类型

## 13、范型
范型（类似于java）
> * 解决冗余代码、类型限制的问题

## 14、库
> * 内置的库：import 'dart:io(convert/math)' as io
> * 自定义的：import 'lib/xxx.dart'
> * pub包管理系统：import 'package:http/http.dart' as http

async（方法变成异步） 和 await（等待异步方法执行完成）
> * 只有async方法可以使用await关键字调用方法
> * 调用别的async方法必须使用await关键字

> 包管理    

1、相关库的地址：  
[https://pub.dev/packages](https://pub.dev/packages)  
[https://pub.flutter-io.cn/packages](https://pub.flutter-io.cn/packages)  
[https://pub.dartlang.org/flutter](https://pub.dartlang.org/flutter)  

2、创建一个pubspec.yaml文件，结构如下：  
    name: xxx  
    description: A new flutter module project  
    dependencies:   
        http: ^0.12.0+2  
        date_format: ^1.0.6  

3、配置dependencies，运行pub get

> 解决库冲突类： import 'xxx' as xxx;

> 部分导入

如果只导入一部分，两种模式：
> * 模式一：只导入需要的部分，使用show，如 import 'package:xxx' show foo
> * 模式二：隐藏不需要的部分，使用hide，如 import 'package:xxx' hide foo


## 15、新特性
> * null safety: 空安全，类型默认不能为空   示例：String a = "123"; a=null;（报错）
> * ？: 可空类型  示例：String? a = "123"; a=null;
> * ! : 类型断言  示例：String? a = "123"; a=null; print(a!.length) // 不加类型断言，无法编译；如果a为null，则抛出异常
> * late : 延迟初始化  示例：用于类的属性的延迟赋值（无构造函数时会报错）
> * require: 标记任何命名参数（函数或类）不为空（原本是注解，现在是内置修饰符）
>
>
