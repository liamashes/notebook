#####

[official site]
# RESOURCES

## Develop for the Quest Platform
Oculus Quest 和 Quest 2 以行业领先的功能和性能提供无线、独立 VR 的自由度，以驱动您的下一个身临其境的应用程序。 这两款设备都包括空间跟踪控制器
、集成的开放式耳机音频以及对 Oculus Link 的支持，使用户能够从他们的游戏兼容 PC 访问他们的 Oculus Rift 应用程序库。

本概述将帮助您启动下一个 VR 开发流程，包括有关 Quest 和 Quest 2 的背景信息、学习性能优化技术的材料以及有关 Oculus 商店提交政策的背景信息。 我
们期待体验您使用这些一体化 VR 系统创造的新世界和现实。

> * [official native platform]
> * [official unreal integration]
> * [official unity integration]

## Quest and Quest 2: Getting better with each generation

Oculus Quest 和 Quest 2 提供 6DOF VR 的自由度、Qualcomm Snapdragon 处理器的强大功能和灵活性，以及​​相同的开发者软件平台。 
Guardian、Passthrough+、手部追踪和我们重新设计的通用菜单等功能也适用于两款耳机。

虽然有相似之处，但我们对 Quest 2 以更强大的功能、更高的分辨率和更实惠的价格为世界各地的 VR 观众带来的潜力感到特别兴奋。请参阅下文，了解这些头戴式
设备之间的一些主要区别，重点介绍 Quest 2 成为优质 VR 设备的原因，供您设计、开发和分发您的下一个 VR 应用程序。

这个过程提高了开发者在 Quest 平台上的成功率和玩家满意度。我们期待与您和您的团队合作，利用这种移动 VR 技术提供富有灵感的游戏和应用程序。

> * 299 美元的价格点：它的价格点比 Oculus Quest 更低，它将为您的 VR 应用程序创造更多用户的潜力。
> * Snapdragon XR2 平台：增加 GPU 功率、更多 CPU、更多内存和更高分辨率的显示。
> * 支持 Android 10：使用 heapprofd 进行内存分析和使用 Vulkan 和 GLES 的验证层进行图形调试。
> * 改进的显示：与 Quest 相比，像素增加 50%，以提高现有或新 Oculus 应用的保真度。
> * 重新设计的触摸控制器：新的优化按钮布局和更大的顶板，可提供更舒适的拇指托和更自然的抓握。这些中的每一个都会带来更好的人体工程学和手部存在感。

想了解更多关于 Quest 2 的信息吗？查看 [Quest2 公告博客文章]，了解有关耳机的更多信息，以及一组初始的开发人员常见问题解答。

## Start developing for the Quest Platform with these documents, videos, and tutorials

Quest 和 Quest 2 具有运行 Android 的移动芯片组，并包含自己的一套开发流程和最佳实践。 对于那些专门从事控制台或 PC 工程的人来说，其中一些细微差别可能是新的，因此要开始为这个 6DOF、一体式 VR 系统进行开发，我们推荐以下用于 Unity、Unreal 和 Native 开发的资源。

> * [Oculus Quest：开发者最佳实践 + 商店：博客文章]
> * [Quest 虚拟现实检查 (VRC) 指南]
> * [签名、密封、交付：Oculus 商店的成功]
> * [更多变化：VR 趋势如何应用于 Quest]
### Unity 3D
> * [在 Unity 中开始使用 Oculus]
> * [Unity：构建第一个应用程序]
> * [Rift 和 Android 的最佳实践]
### 虚幻引擎
> * [Quest and Go：快速入门]
> * [虚幻蓝图参考]
> * [虚幻引擎：Oculus VR 示例]
### 原生开发
> * [原生开发：Android Studio 基础]
> * [原生移动 SDK 入门指南]
> * [原生开发：移动 SDK 示例]

