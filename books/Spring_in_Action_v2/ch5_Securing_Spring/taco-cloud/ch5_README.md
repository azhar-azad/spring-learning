## Chapter 5: Securing Spring
### Enabling Spring Security
By doing nothing more than adding the security starter to the project build, we get 
the following security features: 
- All HTTP request paths require authentication. 
- No specific roles or authorities are required. 
- Authentication is prompted with a simple login page. 
- There's only one user; the username is _user_.

We'll need to at least configure Spring Security to do the following: 
- Provide a login page that is designed to match the website. 
- Provide for multiple users, and enable a registration page so new Taco Cloud 
customers can sign up. 
- Apply different security roles for different request paths. The home page and 
registration pages, for example, shouldn't require authentication at all. 

### Configuring Authentication
To configure a user store (that can handle more than one user) for authentication 
purposes, we'll need to declare a `UserDetailsService` bean. The `UserDetailsService`
interface is relatively simple, including only one method that must be implemented. 
Here's what this interface looks like:

```java
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```
The `loadUserByUsername()` method accepts a username and uses it to look up a 
`UserDetails` object. If no user can be found for the given username, then it will 
throw a `UsernameNotFoundException`. 

As it turns out, Spring Security offers several out-of-the-box implementations of 
`UserDetailsService`, including the following: 
- An in-memory user store
- A JDBC user store
- An LDAP user store

Or, we can also create our own implementation to suit our application's specific 
security needs. 
### Configuration Methods to Define Path Security
- `access(String)` Allows access if the given Spring Expression Language (SpEL)
expression evaluates to `true`.
- `anonymous()` Allows access to anonymous users. 
- `authenticated()` Allows access to authenticated users. 
- `denyAll()` Denies access unconditionally. 
- `fullyAuthenticated()` Allows access if the user is fully authenticated (not remembered).
- `hasAnyAuthority(String...)` Allows access if the user has any of the given authorities.
- `hasAnyRole(String...)` Allows access if the user has any of the given roles.
- `hasAuthority(String)` Allows access if the user has the given authority.
- `hasIpAddress(String)` Allows access if the request comes from the given IP address.
- `hasRole(String)` Allows access if the user has the given role.
- `not()` Negates the effect of any of the other access methods.
- `permitAll()` Allows access unconditionally.
- `rememberMe()` Allows access for users who are authenticated via `remember-me`.

### Custom Login Page
By default, Spring Security listens for login requests at /login and expects that
the username and password fields be named `username` and `password`. This is 
configurable, however. For example, the following configuration customizes the 
path and field names: 
```java
.formLogin(formLogin -> formLogin
        .loginPage("/login")
        .loginProcessingUrl("/authenticate")
        .usernameParameter("user")
        .passwordParameter("pwd"))
```
Here, we specify that Spring Security should listen for requests to /authenticate
to handle login submissions. Also, the username and password fields should now be 
named `user` and `pwd`.

By default, a successful login will take the user directly to the page that they
were navigating to when Spring Security determined that they needed to log in. If
the user were to directly navigate to the login page, a successful login would 
take them to the root path (for example, the home page). But we can change that 
by specifying a default success page, as shown below: 
```java
.formLogin(formLogin -> formLogin
        .loginPage("/login")
        .defaultSuccessUrl("/design))
```
As configured here, if the user were to successfully log in after directly going 
to the login page, they would be directed to the /design page. 

Optionally, we can force the user to the design page after login, even if they 
were navigating elsewhere prior to logging in, by passing `true` as a second 
parameter to `defaultSuccessUrl` as follows: 
```java
.formLogin(formLogin -> formLogin
        .loginPage("/login")
        .defaultSuccessUrl("/design", true))
```

