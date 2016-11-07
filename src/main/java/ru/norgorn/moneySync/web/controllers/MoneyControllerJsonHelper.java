package ru.norgorn.moneySync.web.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import ru.norgorn.moneySync.persistence.entity.Money;
import ru.norgorn.moneySync.web.requestHandler.impl.PlayerMoneyDTO;

public class MoneyControllerJsonHelper {
	static final Pattern COUNTRY_PATTERN = Pattern.compile("country");
	static final Pattern MONEY_PATTERN = Pattern.compile("money");
	private static final Gson gson = new Gson();

	public static String dtoToJson(PlayerMoneyDTO dto) {
		return gson.toJson(dto);
	}

	public static PlayerMoneyDTO jsonToDTO(String body) throws IOException {		
		String country = null;
		Long money = null;

		try(JsonReader reader = new JsonReader(new StringReader(body))){
			reader.beginObject();
			while(reader.hasNext()){
				JsonToken currentToken = reader.peek();
				if(currentToken == JsonToken.END_DOCUMENT)
					break;

				if(currentToken == JsonToken.NAME){
					String currentTokenName = reader.nextName();
					if( isCountryToken(currentTokenName) ){
						if(country != null)
							throw new IOException("Same key 'country' repeats, previous value: "+country);
						country = reader.nextString();
					}
					else if( isMoneyToken(currentTokenName) ){
						if(money != null)
							throw new IOException("Same key 'money' repeats, previous value: "+money);
						money = reader.nextLong();
					}
					else
						reader.skipValue();
				}
			}
		}

		return new PlayerMoneyDTO(country, money);
	}
	
	public static String statsToJson(Map<String, List<Money>> moneyStats) {
		
		Map<String, List<PlayerMoneyDTO>> dtoMap = new HashMap<>();
		
		moneyStats.forEach( (country,moneys) -> {
			List<PlayerMoneyDTO> dtosForCountry = new ArrayList<>();
			moneys.forEach( money -> 
				dtosForCountry.add(
						new PlayerMoneyDTO(money.getCountry(), money.getMoney(), money.getUid())
					) 
			);
			dtoMap.put(country, dtosForCountry);
		});
		
		return gson.toJson(dtoMap);
	}

	private static boolean isMoneyToken(String currentTokenName) {
		return MONEY_PATTERN.matcher(currentTokenName).matches();
	}

	private static boolean isCountryToken(String currentTokenName) {
		return COUNTRY_PATTERN.matcher(currentTokenName).matches();
	}
}
