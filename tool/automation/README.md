# 概述
关于自动测试的相关框架：
> * 国内：
>   * [pyautogui](https://pyautogui.readthedocs.io/en/latest/index.html)
>   * [pyautoit](https://pypi.org/project/PyAutoIt/)
> * 国外：
>   * [Sikuli](http://www.sikuli.org/)
>   * [Autoit3](https://www.autoitscript.com/autoit3/docs/)
>   * [Selenium](https://www.selenium.dev/)
        
框架之间的联系：
>   关系 | 说明 | 优势 | 缺陷
>   --- | --- | --- | ---
>   Sikuli | 是一个基于图像识别的GUI自动化测试框架，底层是基于opencv实现对图像的识别 | 简单易用，不需要编程基础 | 实现功能有限，且易受干扰
>   Autoit3 |一种类似 BASIC 的免费软件脚本语言，旨在自动化 Windows GUI 和一般脚本。 它使用模拟击键、鼠标移动和窗口/控件操作的组合，以便以其他语言（例如 VBScript 和 SendKeys）不可能或不可靠的方式自动执行任务。 | 原则上可以实现所有microsoft提供的所有API功能，可设计性强，独立开发自己的函数库 | 实现起来较为复杂，需要了解win的UIA结构、事件等；仅适用于win系统；资料在国外论坛较为丰富
>   pyautogui ~= Sikuli | 提供与 Sikuli 提供的相同类型的功能 | 提供的功能可以在Windows、Linux、MacOs上使用 | 实现的功能比较基础，且易受干扰，定位元素方式较少; 基于图像定位时需要考虑不同系统缩放比例问题
>   pyautoit  -> Autoit3 | Python调用AutoItX3.dll | 参考Autoit3 | 参考Autoit3
>   selenium | 模拟用户与浏览器交互的扩展、用于扩展浏览器分配的分发服务器，以及用于实现 W3C WebDriver 规范的基础架构，允许为所有主要 Web 浏览器编写可互换的代码。| 多语言支持（java、python、csharp、ruby、javascript、kotlin）；基于页面静态代码中的元素定位 | 需要独立的驱动；仅支持浏览器操作；需要页面内的元素固定，区分度高 

# 流程
> * pyautogui的使用
> * autoit的使用
>   * autoit的语法
>   * Microsoft Windows UIA原理及使用
> * pyautogui 和 pyautoit 结合

# 框架内容

## 1、[pyautogui](./pyautogui.md)
> * [官方文档](https://pyautogui.readthedocs.io/en/latest/)
> * [github 源码、示例](https://github.com/asweigart/pyautogui)

## 2、[autoit3](./au3.md)
> * [官方文档](https://www.autoitscript.com/autoit3/docs/)
> * [应用示例合集](https://www.autoitscript.com/forum/topic/153520-iuiautomation-ms-framework-automate-chrome-ff-ie/#comments)

## 3、[microsoft UIA](./Windows_accessible_API_reference.md)
> * [官方文档](https://docs.microsoft.com/en-us/windows/win32/winauto/entry-uiauto-win32)
> * 相关头文件信息
>   * [uiautomationclient.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationclient/)
>   * [uiautomationcore.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationcore/)
>   * [uiautomationcoreapi.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationcoreapi/)
> * [所有头文件信息汇总](./microsoft-all-headers.pdf)