### Enabling Third-party Authentication
To employ this type of authentication in our Spring application, we'll need to 
add the OAuth2 client starter to the build: 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```
Then at the very least, we'll need to configure details about one or more OAuth2
or OpenID Connect servers that we want to be able to authenticate against. Spring
Security supports sign-in with Facebook, Google, GitHub, and Okta out of the box,
but we can configure other clients by specifying a few extra properties. 
The general set of properties we'll need to set for our application to act as an
OAuth2/OpenID Connect client follows: 
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          <oauth2 or openid provider name>:
            clientId: <client id>
            clientSecret: <client secret>
            scope: <comma-separated list of requested scopes>
```
If, however, we've customized security by declaring a SecurityFilterChain bean,
then we'll need to enable OAuth2 login along with the rest of the security 
configuration as follows:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/design", "/orders").hasRole("USER")
                    .anyRequest().permitAll())
            .formLogin(form -> form.loginPage("/login"))
            .oauth2Login(oauth2 -> oauth2.loginPage("/login"))
            .build();
}
```
This will cause the application to always take the user to the application-provided
login page where they may choose to log in with their username and password as 
usual. But we can also provide a link on that same login page that offers them 
the opportunity to log in with Facebook/whatever. Such a link could look like 
this in the login page's HTML template: 
```html
<a th:href="/oauth2/authorization/facebook">Sign in with Facebook</a>
```
Just as important as logging in to an application is logging out. To enable 
logout, we simply need to call `logout` on the `HttpSecurity` object as follows: 
```java
.logout()
```
This sets up a security filter that inspects POST requests to /logout. Therefore,
to provide logout capability, we just need to add a logout form and button to the
views in our application, as shown: 
```html
<form method="POST" th:action="@{/logout}">
    <input type="submit" value="Logout">
</form>
```
When the user clicks the button, their session will be cleared, and they will 
be logged out of the application. By default, they'll be redirected to the login
page where they can log in again. But if we'd rather they be sent to a different
page, we can call `logoutSuccessUrl()` to specify a different post-logout 
landing page, as shown: 
```java
.logout()
    .logoutSuccessUrl("/")
```
In this case, users will be sent to the home page following logout. 

### Preventing CSRF
CSRF (Cross-Site Request Forgery) is a common security attack. 

Fortunately, Spring Security has built-in CSRF protection. It's enabled by default
and we don't need to explicitly configure it. We only need to make sure that any
forms our application submits include a field named `_csrf` that contains the 
CSRF token. 

Spring Security even makes that easy by placing the CSRF token in a request 
attribute with the name `_csrf`. Therefore, we could render the CSRF token in a 
hidden field with the following in a Thymeleaf template: 
```html
<input type="hidden" name="_csrf" th:value="${_csrf.token}">
```
If we're using Spring MVC's JSP tag library or Thymeleaf with the Spring Security
dialect, we needn't even bother explicitly including a hidden field. The hidden 
field will be rendered automatically for us. 

In Thymeleaf, we just need to make sure that one of the attributes of the `<form>`
element is prefixed as a Thymeleaf attribute. That's usually not a concern, because
it's quite common to let Thymeleaf render the path as context relative. For example
the `th:action` attribute shown next is all we need for Thymeleaf to render the 
hidden field for us: 
```html
<form method="POST" th:action="@{/login}" id="loginForm"></form>
```
It's possible to disable CSRF protection support. 
```java
.csrf(AbstractHttpConfigurer::disable)
```

### Applying Method Level Security
We can apply security directly on any method like this:

```java
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ADMIN')")
public void deleteAllOrders() {
    orderRepository.deleteAll();
}
```
The `@PreAuthorize` annotation takes a SpEL expression, and, if the expression 
evaluates to false, the method will not be invoked. In the event that 
`@PreAuthorize` blocks the call, then Spring Security's `AccessDeniedException`
will be thrown. For `@PreAuthorize` to work, we'll need to enable method
security. For that, we'll need to annotate the security configuration class with 
`@EnableMethodSecurity` as follows:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    // ...
}
```
The `@PostAuthorize` annotation works almost the same as the `@PreAuthorize` 
annotation, except that its expression won't be evaluated until after the target
method is invoked and returns. This allows the expression to consider the return 
value of the method in deciding whether to permit the method invocation. 
For example, if we want to restrict a method (getOrder) from being used expect by admins or 
by the user who the order belongs to, we can use the annotation like following:
```java
import org.springframework.security.access.prepost.PostAuthorize;

@PostAuthorize("hasRole('ADMIN') || " 
        + "returnObj.user.username == authentication.name")
public TacoOrder getOrder(long id) {
    // ...
}
```
### Chapter Summary 