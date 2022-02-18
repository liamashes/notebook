# 1、eureka-core
## 1-1、modules
### 1-1-1、aws
### 1-1-2、cluster
### 1-1-3、lease
### 1-1-4、registry
### 1-1-5、resources
### 1-1-6、transport
### 1-1-7、util
### 1-1-8、root
#### 1-1-8-1、EurekaServerConfig
> * 综述：服务器端配置类
> * 功能：
>   * AWS的配置：AccessId、密钥、EIP绑定

# eureka-client
    
# eureka-server

# eureka-server-governator

# eureka-test-utils



# 2、


# 问题
## 配置
### defaultZone的作用
DefaultEurekaClientConfig.getAvailabilityZones -> 
    ConfigClusterResolver.getClusterEndpointsFromConfig -> getClusterEndpoints (包装为Endpoint列表)
        RetryableEurekaHttpClient.getHostCandidates (获取主机候选人) -> execute() （执行，获取主机的反馈）
            EurekaHttpClientDecorator ->
                register（注册）
                cancel（取消？）
                sendHeartBeat（发送心跳）
                statusUpdate（状态更新）
                deleteStatusOverride（删除状态覆盖）
                getApplications（获取应用）
                getDelta（获取Delta）
                getVip（获取VIP）
                getSecureVip（获取安全VIP）
                getApplication（获取应用）
                getInstance（获取实例）
        
### webjoin启动不断重复启动
日志：
webjoin process (pid:xxxxx) dead. try to restart it ->
    1、判断为webjoin报错，搜索日志出处 -> find ./ -type f -name "*.rb" -exec grep -H "dead. try to restart it" {} \; -> ./shell/webjoin/webjoind-daemon.rb ->
    2、60行调用process_dead，因为 Eaglet::Util.process_exist? pid 判断失败 ->
        ./shell/eaglet/util.rb: 9行，该函数逻辑为 File.exist? "/proc/#{pid}"
    
    
Unable to add the resource at [XXX] to the cache because there was insufficient free space available after
    1、find ./ -type f -name "*.rb" -exec grep -H "Unable to add the resource at" {} \; -> 无效思路
    2、修改tomcat/conf/context.xml，添加参数
        <Resources cachingAllowed="true" cacheMaxSize="100000" />
        <Resources cachingAllowed="false" />

INFO - tomcat [eureka] started
Illegal Argument (command: start) : Cannot attach to process-ID 123196
See --help for possible reasons
    1、find ./ -type f -name "*.rb" -exec grep -H "Illegal Argument" {} \;


