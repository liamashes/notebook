# web
## 3-1、模块信息
> 包 | 描述
> --- | ---
> [web](#3-2-1web) | Spring Security 的网络安全模块。
  [web.access](#3-3-1webaccess) | 访问控制相关的类和包。
  [web.access.channel](#3-3-2webaccesschannel) | 确保通过所需的传输通道接收 Web 请求的类。
  [web.access.expression](#3-3-3webaccessexpression) | 网络安全表达式的实现。
  [web.access.intercept](#3-3-4webaccessintercept) | 强制执行 HTTP 请求的安全性，通常通过请求的 URL。
  [web.authentication](#3-4-1webauthentication) | 身份验证处理机制，使用各种协议（例如 BASIC、CAS、表单登录等）响应身份验证凭据的提交。
  [web.authentication.logout](#3-4-2webauthenticationlogout) | 基于处理特定注销 URL 的过滤器的注销功能。
  [web.authentication.preauth](#3-4-3webauthenticationpreauth) | 支持“预认证”场景，其中 Spring Security 假设传入的请求已经由一些外部配置的系统进行了身份验证。
  [web.authentication.preauth.j2ee](#3-4-4webauthenticationpreauthj2ee) | 对容器身份验证请求的预身份验证支持。
  [web.authentication.preauth.websphere](#3-4-5webauthenticationpreauthwebsphere) | 特定于 Websphere 的预认证类。
  [web.authentication.preauth.x509](#3-4-6webauthenticationpreauthx509) | X.509 客户端证书认证支持。
  [web.authentication.rememberme](#3-4-7webauthenticationrememberme) | 支持在不同的网络会话之间记住用户。
  [web.authentication.session](#3-4-8webauthenticationsession) | 用于处理新认证用户的会话相关行为的策略接口和实现。
  [web.authentication.switchuser](#3-4-9webauthenticationswitchuser) | 提供基于 HTTP 的“切换用户”(su) 功能。
  [web.authentication.ui](#3-4-10webauthenticationui) | 身份验证用户界面呈现代码。
  [web.authentication.www](#3-4-11webauthenticationwww) | 基于 WWW-Authenticate 的认证机制实现：Basic 和 Digest 认证。
  [web.bind.annotation](#3-5-1webbindannotation) |
  [web.bind.support](#3-5-2webbindsupport) |
  [web.context](#3-6-1webcontext) | 负责维护 HTTP 请求之间的安全上下文的类。
  [web.context.request.async](#3-6-2webcontextrequestasync) |
  [web.context.support](#3-6-3webcontextsupport) |
  [web.csrf](#3-7-1webcsrf) |
  [web.debug](#3-7-2webdebug) |
  [web.firewall](#3-7-3webfirewall) |
  [web.header](#3-7-4webheader) |
  [web.header.writers](#3-7-5webheaderwriters) |
  [web.header.writers.frameoptions](#3-7-6webheaderwritersframeoptions) |
  [web.http](#3-7-7webhttp) |
  [web.jaasapi](#3-7-8webjaasapi) | 使 JAAS 主题可用作当前主题。
  [web.jackson2](#3-7-9webjackson2) | 混合类以提供 Jackson2 序列化支持。
  [web.method.annotation](#3-7-10webmethodannotation) |
  [web.reactive.result.method.annotation](#3-7-11webreactiveresultmethodannotation) |
  [web.reactive.result.view](#3-7-12webreactiveresultview) |
  [web.savedrequest](#3-7-13websavedrequest) | 与需要身份验证的 HttpServletRequest 缓存相关的类。
  [web.server](#3-8-1webserver) |
  [web.server.authentication](#3-8-2webserverauthentication) |
  [web.server.authentication.logout](#3-8-3webserverauthenticationlogout) |
  [web.server.authorization](#3-8-4webserverauthorization) |
  [web.server.context](#3-8-5webservercontext) |
  [web.server.csrf](#3-8-4webserverauthorization) |
  [web.server.header](#3-8-7webserverheader) |
  [web.server.jackson2](#3-8-8webserverjackson2) |
  [web.server.savedrequest](#3-8-9webserversavedrequest) |
  [web.server.transport](#3-8-10webservertransport) |
  [web.server.ui](#3-8-11webserverui) |
  [web.server.util.matcher](#3-8-12webserverutilmatcher) |
  [web.servlet.support.csrf](#3-9-1webservletsupportcsrf) |
  [web.servlet.util.matcher](#3-9-2webservletutilmatcher) |
  [web.servletapi](#3-9-3webservletapi) | 使用新的符合 Spring Security 的 HttpServletRequestWrapper 填充 Servlet 请求。
  [web.session](#3-9-4websession) | 会话管理过滤器、HttpSession 事件和发布者类。
  [web.util](#3-9-5webutil) | Web 实用程序类。
  [web.util.matcher](#3-9-6webutilmatcher) |

## 3-2、web
### 3-2-1、web
## 3-3、web.access
### 3-3-1、web.access
### 3-3-2、web.access.channel
### 3-3-3、web.access.expression
### 3-3-4、web.access.intercept
### 3-4、web.authentication
### 3-4-1、web.authentication
### 3-4-2、web.authentication.logout
### 3-4-3、web.authentication.preauth
### 3-4-4、web.authentication.preauth.j2ee
### 3-4-5、web.authentication.preauth.websphere
### 3-4-6、web.authentication.preauth.x509
### 3-4-7、web.authentication.rememberme
### 3-4-8、web.authentication.session
### 3-4-9、web.authentication.switchuser
### 3-4-10、web.authentication.ui
### 3-4-11、web.authentication.www
## 3-5、web.bind
### 3-5-1、web.bind.annotation
### 3-5-2、web.bind.support
### 3-6、web.context
### 3-6-1、web.context
### 3-6-2、web.context.request.async
### 3-6-3、web.context.support
## 3-7、parts
### 3-7-1、web.csrf
### 3-7-2、web.debug
### 3-7-3、web.firewall
### 3-7-4、web.header
### 3-7-5、web.header.writers
### 3-7-6、web.header.writers.frameoptions
### 3-7-7、web.http
### 3-7-8、web.jaasapi
### 3-7-9、web.jackson2
### 3-7-10、web.method.annotation
### 3-7-11、web.reactive.result.method.annotation
### 3-7-12、web.reactive.result.view
### 3-7-13、web.savedrequest
## 3-8、web.server
### 3-8-1、web.server
### 3-8-2、web.server.authentication
### 3-8-3、web.server.authentication.logout
### 3-8-4、web.server.authorization
### 3-8-5、web.server.context
### 3-8-6、web.server.csrf
### 3-8-7、web.server.header
### 3-8-8、web.server.jackson2
### 3-8-9、web.server.savedrequest
### 3-8-10、web.server.transport
### 3-8-11、web.server.ui
### 3-8-12、web.server.util.matcher
## 3-9、parts 2
### 3-9-1、web.servlet.support.csrf
### 3-9-2、web.servlet.util.matcher
### 3-9-3、web.servletapi
### 3-9-4、web.session
### 3-9-5、web.util
### 3-9-6、web.util.matcher