## Performance optimization: Essential to Quest Platform development
如果您不熟悉 VR 开发，请务必注意，与 2D 游戏和软件工程相比，性能优化对于交付成功的应用程序至关重要。在移动和主机游戏中可以适度容忍丢帧，但在 VR 
中，丢帧可能会导致严重的不适，可能会阻止用户一起体验您的应用程序。

与为 PC VR 开发不同的是，针对移动芯片组进行优化需要不同的优化计划和策略。当您计划为 Quest 和 Quest 2 进行开发时，请牢记这一点，因为最佳实践（
如最小化绘制调用和着色器复杂性、彻底的帧分析和有效使用扩展文件）对于您的应用程序的成功都很重要。

认识到优化过程既必要又（可能）耗时，我们创建了许多工具、资源、指南和视频来确保您的成功：

> * [移动优化工具]
> * [使用 Unity 进行 Android 开发]
> * [Android 测试和故障排除]
> * [使用 RenderDoc 进行帧捕获 - Oculus Quest]
> * [Oculus Quest：开发者最佳实践 + 商店]
> * [SUPERHOT VR on Quest：12 个月内从 100W 到 4Wsa>]
> * [Oculus Connect 5 |使用 RenderDoc 增强移动性能]

## 硬件规格 + Playspace 建议
有关 Oculus Quest 和 Quest 2 的详细技术规格集，请参见下文。如果您还没有，请查看入门页面，了解这些与 Rift S 的对比情况。
   
### Quest 2 规格
> * 面板类型：快速切换 LCD
> * 显示分辨率：每眼 1832x1920
> * 支持刷新率：72Hz； 90Hz 未来版本
> * 默认 SDK 色彩空间：Rec.2020 色域，2.2 伽玛，D65 白点
> * USB 连接器：1 个 USB 3.0
> * 跟踪：由内而外，6DOF
> * 音频：集成，带内
> * SoC：Qualcomm® Snapdragon™ XR2 平台
> * 内存：总计 6GB
> * 镜头距离：可调节 IAD，具有 58、63 和 68mm 三种设置

### Quest 规格
> * 面板类型：双 OLED 1600x1440
> * 默认分辨率：1216x1344
> * 支持刷新率：72Hz
> * 默认 SDK 色彩空间：Rec.2020 色域，2.2 伽玛，D65 白点
> * USB 连接器：1 个 USB 3.0
> * 跟踪：由内而外，6DOF
> * 音频：集成，带内
> * CPU：高通® 骁龙 835
> * GPU：Qualcomm® Adreno™ 540 GPU
> * 内存：总共4GB
> * 镜头距离：可调节 IAD 滑块

## Video resources to help you get started

[视频1]
> 听听 Chris Pruett 的介绍，他将详细介绍 Oculus Quest 策划流程、成功游戏的模式以及 VR 未来的主题。

[视频2]
> 听听 SUPERHOT 首席程序员的介绍，他们深入探讨了他们如何为 Oculus Quest 优化热门 VR 游戏。 此演示文稿将帮助那些希望将其标题移植到 Quest 的
>人，同时也将进一步了解该平台。

[视频3]
> 一体机还是 PCVR？ 听听 Oculus 内容团队的 Bruce Wooden 的介绍，他将帮助您了解平台受众、技术注意事项以及确定下一个 VR 项目范围的技巧。

##

