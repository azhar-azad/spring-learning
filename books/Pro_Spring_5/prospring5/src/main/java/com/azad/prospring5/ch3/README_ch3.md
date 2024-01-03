## Chapter 3: Introducing IoC and DI in Spring
### Inversion of Control and Dependency Injection
A component that requires certain dependencies is often referred to as the
dependent object or, in the case of IoC, the target. In general, IoC can 
be decomposed into two subtypes: dependency injection and dependency 
lookup. When we are talking about DI, we are always talking about IoC, 
but when we are talking about IoC, we are not always talking about DI 
(for example, dependency lookup is also a form of IoC). 

### Types of Inversion of Control
There are two types of IoC. IoC is more of a mixture of old and new 
ideas. _Dependency lookup_ is a much more traditional approach, and at 
first glance, it seems more familiar to Java programmers. _Dependency 
injection_, although it appears counterintuitive at first, is actually
much more flexible and usable than _dependency lookup_. With _dependency 
lookup_–style IoC, a component must acquire a reference to a dependency, 
whereas with _dependency injection_, the dependencies are injected into 
the component by the IoC container. _Dependency lookup_ comes in two types:
_dependency pull_ and _contextualized dependency lookup (CDL)_. _Dependency 
injection_ also has two common flavors: _constructor_ and _setter_ dependency 
injection.

#### Dependency Pull
In dependency pull, dependencies are pulled from a registry as required.
Spring also offers dependency pull as a mechanism for retrieving the 
components that the framework manages.
See the `DependencyPull` class. 

#### Contextualized Dependency Lookup
_Contextualized dependency lookup (CDL)_ is similar, in some 
respects, to _dependency pull_, but in CDL, lookup is performed 
against the container that is managing the resource, not from some 
central registry, and it is usually performed at some set point.
CDL works by having the component implement an interface.
See the `cdl` package.

#### Constructor Dependency Injection
_Constructor dependency injection_ occurs when a component’s dependencies
are provided to it in its constructor (or constructors). The component 
declares a constructor or a set of constructors, taking as arguments 
its dependencies, and the IoC container passes the dependencies to the
component when instantiation occurs. An obvious consequence of using 
constructor injection is that an object cannot be created without its
dependencies; thus, they are mandatory.
See the `ConstructorInjection` class. 

#### Setter Dependency Injection
In setter dependency injection, the IoC container injects a component’s
dependencies via JavaBean-style setter methods. A component’s setters 
expose the dependencies the IoC container can manage. An obvious 
consequence of using setter injection is that an object can be created
without its dependencies, and they can be provided later by calling 
the setter. 
See the `SetterInjection` class. 

#### Injection vs. Lookup
Which method should you use, injection or lookup? The answer is most 
definitely injection.
- Using injection has zero impact on your components’ code. The 
dependency pull code, on the other hand, must actively obtain a 
reference to the registry and interact with it to obtain the 
dependencies, and using CDL requires your classes to implement a 
specific interface and look up all dependencies manually.
- When you are using injection, the most your classes have to do is 
allow dependencies to be injected by using either constructors or setters.
- Using injection, you are free to use your classes completely decoupled 
from the IoC container that is supplying dependent objects with their 
collaborators manually, whereas with lookup, your classes are always 
dependent on the classes and interfaces defined by the container.
- Another drawback with lookup is that it becomes difficult to test 
your classes in isolation from the container. Using injection, testing 
your components is trivial because you can simply provide the 
dependencies yourself by using the appropriate constructor or setter.
- All of these reasons aside, the biggest reason to choose injection 
over lookup is that it makes your life easier. You write substantially 
less code when you are using injection, and the code that you do write 
is simple and can, in general, be automated by a good IDE.

#### Setter Injection vs. Constructor Injection
- Constructor injection is particularly useful when you absolutely
must have an instance of the dependency class before your component 
is used. 
- Constructor injection also helps achieve the use of immutable objects.
- Setter injection is useful in a variety of cases. If the component is 
exposing its dependencies to the container but is happy to provide its 
own defaults, setter injection is usually the best way to accomplish 
this.
- Another benefit of setter injection is that it allows dependencies 
to be declared on an interface, although this is not as useful as you 
might first think. See `Oracle` interface and `BookworkOracle` class.  