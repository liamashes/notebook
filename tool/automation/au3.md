
功能点
### 调用dll文件，封装windows功能
> * 1、在 [microsoft windows api](https://docs.microsoft.com/en-us/windows/win32/api/_shell/)  中找到需要的函数、出入参数、ddl文件
> * 2、调用 [DllCall](https://www.autoitscript.com/autoit3/docs/functions/DllCall.htm) ，获取返回值即可

### 定位当前元素
> * 1、使用_UIA_getFirstObjectOfElement获取 所有桌面元素$UIA_oDesktop 的 孩子$treescope_children中 符合条件（通过class名称筛选）的元素，得到对象obj
> * 2、调用obj.setfocus定位元素，使用_UIA_DumpThemAll将obj的所有$treescope_subtree落地为xml文件

