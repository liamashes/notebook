[microsoft 官网](https://docs.microsoft.com/en-us/windows/win32/winauto/entry-uiauto-win32)
#1、UI Automation

本文中：
> * [适用的地方](#1-1适用的地方)
> * [开发者受众](#1-2开发者受众)
> * [运行时要求](#1-3运行时要求)
> * [支持下层操作系统](#1-4支持下层操作系统)
> * [在这个部分](#1-5在这个部分)


Microsoft UI 自动化是一个可访问性框架，它使 Windows 应用程序能够提供和使用有关用户界面 (UI) 的编程信息。 它提供对桌面上大多数 UI 元素的
编程访问。 它使辅助技术产品（例如屏幕阅读器）能够向最终用户提供有关 UI 的信息，并通过标准输入以外的方式操作 UI。 UI 自动化还允许自动化测试脚
本与 UI 交互。
    
## 1-1、适用的地方

通过使用 UI 自动化并遵循可访问的设计实践，开发人员可以使在 Windows 上运行的应用程序更容易被许多有视力、听力或运动障碍的人访问。 此外，UI 
自动化专门设计用于为自动化测试场景提供强大的功能。
    
## 1-2、开发者受众

UI 自动化专为有经验的 C/C++ 开发人员而设计。 一般来说，开发人员需要对组件对象模型 (COM) 对象和接口、Unicode 和 Windows API 编程有一定
程度的了解。
    
有关托管代码的 UI 自动化的信息，请参阅 MSDN 的 .NET Framework 开发人员指南部分中的 [辅助功能](https://docs.microsoft.com/en-us/dotnet/framework/ui-automation/) 。

## 1-3、运行时要求

以下操作系统支持 UI 自动化：Windows XP、Windows Server 2003、Windows Server 2003 R2、Windows Vista、Windows 7、Windows 10、
Windows Server 2008、Windows Server 2008 R2、Windows Server 2012、Windows Server 2012 R2、Windows 服务器 2016 和 Windows 
服务器 2019。

    注意
    
    Windows XP 和 Windows Server 2003 还需要 Microsoft .NET Framework 3.0。

## 1-4、支持下层操作系统

Windows Vista 的平台更新是一组运行时库，使开发人员能够将应用程序定位到 Windows 7 和更低级别的操作系统。 Windows Server 2008 平台更新是一
组运行时库，使开发人员能够将应用程序定位到 Windows Server 2008 R2 和以前版本的 Windows Server。 Windows Vista 平台更新和 Windows 
Server 2008 平台更新将通过 Windows 更新提供给所有 Windows Vista 和 Windows Server 2008 客户。 需要 Windows Vista 平台更新或 Windows 
Server 2008 平台更新的第三方应用程序可以让 Windows 更新检测是否已安装； 如果不是，Windows Update 将在后台下载并安装它。

适用于 Windows Vista 的平台更新和适用于 Windows Server 2008 的平台更新都支持以下操作系统上的整个 Windows 自动化 API 3.0 功能集。

> * Windows XP (English)
> * Windows XP Home SP3 x86
> * Windows XP Professional SP3 x86

> * Windows Server 2003 (English)
> * Windows Server 2003 SP2 (x86 and x64)

> * Windows Vista (English)
> * Starter SP2 (x86 and x64)
> * Home Premium SP2 (x86 and x64)
> * Business SP2 (x86 and x64)
> * Enterprise SP2 (x86 and x64)
> * Ultimate SP2 (x86 and x64)

> * Windows Server 2008 (English)
> * Windows Server 2008 SP2 (x86 and x64)

有关这两个更新的详细信息，请参阅适用于 [Windows Vista 的平台更新](https://docs.microsoft.com/en-us/windows/win32/win7ip/platform-update-for-windows-vista-portal) 。

## 1-5、在这个部分
