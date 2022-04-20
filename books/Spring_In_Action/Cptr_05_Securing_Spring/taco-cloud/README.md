# Securing Spring

## Have my hands on
- Autoconfiguring Spring Security
- Defining custom user storage
- Customizing the login page
- Securing against CSRF attacks
- Knowing the user

## Topics Covered
- **Enabling Spring Security**

By doing nothing more than adding the security starter to the project build, one can get the following security features:
- All HTTP request paths require authentication.
- No specific roles or authorities are required.
- Authentication is prompted with a simple login page.
- There’s only one user; the username is user.


- **Configuring authentication**

Password encoders provided by Spring Security:
- BCryptPasswordEncoder —Applies bcrypt strong hashing encryption
- NoOpPasswordEncoder —Applies no encoding
- Pbkdf2PasswordEncoder —Applies PBKDF2 encryption
- SCryptPasswordEncoder —Applies Scrypt hashing encryption
- StandardPasswordEncoder —Applies SHA-256 hashing encryption


- **Securing web requests**

The key things to note about the login page are the path it posts to and the names of the username and password fields. By default, Spring Security listens for login requests at /login and expects that the username and password fields be named username and password . This is configurable, however. For example, the following configuration customizes the path and field names:

```
.and()
	.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/authenticate")
		.usernameParameter("user")
		.passwordParameter("pwd")
```

Here, it is specified that Spring Security should listen for requests to /authenticate to handle login submissions. Also, the username and password fields should now be named user and pwd.

By default, a successful login will take the user directly to the page that they were navigating to when Spring Security determined that they needed to log in. If the user were to directly navigate to the login page, a successful login would take them to the root path (for example, the home page). But it can be changed by specifying a default success page, as shown next:

```
.and()
	.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/design")
```

As configured here, if the user were to successfully log in after directly going to the login page, they would be directed to the /design page.

Optionally, the user can be forced to the design page after login, even if they were navigating elsewhere prior to logging in, by passing true as a second parameter to defaultSuccessUrl as follows:

```
.and()
	.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/design", true)
```



- **Applying method-level security**


- **Knowing the user**

