# Customizing Auto-Configuration in Spring Boot Application


## Chapter Coverage
- Understanding auto-configuration
- Customizing Spring Boot
	+ Overriding the auto-configuration of Spring Boot
- Externalizing configuration with properties
- Fine-tuning with logging
- Customizing application error pages


### Understanding auto-configuration


##### Learning how auto-configuration works

- `@Conditional` annotation

```
// creates a bean if other beans exist (or don't exist)
@Bean
@ConditionalOnBean(name={"dataSource"})
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
}

// create the bean by checking the type of other classes
@Bean
@ConditionalOnBean(type={DataSource.class})
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
}
```


### Customizing Spring Boot


##### Customizing using Spring Boot properties

```
# Connection settings
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=

# SQL scripts to execute
spring.datasource.schema=
spring.datasource.data=

# Connection pool settings
spring.datasource.initial-size=
spring.datasource.max-active=
spring.datasource.max-idle=
spring.datasource.min-idle=

# Web container configs
server.port=9000
server.address=192.168.11.21
server.session-timeout=1800
server.context-path=/accounts
server.servlet-path=/admin
```



##### Replacing generated beans

```
@Bean
public DataSource dataSource() {
	return new EmbeddedDatabaseBuilder().setName("AccountDB").build();
}
```


##### Disabling specific auto-configuration classes


```
@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
	public class ApplicationConfiguration {
	...
}
```


##### Changing a library's dependencies

```
<properties>
	<spring.version>5.0.0.RELEASE</spring.version>
</properties>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-websocket</artifactId>
	<exclusions>
		<exclusion>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
		</exclusion>
	</exclusions>
</dependency>

<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
</dependency>
```


### Externalizing configuration with properties


**Order of evaluation for overridden properties**
1. Defined properties for the Devtools global settings in the home directory.
2. Defined properties for `@TestPropertySource` annotations on the tests. 
3. Properties as command-line arguments.
4. Defined properties from `SPRING_APPLICATION_JSON`.
5. The properties with `ServletConfig init` parameters. 
6. The properties with `ServletContext init` parameters.
7. JNDI attributes from `java:comp/env`.
8. Java system properties.
9. OS environment variables.
10. Property file(s) - including `application.properties` and its YAML variant. 


**Order of evolution of the properties file and YAML variants**
1. A `RandomValuePropertySource` class that injects properties with random value into configuration file define as `random.*`.
2. Profile-specific application properties outside of the packaged JAR, that is, in the `/config` subdirectory of the directory from which the application is run (`application-{profile}.properties` and YAML variants).
3. Profile-specific application properties outside of the packaged JAR but in a directory from which tha application is run (`application-{profile}.properties` and YAML variants).
4. Profile-specific application properties packaged inside your JAR but in a package named config (`application-{profile}.properties` and YAML variants).
5. Profile-specific application properties packaged inside your JAR but at the root of the classpath (`application-{profile}.properties` and YAML variants).
6. Application properties outside of your packaged JAR, that is, in the `/config` subdirectory of the directory from which the application is run
(`application.properties` and YAML variants).
7. Application properties outside of your packaged JAR but in a directory from which the application is run (`application.properties` and YAML variants).
8. Application properties packaged inside your JAR but in a package named config (`application.properties` and YAML variants).
9. Application properties packaged inside your JAR but at the root of the classpath (`application.properties` and YAML variants).
10. `@PropertySource` annotations on your `@Configuration` classes.
11. Default properties (specified using `SpringApplication.setDefaultProperties`).


##### Renaming application.properties in the Spring application
```
package com.dineshonjava.masteringspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MasteringSpringBootApplication {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "myapp");
		SpringApplication.run(MasteringSpringBootApplication.class, args);
	}
}
```


> The property filename must be defined as `myapp`, not `myapp.properties`; if we use `myapp.peroerties`, the file would get named as `mysapp.properties.properties`.


##### Externally configuring application properties


```
@Component
@ConfigurationProperties(prefix="accounts.client")
public class ConnectionSettings {
	private String host;
	private int port;
	private String logdir;
	private int timeout;
	...
	// getters/setters
	...
}
```

This POJO defines the following properties in the `application.properties` file: 

```
accounts.client.host=192.168.10.21
accounts.client.port=8181
accounts.client.logdir=/logs
accounts.client.timeout=4000
```

##### Using the @EnableConfigurationProperties annotation

```
@Configuration
@EnableConfigurationProperties(ConnectionSettings.class)
public class AccountsClientConfiguration {
	// Spring initialized this automatically
	@Autowired
	ConnectionSettings connectionSettings;

	@Bean
	public AccountClient accountClient() {
		return new AccountClient(
			connectionSettings.getHost(),
			connectionSettings.getPort(),
			...
		);
	}
}
```


### Fine-tuning with logging

Spring Boot includes:
+ **SLF4J**: Logging facade
+ **Logback**: SLF4J implementation

Use another logging frameworks by adding a dependency

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-websocket</artifactId>
	<exclusions>
		<exclusion>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
</dependency>
```

##### Logging output

```
# Use only one of the following properties

# absolute or relative file to the current directory
logging.file=accounts.log

# will write to a spring.log file
logging.path=/var/log/accounts
```

##### Using YAML for configuration

In `application.properties`:

```
database.host = localhost
database.user = admin
```

In `application.yml`:

```
database:
	host: localhost
	user: admin
```

##### Multiple profiles inside a single YAML file

You can define multiple profile-specific configurations in a single YAML file. Spring Boot provides a `spring.profiles` key to indicate when the document applies.

```
#Used for all profiles

logging.level:
org.springframework: INFO

#'dev' profile only
---

spring.profiles: dev
database:
	host: localhost
	user: dev

#'prod' profile only

---

spring.profiles: prod
database:
	host: 192.168.200.109
	user: admin
```

> '---' implies a separation between profiles.

### Customizing application error pages

1. Add a folder named 'error' on the *resources/public* directory.
2. Create an html page with any name, ex: 404.html, under the *resource/public/error* directory. 

















