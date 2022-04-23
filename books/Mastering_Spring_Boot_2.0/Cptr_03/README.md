# Getting Started with Spring CLI and Actuator

## Chapter Coverage

+ Getting started with using Spring Boot CLI
	- Installing the Spring Boot CLI
+ Using the Initializr with the Spring Boot CLI
+ Spring Boot Actuator
    - Taking Application's Insights.
    - Enabling Spring Boot's Actuator in the application
    - Analyzing the Actuator's endpoints
    - Exposing configuration details
    - Exposing metrics endpoints
    - Exposing application information
    - Shutting down the application
    - Customizing the Actuator endpoints
+ Securing the Actuator endpoints
+ The Actuator with Spring Boot 2.X


### Install Spring Boot CLI

Linux

```
curl -s get.sdkman.io | bash

sdk install springboot
spring --version
```

### Using the Initializr with the Spring Boot CLI


Create a default Spring Boot project

```
$ spring init
```

Create a project with dependencies

```
$ spring init -dewb,jpa
```

> *You can specify those initial dependencies with either `--dependencies` or `-d`.*
> *It's important to not type a space between `-d` and the dependencies.*

Create a project with Gradle build

```
$ spring init -dweb,jpa --build gradle
```

Create a project with WAR as packaging instead of JAR

```
$ spring init -dweb,jpa -p war
```

> *You can specify this with the `--packaging` or `-p` parameter.*

Find other parameters

```
$ spring help init
```

### Spring Boot Actuator

The Spring Boot Actuator allows you to monitor production-ready features, such as metrics and the health of the Spring application.

##### Enabling Spring Boot's Actuator in the application

```
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```

##### Analyzing the Actuator's endpoints

+ /actuator
    - It gives a discovery platform in place of a page for other endpoints. To enable Actuator, you have to put Spring HATEOS on the classpath. Actuators are sensitive by default and hence require username and password, or they can be disabled due to disabled web security.
+ /auditevents
    - All information on audit and events is contained in this endpoint.
+ /autoconfig
    - It provides an auto-configuration report of all the auto-configurations applied in the application.
+ /beans
    - It shows all the beans configured in the application. Beans are super important for applications configured in Spring. It is an object that is initialized, assembled, and managed in Spring IoC container.
+ /configprops
    - It shows you the details of config properties.
+ /dump
    - This is for dumping a thread.
+ /env
    - It shows different properties of all configurable environments in Spring.
+ /flyway
    - This helps when you want to see database migrations.
+ /health
    - This displays the health information of an application. Health information includes security, authentication of connections made, and message details of authentications for an application.
+ /info
    - This is the arbitrary application information.
+ /loggers
    - You can use it if you want to show or change the config of different loggers in your application.
+ /liquibase
    - This is in case you want to see migrations of liquibase .
+ /metrics
    - This shows metric information for an application.
+ /mappings
    - This shows a queue of the entire request mapping paths in the application.
+ /shutdown
    - It is enabled to allow the application a graceful shutdown. Spring Boot Actuator does not enable it by default. You will have to enable it should you require it.
+ /trace
    - Shows trace data (timestamp, headers, and so on) which is the 100 latest HTTP requests.
    
##### Shutting down the application

```
endpoints.shutdown.enabled=true
```

Make a POST request to `https://localhost:8080/shutdown`
Returns the following response: 

```
{
	"message": "Shutting down, bye..."
}
```


#### Customizing the Actuator endpoints

##### Enabling or disabling endpoints

```
# enable the /shutdown endpoint
endpoints.shutdown.enabled=true

# disable the /health endpoint
endpoints.health.enabled=false`

# disable all endpoints at once
endpoints.enabled=false
```

#### Creating a custom endpoint

```
package com.dineshonjava.sba;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class MyCustomEndpoint implements Endpoint<List<String>>{
	@Override
	public String getId() {
		return "myCustomEndpoint";
	}
	
	@Override
	public List<String> invoke() {
		// Custom logic to build the output
		List<String> list = new ArrayList<>();
		list.add("App message 1");
		list.add("App message 2");
		list.add("App message 3");
		list.add("App message 4");
		return list;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isSensitive() {
		return true;
	}
}
```

After hitting the /myCustomEndpoint 

```
[
	"App message 1",
	"App message 2",
	"App message 3",
	"App message 4"
]
```


#### Securing the Actuator endpoints

pom.xml

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

application.properties

```
security.basic.enabled=false

security.user.name=admin
security.user.password=secret
management.security.role=SUPERUSER
```







































