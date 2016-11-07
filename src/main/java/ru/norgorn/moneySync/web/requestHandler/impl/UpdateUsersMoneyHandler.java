package ru.norgorn.moneySync.web.requestHandler.impl;

import java.util.Map;
import java.util.Optional;

import ru.norgorn.moneySync.persistence.MoneyRepository;
import ru.norgorn.moneySync.persistence.entity.Money;
import ru.norgorn.moneySync.web.requestHandler.AbstractRequestHandler;
import ru.norgorn.moneySync.web.requestHandler.Answer;

public abstract class UpdateUsersMoneyHandler {
	
//	MoneyRepository repository;
//
//	public UpdateUsersMoneyHandler(MoneyRepository repository) {
//		super(PlayerMoneyDTO.class);
//	}
//
//	@Override
//	protected Answer processImpl(PlayerMoneyDTO value
//			, Map<String, String> queryParams
//			) {
//		
//		Optional<String> uid = extractUid(queryParams);
//		if( !uid.isPresent())
//			return new Answer(HTTP_BAD_REQUEST);
//		
//		Money money = new Money(uid.get(), value.getCountry(), value.getMoney());
//		repository.save(money);
//	}
//
//	private Optional<String> extractUid(Map<String, String> queryParams) {
//		String user = queryParams.get(":user");
//		return Optional.ofNullable(user);
//	}
}
