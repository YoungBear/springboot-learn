# SpringBoot 启动原理学习

## 说明：
spring-boot 版本为`2.1.6.RELEASE`



## 1. ApplicationListener

自定义`ApplicationListener`后，启动时查看日志：

```
2019-07-29 23:03:01.045  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.6.RELEASE)

2019-07-29 23:03:01.347  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.context.event.ApplicationContextInitializedEvent
2019-07-29 23:03:01.359  INFO 94244 --- [           main] c.e.starter.impl.StarterImplApplication  : Starting StarterImplApplication on 192.168.0.100 with PID 94244 (/Users/youngbear/myfile/gitHub/springboot-learn/starter-impl/target/classes started by youngbear in /Users/youngbear/myfile/gitHub/springboot-learn)
2019-07-29 23:03:01.360  INFO 94244 --- [           main] c.e.starter.impl.StarterImplApplication  : No active profile set, falling back to default profiles: default
2019-07-29 23:03:01.447  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.context.event.ApplicationPreparedEvent
2019-07-29 23:03:03.646  INFO 94244 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-07-29 23:03:03.692  INFO 94244 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-07-29 23:03:03.693  INFO 94244 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
2019-07-29 23:03:03.923  INFO 94244 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-07-29 23:03:03.926  INFO 94244 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2479 ms
2019-07-29 23:03:04.310  INFO 94244 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-07-29 23:03:04.855  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.context.event.ContextRefreshedEvent
2019-07-29 23:03:05.045  INFO 94244 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-07-29 23:03:05.050  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
2019-07-29 23:03:05.063  INFO 94244 --- [           main] c.e.starter.impl.StarterImplApplication  : Started StarterImplApplication in 4.502 seconds (JVM running for 5.116)
2019-07-29 23:03:05.063  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.context.event.ApplicationStartedEvent
2019-07-29 23:03:05.075  INFO 94244 --- [           main] c.e.s.listener.MyApplicationListener     : onApplicationEvent, className: org.springframework.boot.context.event.ApplicationReadyEvent
```

可以看出来，时间上，接收到的事件依次为：

```shell
# 1. 
ApplicationEnvironmentPreparedEvent
# 2. 
ApplicationContextInitializedEvent
# 3. 
ApplicationPreparedEvent
# 4. 
ContextRefreshedEvent
# 5. 
ServletWebServerInitializedEvent
# 6. 
ApplicationStartedEvent
# 7. 
ApplicationReadyEvent
```



todo: 一次分析每个event的作用。



`spring.factories` 在SpringBoot工程下的路径为：

```shell
spring-boot-project/spring-boot/src/main/resources/META-INF/spring.factories
```

其内容为：

```properties
# PropertySource Loaders
org.springframework.boot.env.PropertySourceLoader=\
org.springframework.boot.env.PropertiesPropertySourceLoader,\
org.springframework.boot.env.YamlPropertySourceLoader

# Run Listeners
org.springframework.boot.SpringApplicationRunListener=\
org.springframework.boot.context.event.EventPublishingRunListener

# Error Reporters
org.springframework.boot.SpringBootExceptionReporter=\
org.springframework.boot.diagnostics.FailureAnalyzers

# Application Context Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,\
org.springframework.boot.context.ContextIdApplicationContextInitializer,\
org.springframework.boot.context.config.DelegatingApplicationContextInitializer,\
org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer

# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.ClearCachesApplicationListener,\
org.springframework.boot.builder.ParentContextCloserApplicationListener,\
org.springframework.boot.context.FileEncodingApplicationListener,\
org.springframework.boot.context.config.AnsiOutputApplicationListener,\
org.springframework.boot.context.config.ConfigFileApplicationListener,\
org.springframework.boot.context.config.DelegatingApplicationListener,\
org.springframework.boot.context.logging.ClasspathLoggingApplicationListener,\
org.springframework.boot.context.logging.LoggingApplicationListener,\
org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener

# Environment Post Processors
org.springframework.boot.env.EnvironmentPostProcessor=\
org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor,\
org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor,\
org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor,\
org.springframework.boot.reactor.DebugAgentEnvironmentPostProcessor

# Failure Analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
org.springframework.boot.diagnostics.analyzer.BeanCurrentlyInCreationFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BeanDefinitionOverrideFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BeanNotOfRequiredTypeFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BindFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.BindValidationFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.UnboundConfigurationPropertyFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.ConnectorStartFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.NoSuchMethodFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.NoUniqueBeanDefinitionFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.PortInUseFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.ValidationExceptionFailureAnalyzer,\
 org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertiesFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyNameFailureAnalyzer,\
org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyValueFailureAnalyzer

# FailureAnalysisReporters
org.springframework.boot.diagnostics.FailureAnalysisReporter=\
org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter

```



