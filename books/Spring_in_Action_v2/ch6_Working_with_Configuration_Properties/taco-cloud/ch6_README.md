## Chapter 6: Working with Configuration Properties
### Fine-tuning Autoconfiguration
- _Bean wiring_: Configuration that declares application components to be created
as beans in the Spring context and how they should be injected into each other.
- _Property injection_: Configuration that sets values on beans in the Spring 
application context. 

In Spring's XML and Java configuration, these two types of configurations are 
often declared explicitly in the same place. For example, let us consider the 
following `@Bean` method that declares a DataSource for an embedded H2 database:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Bean
public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("taco_schema.sql")
            .addScript("user_data.sql", "ingredient_data.sql")
            .build();
}
```
Whereas this is how we might configure a DataSource bean if we aren't using Spring Boot,
autoconfiguration makes this method completely unnecessary. 

But what if we want to name the SQL scripts something else? Or what if we need to specify
more than two SQL scripts? That's where configuration properties come in. But before we
can start using configuration properties, we need to understand where those properties
come from. 

#### Understanding Spring's environment abstraction
The Spring environment pulls from several property sources, including the following:
- JVM system properties
- Operating system environment variables
- Command-line arguments
- Application property configuration files

_Following figure illustrates how properties from property sources flow through the 
Spring environment abstraction to Spring beans._ 
![spring_env_pulls_from_property](./src/main/resources/images/6_1.png)
As a simple example, suppose that we would like the application's underlying servlet
container to listen for requests on some port other than the default port of 8080. 
To do that, we can specify a different port by setting the `server.port` property in
**src/main/resources/application.properties** like this: 
```properties
server.port=9090
```
Using YAML, it will be like 
```yaml
server:
  port: 9090
```
If we'd prefer to configure that property externally, we could specify the port when
starting the application using a command-line argument as follows: 
```shell
$ java -jar tacocloud-0.0.5-SNAPSHOT.jar --server.port=9090
```
If we want the application to always start on a specific port, we could set it one time
as an operating system environment variable, as shown below: 
```shell
$ export SERVER_PORT=9090
```

#### Configuring a data source
Although we could explicitly configure our own DataSource bean, that's usually unnecessary. 
Instead, it's simpler to configure the URL and credentials for our database via configuration
properties. For example: 
```yaml
spring: 
  datasource:
    url: jdbc:mysql://localhost/tacocloud
    username: tacouser
    password: tacopassword
    driver-class-name: com.mysql.jdbc.Driver
```
The DataSource bean will be pooled using the **HikariCP** connection pool if it's 
available on the classpath. If not, Spring Boot looks for and uses one of the following
other connection pool implementations on the classpath: 
- Tomcat JDBC Connection Pool
- Apache Commons DBCP2

Earlier, we suggested that there might be a way to specify the database initialization
scripts to run when the application starts. We can do that in Spring Boot like following: 
```yaml
spring: 
  datasource: 
    schema: 
    - order-schema.sql
    - ingredient-schema.sql
    - taco-schema.sql
    - user-schema.sql
    data: 
    - ingredients.sql
```
If we'd prefer to configure our data source in the Java Naming and Directory 
Interface (JNDI) and have Spring look it up from there, we can set up our data source
by configuring `spring.datasource.jndi-name` as follows: 
```yaml
spring: 
  datasource:
    jndi-name: java:/comp/env/jdbc/tacoCloudDS
```
_If we set the `spring.datasource.jndi-name` property, the other data source 
connection properties (if set) are ignored._

#### Configuring the embedded server
What happens if `server.port` is set to 0, as shown below: 
```yaml
server: 
  port: 0
```
Although, we're explicitly setting the port to 0, the server won't start on port 0. 
Instead, it'll start on a randomly chosen available port. This is useful when 
running automated integration tests to ensure that any concurrently running tests 
don't clash on a hardcoded port number. 

One of the most common things we'll need to do with the underlying container is to 
set it up to handle HTTPS requests. To do that, the first thing we must do is create 
a keystore using the JDK's `keytool` command-line utility, as shown next: 
```shell
$ keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA
```
Next, we'll need to set a few properties to enable HTTPS in the embedded server. We 
will set them in the `application.properties` or `application.yml` file. 
```yaml
server: 
  port: 8443
  ssl: 
    key-store: file:///path/to/mykeys.jks
    key-store-password: letmein
    key-password: letmein
```

#### Configuring logging
By default, Spring Boot configures logging via Logback to write to the console at an 
`INFO` level. For full control over the logging configuration, we can create a 
`logback.xml` file at the root of the classpath (in **src/main/resources**). Here's 
an example of a simple logback.xml we might use:

```xml

<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>
    <logger name="root" level="INFO"/>
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```
The most common changes we'll make to a logging configuration are to change the 
logging levels and perhaps to specify a file where the logs should be written. With
Spring Boot configuration properties, we can make those changes without having to 
create a logback.xml file. To set the logging levels we create properties that are 
prefixed with `logging.level`, followed by the name of the logger for which we want 
to set the logging level. 

For instance, suppose we'd like to set the root logging level to `WARN`, but log 
Spring Security logs at a `DEBUG` level. The following entries will take care of that: 
```yaml
logging: 
  level: 
    root: WARN
    org: 
      springframework: 
        security: DEBUG
```
Now, suppose that we want to write the log entries to the file TacoCloud.log at 
/var/logs/. The `logging.file.path` and `logging.file.name` properties can help: 
```yaml
logging: 
  file: 
    path: /var/logs/
    file: TacoCloud.log
  level: 
    root: WARN
    org.springframework.security: DEBUG
```
_By default, the log files rotate once they reach 10 MB in size._

#### Using special property values
When setting properties, we can derive their values from other configuration
properties. For example, suppose we want to set a property named `greeting.welcome`
to echo the value of another property named `spring.application.name`. To achieve 
this we can use the `${}` placeholder like follows: 
```yaml
greeting: 
  welcome: ${spring.application.name}
```
We can even embed that placeholder amid other text, as shown below: 
```yaml
greeting: 
  welcome: You are using ${spring.application.name}.
```
