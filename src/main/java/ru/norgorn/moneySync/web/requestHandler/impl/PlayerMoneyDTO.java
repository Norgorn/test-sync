package ru.norgorn.moneySync.web.requestHandler.impl;

public class PlayerMoneyDTO {

	private String uid;
	
	private Long money;
	private String country;
	
	public PlayerMoneyDTO(String country, Long money){
		this(country, money, null);
	}
	
	public PlayerMoneyDTO(String country, Long money, String uid){
		this.uid = uid;
		this.country = country;
		this.money = money;
	}
	
	public Long getMoney() {
		return money;
	}

	public String getCountry() {
		return country;
	}

	public String getUid() {
		return uid;
	}
}
