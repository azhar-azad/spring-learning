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
### Chapter Summary 