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
