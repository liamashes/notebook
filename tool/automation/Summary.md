关于自动测试的相关框架：
    国内：
        [pyautogui](https://pyautogui.readthedocs.io/en/latest/index.html)
        [pyautoit](https://pypi.org/project/PyAutoIt/)
    国外：
        [Sikuli](http://www.sikuli.org/)
        [Autoit3](https://www.autoitscript.com/autoit3/docs/)
        
框架之间的联系：
>   关系 | 说明
>   --- | ---
>   pyautogui ~= Sikuli | 提供与 Sikuli 提供的相同类型的功能。
>   pyautoit  -> Autoit3 | Python调用AutoItX3.dll

# 思路
> * pyautogui的使用
> * autoit的使用
>   * Microsoft Windows UIA原理及使用
>   * autoit的语法
> * pyautogui 和 pyautoit 结合

# 框架内容

## 1、pyautogui
> * [官方文档](https://pyautogui.readthedocs.io/en/latest/)
> * [github 源码、示例](https://github.com/asweigart/pyautogui)

PyAutoGUI 让您的 Python 脚本控制鼠标和键盘以自动与其他应用程序交互。 API 的设计很简单。 PyAutoGUI 适用于 Windows、macOS 和 Linux，并在 
Python 2 和 3 上运行。

要使用 pip 安装，请运行 pip install pyautogui。 有关更多详细信息，请参阅安装页面。

[源代码](https://github.com/asweigart/pyautogui)

    PyAutoGUI 有几个特点：
> * 移动鼠标并单击其他应用程序的窗口。
> * 向应用程序发送击键（例如，填写表格）。
> * 截取屏幕截图，并给出一个图像（例如，一个按钮或复选框），然后在屏幕上找到它。
> * 找到应用程序的窗口，然后移动、调整大小、最大化、最小化或关闭它（目前仅限 Windows）。
> * 显示警报和消息框。

    功能点：
> * 鼠标控制功能
>   * 屏幕和鼠标位置
>   * 鼠标移动
>   * 鼠标拖动
>   * 补间/缓动函数
>   * 鼠标点击
>   * mouseDown() 和 mouseUp() 函数
>   * 鼠标滚动
> * 键盘控制功能
>   * write() 函数
>   * press()、keyDown() 和 keyUp() 函数
>   * hold() 上下文管理器
>   * 热键（）函数
>   * KEYBOARD_KEYS
> * 消息框功能
>   * alert() 函数
>   * 确认（）函数
>   * prompt() 函数
>   * 密码（）函数
> * 截图功能
>   * screenshot() 函数
>   * 定位函数
>       * 灰度匹配
>       * 像素匹配

    Q&A
> * 问：PyAutoGUI 可以在 Android、iOS 或平板电脑/智能手机应用程序上运行吗？
> * 答：不幸的是没有。 PyAutoGUI 仅在 Windows、macOS 和 Linux 上运行。
> * 
> * 问：PyAutoGUI 是否适用于多显示器设置。
> * A：不，现在 PyAutoGUI 只处理主监视器。
> * 
> * 问：PyAutoGUI 做 OCR 吗？
> * 答：不，但这是路线图上的一项功能。
> * 
> * 问：PyAutoGUI 是否可以进行键盘记录，或者检测当前是否按下了某个键？
> * A：不，PyAutoGUI 目前无法做到这一点。

    如何实现所需要的功能
> * 基于图标进行定位（参考已实现的demo）
> * 结合selenium提供的chrome操作的功能，完成浏览器打开、页面元素定位、输入等功能（参考已实现的demo）

## 2、autoit3  
> * [官方文档](https://www.autoitscript.com/autoit3/docs/)

    介绍
AutoIt v3 是一种类似 BASIC 的免费软件脚本语言，旨在自动化 Windows GUI 和一般脚本。它结合了模拟击键、鼠标移动和窗口/控件操作，以便以其他语言
（例如 VBScript 和 SendKeys）不可能或不可靠的方式自动执行任务。 AutoIt 也非常小，是独立的，可以在所有版本的 Windows 上开箱即用地运行，不需
要烦人的“运行时”！

AutoIt 最初是为 PC“推出”情况而设计的，以可靠地自动化和配置数千台 PC。随着时间的推移，它已经成为一种强大的语言，支持复杂的表达式、用户函数、循环
以及资深脚本编写者所期望的所有其他内容。特征：

> * 易于学习的类似 BASIC 的语法
> * 模拟击键和鼠标移动
> * 操作窗口和进程
> * 与所有标准窗口控件交互
> * 脚本可以编译成独立的可执行文件
> * 创建图形用户界面 (GUI)
> * COM 支持
> * 常用表达
> * 直接调用外部DLL和Windows API函数
> * 可编写脚本的 RunAs 函数
> * 详细的帮助文件和大型社区支持论坛
> * 兼容 Windows XP SP3 / 2003 SP2 / Vista / 2008 / Windows 7 / 2008 R2 / Windows 8 / 2012 R2
> * Unicode 和 x64 支持
> * 数字签名让您高枕无忧
> * 适用于 Windows 用户帐户控制 (UAC)

AutoIt 被设计为尽可能小且独立，无需外部 .dll 文件或注册表项，使其可以在服务器上安全使用。可以使用 Aut2Exe 将脚本编译成独立的可执行文件。

还提供了 AutoIt 的 COM 和 DLL 组合版本，称为 AutoItX，它允许您将 AutoIt 的独特功能添加到您自己喜欢的脚本或编程语言中！

最重要的是，AutoIt 继续免费 - 但如果您想支持在项目和网络托管上花费的时间、金钱和精力，那么您可以在 AutoIt 主页上捐款。

    详细功能

> 类基本语法和丰富的功能集

AutoIt 具有类似 BASIC 的语法，这意味着大多数曾经编写过脚本或使用过高级语言的人应该能够轻松掌握它。

尽管它最初是一个简单的自动化工具，但 AutoIt 现在具有允许它用作通用脚本语言的功能和特性（当然还有很棒的自动化！）。语言功能包括：

> * 函数、循环和表达式解析的常用高级元素
> * 数量惊人的字符串处理函数和 Perl 兼容的正则表达式引擎（使用 PCRE 库）。
> * COM 支持
> * 调用 Win32 和第三方 DLL API


> 带有语法高亮的内置编辑器

AutoIt 带有一个定制的“精简”版本的 SciTE，使编辑脚本变得容易。用户还可以下载 SciTE 的完整版本，其中包括使事情变得更容易的其他工具。


> 独立和小型

AutoIt 是一个非常小的独立应用程序，不依赖于大量运行时，例如 .NET 或 VB。运行 AutoIt 脚本所需的只是主要的 AutoIt 可执行文件 (AutoIt3.exe) 和脚本。也可以使用内置的脚本编译器 Aut2Exe 将脚本编码为独立的可执行文件。


> 国际和 64 位支持

AutoIt 完全支持 Unicode，还包括所有主要组件的 x64 版本！你能说多少其他免费脚本语言？

> 键鼠模拟

已花费大量时间优化击键和鼠标模拟功能，使其在所有 Windows 版本上尽可能准确。所有鼠标和键盘例程在模拟“速度”和功能方面都是高度可配置的。

> 窗口管理

您可以期望移动、隐藏、显示、调整大小、激活、关闭以及几乎可以使用 Windows 做任何您想做的事情。可以通过标题、窗口上的文本、大小、位置、类甚至内部 Win32 API 句柄来引用窗口。

> 控件

直接获取有关编辑框、复选框、列表框、组合、按钮、状态栏的信息并与之交互，而不会丢失击键的风险。甚至可以在不活动的窗口中使用控件！

> 图形用户界面 (GUI)

AutoIt v3 还允许您创建一些复杂的 GUI - 就像下面的那些！

![example 1](./images/gui_eg1.png) ![example 2](./images/gui_eg2.png)

## 3、[microsoft UIA](./Windows_accessible_API_reference.md)

