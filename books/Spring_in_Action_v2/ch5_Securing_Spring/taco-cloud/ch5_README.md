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
### Chapter Summary 