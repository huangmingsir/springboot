#Spring boot   

https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/htmlsingle/#boot-features-adding-active-profiles

#druid:
https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98

#log4j2
https://github.com/alibaba/druid/wiki/Druid中使用log4j2进行日志输出

#Shiro
需要引入的依赖
```java
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-core</artifactId>
			<version>1.4.0</version>
		</dependency>
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-spring</artifactId>
			<version>1.4.0</version>
		</dependency>
```
创建ShiroRealm类并继承AuthorizingRealm，然后实现AuthorizationInfo权限管理，AuthenticationInfo登录管理
