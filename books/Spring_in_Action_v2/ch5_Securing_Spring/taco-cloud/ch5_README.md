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
### Chapter Summary 