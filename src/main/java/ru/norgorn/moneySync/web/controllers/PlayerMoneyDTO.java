package ru.norgorn.moneySync.web.controllers;

public class PlayerMoneyDTO {
	
	private String uid;
	
	private Long money;
	private String country;
	
	public PlayerMoneyDTO(String country, Long money){
		this(country,money,null);
	}
	
	public PlayerMoneyDTO(String country, Long money, String uid){
		this.money = money;
		this.country = country;
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
