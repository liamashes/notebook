在实际开发过程中，我们需要详细到一一对应的版本关系：Spring 官方对应版本地址：  (https://start.spring.io/actuator/info)，建议用firefox浏览器打开，你会看见格式化好了json信息：

> 序号	|   版本对应
> ---   |   ---
> 1	    |   <spring-boot.version>2.4.2</spring-boot.version>    <spring-cloud.version>2020.0.0</spring-cloud.version>
> 2	    |   <spring-boot.version>2.4.1</spring-boot.version>    <spring-cloud.version>2020.0.0-M6</spring-cloud.version>
> 3	    |   <spring-boot.version>2.4.0</spring-boot.version>    <spring-cloud.version>2020.0.0-M6</spring-cloud.version>
> 4	    |   <spring-boot.version>2.3.2.RELEASE</spring-boot.version>    <spring-cloud.version>Greenwich.SR2</spring-cloud.version>

```json
{
    "git": {
        "branch": "df41ec3030f26c35b3feb92770c54e4a42b70391",
        "commit": {
            "id": "df41ec3",
            "time": "2022-04-01T12:45:45Z"
        }
    },
    "build": {
        "version": "0.0.1-SNAPSHOT",
        "artifact": "start-site",
        "versions": {
            "spring-boot": "2.6.6",
            "initializr": "0.13.0-SNAPSHOT"
        },
        "name": "start.spring.io website",
        "time": "2022-04-01T12:47:18.599Z",
        "group": "io.spring.start"
    },
    "bom-ranges": {
        "codecentric-spring-boot-admin": {
            "2.4.3": "Spring Boot >=2.3.0.M1 and <2.5.0-M1",
            "2.5.5": "Spring Boot >=2.5.0.M1 and <2.6.0-M1",
            "2.6.2": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
        },
        "solace-spring-boot": {
            "1.1.0": "Spring Boot >=2.3.0.M1 and <2.6.0-M1",
            "1.2.1": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
        },
        "solace-spring-cloud": {
            "1.1.1": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
            "2.1.0": "Spring Boot >=2.4.0.M1 and <2.6.0-M1",
            "2.3.0": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
        },
        "spring-cloud": {
            "Hoxton.SR12": "Spring Boot >=2.2.0.RELEASE and <2.4.0.M1",
            "2020.0.5": "Spring Boot >=2.4.0.M1 and <2.6.0-M1",
            "2021.0.0-M1": "Spring Boot >=2.6.0-M1 and <2.6.0-M3",
            "2021.0.0-M3": "Spring Boot >=2.6.0-M3 and <2.6.0-RC1",
            "2021.0.0-RC1": "Spring Boot >=2.6.0-RC1 and <2.6.1",
            "2021.0.1": "Spring Boot >=2.6.1 and <2.6.7-SNAPSHOT",
            "2021.0.2-SNAPSHOT": "Spring Boot >=2.6.7-SNAPSHOT and <3.0.0-M1",
            "2022.0.0-M1": "Spring Boot >=3.0.0-M1 and <3.1.0-M1"
        },
        "spring-cloud-azure": {
            "4.0.0": "Spring Boot >=2.5.0.M1 and <2.7.0-M1"
        },
        "spring-cloud-gcp": {
            "2.0.9": "Spring Boot >=2.4.0-M1 and <2.6.0-M1",
            "3.2.0": "Spring Boot >=2.6.0-M1 and <2.7.0-M1"
        },
        "spring-cloud-services": {
            "2.3.0.RELEASE": "Spring Boot >=2.3.0.RELEASE and <2.4.0-M1",
            "2.4.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
            "3.3.0": "Spring Boot >=2.5.0-M1 and <2.6.0-M1",
            "3.4.0": "Spring Boot >=2.6.0-M1 and <2.7.0-M1"
        },
        "spring-geode": {
            "1.3.12.RELEASE": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
            "1.4.13": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
            "1.5.12": "Spring Boot >=2.5.0-M1 and <2.6.0-M1",
            "1.6.6": "Spring Boot >=2.6.0-M1 and <2.7.0-M1",
            "1.7.0-M3": "Spring Boot >=2.7.0-M1 and <3.0.0-M1",
            "2.0.0-M2": "Spring Boot >=3.0.0-M1 and <3.1.0-M1"
        },
        "vaadin": {
            "14.8.6": "Spring Boot >=2.1.0.RELEASE and <2.6.0-M1",
            "23.0.3": "Spring Boot >=2.6.0-M1 and <2.8.0-M1"
        },
        "wavefront": {
            "2.0.2": "Spring Boot >=2.1.0.RELEASE and <2.4.0-M1",
            "2.1.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
            "2.2.2": "Spring Boot >=2.5.0-M1 and <2.7.0-M1"
        }
    },
    "dependency-ranges": {
        "native": {
            "0.9.0": "Spring Boot >=2.4.3 and <2.4.4",
            "0.9.1": "Spring Boot >=2.4.4 and <2.4.5",
            "0.9.2": "Spring Boot >=2.4.5 and <2.5.0-M1",
            "0.10.0": "Spring Boot >=2.5.0-M1 and <2.5.2",
            "0.10.1": "Spring Boot >=2.5.2 and <2.5.3",
            "0.10.2": "Spring Boot >=2.5.3 and <2.5.4",
            "0.10.3": "Spring Boot >=2.5.4 and <2.5.5",
            "0.10.4": "Spring Boot >=2.5.5 and <2.5.6",
            "0.10.5": "Spring Boot >=2.5.6 and <2.5.9",
            "0.10.6": "Spring Boot >=2.5.9 and <2.6.0-M1",
            "0.11.0-M1": "Spring Boot >=2.6.0-M1 and <2.6.0-RC1",
            "0.11.0-M2": "Spring Boot >=2.6.0-RC1 and <2.6.0",
            "0.11.0-RC1": "Spring Boot >=2.6.0 and <2.6.1",
            "0.11.0": "Spring Boot >=2.6.1 and <2.6.2",
            "0.11.1": "Spring Boot >=2.6.2 and <2.6.3",
            "0.11.2": "Spring Boot >=2.6.3 and <2.6.4",
            "0.11.3": "Spring Boot >=2.6.4 and <2.6.7-SNAPSHOT",
            "0.11.4-SNAPSHOT": "Spring Boot >=2.6.7-SNAPSHOT and <2.7.0-M1"
        },
        "okta": {
            "1.4.0": "Spring Boot >=2.2.0.RELEASE and <2.4.0-M1",
            "1.5.1": "Spring Boot >=2.4.0-M1 and <2.4.1",
            "2.0.1": "Spring Boot >=2.4.1 and <2.5.0-M1",
            "2.1.5": "Spring Boot >=2.5.0-M1 and <2.7.0-M1"
        },
        "mybatis": {
            "2.1.4": "Spring Boot >=2.1.0.RELEASE and <2.5.0-M1",
            "2.2.2": "Spring Boot >=2.5.0-M1"
        },
        "camel": {
            "3.5.0": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
            "3.10.0": "Spring Boot >=2.4.0.M1 and <2.5.0-M1",
            "3.13.0": "Spring Boot >=2.5.0.M1 and <2.6.0-M1",
            "3.16.0": "Spring Boot >=2.6.0.M1 and <2.7.0-M1"
        },
        "picocli": {
            "4.6.3": "Spring Boot >=2.4.0.RELEASE and <3.0.0-M1"
        },
        "open-service-broker": {
            "3.2.0": "Spring Boot >=2.3.0.M1 and <2.4.0-M1",
            "3.3.1": "Spring Boot >=2.4.0-M1 and <2.5.0-M1",
            "3.4.0-M2": "Spring Boot >=2.5.0-M1 and <2.6.0-M1"
        }
    }
}
```
