package ru.norgorn.moneySync.service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.norgorn.moneySync.persistence.MoneyRepository;
import ru.norgorn.moneySync.persistence.entity.Money;

@Service
public class MoneySyncServiceImpl implements MoneySyncService {

	private final MoneyRepository repository;
	
	@Autowired
	public MoneySyncServiceImpl(MoneyRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly=true)
	public Money get(String uid){
		return repository.findOne(uid);
	}

	@Override
	@Transactional
	public void add(String uid, String country, long money) {
		repository.saveAndFlush(new Money(uid, country, money));
	}

	@Override
	public Map<String, List<Money>> getTopPerAllCountries(int top) {
		List<Money> flatStats = repository.getTopPlayersAllCountries(top);
		return flatStats.stream().collect(Collectors.groupingBy( m -> m.getCountry() ));
	}
}
