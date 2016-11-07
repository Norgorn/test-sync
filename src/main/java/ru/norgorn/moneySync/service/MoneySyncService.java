package ru.norgorn.moneySync.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import ru.norgorn.moneySync.persistence.entity.Money;

public interface MoneySyncService {

	Money get(String uid);

	void add(String uid, String country, long money);

	Map<String, List<Money>> getTopPerAllCountries(int top);

}