[To the top](#)


[official site]: https://developer.oculus.com/quest/
[official native platform]: https://developer.oculus.com/documentation/native/
[official unreal integration]: https://developer.oculus.com/documentation/unreal/
[official unity integration]: https://developer.oculus.com/documentation/unity/

[Quest2 公告博客文章]: https://developer.oculus.com/blog/introducing-oculus-developer-hub/

[Oculus Quest：开发者最佳实践 + 商店：博客文章]: https://developer.oculus.com/blog/down-the-rabbit-hole-w-oculus-quest-developer-best-practices-the-store/
[Quest 虚拟现实检查 (VRC) 指南]: https://developer.oculus.com/distribute/publish-quest-req/
[签名、密封、交付：Oculus 商店的成功]: https://www.oculus.com/lynx/?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DqBi_T90I-BI&e=AT18SZGTIsIo3i-LCzC6MPjC8utKblhvKzoIlLjTE-W4wbFtndphyDeVBkc2-Uhw_kCndodjLeLt5PQhb0dOiqkZU9yn1HzmbXVXIaf00JzWeSvJzQr22hlExhJr3f0Kh5jmYM310IFCeNXCl3O76MECk_k
[更多变化：VR 趋势如何应用于 Quest]: https://www.oculus.com/lynx/?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DvnBUn5e8UTU&e=AT18SZGTIsIo3i-LCzC6MPjC8utKblhvKzoIlLjTE-W4wbFtndphyDeVBkc2-Uhw_kCndodjLeLt5PQhb0dOiqkZU9yn1HzmbXVXIaf00JzWeSvJzQr22hlExhJr3f0Kh5jmYM310IFCeNXCl3O76MECk_k
[在 Unity 中开始使用 Oculus]: https://developer.oculus.com/documentation/unity/unity-gs-overview/
[Unity：构建第一个应用程序]: https://developer.oculus.com/documentation/unity/unity-tutorial/
[Rift 和 Android 的最佳实践]: https://developer.oculus.com/documentation/unity/unity-best-practices-intro/
[Quest and Go：快速入门]: https://developer.oculus.com/documentation/unreal/unreal-quick-start-guide-quest/
[虚幻蓝图参考]: https://developer.oculus.com/documentation/unreal/unreal-blueprints/
[虚幻引擎：Oculus VR 示例]: https://developer.oculus.com/documentation/unreal/unreal-samples/
[原生开发：Android Studio 基础]: https://developer.oculus.com/documentation/native/android/mobile-studio-basics/
[原生移动 SDK 入门指南]: https://developer.oculus.com/documentation/native/android/book-intro/
[原生开发：移动 SDK 示例]: https://developer.oculus.com/documentation/native/android/mobile-native-samples/

[移动优化工具]: https://developer.oculus.com/documentation/tools/book-tools/
[使用 Unity 进行 Android 开发]: https://developer.oculus.com/documentation/unity/unity-mobile-performance-intro/
[Android 测试和故障排除]: https://developer.oculus.com/documentation/native/android/book-testing/
[使用 RenderDoc 进行帧捕获 - Oculus Quest]: https://developer.oculus.com/documentation/native/android/mobile-renderdoc/
[Oculus Quest：开发者最佳实践 + 商店]: https://developer.oculus.com/blog/down-the-rabbit-hole-w-oculus-quest-developer-best-practices-the-store/  
[SUPERHOT VR on Quest：12 个月内从 100W 到 4Wsa>]: https://www.oculus.com/lynx/?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3Dhr35o5R7EMA&e=AT18SZGTIsIo3i-LCzC6MPjC8utKblhvKzoIlLjTE-W4wbFtndphyDeVBkc2-Uhw_kCndodjLeLt5PQhb0dOiqkZU9yn1HzmbXVXIaf00JzWeSvJzQr22hlExhJr3f0Kh5jmYM310IFCeNXCl3O76MECk_k
[Oculus Connect 5 |使用 RenderDoc 增强移动性能]: https://www.oculus.com/lynx/?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DCQxkE_56xMU&e=AT18SZGTIsIo3i-LCzC6MPjC8utKblhvKzoIlLjTE-W4wbFtndphyDeVBkc2-Uhw_kCndodjLeLt5PQhb0dOiqkZU9yn1HzmbXVXIaf00JzWeSvJzQr22hlExhJr3f0Kh5jmYM310IFCeNXCl3O76MECk_k

[视频1]: https://youtu.be/YrcYCLzgSsY
[视频2]: https://youtu.be/hr35o5R7EMA
[视频3]: https://youtu.be/R71QCZZAiUA
