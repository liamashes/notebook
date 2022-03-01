# 概述
关于自动测试的相关框架：
> * 国内：
>   * [pyautogui](https://pyautogui.readthedocs.io/en/latest/index.html)
>   * [pyautoit](https://pypi.org/project/PyAutoIt/)
> * 国外：
>   * [Sikuli](http://www.sikuli.org/)
>   * [Autoit3](https://www.autoitscript.com/autoit3/docs/)
        
框架之间的联系：
>   关系 | 说明
>   --- | ---
>   pyautogui ~= Sikuli | 提供与 Sikuli 提供的相同类型的功能。
>   pyautoit  -> Autoit3 | Python调用AutoItX3.dll

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
