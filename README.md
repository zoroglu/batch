# Minimal Spring Boot Project for Investigating Aspect Trigger Issue

This project is a minimal Spring Boot sample created to reproduce an issue where an `@Aspect` sometimes fails to trigger.

The issue occasionally occurs during load testing, especially after an application restart. This project aims to isolate and demonstrate that behavior.

⚠️ **Version-specific behavior:**  
This issue **does not occur** when using **Spring Boot 3.3.10**, but it **does occur intermittently** with **Spring Boot 3.4.x** versions (such as 3.4.0, 3.4.2, and 3.4.3).

---

## Load Testing Tool

[`Locust`](https://locust.io/) is used to simulate concurrent requests during load testing.

> 💡 **Note:** The issue is **not specific to Locust**. It can also be observed with other load testing tools or custom concurrent request simulations. The main trigger appears to be a combination of load and application restarts.

### Locust Test Script

```python
from locust import HttpUser, task

class BatchProject(HttpUser):    
    @task
    def authentication_microservice(self):
        self.client.get("/batch/findIdBatchByCode?code=OUTDATEDPAYMENT", json={})
```



### Setup Instructions

1. Clone the project:
git clone https://github.com/zoroglu/batch.git
cd batch

2. Create the database and run the SQL script located in:
src/main/resources/db-script.sql

3. Update your application.properties file with your local database configuration:
spring.datasource.url=jdbc:oracle:thin:YOUR_DB






## Observed Behavior

In some cases, especially after a restart during load testing, the aspect method does not get triggered. This behavior is inconsistent.

Below is a sample log output when the aspect is **not** triggered:



```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.4.3)

2025-04-09T02:01:05.973+03:00  INFO 338650 --- [  restartedMain] com.example.batch.BatchApplication       : Starting BatchApplication using Java 21.0.2 with PID 338650 (/home/kernel/projects/java/others/batch/target/classes started by ... in /home/kernel/projects/java/others/batch)
2025-04-09T02:01:05.976+03:00  INFO 338650 --- [  restartedMain] com.example.batch.BatchApplication       : No active profile set, falling back to 1 default profile: "default"
2025-04-09T02:01:06.023+03:00  INFO 338650 --- [  restartedMain] o.s.b.devtools.restart.ChangeableUrls    : The Class-Path manifest attribute in /home/kernel/.m2/repository/com/oracle/database/jdbc/ojdbc11/23.7.0.25.01/ojdbc11-23.7.0.25.01.jar referenced one or more files that do not exist: file:/home/kernel/.m2/repository/com/oracle/database/jdbc/ojdbc11/23.7.0.25.01/oraclepki.jar
2025-04-09T02:01:06.023+03:00  INFO 338650 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2025-04-09T02:01:06.023+03:00  INFO 338650 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2025-04-09T02:01:06.503+03:00  INFO 338650 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-04-09T02:01:06.559+03:00  INFO 338650 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 47 ms. Found 1 JPA repository interface.
2025-04-09T02:01:06.944+03:00  WARN 338650 --- [  restartedMain] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.ws.config.annotation.DelegatingWsConfiguration' of type [org.springframework.ws.config.annotation.DelegatingWsConfiguration$$SpringCGLIB$$0] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying). The currently created BeanPostProcessor [annotationActionEndpointMapping] is declared through a non-static factory method on that class; consider declaring it as static instead.
2025-04-09T02:01:06.975+03:00  INFO 338650 --- [  restartedMain] .w.s.a.s.AnnotationActionEndpointMapping : Supporting [WS-Addressing August 2004, WS-Addressing 1.0]
2025-04-09T02:01:07.351+03:00  INFO 338650 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8088 (http)
2025-04-09T02:01:07.362+03:00  INFO 338650 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-04-09T02:01:07.362+03:00  INFO 338650 --- [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.36]
2025-04-09T02:01:07.387+03:00  INFO 338650 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-04-09T02:01:07.387+03:00  INFO 338650 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1363 ms
2025-04-09T02:01:07.570+03:00  INFO 338650 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : ConnPool - Starting...
2025-04-09T02:01:09.143+03:00  INFO 338650 --- [  restartedMain] com.zaxxer.hikari.pool.HikariPool        : ConnPool - Added connection oracle.jdbc.driver.T4CConnection@4a752d2b
2025-04-09T02:01:09.144+03:00  INFO 338650 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : ConnPool - Start completed.
2025-04-09T02:01:09.181+03:00  INFO 338650 --- [  restartedMain] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-04-09T02:01:09.214+03:00  INFO 338650 --- [  restartedMain] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.8.Final
2025-04-09T02:01:09.235+03:00  INFO 338650 --- [  restartedMain] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-04-09T02:01:09.471+03:00  INFO 338650 --- [  restartedMain] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-04-09T02:01:10.564+03:00  INFO 338650 --- [  restartedMain] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
	Database JDBC URL [Connecting through datasource 'HikariDataSource (ConnPool)']
	Database driver: undefined/unknown
	Database version: 21.3
	Autocommit mode: undefined/unknown
	Isolation level: undefined/unknown
	Minimum pool size: undefined/unknown
	Maximum pool size: undefined/unknown
2025-04-09T02:01:11.246+03:00  INFO 338650 --- [  restartedMain] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2025-04-09T02:01:11.249+03:00  INFO 338650 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-04-09T02:01:11.624+03:00  WARN 338650 --- [  restartedMain] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2025-04-09T02:01:12.099+03:00  INFO 338650 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2025-04-09T02:01:12.125+03:00  INFO 338650 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8088 (http) with context path '/'
2025-04-09T02:01:12.135+03:00  INFO 338650 --- [  restartedMain] com.example.batch.BatchApplication       : Started BatchApplication in 6.593 seconds (process running for 7.501)
2025-04-09T02:01:12.184+03:00  INFO 338650 --- [o-8088-exec-106] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-04-09T02:01:12.184+03:00  INFO 338650 --- [o-8088-exec-106] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-04-09T02:01:12.186+03:00  INFO 338650 --- [o-8088-exec-106] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
2025-04-09T02:01:13.010+03:00 ERROR 338650 --- [io-8088-exec-27] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.IncorrectResultSizeDataAccessException: Query did not return a unique result: 2 results were returned] with root cause

org.hibernate.NonUniqueResultException: Query did not return a unique result: 2 results were returned
	at org.hibernate.query.spi.AbstractSelectionQuery.uniqueElement(AbstractSelectionQuery.java:298) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.hibernate.query.spi.AbstractSelectionQuery.getSingleResult(AbstractSelectionQuery.java:281) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution$SingleEntityExecution.doExecute(JpaQueryExecution.java:224) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution.execute(JpaQueryExecution.java:93) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.doExecute(AbstractJpaQuery.java:152) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.execute(AbstractJpaQuery.java:140) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:149) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:69) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:136) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at com.example.batch.service.BatchBusinessService.findIdBatchByCode(BatchBusinessService.java:15) ~[classes/:na]
	at com.example.batch.rest.BatchService.findIdBatchByCode(BatchService.java:21) ~[classes/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:257) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:190) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:978) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:397) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1743) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

2025-04-09T02:01:13.074+03:00 ERROR 338650 --- [o-8088-exec-169] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.IncorrectResultSizeDataAccessException: Query did not return a unique result: 2 results were returned] with root cause

org.hibernate.NonUniqueResultException: Query did not return a unique result: 2 results were returned
	at org.hibernate.query.spi.AbstractSelectionQuery.uniqueElement(AbstractSelectionQuery.java:298) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.hibernate.query.spi.AbstractSelectionQuery.getSingleResult(AbstractSelectionQuery.java:281) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution$SingleEntityExecution.doExecute(JpaQueryExecution.java:224) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution.execute(JpaQueryExecution.java:93) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.doExecute(AbstractJpaQuery.java:152) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.execute(AbstractJpaQuery.java:140) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:149) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:69) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:136) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at com.example.batch.service.BatchBusinessService.findIdBatchByCode(BatchBusinessService.java:15) ~[classes/:na]
	at com.example.batch.rest.BatchService.findIdBatchByCode(BatchService.java:21) ~[classes/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:257) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:190) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:978) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:397) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1743) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
Hibernate: select b1_0.id,b1_0.apiinventorycode,b1_0.code,b1_0.companyid,b1_0.createdate,b1_0.createuser,b1_0.updatedate,b1_0.updateuser,b1_0.versionid from batch b1_0 where b1_0.code=?
2025-04-09T02:01:13.112+03:00 ERROR 338650 --- [o-8088-exec-117] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.IncorrectResultSizeDataAccessException: Query did not return a unique result: 2 results were returned] with root cause

org.hibernate.NonUniqueResultException: Query did not return a unique result: 2 results were returned
	at org.hibernate.query.spi.AbstractSelectionQuery.uniqueElement(AbstractSelectionQuery.java:298) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.hibernate.query.spi.AbstractSelectionQuery.getSingleResult(AbstractSelectionQuery.java:281) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution$SingleEntityExecution.doExecute(JpaQueryExecution.java:224) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.JpaQueryExecution.execute(JpaQueryExecution.java:93) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.doExecute(AbstractJpaQuery.java:152) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.jpa.repository.query.AbstractJpaQuery.execute(AbstractJpaQuery.java:140) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:170) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:149) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:69) ~[spring-data-commons-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:136) ~[spring-data-jpa-3.4.3.jar:3.4.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138) ~[spring-tx-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.2.3.jar:6.2.3]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223) ~[spring-aop-6.2.3.jar:6.2.3]
	at jdk.proxy4/jdk.proxy4.$Proxy135.findByCode(Unknown Source) ~[na:na]
	at com.example.batch.service.BatchBusinessService.findIdBatchByCode(BatchBusinessService.java:15) ~[classes/:na]
	at com.example.batch.rest.BatchService.findIdBatchByCode(BatchService.java:21) ~[classes/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:257) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:190) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:978) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.2.3.jar:6.2.3]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.36.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.2.3.jar:6.2.3]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.3.jar:6.2.3]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:397) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1743) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.36.jar:10.1.36]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]
