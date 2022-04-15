/**
 * Autowire POJO Fields with the @Autowired Annotation
 * 
 * A service class to generate service objects is another real-world application best practice, which acts as
 * a façade to access DAOs—instead of accessing DAOs directly. Internally, the service object interacts with the
 * DAO to handle the sequence generation requests.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;
	
	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}
	
	public String generate(String sequenceId) {
		Sequence sequence = sequenceDao.getSequence(sequenceId);
		int value = sequenceDao.getNextValue(sequenceId);
		return sequence.getPrefix() + value + sequence.getSuffix();
	}
}
