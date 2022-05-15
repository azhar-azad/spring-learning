package com.azad.cineplex.constants;

public enum GenreType {
	ACTION("action"), ADVENTURE("adventure"), ANIMATION("animation"),
	BIOGRAPHY("biography"),
	COMEDY("comedy"), CRIME("crime"),
	DOCUMENTARY("documentary"), DRAMA("drama"),
	FAMILY("family"), FANTASY("fantasy"), 
	HISTORY("history"), HORROR("horror"),
	MUSIC("music"), MUSICAL("musical"), MYSTERY("mystery"),
	NEWS("news"), 
	REALITY("reality"), ROMANCE("romance"),
	SCI_FI("sci-fi"), SPORT("sport"),
	TALK_SHOW("talk-show"), THRILLER("thriller"),
	WAR("war"), WESTERN("western");
	
	private String value;
	
	private GenreType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
