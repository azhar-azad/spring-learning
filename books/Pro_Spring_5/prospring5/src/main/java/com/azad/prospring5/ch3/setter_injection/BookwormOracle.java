package com.azad.prospring5.ch3.setter_injection;

/***
 * The BookwormOracle class not only implements the Oracle interface but also defines the setter for dependency
 * injection. Spring is more comfortable dealing with structure like this.
 * In actuality, you should strive to keep setters used solely for
 */
public class BookwormOracle implements Oracle {
    private Encyclopedia encyclopedia;

    public void setEncyclopedia(Encyclopedia encyclopedia) {
        this.encyclopedia = encyclopedia;
    }

    @Override
    public String defineMeaningOfLife() {
        return "Encyclopedia is a waste of money - go see the world instead";
    }
}
