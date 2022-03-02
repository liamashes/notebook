[microsoft 官网地址](https://docs.microsoft.com/en-us/windows/win32/winauto/entry-uiauto-win32)

# 1、UI Automation

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

UI 自动化专为有经验的 C/C++ 开发人员而设计。 一般来说，开发人员需要对 [组件对象模型 (COM) ](https://docs.microsoft.com/en-us/windows/win32/com/component-object-model--com--portal) 对象和接口、Unicode 和 Windows API 编程有一定
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
> * [UI自动化基础](#1-5-1UI自动化基础)
> * [UI 自动化提供程序程序员指南](#1-5-2UI自动化提供程序程序员指南)
> * [UI 自动化客户端程序员指南](#1-5-3UI自动化客户端程序员指南)
> * [参考](#1-5-4参考)
> * [样品](#1-5-5样品)
> * [附录](#1-5-6附录)

### 1-5-1、UI自动化基础
Microsoft UI 自动化使辅助技术应用程序和自动化测试工具能够与其他应用程序的 UI 控件进行交互。 本节介绍 UI 自动化所基于的基本概念。

UI 自动化 API 分为两部分。 一部分由 UI 自动化提供程序应用程序使用，另一部分由 UI 自动化客户端应用程序使用。 提供程序 API 使 Microsoft Win32 
自定义控件和其他控件框架的开发人员能够将这些控件公开给 UI 自动化并使它们对客户端应用程序可见。 客户端 API 使应用程序能够与其他应用程序中的控件交互
并检索有关它们的信息。

> * [UI 自动化概述](#1-5-1-1UI自动化概述)
> * [UI 自动化和活动可访问性](#1-5-1-2UI自动化和活动可访问性)
> * [UI 自动化树概述](#1-5-1-3UI自动化树概述)
> * [UI 自动化控件类型概述](#1-5-1-4UI自动化控件类型概述)
> * [UI 自动化控制模式概述](#1-5-1-5UI自动化控制模式概述)
> * [UI 自动化属性概述](#1-5-1-6UI自动化属性概述)
> * [UI 自动化事件概述](#1-5-1-7UI自动化事件概述)
> * [自定义属性、事件和控制模式](#1-5-1-8自定义属性、事件和控制模式)
> * [文本内容的 UI 自动化支持](#1-5-1-9文本内容的UI自动化支持)
> * [拖放的 UI 自动化支持](#1-5-1-10拖放的UI自动化支持)
> * [辅助技术的安全注意事项](#1-5-1-11辅助技术的安全注意事项)
> * [使用安全数组的最佳实践](#1-5-1-12使用安全数组的最佳实践)
> * [UI 自动化规范和社区承诺](#1-5-1-13UI自动化规范和社区承诺)


#### 1-5-1-1、UI自动化概述
本文中：
> * [UI 自动化组件](#1-5-1-1-1UI自动化组件)
> * [UI 自动化头文件](#1-5-1-1-2UI自动化头文件)
> * [UI 自动化模型](#1-5-1-1-3UI自动化模型)
> * [相关话题](#1-5-1-1-4相关话题)

Microsoft UI 自动化是 Windows 的辅助功能框架。 它提供对桌面上大多数 UI 元素的编程访问。 它使辅助技术产品（例如屏幕阅读器）能够向最终用户提供
有关 UI 的信息，并通过标准输入以外的方式操作 UI。 UI 自动化还允许自动化测试脚本与 UI 交互。

UI 自动化首先在 Windows XP 中作为 Microsoft .NET Framework 的一部分提供。 尽管当时还发布了非托管 C++ API，但由于互操作性问题，客户端函数
的用处受到了限制。 对于 Windows 7，API 已在组件对象模型 (COM) 中重写。

    注意
    尽管 UI 自动化早期版本中引入的库函数仍被记录在案，但它们不应在新应用程序中使用。

可以编写 UI 自动化客户端应用程序，确保它们可以在多个 Microsoft Windows 控制框架上运行。 UI 自动化核心掩盖了构成 UI 各个部分的框架中的任何差异
。 例如，Windows Presentation Foundation (WPF) 按钮的 Content 属性、Microsoft Win32 按钮的 Caption 属性和 HTML 图像的 ALT 属性都
映射到 UI 自动化视图中的单个属性 Name。

UI 自动化在 Windows XP、Windows Server 2003 和更高版本的操作系统中提供完整的功能。

UI 自动化提供程序是在控件上实现 UI 自动化支持并通过内置桥接服务为 Microsoft Active Accessibility 客户端应用程序提供一些支持的组件。

    注意
    UI 自动化不会启用由不同用户通过 Run as 命令启动的进程之间的通信。

本主题包含以下部分。
> * [UI 自动化组件](#1-5-1-1-1UI自动化组件)
> * [UI 自动化头文件](#1-5-1-1-2UI自动化头文件)
> * [UI 自动化模型](#1-5-1-1-3UI自动化模型)
> * [相关话题](#1-5-1-1-4相关话题)

##### 1-5-1-1-1、UI自动化组件
UI 自动化有四个主要组件，如下表所示。

> 组件 | 描述
> --- | ---
> Provider API | 一组由 UI 自动化提供程序实现的 COM 接口。 UI 自动化提供程序是提供有关 UI 元素的信息并响应编程输入的对象。
> Client API | 一组 COM 接口，使客户端应用程序能够获取有关 UI 的信息并将输入发送到控件。【弃用的控制模式函数和弃用的节点函数中描述的函数已弃用。 相反，客户端应用程序应使用客户端的 UI 自动化元素接口中描述的 UI 自动化 COM 接口。】
> UIAutomationCore.dll	| 运行时库，有时称为 UI 自动化核心，用于处理提供者和客户端之间的通信。
> Oleacc.dll | Microsoft Active Accessibility 和代理对象的运行时库。 该库还提供 Microsoft Microsoft Active Accessibility to UI Automation Proxy 使用的代理对象，以支持 Win32 控件。

有两种使用 UI 自动化的方法：通过使用提供程序 API 创建对自定义控件的支持，以及创建使用 UI 自动化核心与 UI 元素通信并检索有关 UI 元素的信息的客户
端应用程序。 根据您的重点，您应该参考文档的不同部分。 如果您需要创建对自定义控件的支持，请参阅 UI 自动化提供程序程序员指南。 如果您需要与 UI 元素
通信或检索有关 UI 元素的信息，请参阅 UI 自动化客户端程序员指南。

##### 1-5-1-1-2、UI自动化头文件
UI 自动化 API 在 Windows 软件开发工具包 (SDK) 中包含的几个不同的 C/C++ 头文件中定义。 下表描述了 UI 自动化头文件：

> 头文件 | 描述
> --- | ---
> [UIAutomationClient.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationclient/) | 定义 UI 自动化客户端使用的接口和相关编程元素。
> [UIAutomationCore.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationcore/) | 定义 UI 自动化提供程序使用的接口和相关编程元素。
> [UIAutomationCoreApi.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomationcoreapi/) | 定义 UI 自动化客户端和提供程序使用的通用常量、GUID、数据类型和结构。 它还包含不推荐使用的节点和控制模式函数的定义。
> [UIAutomation.h](https://docs.microsoft.com/en-us/windows/win32/api/uiautomation/) | 包括所有其他 UI 自动化头文件。 因为大多数 UI 自动化应用程序需要来自所有 UI 自动化头文件的元素，所以最好将 UIAutomation.h 包含在您的 UI 自动化应用程序项目中，而不是单独包含每个文件。
##### 1-5-1-1-3、*UI自动化模型*
UI 自动化将 UI 的每个元素作为由 [IUIAutomationElement](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nn-uiautomationclient-iuiautomationelement) 接口表示的对象公开给客户端应用程序。元素包含在树结构中，桌面作为根元素。客户端可以将树的
原始视图过滤为控制视图或内容视图。通过使用 Windows SDK 中包含的 [Inspect](https://docs.microsoft.com/en-us/windows/win32/winauto/inspect-objects) 应用程序，可以轻松查看这些结构的标准视图。应用程序还可以创建自定义视图。

UI 自动化元素公开它所代表的控件或 UI 元素的属性。这些属性之一是控件类型，它将控件或 UI 元素的基本外观和功能定义为单个可识别实体，例如按钮或复选框
。有关控件类型的详细信息，请参阅 UI 自动化控件类型概述。

此外，UI 自动化元素公开了一种或多种控制模式。控件模式提供一组特定于特定控件类型的属性。控制模式还公开了使客户端应用程序能够获取有关元素的更多信息并
向元素提供输入的方法。有关控制模式的更多信息，请参阅 UI 自动化控制模式概述。

    注意
    控制类型和控制模式之间没有一一对应的关系。 一个控件模式可以由多种控件类型支持，一个控件可以支持多个控件模式，每一个都暴露了其行为的不同方面。 
    例如，一个组合框至少有两种控制模式：一种表示其展开和折叠的能力，另一种表示选择机制。 但是，一个控件只能显示一个控件类型。

UI 自动化通过事件向客户端应用程序提供信息。 与 WinEvents 不同，UI 自动化事件不基于广播机制。 UI 自动化客户端注册特定的事件通知，并且可以请求将
特定的属性和控制模式信息传递给它们的事件处理程序。 此外，UI 自动化事件包含对引发它的元素的引用。 提供者可以通过有选择地引发事件来提高性能，具体取决
于是否有任何客户端正在侦听。 有关事件的更多信息，请参阅 [UI 自动化事件概述](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-eventsoverview) 。

##### 1-5-1-1-4、相关话题
> * [UI 自动化控件类型概述](#1-5-1-4UI自动化控件类型概述)
> * [UI 自动化控制模式概述](#1-5-1-5UI自动化控制模式概述)
> * [UI 自动化事件概述](#1-5-1-7UI自动化事件概述)

#### 1-5-1-2、UI自动化和活动可访问性
本文中
> * [编程语言](#1-5-1-2-1编程语言)
> * [服务器和客户端](#1-5-1-2-2服务器和客户端)
> * [用户界面元素](#1-5-1-2-3用户界面元素)
> * [树视图和导航](#1-5-1-2-4树视图和导航)
> * [角色和控制类型](#1-5-1-2-5角色和控制类型)
> * [状态和属性](#1-5-1-2-6状态和属性)
> * [活动](#1-5-1-2-7活动)
> * [从 UI 自动化访问 Active Accessibility 属性和对象](#1-5-1-2-8从UI自动化访问ActiveAccessibility属性和对象)
> * [相关话题](#1-5-1-2-9相关话题)

Microsoft Active Accessibility 是 Windows 95 中引入的遗留 API，旨在使 Windows 应用程序可访问。 Microsoft UI 自动化是 Windows 的新
辅助功能模型，旨在满足辅助技术产品和自动化测试工具的需求。 UI 自动化提供了对 Microsoft Active Accessibility 的许多改进。 本主题解释了这两种
技术之间的差异。

本主题包含以下部分。
> * [编程语言](#1-5-1-2-1编程语言)
> * [服务器和客户端](#1-5-1-2-2服务器和客户端)
> * [用户界面元素](#1-5-1-2-3用户界面元素)
> * [树视图和导航](#1-5-1-2-4树视图和导航)
> * [角色和控制类型](#1-5-1-2-5角色和控制类型)
> * [状态和属性](#1-5-1-2-6状态和属性)
> * [活动](#1-5-1-2-7活动)
> * [从 UI 自动化访问 Active Accessibility 属性和对象](#1-5-1-2-8从UI自动化访问ActiveAccessibility属性和对象)
> * [相关话题](#1-5-1-2-9相关话题)

##### 1-5-1-2-1、编程语言
Microsoft Active Accessibility 基于支持双接口的组件对象模型 (COM)，因此可以使用 C/C++ 和脚本语言进行编程。

引入 UI 自动化时，客户端 API 仅限于托管代码，而提供程序 API 包括托管和非托管实现。 在 Windows 7 中，引入了新的基于 COM 的客户端 API，以便更
轻松地使用 C/C++ 编写 UI 自动化客户端应用程序。
##### 1-5-1-2-2、服务器和客户端
在 Microsoft Active Accessibility 中，服务器和客户端直接通信，主要通过 [IAccessible](https://docs.microsoft.com/en-us/windows/desktop/api/oleacc/nn-oleacc-iaccessible) 接口的服务器实现。

在 UI 自动化中，核心服务位于服务器（提供者）和客户端之间。 核心服务调用提供者实现的接口并提供附加服务，例如为 UI 元素生成唯一的运行时标识符。 客
户端应用程序通过创建 CUIAutomation 对象来访问此核心服务。 该对象支持一组独立于提供者接口的客户端接口。 有关详细信息，请参阅创建 CUIAutomation
 对象。

UI 自动化提供程序可以向 Microsoft Active Accessibility 客户端提供信息，Microsoft Active Accessibility 服务器可以向 UI 自动化客户端
应用程序提供信息。 但是，由于 Microsoft Active Accessibility 没有像 UI 自动化那样公开那么多信息，因此这两种模型并不完全兼容。
##### 1-5-1-2-3、用户界面元素
Microsoft Active Accessibility 将 UI 元素呈现为与子标识符配对的 IAccessible 界面。 很难比较两个 IAccessible 指针以确定它们是否引用相
同的元素。

在 UI 自动化中，每个元素都表示为向客户端公开 IUIAutomationElement 接口的对象。 可以通过使用 IUIAutomationElement::GetRuntimeId 检索元
素的运行时标识符来比较元素。
##### 1-5-1-2-4、树视图和导航
屏幕上的 UI 元素可以看作是一个树形结构，以桌面为根，应用程序窗口为直接子项，应用程序中的元素为进一步的后代。

在 Microsoft Active Accessibility 中，许多与最终用户无关的 UI 元素都暴露在树形结构中。客户端应用程序必须检查树中的所有元素以确定哪些元素是
有意义的。

UI 自动化客户端应用程序通过过滤视图查看 UI。视图仅包含向用户提供信息或用户可以与之交互的元素。仅包含控制元素和仅内容元素的预定义视图可用，并且客户
端应用程序可以定义自定义视图。 UI 自动化可以更轻松地向用户描述 UI，并帮助用户与应用程序交互。

在 Microsoft Active Accessibility 中，元素之间的导航是空间的，例如，移动到位于屏幕左侧的元素，逻辑的，例如，移动到对话框中的下一个菜单项或 
Tab 键顺序中的下一项，或分层，例如，移动到容器中的第一个子元素或从子元素移动到其父元素。由于子元素并不总是实现 IAccessible 的对象，因此分层导航
变得复杂。

在 UI 自动化中，所有 UI 元素都是公开 IUIAutomationElement 接口并支持相同基本功能的 COM 对象。从提供者的角度来看，COM 对象实现了一个从 
IRawElementProviderSimple 继承的接口。导航主要是分层的；也就是说，从父母到孩子，从一个兄弟姐妹到下一个兄弟姐妹。但是，兄弟之间的导航具有逻辑元
素，因为它可能遵循 Tab 键顺序。客户端可以使用 IUIAutomationTreeWalker 使用树的任何过滤视图从任何起点导航。客户端还可以使用 
IUIAutomationElement::FindFirst 和 IUIAutomationElement::FindAll 导航到特定的子代或后代。例如，很容易检索对话框中支持指定控制模式的所
有元素。

UI 自动化中的导航比 Microsoft Active Accessibility 中的导航更加一致。某些元素（例如下拉列表和弹出窗口）在 Microsoft Active 
Accessibility 树中出现两次，从这些元素导航可能会产生意外结果。很难为钢筋控件正确实施 Microsoft Active Accessibility。 UI 自动化支持重新
设置父级和重新定位，因此元素可以放置在树中的任何位置，尽管窗口所有权强加了层次结构。
##### 1-5-1-2-5、角色和控制类型
Microsoft Active Accessibility 使用 accRole 属性 (IAccessible::get_accRole) 来检索 UI 中元素角色的描述，例如 ROLE_SYSTEM_SLIDER 
或 ROLE_SYSTEM_MENUITEM。元素的角色是其可用功能的主要线索。与控件的交互是通过使用 IAccessible::accSelect 和 
IAccessible::accDoDefaultAction 等固定方法来实现的。客户端应用程序和 UI 之间的交互仅限于可以通过 IAccessible 完成的操作。

相比之下，UI 自动化将由 IUIAutomationElement::CurrentControlType（或 IUIAutomationElement::CachedControlType）属性描述的元素的控
件类型与其预期功能分离。功能由提供者通过其专用接口的实现所支持的控制模式确定。可以组合控制模式来描述特定 UI 元素支持的完整功能集。一些提供程序需要
支持特定的控制模式。例如，复选框的提供者必须支持 Toggle 控件模式。其他提供者需要支持一组控制模式中的一个或多个。例如，按钮必须支持 Toggle 或 
Invoke 控件模式。还有一些支持无控制模式。例如，无法移动、调整大小或停靠的窗格没有控制模式。

UI 自动化支持自定义控件，这些控件由 UIA_CustomControlTypeId 常量标识，并且可以由 IUIAutomationElement::CurrentLocalizedControlType
（或 IUIAutomationElement::CachedLocalizedControlType）属性描述。

下表将 Microsoft Active Accessibility 对象角色映射到 UI 自动化控件类型。

> Active Accessibility 角色 | UI 自动化控件类型
> --- | ---
> ROLE_SYSTEM_PUSHBUTTON |	Button
> ROLE_SYSTEM_CLIENT |	Calendar
> ROLE_SYSTEM_CHECKBUTTON |	CheckBox
> ROLE_SYSTEM_COMBOBOX |	ComboBox
> ROLE_SYSTEM_CLIENT |	See Custom Control Types.
> ROLE_SYSTEM_LIST |	DataGrid
> ROLE_SYSTEM_LISTITEM |	DataItem
> ROLE_SYSTEM_DOCUMENT |	Document
> ROLE_SYSTEM_TEXT |	Edit
> ROLE_SYSTEM_GROUPING |	Group
> ROLE_SYSTEM_LIST |	Header
> ROLE_SYSTEM_COLUMNHEADER |	HeaderItem
> ROLE_SYSTEM_LINK |	Hyperlink
> ROLE_SYSTEM_GRAPHIC |	Image
> ROLE_SYSTEM_LIST |	List
> ROLE_SYSTEM_LISTITEM |	ListItem
> ROLE_SYSTEM_MENUPOPUP |	Menu
> ROLE_SYSTEM_MENUBAR |	MenuBar
> ROLE_SYSTEM_MENUITEM |	MenuItem
> ROLE_SYSTEM_PANE |	Pane
> ROLE_SYSTEM_PROGRESSBAR |	ProgressBar
> ROLE_SYSTEM_RADIOBUTTON |	RadioButton
> ROLE_SYSTEM_SCROLLBAR |	ScrollBar
> ROLE_SYSTEM_SEPARATOR |	Separator
> ROLE_SYSTEM_SLIDER |	Slider
> ROLE_SYSTEM_SPINBUTTON |	Spinner
> ROLE_SYSTEM_SPLITBUTTON |	SplitButton
> ROLE_SYSTEM_STATUSBAR |	StatusBar
> ROLE_SYSTEM_PAGETABLIST |	Tab
> ROLE_SYSTEM_PAGETAB |	TabItem
> ROLE_SYSTEM_TABLE |	Table
> ROLE_SYSTEM_STATICTEXT |	Text
> ROLE_SYSTEM_INDICATOR |	Thumb
> ROLE_SYSTEM_TITLEBAR |	TitleBar
> ROLE_SYSTEM_TOOLBAR |	ToolBar
> ROLE_SYSTEM_TOOLTIP |	ToolTip
> ROLE_SYSTEM_OUTLINE |	Tree
> ROLE_SYSTEM_OUTLINEITEM |	TreeItem
> ROLE_SYSTEM_WINDOW |	Window

##### 1-5-1-2-6、状态和属性
Microsoft Active Accessibility 元素支持一组通用属性。某些属性（例如 accState）必须描述不同的条件，具体取决于元素角色。服务器必须实现所有返
回属性的 IAccessible 方法，即使是那些与元素无关的属性。

UI 自动化定义了其他属性，其中一些对应于 Microsoft Active Accessibility 中的状态。某些属性对所有元素都是通用的，但其他属性特定于控件类型和控
件模式。 UI 自动化提供程序不必实现不相关的属性，但可以为它不支持的任何属性返回空值。 UI 自动化核心服务可以从默认窗口提供程序获取一些属性，这些属性
与提供程序显式实现的属性合并。

除了支持更多属性外，UI 自动化还通过允许缓存属性来实现更好的性能。
> Active Accessibility 属性访问器 | UI 自动化属性 ID | 备注
> --- | --- | ---
> get_accKeyboardShortcut |	UIA_AccessKeyPropertyId or UIA_AcceleratorKeyPropertyId |	UIA_AccessKeyPropertyId takes precedence if both are present.
> get_accName |	UIA_NamePropertyId	 |
> get_accRole |	UIA_ControlTypePropertyId |	See the previous table for mapping roles to control types.
> get_accValue |	UIA_ValueValuePropertyId or UIA_RangeValueValuePropertyId |	Valid only for control types that support IUIAutomationValuePattern or IUIAutomationRangeValuePattern. Range values are normalized to 0-100, to be consistent with Microsoft Active Accessibility behavior. Values are represented as strings.
> get_accHelp |	UIA_HelpTextPropertyId	 |
> accLocation |	UIA_BoundingRectanglePropertyId	 |
> get_accDescription |	Not supported. |	accDescription did not have a clear specification in Microsoft Active Accessibility, which resulted in servers placing different pieces of information in this property.
> get_accHelpTopic |	Not supported.	 |

[此表](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-entry-propids) 显示了两个模型中一些属性之间的对应关系。有关 
UI 自动化属性 ID 的描述，请参阅自动化元素属性标识符。

##### 1-5-1-2-7、活动
与 Microsoft Active Accessibility 不同，UI 自动化中的事件机制不依赖于与窗口句柄密切相关的 Windows 事件路由，并且不需要客户端应用程序设置
挂钩。 对事件的订阅可以微调到树的特定部分，而不仅仅是特定事件。 提供者还可以通过跟踪正在侦听的事件来微调引发事件。

客户端也更容易检索引发事件的元素，因为这些元素直接传递给事件回调。 如果在客户端订阅事件时提供了缓存请求，则会自动预取元素的属性。

下表显示了 Microsoft Active Accessibility 事件常量和 UI 自动化事件 ID 的对应关系。

> win事件 | UI自动化事件ID
> --- | ---
> EVENT_OBJECT_ACCELERATORCHANGE	| UIA_AcceleratorKeyPropertyId property change.
> EVENT_OBJECT_CONTENTSCROLLED	| UIA_ScrollVerticalScrollPercentPropertyId or UIA_ScrollHorizontalScrollPercentPropertyId property change on the associated scroll bars.
> EVENT_OBJECT_CREATE	| UIA_StructureChangedEventId.
> EVENT_OBJECT_DEFACTIONCHANGE	| No equivalent.
> EVENT_OBJECT_DESCRIPTIONCHANGE	| No exact equivalent; perhaps UIA_HelpTextPropertyId or UIA_LocalizedControlTypePropertyId property change.
> EVENT_OBJECT_DESTROY	| UIA_StructureChangedEventId.
> EVENT_OBJECT_FOCUS	| UIA_AutomationFocusChangedEventId.
> EVENT_OBJECT_HELPCHANGE	| UIA_HelpTextPropertyId change.
> EVENT_OBJECT_HIDE	| UIA_StructureChangedEventId.
> EVENT_OBJECT_LOCATIONCHANGE	| UIA_BoundingRectanglePropertyId property change.
> EVENT_OBJECT_NAMECHANGE	| UIA_NamePropertyId property change.
> EVENT_OBJECT_PARENTCHANGE	| UIA_StructureChangedEventId.
> EVENT_OBJECT_REORDER	| Not consistently used in Microsoft Active Accessibility. No directly corresponding event is defined in UI Automation.
> EVENT_OBJECT_SELECTION	| UIA_SelectionItem_ElementSelectedEventId.
> EVENT_OBJECT_SELECTIONADD	| UIA_SelectionItem_ElementAddedToSelectionEventId.
> EVENT_OBJECT_SELECTIONREMOVE	| UIA_SelectionItem_ElementRemovedFromSelectionEventId.
> EVENT_OBJECT_SELECTIONWITHIN	| No equivalent.
> EVENT_OBJECT_SHOW	| UIA_StructureChangedEventId.
> EVENT_OBJECT_STATECHANGE	| Various property-changed events.
> EVENT_OBJECT_VALUECHANGE	| UIA_RangeValueValuePropertyId and UIA_ValueValuePropertyId changed.
> EVENT_SYSTEM_ALERT	| No equivalent.
> EVENT_SYSTEM_CAPTUREEND	| No equivalent.
> EVENT_SYSTEM_CAPTURESTART	| No equivalent.
> EVENT_SYSTEM_CONTEXTHELPEND	| No equivalent.
> EVENT_SYSTEM_CONTEXTHELPSTART	| No equivalent.
> EVENT_SYSTEM_DIALOGEND	| UIA_Window_WindowClosedEventId.
> EVENT_SYSTEM_DIALOGSTART	| UIA_Window_WindowOpenedEventId.
> EVENT_SYSTEM_DRAGDROPEND	| No equivalent.
> EVENT_SYSTEM_DRAGDROPSTART	| No equivalent.
> EVENT_SYSTEM_FOREGROUND	| UIA_AutomationFocusChangedEventId.
> EVENT_SYSTEM_MENUEND	| UIA_MenuModeEndEventId.
> EVENT_SYSTEM_MENUPOPUPEND	| UIA_MenuClosedEventId.
> EVENT_SYSTEM_MENUPOPUPSTART	| UIA_MenuOpenedEventId.
> EVENT_SYSTEM_MENUSTART	| UIA_MenuModeStartEventId.
> EVENT_SYSTEM_MINIMIZEEND	| UIA_WindowWindowVisualStatePropertyId property change.
> EVENT_SYSTEM_MINIMIZESTART	| UIA_WindowWindowVisualStatePropertyId property change.
> EVENT_SYSTEM_MOVESIZEEND	| UIA_BoundingRectanglePropertyId property change.
> EVENT_SYSTEM_MOVESIZESTART	| UIA_BoundingRectanglePropertyId property change.
> EVENT_SYSTEM_SCROLLINGEND	| UIA_ScrollVerticalScrollPercentPropertyId or UIA_ScrollHorizontalScrollPercentPropertyId property change.
> EVENT_SYSTEM_SCROLLINGSTART	| UIA_ScrollVerticalScrollPercentPropertyId or UIA_ScrollHorizontalScrollPercentPropertyId property change.
> EVENT_SYSTEM_SOUND	| No equivalent.
> EVENT_SYSTEM_SWITCHEND	| No equivalent, but a UIA_AutomationFocusChangedEventId event signals that a new application has received the focus.
> EVENT_SYSTEM_SWITCHSTART	| No equivalent.
> No equivalent.	| UIA_MultipleViewCurrentViewPropertyId property change.
> No equivalent.	| UIA_ScrollHorizontallyScrollablePropertyId property change.
> No equivalent.	| UIA_ScrollVerticallyScrollablePropertyId property change.
> No equivalent.	| UIA_ScrollHorizontalScrollPercentPropertyId property change.
> No equivalent.	| UIA_ScrollVerticalScrollPercentPropertyId property change.
> No equivalent.	| UIA_ScrollHorizontalViewSizePropertyId property change.
> No equivalent.	| UIA_ScrollVerticalViewSizePropertyId property change.
> No equivalent.	| UIA_ToggleToggleStatePropertyId property change.
> No equivalent.	| UIA_WindowWindowVisualStatePropertyId property change
> No equivalent.	| UIA_AsyncContentLoadedEventId event.
> No equivalent.	| UIA_ToolTipOpenedEventId event.

##### 1-5-1-2-8、从UI自动化访问ActiveAccessibility属性和对象
在 Microsoft Active Accessibility 中不可用的 UI 自动化的一个关键特性是能够通过单个跨进程操作获取多个属性。

现有的 Microsoft Active Accessibility 客户端可以通过使用 IUIAutomationLegacyIAccessiblePattern 接口来利用此功能。 此接口表示一种控
件模式，该模式在 UI 元素上公开 Microsoft Active Accessibility 属性和方法。 检索元素时，应用程序可以请求缓存此控制模式及其属性。

IUIAutomationLegacyIAccessiblePattern 还使客户端能够从对 IAccessible 没有本机支持的元素获取 Microsoft Active Accessibility 属性。

IUIAutomationLegacyIAccessiblePattern 的属性更改不会引发 UI 自动化事件。
##### 1-5-1-2-9、相关话题
> * [UI 自动化树概述](#1-5-1-3UI自动化树概述)
> * [UI 自动化控件类型概述](#1-5-1-4UI自动化控件类型概述)
> * [UI 自动化属性概述](#1-5-1-6UI自动化属性概述)
> * [UI 自动化事件概述](#1-5-1-7UI自动化事件概述)

#### 1-5-1-3、*UI自动化树概述*
辅助技术产品和测试脚本导航 Microsoft UI 自动化树以收集有关 UI 及其元素的信息。

UI 自动化树中有一个根元素，它代表 Windows 桌面窗口（“桌面”），其子元素代表应用程序窗口。这些子元素中的每一个都可以包含表示 UI 片段的元素，例如
菜单、按钮、工具栏和列表框。这些元素可以包含元素，例如列表项。

UI 自动化树不是一个固定的结构。它的整体很少见，因为它可能包含数千种元素。 UI 自动化树的某些部分是根据客户的需要构建的，并且树的结构会随着元素的添
加、移动或删除而变化。

UI 自动化提供程序通过在片段中的项目之间实现导航来支持 UI 自动化树。片段是来自特定框架的元素的完整子树，并且具有通常托管在窗口中的根元素（称为片段
根）。

提供者不关心从一个控件到另一个控件的导航。这是由 UI 自动化核心管理的，它使用来自默认窗口提供程序的信息。

本主题包含以下部分。
> * [UI 自动化树的视图](#1-5-1-3-1UI自动化树的视图)
>   * [原始视图](#1-5-1-3-1-1原始视图)
>   * [控制视图](#1-5-1-3-1-2控制视图)
>   * [内容视图](#1-5-1-3-1-3内容视图)
> * [相关话题](#1-5-1-3-2相关话题)

> * [IUIAutomation::RawViewWalker](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomation-get_rawviewwalker)
> * [IUIAutomationTreeWalker](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nn-uiautomationclient-iuiautomationtreewalker)
> * [IUIAutomationElement::IsControlElement](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomationelement-get_currentiscontrolelement)
> * [ControlViewWalker](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomation-get_controlviewwalker)

##### 1-5-1-3-1、UI自动化树的视图
可以过滤 UI 自动化树以创建仅包含与特定客户端相关的那些 UI 自动化元素的视图。这种方法允许客户自定义通过 UI 自动化呈现的结构以满足他们的特定需求。

客户端可以通过范围和过滤来自定义视图。范围界定是从基本元素开始定义视图的范围。例如，应用程序可能只想查找桌面的直接子项，或应用程序窗口的所有后代。
过滤是定义视图中包含的元素的类型。

UI 自动化提供程序通过定义元素的属性来支持过滤，包括 IUIAutomationElement::IsControlElement 和 
IUIAutomationElement::IsContentElement 属性。

UI 自动化提供三种默认视图：原始视图、控制视图和内容视图。这些视图由执行的过滤类型定义。任何视图的范围都由应用程序定义。该应用程序可以对属性应用其他
过滤器；例如，在控件视图中仅包含启用的控件。
##### 1-5-1-3-1-1、原始视图
UI 自动化树的原始视图是以桌面为根的自动化元素的完整树。 原始视图紧跟应用程序的原生编程结构，是可用的最详细视图。 它也是构建树的其他视图的基础。 由
于此视图依赖于底层 UI 框架，Windows Presentation Foundation (WPF) 按钮的原始视图与 Microsoft Win32 按钮具有不同的原始视图。

原始视图是通过在不指定属性的情况下搜索元素或使用 IUIAutomation::RawViewWalker 获取 IUIAutomationTreeWalker 接口来导航树获得的。
##### 1-5-1-3-1-2、控制视图
控制视图是原始视图的子集。它仅包括那些将 IUIAutomationElement::IsControlElement 属性设置为 TRUE 的 UI 项。

控制视图包括向用户提供信息或使用户能够执行操作的 UI 项。这些是自动化测试应用程序最感兴趣的 UI 项。

控制视图还包括有助于 UI 逻辑结构的非交互式 UI 项。其中包括项目容器，例如列表视图标题、工具栏、菜单和状态栏。出现在控件视图中的其他非交互项是带有信
息的图形和对话框中的静态文本。

仅用于布局或装饰目的的非交互项，例如用于在对话框中布局控件的面板，不会出现在控件视图中。

UI 自动化树的控制视图紧密映射到最终用户感知的 UI 结构。这使得辅助技术产品更容易向最终用户描述 UI 并帮助最终用户与应用程序交互。

通过搜索将 IUIAutomationElement::IsControlElement 属性设置为 true 的元素，或通过使用 ControlViewWalker 获取用于导航树的 
IUIAutomationTreeWalker 接口来获取控件视图。
##### 1-5-1-3-1-3、内容视图
UI 自动化树的内容视图是控制视图的子集。它仅包括那些同时将 IUIAutomationElement::IsControlElement 和 
IUIAutomationElement::IsContentElement 属性设置为 TRUE 的 UI 项。

内容视图包含在用户界面中传达实际信息的 UI 项，包括可以接收键盘焦点的 UI 项和一些不是 UI 项上的标签的文本。这些是屏幕阅读器应用程序最感兴趣的 UI 
项目。例如，下拉组合框中的值显示在内容视图中，因为这些值表示最终用户正在使用的信息。

在内容视图中，组合框和列表框都表示为 UI 项的集合，其中可以选择一个或多个项。一项始终打开且一项可以展开和折叠的事实与内容视图无关，因为它旨在显示正
在呈现给用户的数据或内容。

内容视图是通过搜索 IsControlElement 和 CurrentIsContentElement 属性都设置为 TRUE 的元素获得的，或者通过使用 
IUIAutomation::ContentViewWalker 获得用于导航树的 IUIAutomationTreeWalker 接口。

下图显示了控制视图和内容视图之间的区别。第一个图像显示了一个简单的组合框，下拉列表中有三个项目。第二张图片显示了组合框 UI 项在 UISpy.exe 应用程
序的控件和内容视图中的显示方式。
##### 1-5-1-3-2、相关话题

#### 1-5-1-4、UI自动化控件类型概述
Microsoft UI 自动化控件类型是用作众所周知的标识符的属性，这些标识符指示特定 UI 元素表示的控件类型，例如组合框或按钮。 客户端应用程序使用该类型
来识别控件的功能并确定如何与之交互。

本主题包含以下部分：
> * [UI 自动化控制类型要求](#1-5-1-4-1UI自动化控制类型要求)
> * [LocalizedControlType 属性](#1-5-1-4-2LocalizedControlType属性)
> * [当前的 UI 自动化控件类型](#1-5-1-4-3当前的UI自动化控件类型)
> * [相关话题](#1-5-1-4-4相关话题)

> * [IUIAutomationElement::CurrentControlType](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomationelement-get_currentcontroltype)
> * [IUIAutomationElement::CachedControlType](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomationelement-get_cachedcontroltype)
> * [UIA_CustomControlTypeId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-controltype-ids)
> * [UIA_LocalizedControlTypePropertyId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-automation-element-propids)

##### 1-5-1-4-1、UI自动化控制类型要求
每个 UI 自动化控件类型都有一组与之关联的条件。当提供者将控件类型分配给控件时，提供者必须确保控件满足与该控件类型关联的所有条件。条件包括：

> * UI 自动化控件模式：每种控件类型都有一组控件必须支持的控件模式、一个可选的集合和一个控件不得支持的集合。
> * UI 自动化属性值：每个控件类型都有一组控件必须支持的属性。
> * UI 自动化事件：每种控件类型都有一组控件必须支持的事件。
> * UI 自动化树结构：每种控件类型都定义了控件必须如何出现在 UI 自动化树结构中。

当控件满足特定控件类型的条件时，IUIAutomationElement::CurrentControlType（或 IUIAutomationElement::CachedControlType）属性值将指
示该控件类型。

如果您的控件不符合特定控件类型的规范，请使用 UIA_CustomControlTypeId 作为控件类型 ID，并使用相关的控件模式和属性完整描述该控件。您还可以将 
UIA_LocalizedControlTypePropertyId 属性设置为最能描述控件类型的字符串。

##### 1-5-1-4-2、LocalizedControlType属性
如果您使用预定义的控件类型来描述您的控件，请使用 UIA_LocalizedControlTypePropertyId 属性的默认值，并允许 UI 自动化提供本地化字符串以供提供
程序正确公开。 如果您无法使用预定义的控件类型来描述您的控件，请将 UIA_LocalizedControlTypePropertyId 属性设置为准确描述控件类型的本地化字符
串。 该字符串应该简洁，但要足够准确，以便屏幕阅读器等辅助技术可以在 UI 中使用它来通知用户控件的类型。

##### 1-5-1-4-3、当前的UI自动化控件类型
以下主题描述了 UI 自动化控件类型。 对于每种控件类型，描述包括给定类型的控件必须支持的一组条件：
> * AppBar Control Type
> * Button Control Type
> * Calendar Control Type
> * CheckBox Control Type
> * ComboBox Control Type
> * DataGrid Control Type
> * DataItem Control Type
> * Document Control Type
> * Edit Control Type
> * Group Control Type
> * Header Control Type
> * HeaderItem Control Type
> * Hyperlink Control Type
> * Image Control Type
> * List Control Type
> * ListItem Control Type
> * Menu Control Type
> * MenuBar Control Type
> * MenuItem Control Type
> * Pane Control Type
> * ProgressBar Control Type
> * RadioButton Control Type
> * ScrollBar Control Type
> * SemanticZoom Control Type
> * Separator Control Type
> * Slider Control Type
> * Spinner Control Type
> * SplitButton Control Type
> * StatusBar Control Type
> * Tab Control Type
> * TabItem Control Type
> * Table Control Type
> * Text Control Type
> * Thumb Control Type
> * TitleBar Control Type
> * ToolBar Control Type
> * ToolTip Control Type
> * Tree Control Type
> * TreeItem Control Type
> * Window Control Type

##### 1-5-1-4-4、相关话题


#### 1-5-1-5、UI自动化控制模式概述
控件模式是一种接口实现，它向 Microsoft UI 自动化客户端应用程序公开控件功能的特定方面。客户端使用通过控件模式公开的属性和方法来检索有关控件特定功
能的信息，或操纵控件行为的特定方面。例如，呈现表格界面的控件使用网格控件模式来公开表格中的行数和列数，并使客户端能够从表格中检索项目。

UI 自动化使用控制模式来表示常见的控制行为。例如，对于可以调用的控件（例如按钮）使用 Invoke 控件模式，对于具有滚动条的控件（例如列表框、列表视图或
组合框）使用 Scroll 控件模式。因为每个控制模式代表一个单独的功能，所以可以组合控制模式来描述特定控件支持的完整功能集。

    笔记
    聚合控件由子控件构建，这些子控件为父控件公开的功能提供用户界面，并且父控件应实现通常与其子控件关联的所有控件模式。反过来，这些相同的控制模式不需要由
    子控件实现。

本主题包含以下部分：
> * [UI 自动化控制模式组件](#1-5-1-5-1UI自动化控制模式组件)
> * [提供者和客户端中的控制模式](#1-5-1-5-2提供者和客户端中的控制模式)
> * [动态控制模式](#1-5-1-5-3动态控制模式)
> * [控制模式和相关接口](#1-5-1-5-4控制模式和相关接口)
> * [相关话题](#1-5-1-5-5相关话题)

> * [IScrollProvider](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/nn-uiautomationcore-iscrollprovider)
> * [IUIAutomationScrollPattern](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nn-uiautomationclient-iuiautomationscrollpattern)


##### 1-5-1-5-1、UI自动化控制模式组件
控件模式支持定义控件中可用的离散功能块所需的方法、属性、事件和关系。

> * 这些方法允许 UI 自动化客户端操作控件。
> * 属性和事件提供有关控件功能和状态的信息。
> * UI 自动化元素与其父级、子级和同级之间的关系描述了 UI 自动化树中的元素结构。

控制模式与控件相关的方式类似于接口与组件对象模型 (COM) 对象的相关方式。 在 COM 中，您可以查询一个对象以询问它支持哪些接口，然后使用这些接口访问
功能。 在 UI 自动化中，客户端可以询问控件它支持哪些控件模式，然后通过受支持的控件模式公开的属性、方法、事件和结构与控件进行交互。
##### 1-5-1-5-2、提供者和客户端中的控制模式
UI 自动化提供程序实现控件模式接口，以公开控件支持的特定功能的适当行为。 这些接口不直接暴露给客户端，而是由 UI 自动化核心用于实现另一组客户端接口。 
例如，提供程序通过 IScrollProvider 向 UI 自动化公开滚动功能，而 UI 自动化通过 IUIAutomationScrollPattern 向客户端公开该功能。
##### 1-5-1-5-3、动态控制模式
某些控件并不总是支持同一组控制模式。 例如，多行编辑控件仅在其包含的文本行数超过其可视区域中显示的行数时才启用垂直滚动。 删除足够多的文本时禁用滚动，
从而不再需要滚动。 对于此示例，动态支持 IUIAutomationScrollPattern，具体取决于编辑框中的文本量。
##### 1-5-1-5-4、控制模式和相关接口
下表描述了 UI 自动化控制模式。 该表还列出了用于实现控制模式的提供者接口，以及用于访问它们的客户端接口。
> Name |	Provider interface |	Client interface |	Description
> --- | --- | --- | ---
> Annotation	| IAnnotationProvider	| IUIAutomationAnnotationPattern	| Used to expose the properties of an annotation in a document, for example comments in the margin that are connected to document text.
> Dock	| IDockProvider	| IUIAutomationDockPattern	| Used for controls that can be docked in a docking container, for example, toolbars or tool palettes.
> Drag	| IDragProvider	| IUIAutomationDragPattern	| Used to support draggable controls, or controls with draggable items.
> DropTarget	| IDropTargetProvider	| IUIAutomationDropTargetPattern	| Used to support controls that can be the target of a drag-and-drop operation.
> ExpandCollapse	| IExpandCollapseProvider	| IUIAutomationExpandCollapsePattern	| Used for controls that can be expanded or collapsed, for example, menu items in an application, such as the File menu.
> Grid	| IGridProvider	| IUIAutomationGridPattern	| Used for controls that support grid functionality, such as sizing and moving to a specified cell, for example, the large icon view in Windows Explorer or simple tables in Microsoft Office Word.
> GridItem	| IGridItemProvider	| IUIAutomationGridItemPattern	| Used for controls that have cells in grids. The individual cells should support the GridItem pattern, for example, each cell in Windows Explorer detail view.
> Invoke	| IInvokeProvider	| IUIAutomationInvokePattern	| Used for controls that can be invoked, such as buttons.
> ItemContainer	| IItemContainerProvider	| IUIAutomationItemContainerPattern	| Used for controls that can contain other items.
> LegacyIAccessible	| ILegacyIAccessibleProvider	| IUIAutomationLegacyIAccessiblePattern	| Used to expose Microsoft Active Accessibility properties and methods to UI Automation clients.
> MultipleView	| IMultipleViewProvider	| IUIAutomationMultipleViewPattern	| Used for controls that can switch between multiple representations of the same set of information, data, or children, for example, a list-view control where data is available in thumbnail, tile, icon, list, or detail views.
> ObjectModel	| IObjectModelProvider	| IUIAutomationObjectModelPattern	| Used to expose a pointer to the underlying object model of a document. This control pattern allows a client to navigate from a UI Automation element into the underlying object model.
> RangeValue	| IRangeValueProvider	| IUIAutomationRangeValuePattern	| Used for controls that have a range of values. For example, a spinner control that displays years might have a range of 1900—2010, while a spinner control that displays months would have a range of 1—12.
> Scroll	| IScrollProvider	| IUIAutomationScrollPattern	| Used for controls that can scroll when there is more information than can be displayed in the viewable area of the control.
> ScrollItem	| IScrollItemProvider	| IUIAutomationScrollItemPattern	| Used for controls that have individual items in a list that scrolls, for example, a list control in a combo box control.
> Selection	| ISelectionProvider	| IUIAutomationSelectionPattern	| Used for selection container controls, for example, list boxes and combo boxes.
> SelectionItem	| ISelectionItemProvider	| IUIAutomationSelectionItemPattern	| Used for individual items in selection container controls, such as list boxes and combo boxes.
> Spreadsheet	| ISpreadsheetProvider	| IUIAutomationSpreadsheetPattern	| Used to expose the contents of a spreadsheet or other grid-based document. Controls that implement the Spreadsheet control pattern should also implement the Grid control pattern.
> SpreadsheetItem	| ISpreadsheetItemProvider	| IUIAutomationSpreadsheetItemPattern	| Used to expose the properties of a cell in a spreadsheet or other grid-based document. Controls that implement the SpreadsheetItem control pattern should also implement the GridItem control pattern.
> Styles	| IStylesProvider	| IUIAutomationStylesPattern	| Used to describe a UI element that has a specific style, fill color, fill pattern, or shape.
> SynchronizedInput	| ISynchronizedInputProvider	| IUIAutomationSynchronizedInputPattern	| Used for controls that accept keyboard or mouse input.
> Table	| ITableProvider	| IUIAutomationTablePattern	| Used for controls that have a grid and header information.
> TableItem	| ITableItemProvider	| IUIAutomationTableItemPattern	| Used for items in a table.
> Text	| ITextProvider	| IUIAutomationTextPattern	| Used for edit controls and documents that expose textual information.
> TextEdit	| ITextEditProvider	| IUIAutomationTextEditPattern	| Used for edit controls that modify text programmatically, for example a control that performs auto-correction or enables input composition.
> TextChild	| ITextChildProvider	| IUIAutomationTextChildPattern	| Used to access an element’s nearest ancestor that supports the Text control pattern.
> TextRange	| ITextRangeProvider	| IUIAutomationTextRange	| Used for retrieving textual content, text attributes, and embedded objects from text-based controls such as edit controls and documents.
> Toggle	| IToggleProvider	| IUIAutomationTogglePattern	| Used for controls where the state can be toggled, for example, check boxes and checkable menu items.
> Transform	| ITransformProvider	| IUIAutomationTransformPattern	| Used for controls that can be resized, moved, and rotated. Typical uses for the Transform control pattern are in designers, forms, graphical editors, and drawing applications.
> Value	| IValueProvider	| IUIAutomationValuePattern	| Used for controls that have a value that does not lie within a specified range, for example, a date-time picker.
> VirtualizedItem	| IVirtualizedItemProvider	| IUIAutomationVirtualizedItemPattern	| Used for controls that work with items in a virtual list.
> Window	| IWindowProvider	| IUIAutomationWindowPattern	| Used for windows. Examples are top-level application windows, multiple-document interface (MDI) child windows, and dialog boxes.
##### 1-5-1-5-5、相关话题

#### 1-5-1-6、UI自动化属性概述
Microsoft UI 自动化提供程序公开 UI 自动化元素的属性。 属性使客户端应用程序能够检索有关控件的信息。

UI 自动化公开了两种不同的属性：自动化元素属性和控制模式属性。 自动化元素属性由一组通用属性组成，例如 Name、AcceleratorKey 和 ClassName，这些
属性由所有 UI 自动化元素公开，无论控件类型如何。 大多数自动化元素属性都是静态值。

控件模式属性是由支持特定控件模式的控件公开的属性。 每个控件模式都有一组相应的控件必须公开的控件模式属性。 例如，支持 Grid 控件模式的控件公开了 
ColumnCount 和 RowCount 属性。 大多数控制模式属性都是动态值。

本主题包含以下部分。
> * [属性标识符](#1-5-1-6-1属性标识符)
> * [属性值](#1-5-1-6-2属性值)
> * [属性和事件](#1-5-1-6-3属性和事件)
> * [相关话题](#1-5-1-6-4相关话题)

> * [IRawElementProviderAdviseEvents::AdviseEventAdded](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/nf-uiautomationcore-irawelementprovideradviseevents-adviseeventadded)
> * [IUIAutomationElement::GetCachedPropertyValue](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomationelement-getcachedpropertyvalue)
> * [IDockProvider::SetDockPosition](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/nf-uiautomationcore-idockprovider-setdockposition)
> * [IUIAutomationDockPattern::SetDockPosition](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomationdockpattern-setdockposition)
> * [UIA_SelectionItem_ElementSelectedEventId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids)
> * [IUIAutomationPropertyChangedEventHandler](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nn-uiautomationclient-iuiautomationpropertychangedeventhandler)
> * [IUIAutomation::AddPropertyChangedEventHandler](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomation-addpropertychangedeventhandler)
> * [IUIAutomation::AddPropertyChangedEventHandlerNativeArray](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nf-uiautomationclient-iuiautomation-addpropertychangedeventhandlernativearray)

##### 1-5-1-6-1、属性标识符
每个属性都由一个称为属性标识符 (ID) 的 PROPERTYID 数值标识。 提供者和客户端在方法调用（例如 
IRawElementProviderAdviseEvents::AdviseEventAdded 和 IUIAutomationElement::GetCachedPropertyValue）中使用数字 ID 来识别属性请
求。 有关每个 UI 自动化属性标识符的详细说明，包括每个属性的数据类型和默认值，请参阅 [属性标识符](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-entry-propids) 。
##### 1-5-1-6-2、属性值
所有属性都是只读的，尽管可以使用作用于控件的方法来更改某些属性，例如 IDockProvider::SetDockPosition（提供者）或 
IUIAutomationDockPattern::SetDockPosition（客户端）。

有关检索属性值的信息，请参阅 [从 UI 自动化元素中检索属性](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-propertiesforclients) 。
##### 1-5-1-6-3、属性和事件
与 UI 自动化中的属性密切相关的是属性更改事件的概念。对于动态属性，客户端应用程序需要一种方法来知道属性值已更改，以便它可以更新其信息缓存或以其他方
式对新信息做出反应。客户端可以注册以侦听任何属性上的属性更改事件。

当 UI 中的某些内容发生变化时，提供者会引发事件。例如，如果选中或清除复选框，则由 Toggle 控件模式的提供者实现引发属性更改事件。提供者可以选择性地
引发事件，具体取决于是否有任何客户端正在侦听事件或侦听特定事件。

并非所有属性更改都会引发事件；这完全取决于元素的 UI 自动化提供程序的实现。例如，列表框的标准代理提供程序不会在 Selection 属性更改时引发属性更改
事件。在这种情况下，应用程序必须侦听选择更改时引发的事件 (UIA_SelectionItem_ElementSelectedEventId)。

客户端通过订阅事件来监听事件，如订阅 UI 自动化事件中所述。特别是对于属性更改的事件，客户端必须实现 IUIAutomationPropertyChangedEventHandler 
并将接口传递给 IUIAutomation::AddPropertyChangedEventHandler 或 IUIAutomation::AddPropertyChangedEventHandlerNativeArray。
##### 1-5-1-6-4、相关话题

#### 1-5-1-7、UI自动化事件概述
Microsoft UI 自动化事件通知是辅助技术的一项关键功能，例如屏幕阅读器和屏幕放大器。 这些 UI 自动化客户端跟踪 UI 自动化提供程序在 UI 中发生某些事
情时引发的事件，并使用这些信息来通知最终用户。

通过允许提供程序应用程序有选择地引发事件来提高效率，这取决于是否有任何客户端订阅了这些事件，或者根本不订阅这些事件，如果没有客户端正在侦听任何事件。

UI 自动化事件分为以下几类。

> 事件分类 |	描述
> --- | ---
> Property change | 当 UI 自动化元素或控件模式上的属性发生更改时引发。 例如，如果客户端需要监视应用程序复选框控件，它可以注册以侦听 IUIAutomationTogglePattern::CurrentToggleState 属性上的属性更改事件。 当复选框控件被选中或取消选中时，提供者会引发事件，客户端可以根据需要采取行动。
> Element action | 当最终用户或编程活动导致 UI 更改时引发，例如，当单击或通过 IUIAutomationInvokePattern 调用按钮时。
> Structure change | 当 UI 自动化树的结构发生变化时引发。 当新的 UI 项目在桌面上变得可见、隐藏或删除时，结构会发生变化。
> Global desktop change | 当客户端发生全局感兴趣的操作时引发，例如当焦点从一个元素转移到另一个元素时，或者当窗口关闭时。
> Notification | 当应用调用 UiaRaiseNotificationEvent 函数时引发。 NotificationKind 指示通知的类型。

有些事件并不一定意味着 UI 的状态发生了变化。 例如，如果用户选择文本输入字段，然后单击按钮更新该字段，则会引发 UIA_Text_TextChangedEventId 事
件，即使用户实际上并未更改文本。 处理事件时，客户端应用程序可能需要在采取行动之前检查是否有任何实际变化。

即使 UI 的状态没有改变，也可能引发以下事件。
> * [UIA_AutomationPropertyChangedEventId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids) (depending on the property that has changed)
> * [UIA_SelectionItem_ElementSelectedEventId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids)
> * [UIA_Selection_InvalidatedEventId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids)
> * [UIA_Text_TextChangedEventId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids)

有关所有 UI 自动化事件的描述，请参阅 [事件标识符](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-event-ids) 。

#### 1-5-1-8、自定义属性、事件和控制模式
Microsoft UI 自动化 API 指定了一组预定义的核心属性、控制模式和事件。 但是，应用程序不限于使用这些预定义的规范。 UI 自动化可扩展性功能使第三方
能够引入自定义的、相互同意的属性、事件和控制模式，以支持新的 UI 元素和应用程序场景。 UI 自动化提供程序和客户端可以立即开始使用自定义属性、事件和
控件模式，而无需更新核心 UI 自动化框架。

##### 1-5-1-8-1、[Designing Custom Properties, Events, and Control Patterns](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-designingcustompropseventpatterns)

自定义属性、事件或控件模式的设计应该在各种控件实现中都很有用。应避免仅在有限情况下有用的特定于控制或应用程序的设计。设计应遵循现有 Microsoft UI 
自动化属性、事件和控制模式的示例，这些属性、事件和控制模式都经过精心设计，以满足各种可访问性和自动化测试应用程序的需求。

为自定义属性、事件或控制模式实现规范涉及客户端和提供方双方的合作和协议，并且需要双方一致地实施规范。鼓励公司与可访问性互操作性联盟 (AIA) 等行业组
织合作，设计和发布自定义属性、事件或控制模式的规范。通过这种方式，可以达成共识并确保与最广泛的应用程序的互操作性。

本主题包含以下部分：
> * [何时使用自定义属性和事件](#1-5-1-8-1-1何时使用自定义属性和事件)
> * [设计自定义属性](#1-5-1-8-1-2设计自定义属性)
> * [设计自定义事件](#1-5-1-8-1-3设计自定义事件)
>   * [自定义 UI 自动化事件和 WinEvents](#1-5-1-8-1-3-1自定义UI自动化事件和WinEvents)
> * [设计自定义控制模式](#1-5-1-8-1-4设计自定义控制模式)
> * [自定义控件类型](#1-5-1-8-1-5自定义控件类型)
> * [相关话题](#1-5-1-8-1-6相关话题)

> * [UIA_IsExpandCollapsePatternAvailablePropertyId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-control-pattern-availability-propids)
> * [UIA_IsScrollItemPatternAvailablePropertyId](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-control-pattern-availability-propids)
> * [UIAutomationType](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ne-uiautomationcore-uiautomationtype)

###### 1-5-1-8-1-1、何时使用自定义属性和事件
在创建自定义属性、事件或控件模式之前，请确保 UI 自动化不提供现有解决方案。 例如，不需要创建自定义的“Click”控件模式，因为 Invoke 控件模式已经描
述了该功能。

如果您决定需要自定义属性、事件或控件模式，请确保它不是太模糊或通用。 例如，称为“显示”的控件模式没有用处，因为控件的可见性可以由元素上的可用性属性指
示，例如 UIA_IsExpandCollapsePatternAvailablePropertyId 或 UIA_IsScrollItemPatternAvailablePropertyId。

在实施自定义解决方案之前，请仔细确认是否需要，然后完整设计功能。
###### 1-5-1-8-1-2、设计自定义属性
UI 自动化包括两种基本类型的属性：自动化元素属性和控制模式属性。自动化元素属性由一组通用属性组成，例如 Name、AcceleratorKey 和 ClassName，这
些属性由所有 UI 自动化元素公开，无论控件类型如何。控件模式属性由控件通过特定的控件模式公开。每个控件模式都有一组相应的控件必须公开的控件模式属性。
例如，支持 [Grid](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementinggrid) 控件模式的控件公开了 ColumnCount 和 RowCount 属性。

自定义自动化元素属性或控制模式属性应遵循以下设计准则：

> * 自定义属性必须具有 [UIAutomationType](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ne-uiautomationcore-uiautomationtype) 枚举指定的以下数据类型之一。自定义属性不支持其他数据类型。
>   * UIAutomationType_Bool
>   * UIAutomationType_Double
>   * UIAutomationType_Element
>   * UIAutomationType_Int
>   * UIAutomationType_Point
>   * UIAutomationType_String
> * 如果自定义属性包含字符串数据 ( [BSTR](https://docs.microsoft.com/en-us/previous-versions/windows/desktop/automat/bstr) )，规范必须说明该属性是否可本地化（即字符串是否可以翻译成不同的 UI 语言）。
> * 自定义属性不应与现有属性的特性或功能重叠。
###### 1-5-1-8-1-3、设计自定义事件
应用程序使用 UI 自动化事件通知来响应涉及 UI 项的更改和操作。 大多数属性都有关联的属性更改事件，当属性值更改时 UI 自动化会引发这些事件。 如果您引
入自定义属性，则应考虑引入任何可能还需要的相应自定义事件。

自定义事件应遵循以下设计准则：

> * 自定义事件必须是“无状态的”。 它不能与特定的属性或值相关联。
> * 自定义事件不应与任何现有事件的定义或角色重叠。
####### 1-5-1-8-1-3-1、自定义UI自动化事件和WinEvents
[WinEvents](https://docs.microsoft.com/en-us/windows/win32/winauto/winevents-infrastructure) 是 Microsoft Windows 平台中一种有用的进程间通信和事件机制。 但是，引入新的 WinEvent ID 是有风险的，因为它可能会导致与其他应用程
序或操作系统发生冲突，从而导致系统变得不稳定。 为避免冲突，Microsoft 定义了几种不同的 WinEvent 类别，并且为每个类别定义了一个或多个值范围以用作
 WinEvent ID。 有关详细信息，请参阅 [WinEvent ID 的分配](https://docs.microsoft.com/en-us/windows/win32/winauto/allocation-of-winevent-ids) 。

自定义 UI 自动化事件通过在 UI 自动化框架内部分配事件 ID 来避免冲突。
###### 1-5-1-8-1-4、设计自定义控制模式
控制模式是具有属性、方法和事件的接口，这些接口定义了可从自动化元素获得的离散功能。控件模式方法允许 UI 自动化客户端操作控件的特定方面。控制模式属性
和事件提供有关控件某些方面的信息，并提供有关实现控制模式的自动化元素的状态的信息。

自定义控件模式应遵循以下设计准则：

> * 自定义控制模式应涵盖特定场景。例如，[ItemContainer](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementingitemcontainer) 控件模式旨在查询包含的对象，而不管虚拟化状态如何，但它不会枚举或计数包含的对象。
> * 自定义控件模式不应与现有控件模式的功能重叠。例如，不应将 [Invoke](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementinginvoke) 和 [ExpandCollapse](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementingexpandcollapse) 控件模式组合起来并作为新的控件模式呈现。要么重用现有的控制模
> 式，要么使用新的控制模式定义独特的场景。
> * 可以一起设计多个自定义控制模式以支持复杂的场景。例如，[Selection](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementingselection) 和 [SelectionItem](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-implementingselectionitem) 控件模式协同工作以支持一般对象选择方案。
###### 1-5-1-8-1-5、自定义控件类型
尽管本主题侧重于如何注册自定义 UI 自动化属性、事件和控件模式，但也可以引入新的控件类型。 与自定义属性、事件和控件模式不同，自定义控件类型不能在运
行时以编程方式注册，因为它实际上只是 UI 自动化 ControlType 属性的潜在值。 但是，可以定义、发布自定义控件类型 ID，并使其可供其他客户端和提供者使
用。 有关控件类型的详细信息，请参阅 [UI 自动化控件类型概述](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-controltypesoverview) 。
###### 1-5-1-8-1-6、相关话题

##### 1-5-1-8-2、[Registering Custom Properties, Events, and Control Patterns](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-regcustompropseventpatterns)
在可以使用自定义属性、事件或控制模式之前，提供者和客户端都必须在运行时注册属性、事件或控制模式。注册在应用程序进程内全局有效，并且在进程关闭或最后
一个 Microsoft UI 自动化元素对象（IUIAutomation 或 IRawElementProviderSimple）在进程内释放之前一直有效。

注册涉及将 GUID 以及有关自定义属性、事件或控件模式的详细信息传递给 UI 自动化。尝试使用相同的信息再次注册相同的 GUID 将成功，但尝试使用不同的信息
（例如，不同类型的自定义属性）再次注册相同的 GUID 将失败。未来，如果自定义规范被接受并集成到 UI Automation 核心中，UI Automation 将验证自定
义注册信息并使用已经注册的代码而不是“官方”框架实现，从而最大限度地减少应用程序兼容性问题。您不能删除已注册的属性、事件或控制模式。

本主题包含以下部分：
> * [注册自定义属性和事件](#1-5-1-8-2-1注册自定义属性和事件)
> * [实现自定义控制模式](#1-5-1-8-2-2实现自定义控制模式)
>   * [客户端包装器和模式处理程序](#1-5-1-8-2-2-1客户端包装器和模式处理程序)
>   * [实现客户端包装器](#1-5-1-8-2-2-2实现客户端包装器)
>   * [实现模式处理程序](#1-5-1-8-2-2-3实现模式处理程序)
>   * [注册自定义控制模式](#1-5-1-8-2-2-4注册自定义控制模式)
>   * [自定义控制模式的示例实现](#1-5-1-8-2-2-5自定义控制模式的示例实现)
> * [相关话题](#1-5-1-8-2-3相关话题)

> * [IUIAutomation](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationClient/nn-uiautomationclient-iuiautomation)
> * [IRawElementProviderSimple](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/nn-uiautomationcore-irawelementprovidersimple)
> * [UIAutomationPropertyInfo](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ns-uiautomationcore-uiautomationpropertyinfo)
> * [UIAutomationEventInfo](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ns-uiautomationcore-uiautomationeventinfo)
> * [UIAutomationType](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ne-uiautomationcore-uiautomationtype)
> * [CoCreateInstance](https://docs.microsoft.com/en-us/windows/win32/api/combaseapi/nf-combaseapi-cocreateinstance)
> * [CUIAutomationRegistrar](https://docs.microsoft.com/en-us/previous-versions/windows/desktop/legacy/ff384837(v=vs.85))
> * [IUIAutomationRegistrar](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/nn-uiautomationcore-iuiautomationregistrar)


###### 1-5-1-8-2-1、注册自定义属性和事件
注册自定义属性或事件使提供者和客户端能够获取属性或事件的 ID，然后可以将其传递给以 ID 作为参数的各种 API 方法。

注册属性或事件：

> * 为自定义属性或事件定义 GUID。
> * 使用有关属性或事件的信息填充 UIAutomationPropertyInfo 或 UIAutomationEventInfo 结构，包括 GUID 和包含自定义属性或事件名称的不可本
>地化的字符串。自定义属性还需要指定属性的数据类型，例如，属性是整数还是字符串。数据类型必须是 UIAutomationType 枚举指定的以下类型之一。自定义属
>性不支持其他数据类型。
>   * UIAutomationType_Bool
>   * UIAutomationType_Double
>   * UIAutomationType_Element
>   * UIAutomationType_Int
>   * UIAutomationType_Point
>   * UIAutomationType_String
> * 使用 CoCreateInstance 函数创建 CUIAutomationRegistrar 对象的实例并检索指向该对象的 IUIAutomationRegistrar 接口的指针。
> * 调用 IUIAutomationRegistrar::RegisterProperty 或 RegisterEvent 方法并传递 UIAutomationPropertyInfo 结构或 
>UIAutomationEventInfo 结构的地址。

IUIAutomationRegistrar::RegisterProperty 或 RegisterEvent 方法返回一个属性 ID 或事件 ID，应用程序可以将其传递给将此类标识符作为参数的
任何 UI 自动化方法。例如，您可以将注册的属性 ID 传递给 IUIAutomationElement::GetCurrentPropertyValue 方法或 
IUIAutomation::CreatePropertyCondition 方法。

下面的示例演示如何注册自定义属性。
```cpp
// Declare a variable for holding the custom property ID.
PATTERNID g_MyCustomPropertyID;
// Define a GUID for the custom property.
GUID GUID_MyCustomProperty = { 0x82f383ff, 0x4b4d, 0x40d3, 
    { 0x8e, 0xd2, 0x90, 0xb5, 0x25, 0x8e, 0xaa, 0x19 } };

HRESULT RegisterProperty()
{
    // Fill the structure with the GUID, name, and data type.
    UIAutomationPropertyInfo MyCustomPropertyInfo = 
    {
        GUID_MyCustomProperty,
        L"MyCustomProp",
        UIAutomationType_String
    };

    // Create the registrar object and get the IUIAutomationRegistrar 
    // interface pointer. 
    IUIAutomationRegistrar * pUIARegistrar = NULL;
    CoCreateInstance(CLSID_CUIAutomationRegistrar, NULL, CLSCTX_INPROC_SERVER, 
            IID_IUIAutomationRegistrar, (void **)&pUIARegistrar);

    if (pUIARegistrar == NULL)
        return E_NOINTERFACE;

    // Register the property and retrieve the property ID. 
    HRESULT hr = pUIARegistrar->RegisterProperty(&MyCustomPropertyInfo, &g_MyCustomPropertyID);
    pUIARegistrar->Release();

    return hr;
}
```

IUIAutomationRegistrar::RegisterProperty 和 RegisterEvent 方法检索的属性和事件标识符仅在检索它们的应用程序的上下文中有效，并且仅在应用
程序的生命周期内有效。 当在同一应用程序的不同运行时实例上调用注册方法时，注册方法可以为同一 GUID 返回不同的整数值。

没有取消注册自定义属性或事件的方法。 相反，当最后一个 UI 自动化对象被释放时，它们会被隐式取消注册。

    重要的
    
    如果您的代码是 Microsoft Active Accessibility (MSAA) 客户端，则在更改自定义属性的值时必须调用 NotifyWinEvent 函数。
###### 1-5-1-8-2-2、实现自定义控制模式
自定义控件模式不包含在 UI 自动化 API 中，但由第三方在运行时提供。 客户端和提供者应用程序的开发人员必须共同定义自定义控制模式，包括控制模式将支持的
方法、属性和事件。 定义控制模式后，客户端和提供者都必须实现支持的组件对象模型 (COM) 对象，以及在运行时注册控制模式的代码。 自定义控件模式需要实现
两个 COM 对象：客户端包装器和模式处理程序。

    笔记
    
    以下主题中的示例说明了如何实现复制现有值控件模式的功能的自定义控件模式。 这些示例仅用于说明目的。 实际的自定义控件模式应该提供标准 UI 自动化
    控件模式不提供的功能。
####### 1-5-1-8-2-2-1、客户端包装器和模式处理程序
客户端包装器实现了客户端用来检索属性和调用自定义控件模式公开的方法的 API。 API 实现为 COM 接口，将所有属性请求和方法调用传递给 UI 自动化核心，
然后将请求和调用编组到提供程序。

注册自定义控件模式的代码必须提供一个类工厂，UI 自动化可以使用该类工厂来创建客户端包装对象的实例。成功注册自定义控件模式后，UI 自动化返回一个 
IUIAutomationPatternInstance 接口指针，客户端使用该指针将属性请求和方法调用转发到 UI 自动化核心。

在提供者端，UI 自动化核心从客户端获取属性请求和方法调用，并将它们传递给模式处理程序对象。然后，模式处理程序调用自定义控件模式的提供者接口上的适当方
法。

注册自定义控件模式的代码会创建模式处理程序对象，并在注册控制模式时为 UI 自动化提供指向对象的 IUIAutomationPatternHandler 接口的指针。

下图显示了客户端属性请求或方法调用如何从客户端包装器，通过 UI 自动化核心组件到模式处理程序，然后到提供程序接口。

![控制模式支持](./images/custompatternsupport.jpeg)

实现客户端包装器和模式处理程序接口的对象必须是自由线程的。 此外，UI 自动化核心必须能够直接调用对象，而无需任何中间编组代码。
####### 1-5-1-8-2-2-2、实现客户端包装器
客户端包装器是一个对象，它公开一个 IXxxPattern 接口，客户端使用该接口来请求自定义控件模式支持的属性和调用方法。 该接口由每个支持的属性
（get_CurrentXxx 和 get_CachedXxx 方法）的一对“getter”方法和每个支持的方法的“调用者”方法组成。 当对象被实例化时，对象构造函数接收一个指向
 IUIAutomationPatternInstance 接口的指针，该接口由 UI 自动化核心实现。 IXxxPattern 接口的方法使用 
 IUIAutomationPatternInstance::GetProperty 和 CallMethod 方法将属性请求和方法调用转发到 UI 自动化核心。

下面的示例演示如何为支持单个属性的简单自定义控件模式实现客户端包装对象。 有关更复杂的示例，请参阅自定义控件模式的示例实现。
```cpp
// Define the client interface.
interface __declspec(uuid("c78b266d-b2c0-4e9d-863b-e3f74a721d47"))
IMyCustomPattern : public IUnknown
{
    STDMETHOD (get_CurrentIsReadOnly)(BOOL * pIsReadOnly) = 0;
    STDMETHOD (get_CachedIsReadOnly)(BOOL * pIsReadOnly) = 0;
};

// Implement the client wrapper class.
class CMyValuePatternClientWrapper :
    public IMyCustomPattern
{
    IUIAutomationPatternInstance * _pInstance;
    
public:
    // Get IUIAutomationPatternInstance interface pointer from the
    // UI Automation core.
    CMyValuePatternClientWrapper(IUIAutomationPatternInstance * pInstance)
        : _pInstance(pInstance)
    {
        _pInstance->AddRef();
    }
    
    ~CMyValuePatternClientWrapper()
    {
        _pInstance->Release();
    }

    // Note: Put standard IUnknown implementation here.

    STDMETHODIMP get_CurrentIsReadOnly(BOOL * pIsReadOnly)
    {
        return _pInstance->GetProperty(0, FALSE, UIAutomationType_Bool, pIsReadOnly);
    }

    STDMETHODIMP get_CachedIsReadOnly(BOOL * pIsReadOnly)
    {
        return _pInstance->GetProperty(0, TRUE, UIAutomationType_Bool, pIsReadOnly);
    }
};
```
####### 1-5-1-8-2-2-3、实现模式处理程序
模式处理程序是一个实现 IUIAutomationPatternHandler 接口的对象。该接口有两个方法：IUIAutomationPatternHandler::CreateClientWrapper 
和 Dispatch。 CreateClientWrapper 方法由 UI 自动化核心调用并接收指向 IUIAutomationPatternInstance 接口的指针。 CreateClientWrapper 
通过实例化客户端包装器对象并将 IUIAutomationPatternInstance 接口指针传递给客户端包装器构造函数来响应。

UI 自动化核心使用 Dispatch 方法将属性请求和方法调用传递给自定义控件模式的提供程序接口。参数包括指向提供程序接口的指针、被调用的属性 getter 或
方法的从零开始的索引，以及包含要传递给提供程序的参数的 UIAutomationParameter 结构数组。模式处理程序通过检查索引参数来确定调用哪个提供程序方法
来响应，然后调用该提供程序接口，传递 UIAutomationParameter 结构中包含的参数。

在注册控制模式之前，模式处理程序对象由注册自定义控制模式的相同代码实例化。代码必须在注册时将模式处理程序对象的 IUIAutomationPatternHandler 
接口指针传递给 UI 自动化核心。

下面的示例演示如何为支持单个属性的简单自定义控件模式实现模式处理程序对象。有关更复杂的示例，请参阅自定义控件模式的示例实现。
```cpp
// Define the provider interface.
interface __declspec(uuid("9f5266dd-f0ab-4562-8175-c383abb2569e"))
IMyValueProvider : public IUnknown
{
    STDMETHOD (get_IsReadOnly)(BOOL * pIsReadOnly) = 0;
};            

// Index used by IUIAutomationPatternHandler::Dispatch.
const int MyValue_GetIsReadOnly = 0; 
            
// Define the pattern handler class.        
class CMyValuePatternHandler : public IUIAutomationPatternHandler
{
public:
    
    // Put standard IUnknown implementation here.

    STDMETHODIMP CreateClientWrapper(
            IUIAutomationPatternInstance * pPatternInstance, 
            IUnknown ** pClientWrapper)
    {
        *pClientWrapper = new CMyValuePatternClientWrapper(pPatternInstance);
        if (*pClientWrapper == NULL)
            return E_INVALIDARG;
        return S_OK;
    }
    
    STDMETHODIMP Dispatch (IUnknown * pTarget, UINT index, 
            const struct UIAutomationParameter *pParams, UINT cParams)
    {
        switch(index)
        {
        case MyValue_GetIsReadOnly:
            return ((IMyValueProvider*)pTarget)->get_IsReadOnly((BOOL*)pParams[0].pData);
        }
        return E_INVALIDARG;
    }
};
```
####### 1-5-1-8-2-2-4、注册自定义控制模式

在使用之前，必须由提供者和客户端注册自定义控件模式。注册为 UI 自动化核心提供有关控制模式的详细信息，并为提供者或客户端提供控制模式 ID，以及控制模
式支持的属性和事件的 ID。在提供者端，必须在关联控件处理 WM_GETOBJECT 消息之前或同时注册自定义控件模式。

注册自定义控件模式时，提供者或客户端提供以下信息：

> * 自定义控件模式的 GUID。
> * 包含自定义控件模式名称的不可本地化字符串。
> * 支持自定义控件模式的提供程序接口的 GUID。
> * 支持自定义控件模式的客户端界面的 GUID。
> * 描述自定义控件模式支持的属性的 UIAutomationPropertyInfo 结构数组。对于每个属性，必须指定 GUID、属性名称和数据类型。
> * 描述自定义控件模式支持的方法的 UIAutomationMethodInfo 结构数组。对于每个方法，该结构包括以下信息：方法名称、参数计数、参数数据类型列表和
>参数名称列表。
> * 一组 UIAutomationEventInfo 结构，用于描述自定义控件模式引发的事件。对于每个事件，必须指定 GUID 和事件名称。
> * 使自定义控件模式对客户端可用的模式处理程序对象的 IUIAutomationPatternHandler 接口的地址。

要注册自定义控件模式，提供者或客户端代码必须执行以下步骤：

> * 使用上述信息填充 UIAutomationPatternInfo 结构。
> * 使用 CoCreateInstance 函数创建 CUIAutomationRegistrar 对象的实例并检索指向该对象的 IUIAutomationRegistrar 接口的指针。
> * 调用 IUIAutomationRegistrar::RegisterPattern 方法，传递 UIAutomationPatternInfo 结构的地址。

RegisterPattern 方法返回一个控制模式 ID，以及属性 ID 和事件 ID 的列表。应用程序可以将这些 ID 传递给任何将此类标识符作为参数的 UI 自动化方法。
例如，您可以将已注册的模式 ID 传递给 IUIAutomationElement::GetCurrentPattern 方法，以检索指向控制模式提供程序接口的指针。

没有取消注册自定义控件模式的方法。相反，当最后一个 UI 自动化对象被释放时，它会隐式取消注册。

有关显示如何注册自定义控件模式的示例，请参阅以下部分。

####### 1-5-1-8-2-2-5、自定义控制模式的示例实现
本节包含演示如何为自定义控件模式实现客户端包装器和模式处理程序对象的示例代码。 该示例实现了一个基于 Value 控件模式的自定义控件模式。

```cpp
// Step 1: Define the public provider and client interfaces using IDL. Interface 
// definitions are in C here to simplify the example.

// Define the provider interface.
interface __declspec(uuid("9f5266dd-f0ab-4562-8175-c383abb2569e"))
IMyValueProvider : public IUnknown
{
    STDMETHOD (get_Value)(BSTR * pValue) = 0;
    STDMETHOD (get_IsReadOnly)(BOOL * pIsReadOnly) = 0;
    STDMETHOD (SetValue)(LPCWSTR pNewValue) = 0;
    STDMETHOD (Reset)() = 0;
};

// Define the client interface.
interface __declspec(uuid("103b8323-b04a-4180-9140-8c1e437713a3"))
IUIAutomationMyValuePattern : public IUnknown
{
    STDMETHOD (get_CurrentValue)(BSTR * pValue) = 0;
    STDMETHOD (get_CachedValue)(BSTR * pValue) = 0;

    STDMETHOD (get_CurrentIsReadOnly)(BOOL * pIsReadOnly) = 0;
    STDMETHOD (get_CachedIsReadOnly)(BOOL * pIsReadOnly) = 0;

    STDMETHOD (SetValue)(LPCWSTR pNewValue) = 0;
    STDMETHOD (Reset)() = 0;
};

// Enumerate the properties and methods starting from 0, and placing the 
// properties first. 
enum
{
    MyValue_GetValue = 0,
    MyValue_GetIsReadOnly = 1,
    MyValue_SetValue = 2,
    MyValue_Reset = 3,
};

// Step 2: Implement the client wrapper class.
class CMyValuePatternClientWrapper :
    public IUIAutomationMyValuePattern
{
    IUIAutomationPatternInstance * _pInstance;

public:
    // Get IUIAutomationPatternInstance interface pointer.
    CMyValuePatternClientWrapper(IUIAutomationPatternInstance * pInstance)
    {
        _pInstance = pInstance;
        _pInstance->AddRef();
    }

    // Put standard IUnknown implementation here.

    STDMETHODIMP get_CurrentValue(BSTR * pValue)
    {
        return _pInstance->GetProperty(MyValue_GetValue, FALSE, 
                UIAutomationType_String, pValue);
    }

    STDMETHODIMP get_CachedValue(BSTR * pValue)
    {
        return _pInstance->GetProperty(MyValue_GetValue, TRUE, 
                UIAutomationType_String, pValue);
    }

    STDMETHODIMP get_CurrentIsReadOnly(BOOL * pIsReadOnly)
    {
        return _pInstance->GetProperty(MyValue_GetIsReadOnly, FALSE, 
                UIAutomationType_Bool, pIsReadOnly);
    }

    STDMETHODIMP get_CachedIsReadOnly(BOOL * pIsReadOnly)
    {
        return _pInstance->GetProperty(MyValue_GetIsReadOnly, TRUE, 
                UIAutomationType_Bool, pIsReadOnly);
    }

    STDMETHODIMP SetValue(LPCWSTR pValue)
    {
        UIAutomationParameter SetValueParams[] = 
                { UIAutomationType_String, &pValue };
        return _pInstance->CallMethod(MyValue_SetValue,  SetValueParams, 
                ARRAYSIZE(SetValueParams));
    }

    STDMETHODIMP Reset()
    {
        return _pInstance->CallMethod(MyValue_Reset, NULL, 0);
    }
};

// Step 3: Implement the pattern handler class.
class CMyValuePatternHandler : public IUIAutomationPatternHandler
{
public:

    // Put standard IUnknown implementation here.
    
    STDMETHODIMP CreateClientWrapper(
            IUIAutomationPatternInstance * pPatternInstance, 
            IUnknown ** pClientWrapper)
    {
        *pClientWrapper = new CMyValuePatternClientWrapper(pPatternInstance);
        if (*pClientWrapper == NULL)
            return E_INVALIDARG;
        return S_OK;
    }
    
    STDMETHODIMP Dispatch (IUnknown * pTarget, UINT index, 
            const struct UIAutomationParameter *pParams, 
            UINT cParams)
    {
        switch(index)
        {
        case MyValue_GetValue:
            return ((IMyValueProvider*)pTarget)->get_Value((BSTR*)pParams[0].pData);

        case MyValue_GetIsReadOnly:
            return ((IMyValueProvider*)pTarget)->get_IsReadOnly((BOOL*)pParams[0].pData);

        case MyValue_SetValue:
            return ((IMyValueProvider*)pTarget)->SetValue(*(LPCWSTR*)pParams[0].pData);

        case MyValue_Reset:
            return ((IMyValueProvider*)pTarget)->Reset();
        }
        return E_INVALIDARG;
    }
};

CMyValuePatternHandler g_MyValuePatternHandler;

// Step 4: Declare information about the properties and methods supported
// by the custom control pattern.

// Define GUIDs for the custom control pattern and each of its properties 
// and events.
static const GUID MyValue_Pattern_Guid = { 0xa49aa3c0, 0xe413, 0x4ecf, 
        { 0xa1, 0xc3, 0x37, 0x42, 0xa7, 0x86, 0x67, 0x3f } };
static const GUID MyValue_Value_Property_Guid = { 0xe58f3f67, 0x22c7, 0x44f0, 
        { 0x83, 0x55, 0xd8, 0x76, 0x14, 0xa1, 0x10, 0x81 } };
static const GUID MyValue_IsReadOnly_Property_Guid = { 0x480540f2, 0x9829, 0x4acd, 
        { 0xb8, 0xea, 0x6e, 0x2a, 0xdc, 0xe5, 0x3a, 0xfb } };
static const GUID MyValue_Reset_Event_Guid = { 0x5b80edd3, 0x67f, 0x4a70, 
        { 0xb0, 0x7, 0x4, 0x12, 0x85, 0x11, 0x1, 0x7a } };

// Declare information about the properties, in the same order as the
// previously defined "MyValue_" enumerated type.
UIAutomationPropertyInfo g_MyValueProperties[] = 
{
    // GUID, name, data type.
    { MyValue_Value_Property_Guid, L"MyValuePattern.Value", 
                                                    UIAutomationType_String },
    { MyValue_IsReadOnly_Property_Guid, L"MyValuePattern.IsReadOnly", 
                                                    UIAutomationType_Bool },
};

// Declare information about the event.
UIAutomationEventInfo g_MyValueEvents [] =
{
    { MyValue_Reset_Event_Guid,  L"MyValuePattern.Reset" },
};

// Declare the data type and name of the SetValue method parameter. 
UIAutomationType g_SetValueParamTypes[] = { UIAutomationType_String };
LPCWSTR g_SetValueParamNames[] = {L"pNewValue"};

// Declare information about the methods.
UIAutomationMethodInfo g_MyValueMethods[] =
{
    // Name, focus flag, count of in parameters, count of out parameters, types, parameter names.
    { L"MyValuePattern.SetValue", TRUE, 1, 0, g_SetValueParamTypes, g_SetValueParamNames },
    { L"MyValuePattern.Reset", TRUE, 0, 0, NULL, NULL },
};

// Declare the custom control pattern using the previously defined information.
UIAutomationPatternInfo g_ValuePatternInfo =
{
    MyValue_Pattern_Guid,
    L"MyValuePattern",
    __uuidof(IMyValueProvider),
    __uuidof(IUIAutomationMyValuePattern),
    ARRAYSIZE(g_MyValueProperties), g_MyValueProperties, // properties
    ARRAYSIZE(g_MyValueMethods), g_MyValueMethods,       // methods
    ARRAYSIZE(g_MyValueEvents), g_MyValueEvents,         // events 
    &g_MyValuePatternHandler
};

// Step 5: Register the custom control pattern and retrieve the control pattern and property 
// identifiers.

// Control pattern, property, and event IDs.
PATTERNID  g_MyValue_PatternID;
PROPERTYID g_MyValue_Value_PropertyID;
PROPERTYID g_MyValue_IsReadOnly_PropertyID;
EVENTID    g_MyValueReset_EventID;

// ID used by the client to determine whether the custom control pattern is available.
PROPERTYID g_IsMyValuePatternAvailable_PropertyID;

HRESULT RegisterPattern()
{
    // Create the registrar object and get the IUIAutomationRegistrar interface pointer. 
    IUIAutomationRegistrar * pUIARegistrar;
    CoCreateInstance(CLSID_CUIAutomationRegistrar, NULL, CLSCTX_INPROC_SERVER, 
            IID_IUIAutomationRegistrar, (void **)&pUIARegistrar);

    if (pUIARegistrar == NULL)
        return E_NOINTERFACE;

    PROPERTYID propIDs[2]; // Array for property IDs.

    // Register the control pattern.
    HRESULT hr = pUIARegistrar->RegisterPattern(
        &g_ValuePatternInfo,
        &g_MyValue_PatternID,
        &g_IsMyValuePatternAvailable_PropertyID,
        ARRAYSIZE(propIDs), 
        propIDs,
        1,
        &g_MyValueReset_EventID);
            
    if (hr == S_OK)
    {
        // Copy the property IDs.
        g_MyValue_Value_PropertyID = propIDs[0];
        g_MyValue_IsReadOnly_PropertyID = propIDs[1];
    }

    pUIARegistrar->Release();
    return hr;
}
```
###### 1-5-1-8-2-3、相关话题



#### 1-5-1-9、文本内容的UI自动化支持
本节介绍如何使用 Microsoft UI 自动化在支持 UI 自动化的平台中公开文本控件的文本内容，包括格式和样式属性。 文本控件包括但不限于文档、工具提示、编辑控件和富编辑控件。

在这个部分
> * [关于 Text 和 TextRange 控件模式](#1-5-1-9-1关于Text和TextRange控件模式)
> * [UI 自动化如何暴露嵌入式对象](#1-5-1-9-2UI自动化如何暴露嵌入式对象)
> * [UI 自动化文本属性](#1-5-1-9-3UI自动化文本属性)
> * [UI 自动化文本单元](#1-5-1-9-4UI自动化文本单元)

##### 1-5-1-9-1、关于Text和TextRange控件模式
控件的文本内容通过使用 Text 控件模式公开，该模式将文本容器的内容表示为文本流。 Text 控件模式需要 TextRange 控件模式的支持才能公开格式和样式属
性。 TextRange 控件模式通过在具有开始和结束端点集合的文本容器中表示连续或多个不相交的文本跨度（或范围）来支持文本控件模式。 TextRange 控制模式
支持选择、比较、检索和遍历等功能。

    笔记
    
    Text 控件模式不提供插入或修改文本的方法。但是，根据控件的不同，这可以通过使用 Microsoft UI 自动化值控件模式或通过直接键盘输入来完成。还有一个 
    TextEdit 模式支持以编程方式更改文本。


本主题中描述的功能对于辅助技术供应商及其最终用户至关重要。辅助技术可以使用 UI 自动化为用户收集完整的文本格式信息，并通过 TextUnit（字符、单词、
行或段落）提供编程导航和文本选择。

本主题包含以下部分：

> * [UI 自动化 TextPattern 和文本服务框架](#1-5-1-9-1-1UI自动化TextPattern和文本服务框架)
> * [控制类型](#1-5-1-9-1-2控制类型)
> * [提供者接口](#1-5-1-9-1-3提供者接口)
> * [客户端接口](#1-5-1-9-1-4客户端接口)
> * [表现](#1-5-1-9-1-5表现)
> * [文本模式和虚拟化嵌入对象](#1-5-1-9-1-6文本模式和虚拟化嵌入对象)
> * [将自定义控件类型与文本控件模式一起使用](#1-5-1-9-1-7将自定义控件类型与文本控件模式一起使用)
> * [文本范围的生命周期](#1-5-1-9-1-8文本范围的生命周期)
> * [相关话题](#1-5-1-9-1-9相关话题)

###### 1-5-1-9-1-1、UI自动化TextPattern和文本服务框架
文本服务框架 (TSF) 是一个简单且可扩展的系统框架，可在桌面和应用程序中实现自然语言服务和高级文本输入。 除了为应用程序提供暴露其文本存储的接口外，它
还支持文本存储的元数据。

TSF 专为需要将输入注入上下文感知场景的应用程序而设计。 然而，文本控件模式是一种只读解决方案，旨在为屏幕阅读器和盲文设备提供对文本存储的优化访问。

需要对文本存储进行只读访问的可访问技术可以使用文本控制模式，但需要 TSF 的功能来进行上下文感知输入。

有关详细信息，请参阅 [文本服务框架](https://docs.microsoft.com/en-us/windows/desktop/TSF/text-services-framework) 。
###### 1-5-1-9-1-2、控制类型
UI Automation Edit 控件类型和 Document 控件类型必须支持 Text 控件模式。为了提高可访问性，Microsoft 建议 ToolTip 和 Text 控件类型也支持
 Text 控件模式，但这不是必需的。

###### 1-5-1-9-1-3、提供者接口
UI 自动化提供程序通过实现 ITextProvider 和 ITextRangeProvider 接口来支持控件的文本控件模式。这些接口公开了控件中文本的详细属性信息，并提供
了强大的导航功能。

如果控件不支持任何特定属性，则提供程序不需要支持所有文本属性。

如果控件支持文本选择或在文本区域内放置文本光标（或系统插入符号），则提供程序必须支持 ITextProvider::GetSelection 和 
ITextRangeProvider::Select 方法。如果控件不支持此功能，则不需要支持这些方法中的任何一种。但是，控件必须通过实现 
ITextProvider::SupportedTextSelection 属性来公开它支持的文本选择类型。

提供者必须始终支持 [TextUnit](https://docs.microsoft.com/en-us/windows/desktop/api/UIAutomationCore/ne-uiautomationcore-textunit) 常量、TextUnit_Character 和 TextUnit_Document，以及它能够支持的任何其他常量。

    笔记
    
    提供者可以通过推迟到按以下顺序支持的下一个最大单元来跳过对特定 TextUnit 的支持：TextUnit_Character、TextUnit_Format、TextUnit_Word、
    TextUnit_Line、TextUnit_Paragraph、TextUnit_Page 和 TextUnit_Document。

###### 1-5-1-9-1-4、客户端接口
UI 自动化客户端应用程序使用 IUIAutomationTextPattern 和 IUIAutomationTextRange 接口来访问文本控件的文本内容。客户端使用 
IUIAutomationTextPattern 来选择称为文本范围的文本范围，并检索指向范围的 IUIAutomationTextRange 接口的指针。 IUIAutomationTextRange 
接口使客户端能够操作文本范围，并检索有关范围内文本的信息，包括字体名称、前景色、下划线样式等属性。有关详细信息，请参阅文本属性标识符。

###### 1-5-1-9-1-5、表现
Text 控件模式的大部分功能依赖于跨进程调用，因此它不提供缓存机制来提高处理内容时的性能。可以使用 IUIAutomationElement::GetCachedPattern 
方法访问 Microsoft UI 自动化中的其他控制模式。

提高性能的一种技术是确保 UI 自动化客户端尝试使用 IUIAutomationTextRange::GetText 方法检索中等大小的文本块。例如，使用 GetText 检索单个字
符将导致每个字符的跨进程命中，而在调用 GetText 时未指定最大长度将导致跨进程命中，但可能具有高延迟，具体取决于文本范围的大小。
###### 1-5-1-9-1-6、文本模式和虚拟化嵌入对象
只要有可能，ITextProvider 和 ITextRangeProvider 的提供者实现应该支持文档的整个文本，包括视口之外的任何文本。对于虚拟化的屏幕外文本或嵌入对象
，提供程序应支持 VirtualizedItem 控制模式 (IVirtualizedItemProvider)。

如果在整个文本流仍然可用时虚拟化了文档，则 ITextProvider::DocumentRange 属性将检索包含整个文档的文本范围。但是，调用 ITextRangeProvider 
方法将检索代表文档中所有嵌入对象的虚拟化对象集合。要与虚拟化嵌入对象进行交互，客户端必须调用 IVirtualizedItemProvider::Realize 方法，这使得
项目可以作为 UI 自动化元素完全访问。客户端必须遵循类似的过程来处理嵌入表格中的网格元素，其中表格的一部分是屏幕外的并被虚拟化。

###### 1-5-1-9-1-7、将自定义控件类型与文本控件模式一起使用
虽然文本控件模式支持许多文本属性和嵌入对象，但不可能预先定义所有可能的文档元素和表示类型。对于现有属性或标准控件类型不支持的文档元素，提供者可以使用
 UI 自动化自定义控件类型提供的可扩展性功能。

对于基于页面呈现的应用程序和用户界面，“页面”的边界和布局呈现也可以表示为具有自定义控件类型（即LocalizedControlType="page"）的嵌入对象。这样，
嵌入对象可以托管其他不能轻易成为文档文本流一部分的页面元素，例如每个页面的页眉和页脚字段，作为“页面”嵌入对象的子项。或者，每个“页面”对象都可以独立
地支持文本控制模式，这适用于幻灯片演示文稿的创作工具或基于页面的桌面发布环境等应用程序。

###### 1-5-1-9-1-8、文本范围的生命周期
如果可能，提供者应确保任何文本更改（例如删除、插入和移动）都反映在关联的文本范围中。如果无法更新文本范围，则提供程序应引发 
UIA_Text_TextChangedEventId 事件以通知客户端文本范围不再有效，必须检索新的文本范围。

###### 1-5-1-9-1-9、相关话题


##### 1-5-1-9-2、[UI自动化如何暴露嵌入式对象](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-textpattern-and-embedded-objects-overview)
##### 1-5-1-9-3、[UI自动化文本属性](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-textattributes)
##### 1-5-1-9-4、[UI自动化文本单元](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-uiautomationtextunits)


#### 1-5-1-10、[拖放的UI自动化支持](https://docs.microsoft.com/en-us/windows/win32/winauto/ui-automation-support-for-drag-and-drop)
#### 1-5-1-11、辅助技术的安全注意事项
#### 1-5-1-12、[使用安全数组的最佳实践](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-workingwithsafearrays)
#### 1-5-1-13、[UI自动化规范和社区承诺](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-specandcommunitypromise)

### 1-5-2、[UI自动化提供程序程序员指南](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-providerportal)
### 1-5-3、[UI自动化客户端程序员指南](https://docs.microsoft.com/en-us/windows/win32/winauto/uiauto-clientportal)
### 1-5-4、[参考](https://docs.microsoft.com/en-us/windows/win32/winauto/entry-uiautocore-ref)
### 1-5-5、[样品](https://docs.microsoft.com/en-us/windows/win32/winauto/samples-entry)
### 1-5-6、[附录](https://docs.microsoft.com/en-us/windows/win32/winauto/appendix-entry)

