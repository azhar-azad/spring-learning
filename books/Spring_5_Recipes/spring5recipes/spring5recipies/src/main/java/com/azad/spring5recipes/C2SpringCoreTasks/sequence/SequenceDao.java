/**
 * You create an interface for the DAO, which is responsible for accessing data from the database.
 * The getSequence() method loads a POJO or Sequence object from a database table by its ID, while the
 * getNextValue() method retrieves the next value of a particular database sequence.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

public interface SequenceDao {
	
	public Sequence getSequence(String sequenceId);
	public int getNextValue(String sequenceId);

}
