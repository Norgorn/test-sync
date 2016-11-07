package ru.norgorn.moneySync.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name="money",
		indexes={
			@Index( name="country_index", columnList="country")
			,@Index( name="money_index", columnList="money")
		}
	  )
public class Money {

	@Id
	private String uid;
	
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private long money;
	
	Money(){}
	
	public Money(String uid, String country, long money){
		this.uid = uid;
		this.country = country;
		this.money = money;
	}
	
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUid() {
		return uid;
	}
	
	@Override
	public String toString() {
		return country+" "+uid+" "+money;
	}
}
