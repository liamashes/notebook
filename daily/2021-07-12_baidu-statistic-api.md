# 必要信息
> * 地址：https://tongji.baidu.com/web/welcome/login  
> * 用户：18810356554  
> * 密码：amber129  

# 参考资料
> * 官方 API 文档：https://tongji.baidu.com/api/manual/  
> * 白皮书：[网站分析白皮书V3.0](https://tongji.baidu.com/web/image/百度发布《网站分析白皮书V3.0》.pdf)  

# 获取token  
#### 1、创建工程
> 创建工程网址：http://developer.baidu.com/console#app/project  
> 获取API Key、Secret Key  
>   * API Key：
>     MQ8dL619Chm40wVGYgxBqn8G  
>   * Secret Key：
>     VRjapy5l1OOaTY53OWHSHuWyNGWbFshZ  
#### 2、访问网址，获取授权
> API授权网址: [API授权网址](http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=MQ8dL619Chm40wVGYgxBqn8G&redirect_uri=bdconnect://success&scope=basic&display=popup)  
> 获取结果：3c043a7f3b9a8678836c08ef35724f20  
#### 3、访问地址，获取token  
> TOKEN获取网址：[TOKEN网站](http://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=3c043a7f3b9a8678836c08ef35724f20&client_id=MQ8dL619Chm40wVGYgxBqn8G&client_secret=VRjapy5l1OOaTY53OWHSHuWyNGWbFshZ&redirect_uri=bdconnect://success)  
> 返回结果：{"expires_in":2592000,"refresh_token":"122.60cbc5e9ca8ee269ad732b35c18a5011.YBrw26QdmjFElxJthmrcd3J0HsRlzGnTrGcd-AQ.6nD-XQ","access_token":"121.b6528c63c5779bd24a8a3b12ba7ba593.YDrLIWAu_vY4E0kuhRYKbtvGZ3lbwP6d0tnxvkw.zRdJPg","session_secret":"","session_key":"","scope":"basic"}
