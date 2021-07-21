## 反射
org.apache.ibatis.reflection
Reflector、ParamNameResolver

java.lang.reflect
Constructor

## 对象工厂
org.apache.ibatis.reflection.factory.ObjectFactory
org.apache.ibatis.reflection.factory.DefaultObjectFactory

## 代理
org.apache.ibatis.executor.loader.ProxyFactory

java.lang.reflect
Proxy

## 会话
org.apache.ibatis.session
Configuration、SqlSession

## 代理
org.apache.ibatis.binding

## 构建
org.apache.ibatis.builder
XMLConfigBuilder

## 缓存
org.apache.ibatis.cache
Cache、TransactionalCache

org.mybatis.caches.redis
RedisCache

## 语言驱动
org.apache.ibatis.scripting
LanguageDriver

org.apache.ibatis.scripting.xmltags
OgnlCache

ognl
Ognl、OgnlParser

## 插件核心类
Plugin：  
    getSignatureMap：获取Intercepts注解 -> 获取所有Signature注解 -> 遍历Signature，将注解中配置的所有组件、方法加入到map，key为四个组件的class对象，value为拦截的所有方法对应的Method对象数组  
    plugin：返回一个动态代理对象  
    setProperties：设置plugin标签的属性值  
Interceptor  
