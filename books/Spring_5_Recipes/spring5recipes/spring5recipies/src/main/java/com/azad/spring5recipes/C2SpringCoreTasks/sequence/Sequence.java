/**
 * Create POJO Class with the @Component Annotation to Create Beans with DAO
 * 
 * We’ll use a Domain class and a Data Access Object (DAO) class to create POJOs. You still won’t need to set up a
 * database—you’ll actually hard-code values in the DAO class—but familiarizing yourself with this type of
 * application structure is important since it’s the basis for most real-world applications and future recipes.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

public class Sequence {

	private final String id;
	private final String prefix;
	private final String suffix;
	
	public Sequence(String id, String prefix, String suffix) {
		this.id = id;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getId() {
		return id;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}
	
	
}
