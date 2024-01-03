package com.azad.prospring5.ch3.setter_injection;

/***
 * Oracle is typical business interface with one business method defineMeaningOfLife(). If, in addition to this
 * method, you define a setter for injection such as setEncyclopedia(), you are mandating that all implementations
 * must use or at least be aware of the encyclopedia dependency. However, you don't need to define setEncyclopedia() in
 * the business interface. Instead, you can define the method in the classes implementing the business interface.
 * While programming in this way, all recent IoC containers, Spring included, can work with the component in terms of
 * the business interface but still provide the dependencies of the implementing class.
 * See the BookwormOracle class as an implementation.
 */
public interface Oracle {
    String defineMeaningOfLife();
}
